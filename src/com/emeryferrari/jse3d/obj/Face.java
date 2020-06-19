package com.emeryferrari.jse3d.obj;
import java.util.*;
import java.io.*;
public class Face implements Serializable, Comparable<Face> {
	// Note: this class has a natural ordering that is inconsistent with equals.
	private static final long serialVersionUID = 1L;
	public Triangle[] triangles;
	public double camDist;
	public Face(Triangle[] triangles) {
		this.triangles = triangles;
		camDist = 0;
	}
	public boolean contains(int pointID) {
		for (int i = 0; i < triangles.length; i++) {
			if (triangles[i].contains(pointID)) {
				return true;
			}
		}
		return false;
	}
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
	public int compareTo(Face face) {
		if (camDist > face.camDist) {
			return 1;
		} else if (camDist == face.camDist) {
			return 0;
		}
		return -1;
	}
}