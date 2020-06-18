package com.emeryferrari.jse3d.obj;
import java.io.*;
public class Scene implements Serializable {
	private static final long serialVersionUID = 1L;
	public Object3D[] object;
	public double camDist;
	public Scene(Object3D[] object, double camDist) {
		this.object = object;
		this.camDist = camDist;
	}
	public Object3D[] getObjects() {
		return object;
	}
	public double getCameraDistance() {
		return camDist;
	}
	public Scene setObjects(Object3D[] object) {
		this.object = object;
		return this;
	}
	public Scene setObject(Object3D object, int index) {
		this.object[index] = object;
		return this;
	}
	public Scene setCameraDistance(double camDist) {
		this.camDist = camDist;
		return this;
	}
}