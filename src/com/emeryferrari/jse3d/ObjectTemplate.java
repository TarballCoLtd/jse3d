package com.emeryferrari.jse3d;

import java.awt.Color;

public class ObjectTemplate {
	private ObjectTemplate() {}
	public static Object3D getCube() {
		Point3D[] points = {new Point3D(1, 1, 1), new Point3D(1, 1, -1), new Point3D(1, -1, 1), new Point3D(1, -1, -1), new Point3D(-1, 1, 1), new Point3D(-1, 1, -1), new Point3D(-1, -1, 1), new Point3D(-1, -1, -1)};
		Line[] edges = {new Line(0, 1), new Line(2, 3), new Line(0, 2), new Line(1, 3), new Line(4, 5), new Line(6, 7), new Line(4, 6), new Line(5, 7), new Line(0, 4), new Line(1, 5), new Line(2, 6), new Line(3, 7)};
		Triangle[] triangles1 = {new Triangle(0, 1, 2, Color.BLUE), new Triangle(1, 2, 3, Color.BLUE)};
		Triangle[] triangles2 = {new Triangle(0, 2, 4, Color.RED), new Triangle(2, 4, 6, Color.RED)};
		Face[] faces = {new Face(triangles1), new Face(triangles2)};
		return new Object3D(points, faces, edges);
	}
	public static Object3D getTriangle() {
		Point3D[] points = {new Point3D(0, 0, 0), new Point3D(2, 0, 0), new Point3D(1, 1, 0)};
		Line[] edges = {new Line(0, 1), new Line(1, 2), new Line(0, 2)};
		Triangle[] triangle = {new Triangle(0, 1, 2, Color.RED)};
		Face[] face = {new Face(triangle)};
		return new Object3D(points, face, edges);
	}
	public static Object3D getSquarePyramid() {
		Point3D[] points = {new Point3D(-1, -1, -1), new Point3D(1, -1, -1), new Point3D(1, -1, 1), new Point3D(-1, -1, 1), new Point3D(0, 1, 0)};
		Line[] edges = {new Line(0, 1), new Line(0, 3), new Line(2, 3), new Line(1, 2), new Line(0, 4), new Line(1, 4), new Line(2, 4), new Line(3, 4)};
		return new Object3D(points, edges);
	}
}