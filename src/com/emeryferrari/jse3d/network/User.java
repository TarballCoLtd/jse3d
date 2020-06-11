package com.emeryferrari.jse3d.network;
import com.emeryferrari.jse3d.*;
import java.io.*;
class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private Point3D location;
	public User(String username, Point3D location) {
		this.username = username;
		this.location = location;
	}
	public String getUsername() {
		return username;
	}
	public Point3D getLocation() {
		return location;
	}
}