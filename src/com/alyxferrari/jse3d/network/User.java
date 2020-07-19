package com.alyxferrari.jse3d.network;
import com.alyxferrari.jse3d.obj.*;
import java.io.*;
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private Vector3 location;
	public User(String username, Vector3 location) {
		this.username = username;
		this.location = location;
	}
	public String getUsername() {
		return username;
	}
	public Vector3 getLocation() {
		return location;
	}
}