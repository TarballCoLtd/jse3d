package com.emeryferrari.jse3d.obj;
import java.io.*;
import com.emeryferrari.jse3d.gfx.*;
import com.emeryferrari.jse3d.interfaces.*;
/** Represents a collection of Vector3s.
 * @author Emery Ferrari
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
	public Line[] edges = {};
	/** The Faces contained in this Object3D.
	 */
	public Face[] faces = {};
	/** The distance from this Object3D to the camera. Do not set this value.
	 */
	public double camDist = 0;
	/** This Object3D's script.
	 */
	public Updatable updatable = new Updatable() {
		@Override
		public void start() {}
		@Override
		public void update() {}
		@Override
		public void fixedUpdate() {}
	};
	/** Constructs an Object3D with the specified points, Faces, and Lines.
	 * @param points An array of points in the form of Vector3s.
	 * @param faces An array of Faces to be rendered.
	 * @param edges An array of Lines to be rendered.
	 */
	public Object3D(Vector3[] points, Face[] faces, Line[] edges) {
		this(points, faces);
		this.edges = edges;
	}
	/** Constructs an Object3D with the specified points, Faces, and Lines.
	 * @param points An array of points in the form of Vector3s.
	 * @param edges An array of Lines to be rendered.
	 * @param faces An array of Faces to be rendered.
	 */
	public Object3D(Vector3[] points, Line[] edges, Face[] faces) {
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
	public Object3D(Vector3[] points, Line[] edges) {
		this(points);
		this.edges = edges;
	}
	/** Constructs an Object3D with the specified points.
	 * @param points An array of points in the form of Vector3s.
	 */
	public Object3D(Vector3[] points) {
		this.points = points;
	}
	public Object3D movePosRel(Vector3 diff, Display display) {
		movePosRel(diff.x, diff.y, diff.z, display);
		return this;
	}
	public Object3D movePosRel(Vector3 diff, Vector3 camPos) {
		movePosRel(diff.x, diff.y, diff.z, camPos);
		return this;
	}
	public Object3D movePosRel(double xDiff, double yDiff, double zDiff, Display display) {
		movePosRel(xDiff, yDiff, zDiff, display.getCameraPosition());
		return this;
	}
	public Object3D transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Display display) {
		transitionPosRel(xDiff, yDiff, zDiff, millis, display.getCameraPosition(), display.getPhysicsTimestep());
		return this;
	}
	public Object3D movePosRel(double xDiff, double yDiff, double zDiff, Vector3 camPos) {
		for (int i = 0; i < points.length; i++) {
			points[i].movePosRel(xDiff, yDiff, zDiff, camPos);
		}
		return this;
	}
	public Object3D transitionPosRel(Vector3 diff, int millis, Vector3 camPos, int physicsTimestep) {
		transitionPosRel(diff.x, diff.y, diff.z, millis, camPos, physicsTimestep);
		return this;
	}
	public Object3D transitionPosRel(Vector3 diff, Vector3 camPos, int millis, int physicsTimestep) {
		transitionPosRel(diff, millis, camPos, physicsTimestep);
		return this;
	}
	public Object3D transitionPosRel(Vector3 diff, int millis, Display display) {
		transitionPosRel(diff, millis, display.getCameraPosition(), display.getPhysicsTimestep());
		return this;
	}
	public Object3D transitionPosRel(Vector3 diff, Display display, int millis) {
		transitionPosRel(diff, millis, display);
		return this;
	}
	public Object3D transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Vector3 camPos, int physicsTimestep) {
		for (int i = 0; i < points.length; i++) {
			points[i].transitionPosRel(xDiff, yDiff, zDiff, millis, camPos, physicsTimestep);
		}
		return this;
	}
	public Object3D scale(double multiplierX, double multiplierY, double multiplierZ, Vector3 around, Display display) {
		scale(multiplierX, multiplierY, multiplierZ, around, display.getCameraPosition());
		return this;
	}
	public Object3D scale(double multiplierX, double multiplierY, double multiplierZ, Vector3 around, Vector3 camPos) {
		for (int i = 0; i < points.length; i++) {
			points[i].scale(multiplierX, multiplierY, multiplierZ, around, camPos);
		}
		return this;
	}
	public int getPointID(Vector3 point) {
		for (int i = 0; i < points.length; i++) {
			if (points[i].x == point.x && points[i].y == point.y && points[i].z == point.z) {
				return i;
			}
		}
		return -1;
	}
	/** Sets this Object3D's script.
	 * @param updatable
	 * @return The Object3D on which this method was called.
	 */
	public Object3D setScript(Updatable updatable) {
		this.updatable = updatable;
		return this;
	}
	public void start() {
		if (!started) {
			updatable.start();
		}
		started = true;
	}
	public void update() {
		updatable.update();
	}
	public void fixedUpdate() {
		updatable.fixedUpdate();
	}
	@Override
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
}