package com.alyxferrari.jse3d.network;
/** Used to establish a connection with a jse3dserver.
 * @author Alyx Ferrari
 */
public class Client3D {
	private String[] args;
	private boolean started;
	/** Constructs a Client3D which can be used to connect to a jse3dserver.
	 * @param ip The IPv4 address to which to connect.
	 * @param port The port at which to connect.
	 * @param username The username which should be used to connect.
	 */
	public Client3D(String ip, int port, String username) {
		args = new String[3];
		args[0] = ip;
		args[1] = ""+port;
		args[2] = username;
		started = false;
	}
	/** Constructs a Client3D which can be used to connect to a jse3dserver.
	 * @param ip The IPv4 address to which to connect.
	 * @param username The username which should be used to connect.
	 * @param port The port at which to connect.
	 */
	public Client3D(String ip, String username, int port) {
		this(ip, port, username);
	}
	/** Constructs a Client3D which can be used to connect to a jse3dserver.
	 * @param port The port at which to connect.
	 * @param ip The IPv4 address to which to connect.
	 * @param username The username which should be used to connect.
	 */
	public Client3D(int port, String ip, String username) {
		this(ip, port, username);
	}
	/** Attempts to establish a connection.
	 * @return True if a connection was successfully established.
	 */
	public boolean start() {
		if (!started) {
			started = JSE3DNetworkHandler.main(args);
			return started;
		}
		return true;
	}
	/** Halts connection to the server.
	 */
	public void stop() {
		JSE3DNetworkHandler.CLASS_OBJ.stop();
	}
}