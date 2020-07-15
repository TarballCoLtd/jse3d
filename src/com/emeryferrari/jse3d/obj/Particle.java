package com.emeryferrari.jse3d.obj;
import com.emeryferrari.jse3d.interfaces.*;
/** Represents a particle.
 * @author Alyx Ferrari
 * @since 2.5
 */
public class Particle {
	protected boolean started = false;
	protected Vector3 position;
	protected Trajectory trajectory;
	/** Constructs a Particle with a given starting point and Trajectory.
	 * @param position The Particle's starting point.
	 * @param trajectory The Particle's Trajectory.
	 */
	public Particle(Vector3 position, Trajectory trajectory) {
		this.position = position;
		Trajectory temp = trajectory;
		temp.setParticle(this);
		this.trajectory = temp;
	}
	/** Returns the current position of this Particle.
	 * @return The current position of this Particle.
	 */
	public Vector3 getPosition() {
		return position;
	}
	/** Sets the current position of this Particle.
	 * @param position The Particle's new position.
	 * @return The Particle on which this method was called.
	 */
	public Particle setPosition(Vector3 position) {
		this.position = position;
		return this;
	}
	/** Executes the start() method of the Trajectory of this Particle. Do not call this unless you know what you're doing.
	 */
	public void start() {
		if (!started) {
			trajectory.start();
		}
		started = true;
	}
	/** Executes the update() method of the Trajectory of this Particle. Do not call this unless you know what you're doing.
	 */
	public void update() {
		trajectory.update();
	}
	/** Executes the fixedUpdate() method of the Trajectory of this Particle. Do not call this unless you know what you're doing.
	 */
	public void fixedUpdate() {
		trajectory.fixedUpdate();
	}
	/** Returns the Trajectory of this Particle.
	 * @return The Trajectory of this Particle.
	 */
	public Trajectory getTrajectory() {
		return trajectory;
	}
	/** Sets the script of this Particle's Trajectory.
	 * @param script This Particle's new script.
	 * @return The Particle on which this method was called.
	 */
	public Particle setScript(Script script) {
		trajectory.setScript(script);
		return this;
	}
}