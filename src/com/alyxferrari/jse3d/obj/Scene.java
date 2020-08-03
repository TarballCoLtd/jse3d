package com.alyxferrari.jse3d.obj;
import java.io.*;
import java.util.*;
/** Represents a collection of Object3Ds and Particles.
 * @author Alyx Ferrari
 * @since 1.0 beta
 */
public class Scene implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The array of Object3Ds contained in this Scene.
	 */
	public Object3D[] object;
	/** The actual distance between the camera and its position. Used for pivoting around the camera's position.
	 */
	public double camDist;
	/** The array of Particles contained in this Scene.
	 */
	public ArrayList<Particle> particles;
	/** The array of directional lights in this Scene.
	 */
	public DirectionalLight[] lights;
	/** Specifies how much triangles should be tinted.
	 */
	private float ambientLight = 0.2f;
	/** Constructs a Scene with the specified Object3Ds and camera distance.
	 * @param object The objects desired to be in this Scene.
	 * @param camDist The camera distance.
	 */
	public Scene(Object3D[] object, double camDist) {
		this(object, camDist, new ArrayList<Particle>());
	}
	/** Constructs a Scene with the specified Object3Ds, Particles, and camera distance.
	 * @param object The objects desired to be in this Scene.
	 * @param camDist The camera distance.
	 * @param particles The Particles desired to be in this Scene.
	 */
	public Scene(Object3D[] object, double camDist, ArrayList<Particle> particles) {
		this(object, camDist, particles, new DirectionalLight[0]);
	}
	public Scene(Object3D[] object, double camDist, ArrayList<Particle> particles, DirectionalLight[] lights) {
		this.object = object;
		this.camDist = camDist;
		this.particles = particles;
		this.lights = lights;
	}
	/** Constructs a Scene with the specified Object3Ds, Particles, and camera distance.
	 * @param object The objects desired to be in this Scene.
	 * @param camDist The camera distance.
	 * @param particles The Particles desired to be in this Scene.
	 */
	public Scene(Object3D[] object, double camDist, Particle[] particles) {
		this(object, camDist, new ArrayList<Particle>());
		this.particles = new ArrayList<Particle>();
		for (int i = 0; i < particles.length; i++) {
			this.particles.add(particles[i]);
		}
	}
	@Deprecated
	/** Returns an array of all Object3Ds in the Scene. Do not set any values of this object, use setObjects().
	 * @return An array of all Object3Ds in the Scene.
	 */
	public Object3D[] getObjects() {
		return object;
	}
	@Deprecated
	/** Returns an array of all Particles in the Scene. Do not set any values of this object, use setObjects().
	 * @return An array of all Particles in the Scene.
	 */
	public ArrayList<Particle> getParticles() { // only use this to read, do not set any values
		return particles;
	}
	/** Returns the camera distance.
	 * @return The camera distance.
	 */
	public double getCameraDistance() {
		return camDist;
	}
	/** Sets the Object3Ds in the Scene.
	 * @param object The new Object3D array.
	 * @return The Scene on which this method was called.
	 */
	public Scene setObjects(Object3D[] object) {
		for (int i = 0; i < object.length; i++) {
			object[i].start();
		}
		this.object = object;
		return this;
	}
	/** Sets a single Object3D in the Scene.
	 * @param object The new object.
	 * @param index The index in the object array at which this object should be placed.
	 * @return The Scene on which this method was called.
	 */
	public Scene setObject(Object3D object, int index) {
		object.start();
		this.object[index] = object;
		return this;
	}
	/** Sets a new camera distance.
	 * @param camDist The new camera distance.
	 * @return The Scene on which this method was called.
	 */
	public Scene setCameraDistance(double camDist) {
		this.camDist = camDist;
		return this;
	}
	/** Sets the Particles in the Scene.
	 * @param particles The new Particle array.
	 * @return The Scene on which this method was called.
	 */
	public Scene setParticles(ArrayList<Particle> particles) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).start();
		}
		this.particles = particles;
		return this;
	}
	/** Sets the Particles in the Scene.
	 * @param particles The new Particle array.
	 * @return The Scene on which this method was called.
	 */
	public Scene setParticles(Particle[] particles) {
		this.particles = new ArrayList<Particle>();
		for (int i = 0; i < particles.length; i++) {
			particles[i].start();
			this.particles.add(particles[i]);
		}
		return this;
	}
	/** Sets a single Particle in the Scene.
	 * @param particle The new Particle.
	 * @param index The index in the Particle array at which this Particle should be placed.
	 * @return The Scene on which this method was called.
	 */
	public Scene setParticle(Particle particle, int index) {
		particle.start();
		particles.set(index, particle);
		return this;
	}
	@Override
	/** Checks for equality between this Scene and another object.
	 * @param object The object against which to check equality.
	 * @return True if the two objects are equal.
	 */
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
	/** Returns this Scene in the form of a String.
	 * @return The String form of this Scene.
	 */
	public String toString() {
		String ret = "{";
		for (int i = 0; i < object.length; i++) {
			ret += object[i].toString() + ", ";
		}
		ret += "camDist=" + camDist + "}";
		return ret;
	}
	public DirectionalLight[] getDirectionalLights() {
		return lights;
	}
	public DirectionalLight getDirectionalLight(int index) {
		return lights[index];
	}
	public Scene setDirectionalLight(DirectionalLight light, int index) {
		lights[index] = light;
		return this;
	}
	public Scene setDirectionalLight(int index, DirectionalLight light) {
		lights[index] = light;
		return this;
	}
	public Scene setDirectionalLights(ArrayList<DirectionalLight> lights) {
		DirectionalLight[] ret = new DirectionalLight[lights.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = lights.get(i);
		}
		return this;
	}
	public Scene setDirectionalLights(DirectionalLight[] lights) {
		this.lights = lights;
		return this;
	}
	public float getAmbientLight() {
		return ambientLight;
	}
	public Scene setAmbientLight(float ambientLight) {
		this.ambientLight = ambientLight > 0.0f ? ambientLight : this.ambientLight;
		return this;
	}
}