package com.emeryferrari.jse3d;
import java.awt.*;
public class Scene {
	Object3D object;
	double camDist;
	double viewAngle;
	private Scene(Object3D object, double camDist, double viewAngle) {
		this.object = object;
		this.camDist = camDist;
		this.viewAngle = viewAngle;
	}
	public static Scene getDemoInstance() {
		Point3D[] points = {new Point3D(1, 1, 1), new Point3D(1, 1, -1), new Point3D(1, -1, 1), new Point3D(1, -1, -1), new Point3D(-1, 1, 1), new Point3D(-1, 1, -1), new Point3D(-1, -1, 1), new Point3D(-1, -1, -1)};
		Line[] edges = {new Line(0, 1), new Line(2, 3), new Line(0, 2), new Line(1, 3), new Line(4, 5), new Line(6, 7), new Line(4, 6), new Line(5, 7), new Line(0, 4), new Line(1, 5), new Line(2, 6), new Line(3, 7)};
		Triangle[] triangles1 = {new Triangle(0, 1, 2, Color.BLUE), new Triangle(1, 2, 3, Color.BLUE)};
		Triangle[] triangles2 = {new Triangle(0, 2, 4, Color.RED), new Triangle(2, 4, 6, Color.RED)};
		Face[] faces = {new Face(triangles1), new Face(triangles2)};
		Object3D object = new Object3D(points, faces, edges);
		return new Scene(object, 5, 1);
	}
	public static Scene getInstance(Object3D object, double camDist) {
		return new Scene(object, camDist, 0.56);
	}
	public static Scene getInstance(Object3D object, double camDist, double viewAngle) {
		return new Scene(object, camDist, viewAngle);
	}
}