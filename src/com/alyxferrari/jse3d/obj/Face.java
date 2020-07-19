package com.alyxferrari.jse3d.obj;
import java.util.*;
import java.io.*;
/** Represents a collection of Triangles that form a face of an Object3D.
 * @author Alyx Ferrari
 * @since 1.0 beta
 */
public class Face implements Serializable, Comparable<Face> {
	// Note: this class has a natural ordering that is inconsistent with equals.
	// The above disclaimer is recommended by the Java documentation based on the contents of this class.
	private static final long serialVersionUID = 1L;
	/** Triangles contained in this Face.
	 */
	public Triangle[] triangles;
	/** Distance from this Face to the camera, updated every frame. Used internally by the face renderer.
	 */
	public double camDist;
	/** Constructs a Face with the specified Triangles.
	 * @param triangles Triangles with which to construct this Face.
	 */
	public Face(Triangle[] triangles) {
		this.triangles = triangles;
		camDist = 0;
	}
	/** Checks whether this Face contains the specified point ID.
	 * @param pointID The point ID to check this Face for.
	 * @return Whether this Face contains the specified point ID.
	 */
	public boolean contains(int pointID) {
		for (int i = 0; i < triangles.length; i++) {
			if (triangles[i].contains(pointID)) {
				return true;
			}
		}
		return false;
	}
	/** Returns an array of all point IDs in this Face.
	 * @return An arrays of all point IDs in this Face.
	 */
	public int[] getPointIDs() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		for (int i = 0; i < triangles.length; i++) {
			if (!array.contains(triangles[i].pointID1)) {
				array.add(triangles[i].pointID1);
			}
			if (!array.contains(triangles[i].pointID2)) {
				array.add(triangles[i].pointID2);
			}
			if (!array.contains(triangles[i].pointID3)) {
				array.add(triangles[i].pointID3);
			}
		}
		int[] ret = new int[array.size()];
		for (int i = 0; i < array.size(); i++) {
			ret[i] = array.get(i);
		}
		return ret;
	}
	@Override
	@Deprecated
	/** Compares this Face's camDist to another's. Do not use this method.
	 * @param face The Face to which to compare this Face.
	 * @return A value in the range [-1,1] depending on this Face's camDist in relation to the specifies Face's.
	 */
	public int compareTo(Face face) {
		if (camDist > face.camDist) {
			return 1;
		} else if (camDist == face.camDist) {
			return 0;
		}
		return -1;
	}
	@Override
	/** Compares the equality of this Face and another object.
	 * @param object The object to which to compare this Face.
	 * @return True if the two objects are equal.
	 */
	public boolean equals(Object object) {
		if (object instanceof Face) {
			Face temp = (Face) object;
			if (triangles.length == temp.triangles.length) {
				for (int i = 0; i < triangles.length; i++) {
					if (!(triangles[i].equals(temp.triangles[i]))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}