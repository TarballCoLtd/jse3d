package com.emeryferrari.jse3d.interfaces;
/** Represents a script for a scriptable object.
 * @author Alyx Ferrari
 * @since 2.5.2
 */
public interface Scriptable {
	/** This method is called once at the beginning of Scene rendering.
	 */
	public void start();
	/** Called once every frame.
	 */
	public void update();
	/** Called once every physics update.
	 */
	public void fixedUpdate();
}