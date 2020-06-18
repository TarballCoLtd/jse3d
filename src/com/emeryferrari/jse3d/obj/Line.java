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
}