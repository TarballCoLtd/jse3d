package com.emeryferrari.jse3d;
public class Math3D {
	private Math3D() {}
	public static final double sin(double x, int n) {
		double a = x;
		double pi2 = Math.PI*2;
		if (a > pi2) {
			a -= pi2;
			while (a > pi2) {
				a -= pi2;
			}
		} else if (a < -pi2) {
			a += pi2;
			while (a < -pi2) {
				a += pi2;
			}
		}
		double ret = a;
		for (int i = 1; i < n; i++) {
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
	public static final double hypot3(double x, double y, double z) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
}