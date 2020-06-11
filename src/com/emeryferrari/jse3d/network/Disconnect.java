package com.emeryferrari.jse3d.network;
import java.io.*;
class Disconnect implements Serializable {
	private static final long serialVersionUID = 1L;
	private DisconnectType type;
	public Disconnect(DisconnectType type) {
		this.type = type;
	}
	public DisconnectType getType() {
		return type;
	}
}