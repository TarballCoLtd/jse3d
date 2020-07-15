package com.emeryferrari.jse3d.exc;
public class ParticleNotSetException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ParticleNotSetException() {
		super("The particle of a Trajectory was accessed, but it was not set. Please assign the affected Trajectory to a Particle before calling the affected code.");
	}
}