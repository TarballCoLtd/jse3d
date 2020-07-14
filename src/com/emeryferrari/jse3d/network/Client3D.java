package com.emeryferrari.jse3d.network;
/** Used to establish a connection with a jse3dserver.
 * @author Alyx Ferrari
 */
public class Client3D {
	private String[] args;
	private boolean started;
	public Client3D(String ip, int port, String username) {
		args = new String[3];
		args[0] = ip;
		args[1] = ""+port;
		args[2] = username;
		started = false;
	}
	public Client3D(String ip, String username, int port) {
		this(ip, port, username);
	}
	public Client3D(int port, String ip, String username) {
		this(ip, port, username);
	}
	public boolean start() {
		if (!started) {
			started = JSE3DNetworkHandler.main(args);
			return started;
		}
		return true;
	}
	public void stop() {
		JSE3DNetworkHandler.CLASS_OBJ.stop();
	}
}