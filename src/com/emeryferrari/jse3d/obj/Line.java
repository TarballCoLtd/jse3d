package com.emeryferrari.jse3d.obj;
import java.io.*;
public class Line implements Serializable {
	private static final long serialVersionUID = 1L;
	public int pointID1;
	public int pointID2;
	public Line(int pointID1, int pointID2) {
		this.pointID1 = pointID1;
		this.pointID2 = pointID2;
	}
	@Override
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