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
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public void setFrameTitle(String frameTitle) {
		this.frameTitle = frameTitle;
	}
	public void setFrameVisible(boolean frameVisible) {
		this.frameVisible = frameVisible;
	}
	public void setRenderPoints(boolean renderPoints) {
		this.renderPoints = renderPoints;
	}
	public void setPointSize(Dimension pointSize) {
		this.pointSize = pointSize;
	}
	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}
	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
	public void setFrameSize(int frameWidth, int frameHeight) {
		setFrameWidth(frameWidth);
		setFrameHeight(frameHeight);
	}
	public void setFrameSize(Dimension size) {
		setFrameSize(size.width, size.height);
	}
	public void setFps(int fps) {
		this.fps = fps;
	}
	public void setFovRadians(int fovRadians) {
		this.fovRadians = fovRadians;
	}
	public void setMaxPointsTotal(int maxPointsTotal) {
		this.maxPointsTotal = maxPointsTotal;
	}
	public void setMaxPointsObject(int maxPointsObject) {
		this.maxPointsObject = maxPointsObject;
	}
	public void setMaxObjects(int maxObjects) {
		this.maxObjects = maxObjects;
	}
	public Display build() {
		return new Display(scene, frameTitle, frameVisible, renderPoints, pointSize, frameWidth, frameHeight, fps, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	
}