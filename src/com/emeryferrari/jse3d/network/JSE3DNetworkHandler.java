package com.emeryferrari.jse3d.network;
import java.net.*;
import java.io.*;
import com.emeryferrari.jse3d.*;
import com.emeryferrari.jse3d.obj.*;
import com.emeryferrari.rsacodec.*;
import java.security.spec.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.security.*;
class JSE3DNetworkHandler {
	private static SecureRandom random;
	ArrayList<User> locations = new ArrayList<User>();
	Display display;
	InetAddress address;
	int port;
	String username;
	Socket socket;
	private boolean stop = false;
	static final JSE3DNetworkHandler CLASS_OBJ = new JSE3DNetworkHandler();
	static final String alphanumeral = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static boolean main(String[] args) {
		random = new SecureRandom();
		for (int i = 0; i < 1000; i++) {
			CLASS_OBJ.locations.add(null);
		}
		if (args.length == 3) {
			CLASS_OBJ.address = null;
			CLASS_OBJ.port = 5107;
			CLASS_OBJ.username = args[2];
			try {
				CLASS_OBJ.address = InetAddress.getByName(args[0]);
				CLASS_OBJ.port = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex) {
				System.out.println("Incorrectly formatted port. Defaulting to 5107...");
			} catch (UnknownHostException ex) {
				System.out.println("Incorrectly formatted IP address.");
				printUsage(2);
			}
			Thread clientStarter = new Thread(CLASS_OBJ.new ClientStarter());
			clientStarter.start();
			return true;
		} else {
			printUsage(4);
		}
		return false;
	}
	private class ClientStarter implements Runnable {
		public void run() {
			CLASS_OBJ.start();
		}
	}
	public void start() {
		if (address != null) {
			try {
				System.out.println("Connecting to server at " + address + ":" + port + "...");
				socket = new Socket(address, port);
				System.out.println("Connected!");
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				PublicKey pubKey = null;
				Object obj = ois.readObject();
				if (obj instanceof PublicKey) {
					pubKey = (PublicKey) obj;
				} else {
					System.out.println("Secure handshake failed. Disconnecting...");
					socket.close();
					System.exit(10);
				}
				System.out.println("got public key");
				byte[] randKey = new byte[64];
				for (int i = 0; i < randKey.length; i++) {
					randKey[i] = (byte) alphanumeral.charAt(random.nextInt(alphanumeral.length()));
				}
				oos.writeObject(RSACodec.encrypt(randKey, pubKey));
				KeySpec spec = new PBEKeySpec(new String(randKey, "UTF-8").toCharArray(), new byte[0], 1);
				SecretKeyFactory skf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
				SecretKey key = skf.generateSecret(spec);
				Cipher decrypt = Cipher.getInstance("PBEWithMD5AndDES");
				Cipher encrypt = Cipher.getInstance("PBEWithMD5AndDES");
				decrypt.init(Cipher.DECRYPT_MODE, key);
				encrypt.init(Cipher.ENCRYPT_MODE, key);
				CipherOutputStream cos = new CipherOutputStream(socket.getOutputStream(), encrypt);
				CipherInputStream cis = new CipherInputStream(socket.getInputStream(), decrypt);
				oos = new ObjectOutputStream(cos);
				ois = new ObjectInputStream(cis);
				oos.writeObject(username);
				oos.writeObject(JSE3DClientConst.API_VERSION);
				oos.flush();
				System.out.println("wrote username");
				Object object = ois.readObject();
				Scene scene = null;
				if (object instanceof Scene) {
					scene = (Scene) object;
				} else if (object instanceof Disconnect) {
					Disconnect ds = (Disconnect) object;
					if (ds.getType() == DisconnectType.USERNAME_TAKEN) {
						System.out.println("Specified username is taken by another user.\nDisconnecting...");
						socket.close();
						System.exit(5);
					} else if (ds.getType() == DisconnectType.USERNAME_INVALID){
						System.out.println("Username is invalid.\nDisconnecting...");
					}
					stop = true;
				} else {
					System.out.println("Invalid response from server.\nDisconnecting...");
					socket.close();
					stop = true;
				}
				Thread receiver = new Thread(new Receiver(socket, ois));
				receiver.setName("receiver");
				receiver.start();
				display = new Display(scene, "jse3dclient", true, true);
				display.startRender();
				JPanel buttons = new JPanel();
				buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
				JButton disconnect = new JButton("Disconnect");
				disconnect.addActionListener(new DisconnectListener(oos));
				buttons.add(disconnect);
				display.getFrame().getContentPane().add(BorderLayout.SOUTH, buttons);
			} catch (IOException ex) {
				handleException(ex);
				stop = true;
			} catch (ClassNotFoundException ex) {
				handleException(ex);
				stop = true;
			} catch (GeneralSecurityException ex) {
				handleException(ex);
				stop = true;
			}
		}
	}
	public class DisconnectListener implements ActionListener {
		private ObjectOutputStream oos;
		public DisconnectListener(ObjectOutputStream oos) {
			this.oos = oos;
		}
		public void actionPerformed(ActionEvent ev) {
			System.out.println("Disconnecting...");
			try {
				oos.writeObject(new Disconnect(DisconnectType.GENERAL_DISCONNECT));
				oos.flush();
				socket.close();
			} catch (IOException ex) {
				handleException(ex);
			}
			stop = true;
 		}
	}
	public class Receiver implements Runnable {
		private Socket socket;
		private ObjectInputStream ois;
		public Receiver(Socket socket, ObjectInputStream ois) {
			this.socket = socket;
			this.ois = ois;
		}
		public void run() {
			if (ois != null) {
				while (!stop) {
					try {
						Object object = ois.readObject();
						if (object instanceof Scene) {
							Scene tmp = (Scene) object;
							display.setScene(tmp);
						} else if (object instanceof Disconnect) {
							Disconnect ds = (Disconnect) object;
							if (ds.getType() == DisconnectType.USERNAME_INVALID) {
								System.out.println("Specified username is invalid.\nDisconnecting...");
								socket.close();
							} else if (ds.getType() == DisconnectType.USERNAME_TAKEN) {
								System.out.println("Specified username is taken by another user.\nDisconnecting...");
								socket.close();
							} else if (ds.getType() == DisconnectType.KICKED) {
								System.out.println("Kicked by server operator.\nDisconnecting...");
								socket.close();
							} else if (ds.getType() == DisconnectType.GENERAL_DISCONNECT) {
								System.out.println("Disconnected by server.");
								socket.close();
							}
							stop = true;
						} else if (object instanceof ArrayList<?>) {
							@SuppressWarnings("unchecked")
							ArrayList<User> tmp = (ArrayList<User>) object;
							locations = tmp;
						}
					} catch (IOException ex) {} catch (ClassNotFoundException ex) {
						handleException(ex);
						try {
							socket.close();
						} catch (IOException ex2) {}
					}
				}
			}
		}
	}
	public static void printUsage(int exitCode) {}
	public static void handleException(Exception ex) {
		ex.printStackTrace();
		/*
		System.out.println("  ** EXCEPTION THROWN **");
		System.out.println(ex.getClass() + " occurred in thread \"" + Thread.currentThread().getName() + "\":");
		System.out.println("Cause: " + ex.getMessage());
		System.out.println();
		*/
	}
	public void handleCommand(String command) {
		if (command.equalsIgnoreCase("stop")) {
			stop = true;
			System.out.println("Disconnecting...");
		} else {
			System.out.println("Unknown command.");
		}
	}
	public void stop() {
		stop = true;
	}
}