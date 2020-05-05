package com.emeryferrari.jse3d;
import java.awt.*;
public class Triangle {
	public int pointID1;
	public int pointID2;
	public int pointID3;
	public Color color;
	public Triangle(int pointID1, int pointID2, int pointID3) {
		this(pointID1, pointID2, pointID3, Color.GRAY);
	}
	public Triangle(int pointID1, int pointID2, int pointID3, Color color) {
		this.pointID1 = pointID1;
		this.pointID2 = pointID2;
		this.pointID3 = pointID3;
		this.color = color;
	}
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
}