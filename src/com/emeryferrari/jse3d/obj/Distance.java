package com.emeryferrari.jse3d.obj;
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