package com.emeryferrari.jse3d.gfx;
public class Math3D {
	private Math3D() {}
	public static final double hypot3(double x, double y, double z) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
}