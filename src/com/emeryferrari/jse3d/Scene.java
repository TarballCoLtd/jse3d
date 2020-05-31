package com.emeryferrari.jse3d;
import java.io.*;
public class Scene implements Serializable {
	private static final long serialVersionUID = 1L;
	Object3D[] object;
	double camDist;
	double viewAngle;
	public Scene(Object3D[] object, double camDist) {
		this.object = object;
		this.camDist = camDist;
		viewAngle = 0.56;
	}
	public Object3D[] getObjects() {
		return object;
	}
	public double getCameraDistance() {
		return camDist;
	}
	public void setObjects(Object3D[] object) {
		this.object = object;
	}
	public void setObject(Object3D object, int index) {
		this.object[index] = object;
	}
	public void setCameraDistance(double camDist) {
		this.camDist = camDist;
	}
}