package com.emeryferrari.jse3d.obj;
import java.awt.*;
import java.io.*;
public class Triangle implements Serializable {
	private static final long serialVersionUID = 1L;
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
	@Override
	public boolean equals(Object object) {
		if (object instanceof Triangle) {
			Triangle temp = (Triangle) object;
			if (pointID1 == temp.pointID1 && pointID2 == temp.pointID2 && pointID3 == temp.pointID3 && color.equals(temp.color)) {
				return true;
			}
		}
		return false;
	}
}