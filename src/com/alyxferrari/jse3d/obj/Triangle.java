package com.alyxferrari.jse3d.obj;
import java.awt.*;
import java.io.*;
/** Represents a collection of 3 points with a specified color.
 * @author Alyx Ferrari
 * @since 1.0 beta
 */
public class Triangle implements Serializable {
	private static final long serialVersionUID = 1L;
	static final Color DEFAULT = Color.GRAY;
	/** Object3D-specific point ID of the first point in this triangle.
	 */
	public int pointID1;
	/** Object3D-specific point ID of the first point in this triangle.
	 */
	public int pointID2;
	/** Object3D-specific point ID of the first point in this triangle.
	 */
	public int pointID3;
	public Color preLightmap = null;
	/** The desired color of this triangle.
	 */
	public Color color;
	/** Constructs a triangle from three points. Point IDs should be derived from the Object3D this triangle is contained in.
	 * @param pointID1 The first point ID.
	 * @param pointID2 The second point ID.
	 * @param pointID3 The third point ID.
	 */
	public Triangle(int pointID1, int pointID2, int pointID3) {
		this(pointID1, pointID2, pointID3, DEFAULT);
	}
	/** Constructs a Triangle from three points. Point IDs should be derived from the Object3D this triangle is contained in.
	 * @param pointID1 The first point ID.
	 * @param pointID2 The second point ID.
	 * @param pointID3 The third point ID.
	 * @param color The desired color of this Triangle.
	 */
	public Triangle(int pointID1, int pointID2, int pointID3, Color color) {
		this.pointID1 = pointID1;
		this.pointID2 = pointID2;
		this.pointID3 = pointID3;
		this.color = color;
	}
	/** Checks if this triangle contains a specified point ID. For results to be accurate the point ID must be of the same Object3D of this Triangle.
	 * @param pointID The point ID against which to check this Triangle.
	 * @return True if this Triangle contains the specified point ID.
	 */
	public boolean contains(int pointID) {
		if (pointID1 == pointID) {
			return true;
		} else if (pointID2 == pointID) {
			return true;
		} else if (pointID3 == pointID) {
			return true;
		}
		return false;
	}
	@Override
	/** Checks for equality between this Triangle and another object.
	 * @param object The object against which equality should be checked.
	 * @return True if the two objects are equal.
	 */
	public boolean equals(Object object) {
		if (object instanceof Triangle) {
			Triangle temp = (Triangle) object;
			if (pointID1 == temp.pointID1 && pointID2 == temp.pointID2 && pointID3 == temp.pointID3 && color.equals(temp.color)) {
				return true;
			}
		}
		return false;
	}
	public Vector3 cross(Object3D object) {
		Vector3 one = object.points[pointID1];
		Vector3 two = object.points[pointID2];
		Vector3 three = object.points[pointID3];
		Vector3 twoNew = new Vector3(two.x-one.x, two.y-one.y, two.z-one.z);
		Vector3 threeNew = new Vector3(three.x-one.x, three.y-one.y, three.z-one.z);
		return Vector3.cross(twoNew, threeNew).getNormal();
	}
}