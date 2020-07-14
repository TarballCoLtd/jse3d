package com.emeryferrari.jse3d.obj;
import java.io.*;
/** Represents a 2D line between two drawn 3D points.
 * @author Alyx Ferrari
 * @since 1.0 beta
 */
public class Line implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Object3D-specific point ID of the first point of this Line.
	 */
	public int pointID1;
	/** Object3D-specific point ID of the second point of this Line.
	 */
	public int pointID2;
	/** Constructs a Line between two points.
	 * @param pointID1 The first point's ID.
	 * @param pointID2 The second point's ID.
	 */
	public Line(int pointID1, int pointID2) {
		this.pointID1 = pointID1;
		this.pointID2 = pointID2;
	}
	@Override
	/** Compares the equality of this Line and another object.
	 * @param object The object to which to compare this Line.
	 * @return True if the two objects are equal.
	 */
	public boolean equals(Object object) {
		if (object instanceof Line) {
			Line temp = (Line) object;
			if (pointID1 == temp.pointID1 && pointID2 == temp.pointID2) {
				return true;
			}
		}
		return false;
	}
}