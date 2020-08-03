package com.alyxferrari.jse3d.obj;
import java.io.*;
import com.alyxferrari.jse3d.interfaces.*;
public class DirectionalLight implements Serializable {
	private static final long serialVersionUID = 1L;
	private Vector3 direction;
	private float lightStrength;
	private Script script;
	public DirectionalLight(Vector3 direction, float lightStrength) {
		this.direction = direction.getNormal();
		this.lightStrength = lightStrength;
		this.script = new Script() {
			@Override public void start() {}
			@Override public void update() {}
			@Override public void fixedUpdate() {}
			@Override public void stop() {}
		};
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
	public DirectionalLight setScript(Script script) {
		this.script = script;
		return this;
	}
	public Script getScript() {
		return script;
	}
	public void start() {
		script.start();
	}
	public void update() {
		script.update();
	}
	public void fixedUpdate() {
		script.fixedUpdate();
	}
	public void stop() {
		script.stop();
	}
}