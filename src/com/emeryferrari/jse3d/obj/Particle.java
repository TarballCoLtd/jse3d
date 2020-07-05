package com.emeryferrari.jse3d.obj;
public class Particle {
	protected Vector3 position;
	protected Trajectory trajectory;
	public Particle(Vector3 position, Trajectory trajectory) {
		this.position = position;
		Trajectory temp = trajectory;
		temp.setParticle(this);
		this.trajectory = temp;
	}
	public Vector3 getPosition() {
		return position;
	}
	public Particle setPosition(Vector3 position) {
		this.position = position;
		return this;
	}
	public void start() {
		trajectory.start();
	}
	public void update() {
		trajectory.update();
	}
	public void fixedUpdate() {
		trajectory.fixedUpdate();
	}
	public Trajectory getTrajectory() {
		return trajectory;
	}
}