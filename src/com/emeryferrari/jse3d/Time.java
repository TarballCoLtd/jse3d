package com.emeryferrari.jse3d;
public class Time {
	private Time() {}
	private static long fixedLast = System.nanoTime();
	private static long last = System.nanoTime();
	public static double deltaTime = 0.0166666667;
	public static double fixedDeltaTime = 0.0166666667;
	static final void reset() {
		deltaTime = (double)(System.nanoTime()-last)/1000000000.0;
		last = System.nanoTime();
	}
	static final void fixedReset() {
		fixedDeltaTime = (double)(System.nanoTime()-fixedLast)/1000000000.0;
		fixedLast = System.nanoTime();
	}
}