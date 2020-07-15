package com.emeryferrari.jse3d.gfx;
/** Includes methods to get delta time and fixed delta time for timing physics and other operations.
 * @author Alyx Ferrari
 */
public class Time {
	private long fixedLast = System.nanoTime();
	private long last = System.nanoTime();
	/** The time between the last frame and the frame before. Used for timing.
	 */
	public double deltaTime = 0.0166666667;
	/** The time between the last physics update and the update before. Used for timing physics operations.
	 */
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