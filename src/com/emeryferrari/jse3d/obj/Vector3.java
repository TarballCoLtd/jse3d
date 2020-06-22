package com.emeryferrari.jse3d.obj;
import java.io.*;
import com.emeryferrari.jse3d.*;
public class Vector3 implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Vector3 back = new Vector3(0, 0, -1);
	public static final Vector3 down = new Vector3(0, -1, 0);
	public static final Vector3 forward = new Vector3(0, 0, 1);
	public static final Vector3 negativeInfinity = new Vector3(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	public static final Vector3 one = new Vector3(1, 1, 1);
	public static final Vector3 positiveInfinity = new Vector3(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	public static final Vector3 right = new Vector3(1, 0, 0);
	public static final Vector3 left = new Vector3(-1, 0, 0);
	public static final Vector3 up = new Vector3(0, 1, 0);
	public static final Vector3 zero = new Vector3(0, 0, 0);
	protected double x;
	protected double y;
	protected double z;
	protected double magnitude;
	protected double sqrMagnitude;
	protected Vector3 normal;
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		recalculate();
	}
	protected Vector3(double x, double y, double z, Object object) {
		this.x = x;
		this.y = y;
		this.z = z;
		magnitude = Math3D.hypot3(x, y, z);
		sqrMagnitude = Math.pow(magnitude, 2);
		normal = this;
	}
	public Vector3 movePosAbs(double x, double y, double z, Display display) {
		movePosAbs(x, y, z, display.getCameraPosition());
		return this;
	}
	public Vector3 movePosAbs(double x, double y, double z, Vector3 camPos) {
		this.x = x-camPos.x;
		this.y = y-camPos.y;
		this.z = z-camPos.z;
		return this;
	}
	public Vector3 transitionPosAbs(double x, double y, double z, int millis, Display display) {
		transitionPosAbs(x, y, z, millis, display.getCameraPosition(), display.getPhysicsTimestep());
		return this;
	}
	public Vector3 transitionPosAbs(double x, double y, double z, int millis, Vector3 camPos, int physicsTimestep) {
		Thread transition = new Transition(x, y, z, millis, camPos, physicsTimestep);
		transition.start();
		return this;
	}
	public Vector3 movePosRel(double xDiff, double yDiff, double zDiff, Display display) {
		movePosAbs(x+xDiff, y+yDiff, z+zDiff, display);
		return this;
	}
	public Vector3 movePosRel(double xDiff, double yDiff, double zDiff, Vector3 camPos) {
		movePosAbs(x+xDiff, y+yDiff, z+zDiff, camPos);
		return this;
	}
	public Vector3 transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Display display) {
		transitionPosAbs(x+xDiff, y+yDiff, z+zDiff, millis, display);
		return this;
	}
	public Vector3 transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Vector3 camPos, int physicsTimestep) {
		transitionPosAbs(x+xDiff, y+yDiff, z+zDiff, millis, camPos, physicsTimestep);
		return this;
	}
	private class Transition extends Thread implements Serializable {
		private static final long serialVersionUID = 1L;
		private double xt;
		private double yt;
		private double zt;
		private int millis;
		private Vector3 camPos;
		private int physicsTimestep;
		private Transition(double x, double y, double z, int millis, Vector3 camPos, int physicsTimestep) {
			this.xt = x;
			this.yt = y;
			this.zt = z;
			this.millis = millis;
			this.camPos = camPos;
			this.physicsTimestep = physicsTimestep;
		}
		@Override
		public void run() {
			double xDiff = xt-x;
			double yDiff = yt-y;
			double zDiff = zt-z;
			double xIteration = xDiff/((double)physicsTimestep*((double)millis/1000.0));
			double yIteration = yDiff/((double)physicsTimestep*((double)millis/1000.0));
			double zIteration = zDiff/((double)physicsTimestep*((double)millis/1000.0));
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			final long OPTIMAL_TIME = 1000000000 / physicsTimestep;
			for (int i = 0; i < (int)(physicsTimestep*((double)millis/1000.0)); i++) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    lastFpsTime += updateLength;
			    if (lastFpsTime >= 1000000000) {
			        lastFpsTime = 0;
			    }
			    movePosAbs(x+xIteration, y+yIteration, z+zIteration, camPos);
			    try {Thread.sleep((lastLoopTime-System.nanoTime()+OPTIMAL_TIME)/1000000);} catch (InterruptedException ex) {ex.printStackTrace();}
			}
			movePosAbs(xt, yt, zt, camPos);
		}
	}
	public Vector3 scale(double multiplierX, double multiplierY, double multiplierZ, Vector3 around, Display display) {
		scale(multiplierX, multiplierY, multiplierZ, around, display.getCameraPosition());
		return this;
	}
	public Vector3 scale(double multiplierX, double multiplierY, double multiplierZ, Vector3 around, Vector3 camPos) {
		movePosAbs((x*multiplierX)-around.x, (y*multiplierY)-around.y, (z*multiplierZ)-around.z, camPos);
		return this;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	protected void recalculate() {
		magnitude = Math3D.hypot3(x, y, z);
		sqrMagnitude = Math.pow(magnitude, 2);
		normal = new Vector3(x/magnitude, y/magnitude, z/magnitude, null);
	}
	public Vector3 set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		recalculate();
		return this;
	}
	public Vector3 setX(double x) {
		this.x = x;
		recalculate();
		return this;
	}
	public Vector3 setY(double y) {
		this.y = y;
		recalculate();
		return this;
	}
	public Vector3 setZ(double z) {
		this.z = z;
		recalculate();
		return this;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public double getSquareMagnitude() {
		return sqrMagnitude;
	}
	@Override
	public boolean equals(Object object) {
		if (object instanceof Vector3) {
			Vector3 temp = (Vector3) object;
			if (x == temp.x && y == temp.y && z == temp.z) {
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "<" + x + ", " + y + ", " + z + ">";
	}
	public Vector3 normalize() {
		return normal;
	}
	// VECTOR MATH
	public static Vector3 add(Vector3 add1, Vector3 add2) {
		return new Vector3(add1.x+add2.x, add1.y+add2.y, add1.z+add2.z);
	}
	public Vector3 add(Vector3 add) {
		x += add.x;
		y += add.y;
		z += add.z;
		return this;
	}
	public static Vector3 subtract(Vector3 subtract1, Vector3 subtract2) {
		return new Vector3(subtract1.x-subtract2.x, subtract1.y-subtract2.y, subtract1.z-subtract2.z);
	}
	public Vector3 subtract(Vector3 subtract) {
		x -= subtract.x;
		y -= subtract.y;
		z -= subtract.z;
		return this;
	}
	public static Vector3 multiply(Vector3 multiply1, Vector3 multiply2) {
		return new Vector3(multiply1.x*multiply2.x, multiply1.y*multiply2.y, multiply1.z*multiply2.z);
	}
	public Vector3 multiply(Vector3 multiply) {
		x *= multiply.x;
		y *= multiply.y;
		z *= multiply.z;
		return this;
	}
	public static Vector3 divide(Vector3 divide1, Vector3 divide2) {
		return new Vector3(divide1.x/divide2.x, divide1.y/divide2.y, divide1.z/divide2.z);
	}
	public Vector3 divide(Vector3 divide) {
		x /= divide.x;
		y /= divide.y;
		z /= divide.z;
		return this;
	}
}