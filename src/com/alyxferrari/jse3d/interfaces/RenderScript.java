package com.alyxferrari.jse3d.interfaces;
import java.awt.*;
/** Represents a script with methods that are called before and after drawing.
 * @author Alyx Ferrari
 * @since 3.0
 */
public interface RenderScript {
	/** This is called before 3D rendering.
	 * @param graphics Graphics of off-screen image.
	 * @since 3.0
	 */
	public void preRender(Graphics graphics);
	/** This is called after 3D rendering.
	 * @param graphics Graphics of off-screen image.
	 * @since 3.0
	 */
	public void postRender(Graphics graphics);
}