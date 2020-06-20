package com.emeryferrari.jse3d;
public class Math3D {
	private Math3D() {}
	public static final double sin(double x, int n) {
		double a = x;
		double ret = x;
		for (int i = 0; i < n; i++) {
			a *= -x*x/((2*i+1)*(2*i));
			ret += a;
		}
		return ret;
	}
	public static final double cos(double x, int n) {
		return sin(x+Math.PI/2, n);
	}
	public static final double tan(double x, int n) {
		return sin(x, n)/cos(x, n);
	}
}