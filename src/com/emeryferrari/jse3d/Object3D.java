package com.emeryferrari.jse3d;
import java.io.*;
public class Object3D implements Serializable {
	private static final long serialVersionUID = 1L;
	public Point3D[] points;
	public Line[] edges = {};
	public Face[] faces = {};
	double camDist = 0;
	public Object3D(Point3D[] points, Face[] faces, Line[] edges) {
		this(points, faces);
		this.edges = edges;
	}
	public Object3D(Point3D[] points, Line[] edges, Face[] faces) {
		this(points, faces, edges);
	}
	public Object3D(Point3D[] points, Face[] faces) {
		this(points);
		this.faces = faces;
	}
	public Object3D(Point3D[] points, Line[] edges) {
		this.points = points;
		this.edges = edges;
	}
	public Object3D(Point3D[] points) {
		this.points = points;
	}
	public void movePosRel(Point3D diff, Display display) {
		movePosRel(diff.x, diff.y, diff.z, display);
	}
	public void movePosRel(Point3D diff, Point3D camPos) {
		movePosRel(diff.x, diff.y, diff.z, camPos);
	}
	public void movePosRel(double xDiff, double yDiff, double zDiff, Display display) {
		movePosRel(xDiff, yDiff, zDiff, display.getCameraPosition());
	}
	public void transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Display display) {
		transitionPosRel(xDiff, yDiff, zDiff, millis, display.getCameraPosition());
	}
	public void movePosRel(double xDiff, double yDiff, double zDiff, Point3D camPos) {
		for (int i = 0; i < points.length; i++) {
			points[i].movePosRel(xDiff, yDiff, zDiff, camPos);
		}
	}
	public void transitionPosRel(Point3D diff, int millis, Point3D camPos) {
		transitionPosRel(diff.x, diff.y, diff.z, millis, camPos);
	}
	public void transitionPosRel(Point3D diff, Point3D camPos, int millis) {
		transitionPosRel(diff, millis, camPos);
	}
	public void transitionPosRel(Point3D diff, int millis, Display display) {
		transitionPosRel(diff, millis, display.getCameraPosition());
	}
	public void transitionPosRel(Point3D diff, Display display, int millis) {
		transitionPosRel(diff, millis, display);
	}
	public void transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Point3D camPos) {
		for (int i = 0; i < points.length; i++) {
			points[i].transitionPosRel(xDiff, yDiff, zDiff, millis, camPos);
		}
	}
	public int getPointID(Point3D point) {
		for (int i = 0; i < points.length; i++) {
			if (points[i].x == point.x && points[i].y == point.y && points[i].z == point.z) {
				return i;
			}
		}
		return -1;
	}
}