package com.alyxferrari.jse3d.gfx;
import java.awt.*;
/** A triangle type used strictly internally as a return type for triangles that have a lightmap generated.
 * @author Alyx Ferrari
 * @since 3.3
 */
public class FinalizedTriangle {
	public final int[] xs;
	public final int[] ys;
	public final Color color;
	FinalizedTriangle(int[] xs, int[] ys, Color color) {
		this.xs = xs;
		this.ys = ys;
		this.color = color;
	}
}