package com.emeryferrari.jse3d;
public class Point3D {
	public double x;
	public double y;
	public double z;
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void movePos(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void transitionPos(double x, double y, double z, int millis) {
		Thread transition = new Transition(x, y, z, millis);
		transition.start();
	}
	private class Transition extends Thread {
		private double xt;
		private double yt;
		private double zt;
		private int millis;
		private Transition(double x, double y, double z, int millis) {
			this.xt = x;
			this.yt = y;
			this.zt = z;
			this.millis = millis;
		}
		@Override
		public void run() {
			double xDiff = xt-x;
			double yDiff = yt-y;
			double zDiff = zt-z;
			double xIteration = xDiff/(double)(60.0*((double)millis/1000.0));
			double yIteration = yDiff/(double)(60.0*((double)millis/1000.0));
			double zIteration = zDiff/(double)(60.0*((double)millis/1000.0));
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			final long OPTIMAL_TIME = 1000000000 / JSE3DConst.PHYSICS_TIMESTEP;
			for (int i = 0; i < (int)(60.0*((double)millis/1000.0)); i++) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    lastFpsTime += updateLength;
			    if (lastFpsTime >= 1000000000) {
			        lastFpsTime = 0;
			    }
			    movePos(x+xIteration, y+yIteration, z+zIteration);
			    try {Thread.sleep((lastLoopTime-System.nanoTime()+OPTIMAL_TIME)/1000000);} catch (InterruptedException ex) {ex.printStackTrace();}
			}
			movePos(xt, yt, zt);
		}
	}
}