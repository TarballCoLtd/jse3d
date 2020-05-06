package com.emeryferrari.jse3d;
import javax.swing.*;
import java.awt.*;
import java.util.*;
public class Display extends JComponent {
	private static final long serialVersionUID = 1L;
	public Scene scene;
	private JFrame frame;
	private boolean renderPoints;
	private boolean rendering;
	private int pointWidth;
	private int pointHeight;
	private boolean rendererStarted;
	private boolean fpsLimit;
	private boolean fpsLogging;
	
	private ArrayList<ArrayList<Distance>> distance;
	private double camPosX = 0;
	private double camPosY = 0;
	private double camPosZ = 0;
	private ArrayList<ArrayList<Double>> camScale;
	private double scale = 125;
	private double sensitivity = 125;
	private double xTransform = 0;
	private double yTransform = 0;
	private double viewAngleX = 0;
	private double viewAngleY = 0;
	public Display(Scene scene, String frameTitle, boolean visible, boolean renderPoints) {
		this(scene, frameTitle, visible, renderPoints, 5, 5);
	}
	public Display(Scene scene, String frameTitle, boolean visible, boolean renderPoints, int width, int height) {
		this.scene = scene;
		if (frameTitle.equals("")) {
			frame = new JFrame(JSE3DConst.FULL_NAME);
		} else {
			frame = new JFrame(frameTitle + " // " + JSE3DConst.FULL_NAME);
		}
		frame.setSize(500, 500);
		frame.setVisible(visible);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rendering = false;
		frame.getContentPane().add(BorderLayout.CENTER, this);
		distance = new ArrayList<ArrayList<Distance>>(scene.object.length);
		camScale = new ArrayList<ArrayList<Double>>(scene.object.length);
		for (int x = 0; x < scene.object.length; x++) {
			ArrayList<Distance> distTemp = new ArrayList<Distance>(scene.object[x].points.length);
			ArrayList<Double> camScaleTemp = new ArrayList<Double>(scene.object[x].points.length);
			for (int y = 0; y < scene.object[x].points.length; y++) {
				distTemp.add(new Distance(0, -1));
				camScaleTemp.add(0.0);
			}
			distance.add(distTemp);
			camScale.add(camScaleTemp);
		}
		this.renderPoints = renderPoints;
		pointWidth = width;
		pointHeight = height;
		rendererStarted = false;
		fpsLimit = true;
		fpsLogging = false;
	}
	public void startRender() {
		if (!rendererStarted) {
			rendering = true;
			Thread renderer = new Renderer();
			renderer.start();
		}
	}
	public void pauseRender() {
		rendering = false;
	}
	public void resumeRender() {
		rendering = true;
	}
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	public JFrame getFrame() {
		return frame;
	}
	@Override
	public void paintComponent(Graphics graphics) {
		Point mouse = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
		for (int a = 0; a < scene.object.length; a++) {
			Point[] points = new Point[scene.object[a].points.length];
			for (int i = 0; i < scene.object[a].points.length; i++) {
				double zAngle = Math.atan(scene.object[a].points[i].z/scene.object[a].points[i].x);
				if (scene.object[a].points[i].x == 0 && scene.object[a].points[i].z == 0) {
					zAngle = 0;
				}
				double mag = Math.sqrt(Math.pow(scene.object[a].points[i].x, 2) + Math.pow(scene.object[a].points[i].z, 2));
				viewAngleY = -(mouse.y-frame.getHeight()/2)/sensitivity;
				if (Math.abs(mouse.y-frame.getHeight()/2)>Math.PI/2*sensitivity) {
					if (viewAngleY < 0) {
						viewAngleY = -Math.PI/2*sensitivity;
					} else {
						viewAngleY = Math.PI/2*sensitivity;
					}
				}
				viewAngleX = -(mouse.x-frame.getWidth()/2)/sensitivity;
				if (scene.object[a].points[i].x < 0) {
					xTransform = -mag*scale*Math.cos(viewAngleX+zAngle);
					yTransform = -mag*scale*Math.sin(viewAngleX+zAngle)*Math.sin(viewAngleY)+scene.object[a].points[i].y*scale*Math.cos(viewAngleY);
				} else {
					xTransform = mag*scale*Math.cos(viewAngleX+zAngle);
					yTransform = mag*scale*Math.sin(viewAngleX+zAngle)*Math.sin(viewAngleY)+scene.object[a].points[i].y*scale*Math.cos(viewAngleY);
				}
				camPosX = scene.camDist*Math.sin(viewAngleX)*Math.cos(viewAngleY);
				camPosY = -scene.camDist*Math.sin(viewAngleY);
				camPosZ = scene.camDist*Math.cos(viewAngleX)*Math.cos(viewAngleY);
				distance.get(a).set(i, new Distance(Math.sqrt(Math.pow(camPosX-scene.object[a].points[i].x, 2)+Math.pow(camPosY-scene.object[a].points[i].y, 2)+Math.pow(camPosZ-scene.object[a].points[i].z, 2)), i));
				double theta = Math.asin((Math.sqrt(Math.pow(xTransform, 2)+Math.pow(yTransform, 2))/scale)/distance.get(a).get(i).distance);
				camScale.get(a).set(i, distance.get(a).get(i).distance*Math.cos(theta)*Math.sin(scene.viewAngle/2));
				points[i] = new Point((int)(frame.getWidth()/2+xTransform/camScale.get(a).get(i)), (int)(frame.getHeight()/2-yTransform/camScale.get(a).get(i)));
				if (renderPoints) {
					graphics.fillOval(points[i].x, points[i].y, pointWidth, pointHeight);
				}
			}
			for (int x = 0; x < scene.object[a].faces.length; x++) {
				int[] pointIDs = scene.object[a].faces[x].getPointIDs();
				double[] distances = new double[pointIDs.length];
				for (int y = 0; y < pointIDs.length; y++) {
					for (int z = 0; z < distance.get(a).size(); z++) {
						if (distance.get(a).get(z).pointID == pointIDs[y]) {
							distances[y] = distance.get(a).get(z).distance;
						}
					}
				}
				double average = 0.0;
				for (int i = 0; i < distances.length; i++) {
					average += distances[i];
				}
				average /= (double) distances.length;
				scene.object[a].faces[x].camDist = average;
			}
			for (int x = 0; x < scene.object[a].faces.length; x++) {
				for (int y = x+1; y < scene.object[a].faces.length; y++) {
					if (scene.object[a].faces[x].camDist < scene.object[a].faces[y].camDist) {
						Face temp = scene.object[a].faces[x];
						scene.object[a].faces[x] = scene.object[a].faces[y];
						scene.object[a].faces[y] = temp;
					}
				}
			}
			for (int x = 0; x < scene.object[a].faces.length; x++) {
				for (int y = 0; y < scene.object[a].faces[x].triangles.length; y++) {
					int[] xs = {points[scene.object[a].faces[x].triangles[y].pointID1].x, points[scene.object[a].faces[x].triangles[y].pointID2].x, points[scene.object[a].faces[x].triangles[y].pointID3].x};
					int[] ys = {points[scene.object[a].faces[x].triangles[y].pointID1].y, points[scene.object[a].faces[x].triangles[y].pointID2].y, points[scene.object[a].faces[x].triangles[y].pointID3].y};
					graphics.setColor(scene.object[a].faces[x].triangles[y].color);
					graphics.fillPolygon(xs, ys, 3);
				}
			}
			graphics.setColor(Color.BLACK);
			for (int i = 0; i < scene.object[a].edges.length; i++) {
				int point1 = scene.object[a].edges[i].pointID1;
				int point2 = scene.object[a].edges[i].pointID2;
				graphics.drawLine(points[point1].x, points[point1].y, points[point2].x, points[point2].y);
			}
		}
		this.revalidate();
	}
	private class Renderer extends Thread {
		@Override
		public void run() {
			while (true) {
				int fps = 0;
				long lastFpsTime = 0L;
				long lastLoopTime = System.nanoTime();
				while (rendering) {
				    long now = System.nanoTime();
				    long updateLength = now - lastLoopTime;
				    lastLoopTime = now;
				    lastFpsTime += updateLength;
				    fps++;
				    if (lastFpsTime >= 1000000000) {
				    	if (fpsLogging) {
				    		System.out.println("FPS: " + fps);
				    	}
				        lastFpsTime = 0;
				        fps = 0;
				    }
				    renderFrame();
				    if (fpsLimit) {
				    	try {Thread.sleep((lastLoopTime-System.nanoTime()+JSE3DConst.OPTIMAL_TIME)/1000000);} catch (InterruptedException ex) {ex.printStackTrace();}
				    }
				}
			}
		}
		private void renderFrame() {
			repaint();
		}
	}
	public void setTargetFPS(int fps) {
		JSE3DConst.TARGET_FPS = fps;
		JSE3DConst.OPTIMAL_TIME = 1000000000 / JSE3DConst.TARGET_FPS;
	}
	public void enableFPSLimit() {
		fpsLimit = true;
	}
	public void disableFPSLimit() {
		fpsLimit = false;
	}
	public void enableFPSLogging() {
		fpsLogging = true;
	}
	public void disableFPSLogging() {
		fpsLogging = false;
	}
}