package com.alyxferrari.jse3d.gfx;
import com.alyxferrari.jse3d.obj.Scene;
import java.awt.Dimension;
public class DisplayBuilder {
	protected Scene scene;
	protected String frameTitle = "";
	protected boolean frameVisible = true;
	protected boolean renderPoints = false;
	protected Dimension pointSize = new Dimension(5, 5);
	protected int frameWidth = 500;
	protected int frameHeight = 500;
	protected int fps = 60;
	protected double fovRadians = Math.toRadians(80);
	protected int maxPointsTotal = 4096;
	protected int maxPointsObject = 32;
	protected int maxObjects = 128;
	public DisplayBuilder(Scene scene) {
		this.scene = scene;
	}
	public DisplayBuilder setScene(Scene scene) {
		this.scene = scene;
		return this;
	}
	public DisplayBuilder setFrameTitle(String frameTitle) {
		this.frameTitle = frameTitle;
		return this;
	}
	public DisplayBuilder setFrameVisible(boolean frameVisible) {
		this.frameVisible = frameVisible;
		return this;
	}
	public DisplayBuilder setRenderPoints(boolean renderPoints) {
		this.renderPoints = renderPoints;
		return this;
	}
	public DisplayBuilder setPointSize(Dimension pointSize) {
		this.pointSize = pointSize;
		return this;
	}
	public DisplayBuilder setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
		return this;
	}
	public DisplayBuilder setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
		return this;
	}
	public DisplayBuilder setFrameSize(int frameWidth, int frameHeight) {
		setFrameWidth(frameWidth);
		setFrameHeight(frameHeight);
		return this;
	}
	public DisplayBuilder setFrameSize(Dimension size) {
		setFrameSize(size.width, size.height);
		return this;
	}
	public DisplayBuilder setFps(int fps) {
		this.fps = fps;
		return this;
	}
	public DisplayBuilder setFovRadians(int fovRadians) {
		this.fovRadians = fovRadians;
		return this;
	}
	public DisplayBuilder setMaxPointsTotal(int maxPointsTotal) {
		this.maxPointsTotal = maxPointsTotal;
		return this;
	}
	public DisplayBuilder setMaxPointsObject(int maxPointsObject) {
		this.maxPointsObject = maxPointsObject;
		return this;
	}
	public DisplayBuilder setMaxObjects(int maxObjects) {
		this.maxObjects = maxObjects;
		return this;
	}
	public Display build() {
		return new Display(scene, frameTitle, frameVisible, renderPoints, pointSize, frameWidth, frameHeight, fps, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	
}