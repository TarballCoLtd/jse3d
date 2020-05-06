package com.emeryferrari.jse3d;
public class Object3D {
	public Point3D[] points;
	public Line[] edges = {};
	public Face[] faces = {};
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
	public void movePosRel(double xDiff, double yDiff, double zDiff) {
		for (int i = 0; i < points.length; i++) {
			points[i].movePosRel(xDiff, yDiff, zDiff);
		}
	}
	public void transitionPosRel(double xDiff, double yDiff, double zDiff, int millis) {
		for (int i = 0; i < points.length; i++) {
			points[i].transitionPosRel(xDiff, yDiff, zDiff, millis);
		}
	}
}