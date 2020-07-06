package com.emeryferrari.jse3d.gfx;
public class Time {
	private long fixedLast = System.nanoTime();
	private long last = System.nanoTime();
	public double deltaTime = 0.0166666667;
	public double fixedDeltaTime = 0.0166666667;
	Time() {}
	final void reset() {
		deltaTime = (double)(System.nanoTime()-last)/1000000000.0;
		last = System.nanoTime();
	}
	final void fixedReset() {
		fixedDeltaTime = (double)(System.nanoTime()-fixedLast)/1000000000.0;
		fixedLast = System.nanoTime();
	}
}