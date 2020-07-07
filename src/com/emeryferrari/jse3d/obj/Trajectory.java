package com.emeryferrari.jse3d.obj;
import com.emeryferrari.jse3d.gfx.*;
import com.emeryferrari.jse3d.interfaces.*;
import com.emeryferrari.jse3d.obj.update.*;
/** Represents a Particle's trajectory.
 * @author Emery Ferrari
 * @since 2.5
 */
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
		move = new LinearUpdate(particle, increment, time);
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