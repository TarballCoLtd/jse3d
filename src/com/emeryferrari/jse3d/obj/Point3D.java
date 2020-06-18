package com.emeryferrari.jse3d.obj;
import java.io.*;
import com.emeryferrari.jse3d.*;
public class Point3D implements Serializable {
	private static final long serialVersionUID = 1L;
	public double x;
	public double y;
	public double z;
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Point3D movePosAbs(double x, double y, double z, Display display) {
		movePosAbs(x, y, z, display.getCameraPosition());
		return this;
	}
	public Point3D movePosAbs(double x, double y, double z, Point3D camPos) {
		this.x = x-camPos.x;
		this.y = y-camPos.y;
		this.z = z-camPos.z;
		return this;
	}
	public Point3D transitionPosAbs(double x, double y, double z, int millis, Display display) {
		transitionPosAbs(x, y, z, millis, display.getCameraPosition(), display.getPhysicsTimestep());
		return this;
	}
	public Point3D transitionPosAbs(double x, double y, double z, int millis, Point3D camPos, int physicsTimestep) {
		Thread transition = new Transition(x, y, z, millis, camPos, physicsTimestep);
		transition.start();
		return this;
	}
	public Point3D movePosRel(double xDiff, double yDiff, double zDiff, Display display) {
		movePosAbs(x+xDiff, y+yDiff, z+zDiff, display);
		return this;
	}
	public Point3D movePosRel(double xDiff, double yDiff, double zDiff, Point3D camPos) {
		movePosAbs(x+xDiff, y+yDiff, z+zDiff, camPos);
		return this;
	}
	public Point3D transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Display display) {
		transitionPosAbs(x+xDiff, y+yDiff, z+zDiff, millis, display);
		return this;
	}
	public Point3D transitionPosRel(double xDiff, double yDiff, double zDiff, int millis, Point3D camPos, int physicsTimestep) {
		transitionPosAbs(x+xDiff, y+yDiff, z+zDiff, millis, camPos, physicsTimestep);
		return this;
	}
	private class Transition extends Thread implements Serializable {
		private static final long serialVersionUID = 1L;
		private double xt;
		private double yt;
		private double zt;
		private int millis;
		private Point3D camPos;
		private int physicsTimestep;
		private Transition(double x, double y, double z, int millis, Point3D camPos, int physicsTimestep) {
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
			double xIteration = xDiff/(double)(physicsTimestep*((double)millis/1000.0));
			double yIteration = yDiff/(double)(physicsTimestep*((double)millis/1000.0));
			double zIteration = zDiff/(double)(physicsTimestep*((double)millis/1000.0));
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
}