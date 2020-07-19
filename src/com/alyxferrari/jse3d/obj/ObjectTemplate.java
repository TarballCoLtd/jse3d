package com.alyxferrari.jse3d.obj;
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
		Edge[] edges = {new Edge(0, 1), new Edge(2, 3), new Edge(0, 2), new Edge(1, 3), new Edge(4, 5), new Edge(6, 7), new Edge(4, 6), new Edge(5, 7), new Edge(0, 4), new Edge(1, 5), new Edge(2, 6), new Edge(3, 7)};
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
		Edge[] edges = {new Edge(0, 1), new Edge(0, 3), new Edge(2, 3), new Edge(1, 2), new Edge(0, 4), new Edge(1, 4), new Edge(2, 4), new Edge(3, 4)};
		Triangle[] triangles1 = {new Triangle(0, 1, 2, Color.GREEN), new Triangle(0, 2, 3, Color.GREEN)};
		Triangle[] triangles2 = {new Triangle(0, 1, 4, Color.RED)};
		Face[] faces = {new Face(triangles1), new Face(triangles2)};
		return new Object3D(points, faces, edges);
	}
}