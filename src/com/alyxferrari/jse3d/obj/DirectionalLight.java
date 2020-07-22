package com.alyxferrari.jse3d.obj;
public class DirectionalLight {
	private Vector3 direction;
	private float lightStrength;
	public DirectionalLight(Vector3 direction, float lightStrength) {
		this.direction = direction.getNormal();
		this.lightStrength = lightStrength;
	}
	public Vector3 getDirection() {
		return direction;
	}
	public DirectionalLight setDirection(Vector3 direction) {
		this.direction = direction.getNormal();
		return this;
	}
	public float getLightStrength() {
		return lightStrength;
	}
	public DirectionalLight setLightStrength(float strength) {
		lightStrength = strength;
		return this;
	}
}