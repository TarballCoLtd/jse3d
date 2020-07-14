package com.emeryferrari.jse3d.obj;
/** Represents a particle.
 * @author Alyx Ferrari
 * @since 2.5
 */
public class Particle {
	protected boolean started = false;
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
		if (!started) {
			trajectory.start();
		}
		started = true;
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