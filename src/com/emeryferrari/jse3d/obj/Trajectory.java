package com.emeryferrari.jse3d.obj;
import com.emeryferrari.jse3d.gfx.*;
import com.emeryferrari.jse3d.interfaces.*;
public class Trajectory {
	protected Updatable move;
	protected Particle particle;
	public Trajectory() {
		move = new Updatable() {
			@Override
			public void start() {}
			@Override
			public void update() {}
			@Override
			public void fixedUpdate() {}
		};
	}
	public Trajectory(Updatable script) {
		this.move = script;
	}
	public Trajectory setLinear(Vector3 increment, Time time) {
		move = new Updatable() {
			@Override
			public void start() {}
			@Override
			public void update() {}
			@Override
			public void fixedUpdate() {
				Vector3 currentPos = particle.getPosition();
				particle.setPosition(new Vector3(currentPos.getX()+(increment.getX()*time.fixedDeltaTime), currentPos.getY()+(increment.getY()*time.fixedDeltaTime), currentPos.getZ()+(increment.getZ()*time.fixedDeltaTime)));
			}
		};
		return this;
	}
	public void start() {
		move.start();
	}
	public void update() {
		move.update();
	}
	public void fixedUpdate() {
		move.fixedUpdate();
	}
	void setParticle(Particle particle) {
		this.particle = particle;
	}
	public Trajectory setScript(Updatable script) {
		move = script;
		return this;
	}
}