package com.alyxferrari.jse3d.exc;
/** Thrown if the Particle of a Trajectory was accessed, but it was unassigned at runtime.
 * @author Alyx Ferrari
 * @since 2.5.7
 */
public class ParticleNotSetException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/** Default constructor for ParticleNotSetException.
	 */
	public ParticleNotSetException() {
		super("The Particle of a Trajectory was accessed, but it was not set. Please assign the affected Trajectory to a Particle before calling the affected code.");
	}
}