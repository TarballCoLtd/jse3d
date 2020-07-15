package com.emeryferrari.jse3d.obj.update;
import com.emeryferrari.jse3d.obj.*;
import com.emeryferrari.jse3d.interfaces.*;
import com.emeryferrari.jse3d.gfx.*;
/** Represents a particle movement preset.
 * @author Alyx Ferrari
 */
public class LinearUpdate implements Scriptable {
	private Particle particle;
	private Vector3 increment;
	private Time time;
	public LinearUpdate(Particle particle, Vector3 increment, Time time) {
		this.particle = particle;
		this.increment = increment;
		this.time = time;
	}
	@Override
	public void start() {}
	@Override
	public void update() {}
	@Override
	public void fixedUpdate() {
		Vector3 currentPos = particle.getPosition();
		particle.setPosition(new Vector3(currentPos.getX()+(increment.getX()*time.fixedDeltaTime), currentPos.getY()+(increment.getY()*time.fixedDeltaTime), currentPos.getZ()+(increment.getZ()*time.fixedDeltaTime)));
	}
}