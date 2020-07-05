package com.emeryferrari.jse3d.obj;
import com.emeryferrari.jse3d.*;
public class Trajectory {
	protected Runnable move;
	protected Particle particle;
	public Trajectory() {
		move = new Runnable() {
			@Override
			public void run() {}
		};
	}
	public Trajectory(Runnable runnable) {
		this.move = runnable;
	}
	public void setLinear(Vector3 increment) {
		move = new Runnable() {
			@Override
			public void run() {
				Vector3 currentPos = particle.getPosition();
				particle.setPosition(new Vector3(currentPos.getX()+(increment.getX()*Time.fixedDeltaTime), currentPos.getY()+(increment.getY()*Time.fixedDeltaTime), currentPos.getZ()+(increment.getZ()*Time.fixedDeltaTime)));
			}
		};
	}
	public void run() {
		move.run();
	}
	void setParticle(Particle particle) {
		this.particle = particle;
	}
	public void setRunnable(Runnable runnable) {
		move = runnable;
	}
}