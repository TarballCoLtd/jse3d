package com.alyxferrari.jse3d.obj;
import java.io.*;
import java.awt.*;
import com.alyxferrari.jse3d.gfx.*;
import com.alyxferrari.jse3d.interfaces.*;
import com.mokiat.data.front.parser.*;
import java.util.*;
/** Represents a collection of Vector3s.
 * @author Alyx Ferrari
 * @since 1.0 beta
 */
public class Object3D implements Serializable {
	private static final long serialVersionUID = 1L;
	protected boolean started = false;
	/** The points contained in this Object3D.
	 */
	public Vector3[] points;
	/** The Lines contained in this Object3D.
	 */
	public Edge[] edges = {};
	/** The Faces contained in this Object3D.
	 */
	public Face[] faces = {};
	/** The distance from this Object3D to the camera. Do not set this value.
	 */
	public double camDist = 0;
	/** This Object3D's script.
	 */
	public Script updatable = new Script() {
		@Override
		public void start() {}
		@Override
		public void update() {}
		@Override
		public void fixedUpdate() {}
		@Override
		public void stop() {}
	};
	/** Constructs an Object3D with the specified points, Faces, and Lines.
	 * @param points An array of points in the form of Vector3s.
	 * @param faces An array of Faces to be rendered.
	 * @param edges An array of Lines to be rendered.
	 */
	public Object3D(Vector3[] points, Face[] faces, Edge[] edges) {
		this(points, faces);
		this.edges = edges;
	}
	/** Constructs an Object3D with the specified points, Faces, and Lines.
	 * @param points An array of points in the form of Vector3s.
	 * @param edges An array of Lines to be rendered.
	 * @param faces An array of Faces to be rendered.
	 */
	public Object3D(Vector3[] points, Edge[] edges, Face[] faces) {
		this(points, faces, edges);
	}
	/** Constructs an Object3D with the specified points and Faces.
	 * @param points An array of points in the form of Vector3s.
	 * @param faces An array of Faces to be rendered.
	 */
	public Object3D(Vector3[] points, Face[] faces) {
		this(points);
		this.faces = faces;
	}
	/** Constructs an Object3D with the specified points and Lines.
	 * @param points An array of points in the form of Vector3s.
	 * @param edges An array of Lines to be rendered.
	 */
	public Object3D(Vector3[] points, Edge[] edges) {
		this(points);
		this.edges = edges;
	}
	/** Constructs an Object3D with the specified points.
	 * @param points An array of points in the form of Vector3s.
	 */
	public Object3D(Vector3[] points) {
		this.points = points;
	}
	/** Moves this Object3D and its points relative to its current position.
	 * @param diff Relative point to which this Object3D should be moved.
	 * @param display Display object being used to render this Object3D.
	 * @return The Object3D on which this method was called.
	 */
	public Object3D movePosRel(Vector3 diff, Display display) {
		movePosRel(diff.x, diff.y, diff.z, display);
		return this;
	}
	/** Moves this Object3D and its points relative to its current position.
	 * @param diff Relative point to which this Object3D should be moved.
	 * @param camPos Position of the camera (result of Display.getCameraPosition(), not Display.getCameraPositionActual()).
	 * @return The Object3D on which this method was called.
	 */
	public Object3D movePosRel(Vector3 diff, Vector3 camPos) {
		movePosRel(diff.x, diff.y, diff.z, camPos);
		return this;
	}
	/** Moves this Object3D and its points relative to its current position.
	 * @param xDiff Relative X coordinate to which this Object3D should be moved.
	 * @param yDiff Relative Y coordinate to which this Object3D should be moved.
	 * @param zDiff Relative Z coordinate to which this Object3D should be moved.
	 * @param display Display object being used to render this Object3D.
	 * @return The Object3D on which this method was called.
	 */
	public Object3D movePosRel(double xDiff, double yDiff, double zDiff, Display display) {
		movePosRel(xDiff, yDiff, zDiff, display.getCameraPosition());
		return this;
	}
	/** Transitions this Object3D and its points relative to its current position over a specified number of milliseconds.
	 * @param xDiff Relative X coordinate to which this Object3D should be moved.
	 * @param yDiff Relative Y coordinate to which this Object3D should be moved.
	 * @param zDiff Relative Z coordinate to which this Object3D should be moved.
	 * @param millis Milliseconds it should take to transition this Object3D.
	 * @param display Display object being used to render this Object3D.
	 * @return The Object3D on which this method was called.
	 */
	public Object3D transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Display display) {
		transitionPosRel(xDiff, yDiff, zDiff, millis, display.getCameraPosition(), display.getPhysicsTimestep());
		return this;
	}
	/** Moves this Object3D and its points relative to its current position.
	 * @param xDiff Relative X coordinate to which this Object3D should be moved.
	 * @param yDiff Relative Y coordinate to which this Object3D should be moved.
	 * @param zDiff Relative Z coordinate to which this Object3D should be moved.
	 * @param camPos Position of the camera (result of Display.getCameraPosition(), not Display.getCameraPositionActual()).
	 * @return The Object3D on which this method was called.
	 */
	public Object3D movePosRel(double xDiff, double yDiff, double zDiff, Vector3 camPos) {
		for (int i = 0; i < points.length; i++) {
			points[i].movePosRel(xDiff, yDiff, zDiff, camPos);
		}
		return this;
	}
	/** Transitions this Object3D and its points relative to its current position over a specified number of milliseconds.
	 * @param diff Relative point to which this Object3D should be moved.
	 * @param millis Milliseconds it should take to transition this Object3D.
	 * @param camPos Position of the camera (result of Display.getCameraPosition(), not Display.getCameraPositionActual()).
	 * @param physicsTimestep The physics timestep to use to transition this Object3D (if the global physics timestep is undesired).
	 * @return The Object3D on which this method was called.
	 */
	public Object3D transitionPosRel(Vector3 diff, int millis, Vector3 camPos, int physicsTimestep) {
		transitionPosRel(diff.x, diff.y, diff.z, millis, camPos, physicsTimestep);
		return this;
	}
	/** Transitions this Object3D and its points relative to its current position over a specified number of milliseconds.
	 * @param diff Relative point to which this Object3D should be moved.
	 * @param camPos Position of the camera (result of Display.getCameraPosition(), not Display.getCameraPositionActual()).
	 * @param millis Milliseconds it should take to transition this Object3D.
	 * @param physicsTimestep The physics timestep to use to transition this Object3D (if the global physics timestep is undesired).
	 * @return The Object3D on which this method was called.
	 */
	public Object3D transitionPosRel(Vector3 diff, Vector3 camPos, int millis, int physicsTimestep) {
		transitionPosRel(diff, millis, camPos, physicsTimestep);
		return this;
	}
	/** Transitions this Object3D and its points relative to its current position over a specified number of milliseconds.
	 * @param diff Relative point to which this Object3D should be moved.
	 * @param millis Milliseconds it should take to transition this Object3D.
	 * @param display Display object being used to render this Object3D.
	 * @return The Object3D on which this method was called.
	 */
	public Object3D transitionPosRel(Vector3 diff, int millis, Display display) {
		transitionPosRel(diff, millis, display.getCameraPosition(), display.getPhysicsTimestep());
		return this;
	}
	/** Transitions this Object3D and its points relative to its current position over a specified number of milliseconds.
	 * @param diff Relative point to which this Object3D should be moved.
	 * @param display Display object being used to render this Object3D.
	 * @param millis Milliseconds it should take to transition this Object3D.
	 * @return The Object3D on which this method was called.
	 */
	public Object3D transitionPosRel(Vector3 diff, Display display, int millis) {
		transitionPosRel(diff, millis, display);
		return this;
	}
	/** Transitions this Object3D and its points relative to its current position over a specified number of milliseconds.
	 * @param xDiff Relative X coordinate to which this Object3D should be moved.
	 * @param yDiff Relative Y coordinate to which this Object3D should be moved.
	 * @param zDiff Relative Z coordinate to which this Object3D should be moved.
	 * @param millis Milliseconds it should take to transition this Object3D.
	 * @param camPos Position of the camera (result of Display.getCameraPosition(), not Display.getCameraPositionActual()).
	 * @param physicsTimestep The physics timestep to use to transition this Object3D (if the global physics timestep is undesired).
	 * @return The Object3D on which this method was called.
	 */
	public Object3D transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Vector3 camPos, int physicsTimestep) {
		for (int i = 0; i < points.length; i++) {
			points[i].transitionPosRel(xDiff, yDiff, zDiff, millis, camPos, physicsTimestep);
		}
		return this;
	}
	/** Scales this Object3D and its points with a specified point as the center.
	 * @param multiplierX X multiplier.
	 * @param multiplierY Y multiplier.
	 * @param multiplierZ Z multiplier.
	 * @param around Point around which to scale this Object3D.
	 * @param display Display object being used to render this Object3D.
	 * @return The Object3D on which this method was called.
	 */
	public Object3D scale(double multiplierX, double multiplierY, double multiplierZ, Vector3 around, Display display) {
		scale(multiplierX, multiplierY, multiplierZ, around, display.getCameraPosition());
		return this;
	}
	/** Scales this Object3D and its points with a specified point as the center.
	 * @param multiplierX X multiplier.
	 * @param multiplierY Y multiplier.
	 * @param multiplierZ Z multiplier.
	 * @param around Point around which to scale this Object3D.
	 * @param camPos Position of the camera (result of Display.getCameraPosition(), not Display.getCameraPositionActual()).
	 * @return The Object3D on which this method was called.
	 */
	public Object3D scale(double multiplierX, double multiplierY, double multiplierZ, Vector3 around, Vector3 camPos) {
		for (int i = 0; i < points.length; i++) {
			points[i].scale(multiplierX, multiplierY, multiplierZ, around, camPos);
		}
		return this;
	}
	/** Sets this Object3D's script.
	 * @param updatable This Object3D's new script.
	 * @return The Object3D on which this method was called.
	 */
	public Object3D setScript(Script updatable) {
		this.updatable = updatable;
		return this;
	}
	/** Calls the start() method of this Object3D's script. Do not call this method unless you know what you're doing.
	 */
	public void start() {
		if (!started) {
			updatable.start();
		}
		started = true;
	}
	/** Calls the update() method of this Object3D's script. Do not call this method unless you know what you're doing.
	 */
	public void update() {
		updatable.update();
	}
	/** Calls the fixedUpdate() method of this Object3D's script. Do not call this method unless you know what you're doing.
	 */
	public void fixedUpdate() {
		updatable.fixedUpdate();
	}
	/** Calls the stop() method of this Object3D's script. Do not call this method unless you know what you're doing.
	 * @since 2.6.2
	 */
	public void stop() {
		updatable.stop();
	}
	@Override
	/** Checks for equality between this Object3D and the specified object.
	 * @param object The object to which to compare this Object3D.
	 * @return True if the objects are equal.
	 */
	public boolean equals(Object object) {
		if (object instanceof Object3D) {
			Object3D temp = (Object3D) object;
			if (points.length == temp.points.length && edges.length == temp.edges.length && faces.length == temp.faces.length) {
				for (int i = 0; i < points.length; i++) {
					if (!(points[i].equals(temp.points[i]))) {
						return false;
					}
				}
				for (int i = 0; i < edges.length; i++) {
					if (!(edges[i].equals(temp.edges[i]))) {
						return false;
					}
				}
				for (int i = 0; i < faces.length; i++) {
					if (!(faces[i].equals(temp.faces[i]))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	@Override
	/** Converts this Object3D to a String.
	 * @return This Object3D in String form.
	 */
	public String toString() {
		String ret = "{";
		for (int i = 0; i < points.length; i++) {
			if (i == points.length-1) {
				ret += points[i].toString() + "}";
			} else {
				ret += points[i].toString() + ", ";
			}
		}
		return ret;
	}
	public static Object3D createFromObj(String objFile) throws IOException {
		return createFromObj(objFile, null);
	}
	public static Object3D createFromObj(String objFile, Color triangleColor) throws IOException {
		IOBJParser parser = new OBJParser();
		OBJModel model = parser.parse(new FileInputStream(objFile));
		java.util.List<OBJVertex> vertices = model.getVertices();
		Vector3[] points = new Vector3[vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			OBJVertex vertex = vertices.get(i);
			points[i] = new Vector3(vertex.x, vertex.y, vertex.z);
		}
		Object3D temp = new Object3D(points);
		ArrayList<Edge> lines = new ArrayList<Edge>();
		ArrayList<Face> faceTemp = new ArrayList<Face>();
		for (OBJObject object : model.getObjects()) {
			for (OBJMesh mesh : object.getMeshes()) {
				for (OBJFace face : mesh.getFaces()) {
					java.util.List<OBJDataReference> references = face.getReferences();
					if (references.size() == 3) {
						OBJVertex vFirst = model.getVertex(references.get(0));
						OBJVertex vSecond = model.getVertex(references.get(1));
						OBJVertex vThird = model.getVertex(references.get(2));
						Vector3 first = new Vector3(vFirst.x, vFirst.y, vFirst.z);
						Vector3 second = new Vector3(vSecond.x, vSecond.y, vSecond.z);
						Vector3 third = new Vector3(vThird.x, vThird.y, vThird.z);
						int firstID = temp.getPointIDByVector(first);
						int secondID = temp.getPointIDByVector(second);
						int thirdID = temp.getPointIDByVector(third);
						Triangle[] triangle = {new Triangle(firstID, secondID, thirdID, triangleColor)};
						faceTemp.add(new Face(triangle));
						Edge edge1 = new Edge(firstID, secondID);
						Edge edge2 = new Edge(secondID, thirdID);
						Edge edge3 = new Edge(thirdID, firstID);
						if (!lines.contains(edge1)) {
							lines.add(edge1);
						}
						if (!lines.contains(edge2)) {
							lines.add(edge2);
						}
						if (!lines.contains(edge3)) {
							lines.add(edge3);
						}
					}
				}
			}
		}
		Edge[] edges = new Edge[lines.size()];
		Face[] faces = new Face[faceTemp.size()];
		for (int i = 0; i < edges.length; i++) {
			edges[i] = lines.get(i);
		}
		for (int i = 0; i < faces.length; i++) {
			faces[i] = faceTemp.get(i);
		}
		if (triangleColor == null) {
			return new Object3D(points, edges);
		}
		return new Object3D(points, edges, faces);
	}
	public int getPointIDByVector(Vector3 toFind) {
		for (int i = 0; i < points.length; i++) {
			if (points[i].equals(toFind)) {
				return i;
			}
		}
		return -1;
	}
}