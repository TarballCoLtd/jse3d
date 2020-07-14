package com.emeryferrari.jse3d.obj;
import java.io.*;
import java.util.*;
/** Represents a collection of Object3Ds and Particles.
 * @author Alyx Ferrari
 * @since 1.0 beta
 */
public class Scene implements Serializable {
	private static final long serialVersionUID = 1L;
	public Object3D[] object;
	public double camDist;
	public ArrayList<Particle> particles;
	public Scene(Object3D[] object, double camDist) {
		this(object, camDist, new ArrayList<Particle>());
	}
	public Scene(Object3D[] object, double camDist, ArrayList<Particle> particles) {
		this.object = object;
		this.camDist = camDist;
		this.particles = particles;
	}
	public Scene(Object3D[] object, double camDist, Particle[] particles) {
		this.particles = new ArrayList<Particle>();
		for (int i = 0; i < particles.length; i++) {
			this.particles.add(particles[i]);
		}
		this.object = object;
		this.camDist = camDist;
	}
	@Deprecated
	public Object3D[] getObjects() { // only use this to read, do not set any values
		return object;
	}
	@Deprecated
	public ArrayList<Particle> getParticles() { // only use this to read, do not set any values
		return particles;
	}
	public double getCameraDistance() {
		return camDist;
	}
	public Scene setObjects(Object3D[] object) {
		for (int i = 0; i < object.length; i++) {
			object[i].start();
		}
		this.object = object;
		return this;
	}
	public Scene setObject(Object3D object, int index) {
		object.start();
		this.object[index] = object;
		return this;
	}
	public Scene setCameraDistance(double camDist) {
		this.camDist = camDist;
		return this;
	}
	public Scene setParticles(ArrayList<Particle> particles) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).start();
		}
		this.particles = particles;
		return this;
	}
	public Scene setParticles(Particle[] particles) {
		this.particles = new ArrayList<Particle>();
		for (int i = 0; i < particles.length; i++) {
			particles[i].start();
			this.particles.add(particles[i]);
		}
		return this;
	}
	public Scene setParticle(Particle particle, int index) {
		particle.start();
		particles.set(index, particle);
		return this;
	}
	@Override
	public boolean equals(Object object) {
		if (object instanceof Scene) {
			Scene temp = (Scene) object;
			if (this.object.length == temp.object.length && camDist == temp.camDist) {
				for (int i = 0; i < this.object.length; i++) {
					if (!(this.object[i].equals(temp.object[i]))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		String ret = "{";
		for (int i = 0; i < object.length; i++) {
			ret += object[i].toString() + ", ";
		}
		ret += "camDist=" + camDist + "}";
		return ret;
	}
}