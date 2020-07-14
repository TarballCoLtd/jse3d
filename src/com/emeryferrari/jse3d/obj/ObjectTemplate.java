package com.emeryferrari.jse3d.obj;
import java.awt.Color;
/** Holds different Object3D templates.
 * @author Alyx Ferrari
 * @since 1.4.1
 */
public class ObjectTemplate {
	private ObjectTemplate() {}
	/** Returns a basic cube in the form of an Object3D.
	 * @return A basic cube in the form of an Object3D.
	 */
	public static Object3D getCube() {
		Vector3[] points = {new Vector3(1, 1, 1), new Vector3(1, 1, -1), new Vector3(1, -1, 1), new Vector3(1, -1, -1), new Vector3(-1, 1, 1), new Vector3(-1, 1, -1), new Vector3(-1, -1, 1), new Vector3(-1, -1, -1)};
		Line[] edges = {new Line(0, 1), new Line(2, 3), new Line(0, 2), new Line(1, 3), new Line(4, 5), new Line(6, 7), new Line(4, 6), new Line(5, 7), new Line(0, 4), new Line(1, 5), new Line(2, 6), new Line(3, 7)};
		Triangle[] triangles1 = {new Triangle(0, 1, 2, Color.BLUE), new Triangle(1, 2, 3, Color.BLUE)};
		Triangle[] triangles2 = {new Triangle(0, 2, 4, Color.RED), new Triangle(2, 4, 6, Color.RED)};
		Face[] faces = {new Face(triangles1), new Face(triangles2)};
		return new Object3D(points, faces, edges);
	}
	/** Returns a basic square pyramid in the form of an Object3D.
	 * @return A basic square pyramid in the form of an Object3D.
	 */
	public static Object3D getSquarePyramid() {
		Vector3[] points = {new Vector3(-1, -1, -1), new Vector3(1, -1, -1), new Vector3(1, -1, 1), new Vector3(-1, -1, 1), new Vector3(0, 1, 0)};
		Line[] edges = {new Line(0, 1), new Line(0, 3), new Line(2, 3), new Line(1, 2), new Line(0, 4), new Line(1, 4), new Line(2, 4), new Line(3, 4)};
		Triangle[] triangles1 = {new Triangle(0, 1, 2, Color.GREEN), new Triangle(0, 2, 3, Color.GREEN)};
		Triangle[] triangles2 = {new Triangle(0, 1, 4, Color.RED)};
		Face[] faces = {new Face(triangles1), new Face(triangles2)};
		return new Object3D(points, faces, edges);
	}
}