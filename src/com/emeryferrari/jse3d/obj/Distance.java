package com.emeryferrari.jse3d.obj;
/** Represents the distance between a point and the camera. Do not instantiate this class.
 * @author Emery Ferrari
 * @since 1.0 beta
 */
public class Distance {
	public double distance;
	public int pointID;
	public Distance(double distance, int pointID) {
		this.distance = distance;
		this.pointID = pointID;
	}
	@Override
	public boolean equals(Object object) {
		if (object instanceof Distance) {
			Distance temp = (Distance) object;
			if (distance == temp.distance && pointID == temp.pointID) {
				return true;
			}
		}
		return false;
	}
}