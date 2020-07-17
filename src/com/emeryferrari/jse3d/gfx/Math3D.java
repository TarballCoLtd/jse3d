package com.emeryferrari.jse3d.gfx;
public class Math3D {
	private Math3D() {}
	/** Equivalent to Math.hypot(), but with three arguments.
	 * @param x X value.
	 * @param y Y value.
	 * @param z Z value.
	 * @return The square root of x^2 plus y^2 plus z^2.
	 */
	public static final double hypot3(double x, double y, double z) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	/** Equivalent to Math3D.hypot3(i,i,i).
	 * @param i Value to use.
	 * @return The square root of 3*(i^2).
	 */
	public static final double hypot3(double i) {
		return hypot3(i, i, i);
	}
}