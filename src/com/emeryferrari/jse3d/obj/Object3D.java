package com.emeryferrari.jse3d.obj;
import java.io.*;
import com.emeryferrari.jse3d.*;
public class Object3D implements Serializable {
	private static final long serialVersionUID = 1L;
	public Point3D[] points;
	public Line[] edges = {};
	public Face[] faces = {};
	public double camDist = 0;
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
	public Object3D movePosRel(Point3D diff, Display display) {
		movePosRel(diff.x, diff.y, diff.z, display);
		return this;
	}
	public Object3D movePosRel(Point3D diff, Point3D camPos) {
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
	public Object3D movePosRel(double xDiff, double yDiff, double zDiff, Point3D camPos) {
		for (int i = 0; i < points.length; i++) {
			points[i].movePosRel(xDiff, yDiff, zDiff, camPos);
		}
		return this;
	}
	public Object3D transitionPosRel(Point3D diff, int millis, Point3D camPos, int physicsTimestep) {
		transitionPosRel(diff.x, diff.y, diff.z, millis, camPos, physicsTimestep);
		return this;
	}
	public Object3D transitionPosRel(Point3D diff, Point3D camPos, int millis, int physicsTimestep) {
		transitionPosRel(diff, millis, camPos, physicsTimestep);
		return this;
	}
	public Object3D transitionPosRel(Point3D diff, int millis, Display display) {
		transitionPosRel(diff, millis, display.getCameraPosition(), display.getPhysicsTimestep());
		return this;
	}
	public Object3D transitionPosRel(Point3D diff, Display display, int millis) {
		transitionPosRel(diff, millis, display);
		return this;
	}
	public Object3D transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Point3D camPos, int physicsTimestep) {
		for (int i = 0; i < points.length; i++) {
			points[i].transitionPosRel(xDiff, yDiff, zDiff, millis, camPos, physicsTimestep);
		}
		return this;
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