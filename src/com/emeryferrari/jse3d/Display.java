package com.emeryferrari.jse3d;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import com.aparapi.*;
public class Display extends Kernel {
	protected DisplayRenderer renderer;
	protected Scene scene;
	protected JFrame frame;
	protected boolean renderPoints;
	protected boolean rendering;
	protected int pointWidth;
	protected int pointHeight;
	protected boolean rendererStarted;
	protected boolean fpsLimit;
	protected boolean fpsLogging;
	protected Color lineColor;
	protected boolean lineRender;
	protected boolean faceRender;
	protected int targetFps;
	protected long optimalTime;
	protected boolean invertColors;
	protected Color backgroundColor;
	protected Point lastMousePos;	
	protected boolean mouseClicked;
	protected Point mouseDiff;
	protected boolean scrollWheel;
	protected int physicsTimestep = 60;
	Point3D camPos;
	protected CameraMode mode;
	protected ArrayList<ArrayList<Distance>> distance;
	protected ArrayList<ArrayList<Double>> camScale;
	protected final float scale = 125;
	protected final double sensitivity = 125;
	protected double xTransform = 0;
	protected double yTransform = 0;
	protected double viewAngleX = 0;
	protected double viewAngleY = 0;
	protected boolean camPosPrint = false;
	protected int fps = 0;
	protected boolean yAxisClamp;
	protected double viewAngle;
	protected RenderMode renderMode;
	// OPENCL VARIABLES
	final float[] zAngleX;
	final float[] zAngleY;
	final float[] zAngleZ;
	final float[] viewAngleXInput = new float[1];
	final float[] viewAngleYInput = new float[1];
	final float[] sinViewAngleX = new float[1];
	final float[] sinViewAngleY = new float[1];
	final float[] cosViewAngleX = new float[1];
	final float[] cosViewAngleY = new float[1];
	final float[] sinViewAngleXzAngle;
	final float[] cosViewAngleXzAngle;
	final float[] xTransforms;
	final float[] yTransforms;
	final float[] localCamPosX = new float[1];
	final float[] localCamPosY = new float[1];
	final float[] localCamPosZ = new float[1];
	final float[] maths;
	final float[] cosThetas;
	final float[] gpuViewAngle = new float[1];
	final float[] sinViewAngles;
	public Display(Scene scene) {
		this(scene, "");
	}
	public Display(Scene scene, String frameTitle) {
		this(scene, frameTitle, true);
	}
	public Display(Scene scene, String frameTitle, int maxPointsTotal, int maxObjects) {
		this(scene, frameTitle, true, maxPointsTotal, maxObjects);
	}
	public Display(Scene scene, String frameTitle, double fovRadians) {
		this(scene, frameTitle, true, fovRadians);
	}
	public Display(Scene scene, String frameTitle, double fovRadians, int maxPointsTotal, int maxObjects) {
		this(scene, frameTitle, true, fovRadians, maxPointsTotal, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible) {
		this (scene, frameTitle, frameVisible, false);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, int maxPointsTotal, int maxObjects) {
		this (scene, frameTitle, frameVisible, false, maxPointsTotal, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, double fovRadians) {
		this (scene, frameTitle, frameVisible, false, fovRadians);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, double fovRadians, int maxPointsTotal, int maxObjects) {
		this (scene, frameTitle, frameVisible, false, fovRadians, maxPointsTotal, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500, fovRadians);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, double fovRadians, int maxPointsTotal, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500, fovRadians, maxPointsTotal, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight) {
		this(scene, frameTitle, frameVisible, renderPoints, 5, 5, frameWidth, frameHeight);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, 5, 5, frameWidth, frameHeight, fovRadians);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight, double fovRadians, int maxPointsTotal, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, 5, 5, frameWidth, frameHeight, fovRadians, maxPointsTotal, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int pointWidth, int pointHeight ,int frameWidth, int frameHeight) {
		this(scene, frameTitle, frameVisible, renderPoints, pointWidth, pointHeight, frameWidth, frameHeight, Math.toRadians(80));
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int pointWidth, int pointHeight ,int frameWidth, int frameHeight, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, pointWidth, pointHeight, frameWidth, frameHeight, 60, fovRadians, 10000, 100);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int pointWidth, int pointHeight ,int frameWidth, int frameHeight, double fovRadians, int maxPointsTotal, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, pointWidth, pointHeight, frameWidth, frameHeight, 60, fovRadians, maxPointsTotal, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int pointWidth, int pointHeight, int frameWidth, int frameHeight, int fps, double fovRadians, int maxPointsTotal, int maxObjects) {
		zAngleX = new float[maxPointsTotal];
		zAngleY = new float[maxPointsTotal];
		zAngleZ = new float[maxPointsTotal];
		sinViewAngleXzAngle = new float[maxPointsTotal];
		cosViewAngleXzAngle = new float[maxPointsTotal];
		xTransforms = new float[maxPointsTotal];
		yTransforms = new float[maxPointsTotal];
		maths = new float[maxPointsTotal];
		cosThetas = new float[maxPointsTotal];
		sinViewAngles = new float[maxPointsTotal];
		renderMode = RenderMode.CPU_SINGLETHREADED;
		renderer = new DisplayRenderer();
		this.scene = scene;
		if (frameTitle.equals("")) {
			frame = new JFrame(JSE3DConst.FULL_NAME);
		} else {
			frame = new JFrame(frameTitle + " // " + JSE3DConst.FULL_NAME);
		}
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(frameVisible);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rendering = false;
		frame.getContentPane().add(BorderLayout.CENTER, renderer);
		distance = new ArrayList<ArrayList<Distance>>(scene.object.length);
		camScale = new ArrayList<ArrayList<Double>>(scene.object.length);
		for (int x = 0; x < maxObjects; x++) {
			ArrayList<Distance> distTemp = new ArrayList<Distance>();
			ArrayList<Double> camScaleTemp = new ArrayList<Double>();
			for (int y = 0; y < maxPointsTotal; y++) {
				distTemp.add(new Distance(0, -1));
				camScaleTemp.add(0.0);
			}
			distance.add(distTemp);
			camScale.add(camScaleTemp);
		}
		this.renderPoints = renderPoints;
		this.pointWidth = pointWidth;
		this.pointHeight = pointHeight;
		rendererStarted = false;
		fpsLimit = true;
		fpsLogging = false;
		lineRender = true;
		faceRender = false;
		targetFps = fps;
		optimalTime = 1000000000/targetFps;
		invertColors = false;
		lineColor = Color.BLACK;
		backgroundColor = Color.WHITE;
		renderer.addMouseListener(new ClickListener());
		renderer.addMouseWheelListener(new ScrollListener());
		mouseClicked = false;
		scrollWheel = true;
		mode = CameraMode.DRAG;
		camPos = new Point3D(0, 0, 0);
		mouseDiff = new Point(0, 0);
		viewAngle = fovRadians;
		yAxisClamp = true;
	}
	public Display startRender() {
		if (!rendererStarted) {
			lastMousePos = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
			rendering = true;
			Thread renderer = new Thread(new Renderer());
			renderer.start();
		}
		return this;
	}
	public Display pauseRender() {
		rendering = false;
		return this;
	}
	public Display resumeRender() {
		rendering = true;
		return this;
	}
	public Display setVisible(boolean visible) {
		frame.setVisible(visible);
		return this;
	}
	public JFrame getFrame() {
		return frame;
	}
	protected class DisplayRenderer extends JComponent {
		protected static final long serialVersionUID = 1L;
		@Override
		public void paintComponent(Graphics graphics) {
			if (renderMode == RenderMode.CPU_SINGLETHREADED) {
				ArrayList<Point[]> pointArrays = new ArrayList<Point[]>();
				if (invertColors) {
					graphics.setColor(Display.invertColor(backgroundColor));
				} else {
					graphics.setColor(backgroundColor);
				}
				Dimension size = this.getSize();
				Point location = this.getLocation();
				graphics.fillRect(0, 0, size.width+location.x, size.height+location.y);
				Point mouse;
				if (mode == CameraMode.DRAG) {
					if (mouseClicked) {
						Point temp = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
						mouse = new Point(temp.x-mouseDiff.x, temp.y-mouseDiff.y);
					} else {
						mouse = lastMousePos;
					}
				} else {
					mouse = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
				}
				for (int a = 0; a < scene.object.length; a++) {
					Point[] points = new Point[scene.object[a].points.length];
					// WRITTEN BY SAM KRUG START
					for (int i = 0; i < scene.object[a].points.length; i++) {
						viewAngleX = 0;
						viewAngleY = 0;
						Point3D localCamPos = new Point3D(0, 0, 0);
						try {
							viewAngleY = -((location.y+mouse.y-size.height)/2)/sensitivity;
							if (yAxisClamp) {
								if (Math.abs((location.y+mouse.y-size.height)/2)>Math.PI/2*sensitivity) {
									if (viewAngleY < 0) {
										viewAngleY = -Math.PI/2*sensitivity;
									} else {
										viewAngleY = Math.PI/2*sensitivity;
									}
								}
							}
							viewAngleX = -((location.x+mouse.x-size.width)/2)/sensitivity;
							localCamPos = getCameraPositionActual();
						} catch (NullPointerException ex) {}
						if (scene.object[a].points[i].z*Math.cos(viewAngleX)*Math.cos(viewAngleY) + scene.object[a].points[i].x*Math.sin(viewAngleX)*Math.cos(viewAngleY) - scene.object[a].points[i].y*Math.sin(viewAngleY) < scene.camDist) {
							double zAngle = Math.atan((scene.object[a].points[i].z)/(scene.object[a].points[i].x));
							if (scene.object[a].points[i].x == 0 && scene.object[a].points[i].z == 0) {
								zAngle = 0;
							}
							double mag = Math.hypot(scene.object[a].points[i].x, scene.object[a].points[i].z);
							if (scene.object[a].points[i].x < 0) {
								xTransform = -mag*scale*Math.cos(viewAngleX+zAngle);
								yTransform = -mag*scale*Math.sin(viewAngleX+zAngle)*Math.sin(viewAngleY)+(scene.object[a].points[i].y)*scale*Math.cos(viewAngleY);
							} else {
								xTransform = mag*scale*Math.cos(viewAngleX+zAngle);
								yTransform = mag*scale*Math.sin(viewAngleX+zAngle)*Math.sin(viewAngleY)+(scene.object[a].points[i].y)*scale*Math.cos(viewAngleY);
							}
							distance.get(a).set(i, new Distance(Math.sqrt(Math.pow(localCamPos.x-(scene.object[a].points[i].x), 2)+Math.pow(localCamPos.y-scene.object[a].points[i].y, 2)+Math.pow(localCamPos.z-scene.object[a].points[i].z, 2)), i));
							double theta = Math.asin((Math.hypot(xTransform, yTransform)/scale)/distance.get(a).get(i).distance);
							camScale.get(a).set(i, distance.get(a).get(i).distance*Math.cos(theta)*Math.sin(viewAngle/2));
							points[i] = new Point((int)((size.width+location.x)/2+xTransform/camScale.get(a).get(i)), (int)((size.height+location.y)/2-yTransform/camScale.get(a).get(i)));
						}
						// WRITTEN BY SAM KRUG END
						if (renderPoints) {
							if (invertColors) {
								graphics.setColor(Color.WHITE);
							} else {
								graphics.setColor(Color.BLACK);
							}
							graphics.fillOval(points[i].x, points[i].y, pointWidth, pointHeight);
						}
					}
					if (faceRender) {
						double objDist = 0.0;
						for (int x = 0; x < distance.get(a).size(); x++) {
							objDist += distance.get(a).get(x).distance;
						}
						objDist /= (double) distance.get(a).size();
						scene.object[a].camDist = objDist;
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
						pointArrays.add(points);
					}
				}
				if (camPosPrint) {
					Point3D cameraPos = getCameraPositionActual();
					graphics.setColor(invertColor(backgroundColor));
					graphics.drawString("x: " + cameraPos.x + " // y: " + cameraPos.y + " // z: " + cameraPos.z, 0, 11);
				}
				if (faceRender) {
					for (int a = 0; a < scene.object.length; a++) {
						for (int x = a+1; x < scene.object.length; x++) {
							if (scene.object[a].camDist < scene.object[x].camDist) {
								Point[] temp = pointArrays.get(a);
								pointArrays.set(a, pointArrays.get(x));
								pointArrays.set(x, temp);
							}
						}
						for (int x = 0; x < scene.object[a].faces.length; x++) {
							for (int y = 0; y < scene.object[a].faces[x].triangles.length; y++) {
								int[] xs = {0, 0, 0};
								int[] ys = {0, 0, 0};
								try {
									int[] xs2 = {pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID1].x, pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID2].x, pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID3].x};
									int[] ys2 = {pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID1].y, pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID2].y, pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID3].y};
									xs = xs2;
									ys = ys2;
								} catch (NullPointerException ex) {}
								if (invertColors) {
									graphics.setColor(Display.invertColor(scene.object[a].faces[x].triangles[y].color));
								} else {
									graphics.setColor(scene.object[a].faces[x].triangles[y].color);
								}
								graphics.fillPolygon(xs, ys, 3);
							}
						}
					}
				}
				if (lineRender) {
					for (int a = 0; a < scene.object.length; a++) {
						if (lineRender) {
							if (invertColors) {
								graphics.setColor(Display.invertColor(lineColor));
							} else {
								graphics.setColor(lineColor);
							}
							for (int i = 0; i < scene.object[a].edges.length; i++) {
								int point1 = scene.object[a].edges[i].pointID1;
								int point2 = scene.object[a].edges[i].pointID2;
								try {graphics.drawLine(pointArrays.get(a)[point1].x, pointArrays.get(a)[point1].y, pointArrays.get(a)[point2].x, pointArrays.get(a)[point2].y);} catch (NullPointerException ex) {} catch (IndexOutOfBoundsException ex) {}
							}
						}
					}
				}
				fps++;
				this.revalidate();
			} else {
				Point3D localCamPos = new Point3D(0, 0, 0);
				Point mouse;
				if (mode == CameraMode.DRAG) {
					if (mouseClicked) {
						Point temp = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
						mouse = new Point(temp.x-mouseDiff.x, temp.y-mouseDiff.y);
					} else {
						mouse = lastMousePos;
					}
				} else {
					mouse = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
				}
				Dimension size = this.getSize();
				Point location = this.getLocation();
				viewAngleX = 0;
				viewAngleY = 0;
				// WRITTEN BY SAM KRUG START
				try {
					viewAngleY = -((location.y+mouse.y-size.height)/2)/sensitivity;
					if (yAxisClamp) {
						if (Math.abs((location.y+mouse.y-size.height)/2)>Math.PI/2*sensitivity) {
							if (viewAngleY < 0) {
								viewAngleY = -Math.PI/2*sensitivity;
							} else {
								viewAngleY = Math.PI/2*sensitivity;
							}
						}
					}
					viewAngleX = -((location.x+mouse.x-size.width)/2)/sensitivity;
					localCamPos = getCameraPositionActual();
				} catch (NullPointerException ex) {}
				localCamPosX[0] = (float) localCamPos.x;
				localCamPosY[0] = (float) localCamPos.y;
				localCamPosZ[0] = (float) localCamPos.z;
				gpuViewAngle[0] = (float) viewAngle;
				viewAngleXInput[0] = (float) viewAngleX;
				viewAngleYInput[0] = (float) viewAngleY;
				// WRITTEN BY SAM KRUG END
				int zAngleLength = 0;
				for (int x = 0; x < scene.object.length; x++) {
					zAngleLength += scene.object[x].points.length;
				}
				for (int x = 0; x < scene.object.length; x++) {
					for (int y = 0; y < scene.object[x].points.length; y++) {
						int index = (scene.object[x].points.length*x)+y;
						zAngleX[index] = (float) scene.object[x].points[y].x;
						zAngleY[index] = (float) scene.object[x].points[y].y;
						zAngleZ[index] = (float) scene.object[x].points[y].z;
					}
				}
				execute(Range.create(zAngleLength));
				ArrayList<Point[]> pointArrays = new ArrayList<Point[]>();
				if (invertColors) {
					graphics.setColor(Display.invertColor(backgroundColor));
				} else {
					graphics.setColor(backgroundColor);
				}
				graphics.fillRect(0, 0, size.width+location.x, size.height+location.y);
				for (int a = 0; a < scene.object.length; a++) {
					Point[] points = new Point[scene.object[a].points.length];
					// WRITTEN BY SAM KRUG START: HEAVILY MODIFIED
					for (int i = 0; i < scene.object[a].points.length; i++) {
						int id = (scene.object[a].points.length*a)+i;
						if (scene.object[a].points[i].z*cosViewAngleX[0]*cosViewAngleY[0] + scene.object[a].points[i].x*sinViewAngleX[0]*cosViewAngleY[0] - scene.object[a].points[i].y*sinViewAngleY[0] < scene.camDist) {
							xTransform = xTransforms[id];
							yTransform = yTransforms[id];
							distance.get(a).set(i, new Distance(maths[id], i));
							camScale.get(a).set(i, distance.get(a).get(i).distance*cosThetas[id]*sinViewAngles[id]);
							points[i] = new Point((int)((size.width+location.x)/2+xTransform/camScale.get(a).get(i)), (int)((size.height+location.y)/2-yTransform/camScale.get(a).get(i)));
						}
						// WRITTEN BY SAM KRUG END
						if (renderPoints) {
							if (invertColors) {
								graphics.setColor(Color.WHITE);
							} else {
								graphics.setColor(Color.BLACK);
							}
							graphics.fillOval(points[i].x, points[i].y, pointWidth, pointHeight);
						}
					}
					if (faceRender) {
						double objDist = 0.0;
						for (int x = 0; x < distance.get(a).size(); x++) {
							objDist += distance.get(a).get(x).distance;
						}
						objDist /= (double) distance.get(a).size();
						scene.object[a].camDist = objDist;
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
						pointArrays.add(points);
					}
				}
				if (camPosPrint) {
					Point3D cameraPos = getCameraPositionActual();
					graphics.setColor(invertColor(backgroundColor));
					graphics.drawString("x: " + cameraPos.x + " // y: " + cameraPos.y + " // z: " + cameraPos.z, 0, 11);
				}
				if (faceRender) {
					for (int a = 0; a < scene.object.length; a++) {
						for (int x = a+1; x < scene.object.length; x++) {
							if (scene.object[a].camDist < scene.object[x].camDist) {
								Point[] temp = pointArrays.get(a);
								pointArrays.set(a, pointArrays.get(x));
								pointArrays.set(x, temp);
							}
						}
						for (int x = 0; x < scene.object[a].faces.length; x++) {
							for (int y = 0; y < scene.object[a].faces[x].triangles.length; y++) {
								int[] xs = {0, 0, 0};
								int[] ys = {0, 0, 0};
								try {
									int[] xs2 = {pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID1].x, pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID2].x, pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID3].x};
									int[] ys2 = {pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID1].y, pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID2].y, pointArrays.get(a)[scene.object[a].faces[x].triangles[y].pointID3].y};
									xs = xs2;
									ys = ys2;
								} catch (NullPointerException ex) {}
								if (invertColors) {
									graphics.setColor(Display.invertColor(scene.object[a].faces[x].triangles[y].color));
								} else {
									graphics.setColor(scene.object[a].faces[x].triangles[y].color);
								}
								graphics.fillPolygon(xs, ys, 3);
							}
						}
					}
				}
				if (lineRender) {
					for (int a = 0; a < scene.object.length; a++) {
						if (lineRender) {
							if (invertColors) {
								graphics.setColor(Display.invertColor(lineColor));
							} else {
								graphics.setColor(lineColor);
							}
							for (int i = 0; i < scene.object[a].edges.length; i++) {
								int point1 = scene.object[a].edges[i].pointID1;
								int point2 = scene.object[a].edges[i].pointID2;
								try {graphics.drawLine(pointArrays.get(a)[point1].x, pointArrays.get(a)[point1].y, pointArrays.get(a)[point2].x, pointArrays.get(a)[point2].y);} catch (NullPointerException ex) {} catch (IndexOutOfBoundsException ex) {}
							}
						}
					}
				}
				fps++;
				this.revalidate();
			}
		}
	}
	protected class Renderer implements Runnable {
		public void run() {
			while (true) {
				long lastFpsTime = 0L;
				long lastLoopTime = System.nanoTime();
				while (rendering) {
				    long now = System.nanoTime();
				    long updateLength = now - lastLoopTime;
				    lastLoopTime = now;
				    lastFpsTime += updateLength;
				    if (lastFpsTime >= 1000000000) {
				    	if (fpsLogging) {
				    		System.out.println("FPS: " + fps);
				    	}
				        lastFpsTime = 0;
				        fps = 0;
				    }
				    renderFrame();
				    if (fpsLimit) {
				    	long tmp = (lastLoopTime-System.nanoTime()+optimalTime)/1000000;
				    	if (tmp > 0) {
				    		try {Thread.sleep(tmp);} catch (InterruptedException ex) {ex.printStackTrace();}
				    	}
				    }
				}
			}
		}
		protected void renderFrame() {
			getFrame().repaint();
		}
	}
	// ORIGINAL CPU CODE BY SAM KRUG, GPU ADAPTATION BY EMERY FERRARI
	public void run() {
		int id = getGlobalId();
		if (id == 0) {
			sinViewAngleX[0] = sin(viewAngleXInput[0]);
			sinViewAngleY[0] = sin(viewAngleYInput[0]);
			cosViewAngleX[0] = cos(viewAngleXInput[0]);
			cosViewAngleY[0] = cos(viewAngleYInput[0]);
		}
		float zAngles = 0f;
		float mags = 0f;
		if (!(zAngleX[id] == 0f && zAngleZ[id] == 0f)) {
			zAngles = atan(zAngleZ[id]/zAngleX[id]);
		}
		sinViewAngleXzAngle[id] = sin(viewAngleXInput[0]+zAngles);
		cosViewAngleXzAngle[id] = cos(viewAngleXInput[0]+zAngles);
		mags = hypot(zAngleX[id], zAngleZ[id]);
		if (zAngleX[id] < 0) {
			xTransforms[id] = -mags*scale*cosViewAngleXzAngle[id];
			yTransforms[id] = -mags*scale*sinViewAngleXzAngle[id]*sinViewAngleY[0]+zAngleY[id]*scale*cosViewAngleY[0];
		} else {
			xTransforms[id] = mags*scale*cosViewAngleXzAngle[id];
			yTransforms[id] = mags*scale*sinViewAngleXzAngle[id]*sinViewAngleY[0]+zAngleY[id]*scale*cosViewAngleY[0];
		}
		maths[id] = sqrt(pow(localCamPosX[0]-zAngleX[id], 2)+pow(localCamPosY[0]-zAngleY[id], 2)+pow(localCamPosZ[0]-zAngleZ[id], 2));
		cosThetas[id] = cos(asin((hypot(xTransforms[id], yTransforms[id])/scale)/maths[id]));
		sinViewAngles[id] = sin(gpuViewAngle[0]/2);
	}
	protected class ClickListener implements MouseListener {
		public void mouseEntered(MouseEvent ev) {}
		public void mousePressed(MouseEvent ev) {
			mouseClicked = true;
			Point temp = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
			mouseDiff = new Point(temp.x-lastMousePos.x, temp.y-lastMousePos.y);
		}
		public void mouseClicked(MouseEvent ev) {}
		public void mouseReleased(MouseEvent ev) {
			mouseClicked = false;
			Point temp = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
			lastMousePos = new Point(temp.x-mouseDiff.x, temp.y-mouseDiff.y);
		}
		public void mouseExited(MouseEvent ev) {}
	}
	protected class ScrollListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent ev) {
			if (scrollWheel) {
				if (ev.getWheelRotation() > 0) {
					scene.camDist *= 1.2;
				} else {
					scene.camDist /= 1.2;
				}
			}
		}
	}
	public Display setTargetFPS(int fps) {
		targetFps = fps;
		optimalTime = 1000000000 / targetFps;
		return this;
	}
	public Display enableFPSLimit() {
		fpsLimit = true;
		return this;
	}
	public Display disableFPSLimit() {
		fpsLimit = false;
		return this;
	}
	public Display enableFPSLogging() {
		fpsLogging = true;
		return this;
	}
	public Display disableFPSLogging() {
		fpsLogging = false;
		return this;
	}
	public Display enableLineRendering() {
		lineRender = true;
		return this;
	}
	public Display disableLineRendering() {
		lineRender = false;
		return this;
	}
	public Display enableFaceRendering() {
		faceRender = true;
		return this;
	}
	public Display disableFaceRendering() {
		faceRender = false;
		return this;
	}
	public Display setLineColor(Color color) {
		lineColor = color;
		return this;
	}
	public Display enableInvertColors() {
		invertColors = true;
		return this;
	}
	public Display disableInvertColors() {
		invertColors = false;
		return this;
	}
	public Display setBackgroundColor(Color color) {
		backgroundColor = color;
		return this;
	}
	public Scene getScene() {
		return scene;
	}
	public Display setScene(Scene scene) {
		this.scene = scene;
		return this;
	}
	public Display setPhysicsTimestep(int timestep) { // not an actual timestep, the precision of physics movements is proportional to the timestep, rather than inversely proportional
		physicsTimestep = timestep;
		return this;
	}
	public int getPhysicsTimestep() {
		return physicsTimestep;
	}
	public Display setCameraPositionRel(Point3D point) {
		Thread cameraPos = new CameraPos(point, this);
		cameraPos.start();
		return this;
	}
	public Display transitionCameraPositionRel(Point3D point, int millis) {
		Thread transition = new Transition(point, millis, this);
		transition.start();
		return this;
	}
	public Point3D getCameraPosition() {
		return camPos;
	}
	protected class CameraPos extends Thread {
		protected double xt;
		protected double yt;
		protected double zt;
		protected Display display;
		protected CameraPos(Point3D point, Display display) {
			this.xt = point.x;
			this.yt = point.y;
			this.zt = point.z;
			this.display = display;
		}
		@Override
		public void run() {
			for (int i = 0; i < scene.object.length; i++) {
				scene.object[i].movePosRel(xt, yt, zt, display);
			}
			camPos.x += xt;
			camPos.y += yt;
			camPos.z += zt;
		}
	}
	protected class Transition extends Thread {
		protected double xt;
		protected double yt;
		protected double zt;
		protected int millis;
		protected Display display;
		protected Transition(Point3D point, int millis, Display display) {
			this.xt = point.x;
			this.yt = point.y;
			this.zt = point.z;
			this.millis = millis;
			this.display = display;
		}
		@Override
		public void run() {
			double xIteration = xt/(double)physicsTimestep*((double)millis/1000.0);
			double yIteration = yt/(double)physicsTimestep*((double)millis/1000.0);
			double zIteration = zt/(double)physicsTimestep*((double)millis/1000.0);
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			final long OPTIMAL_TIME = 1000000000 / physicsTimestep;
			for (int x = 0; x < (int)(physicsTimestep*((double)millis/1000.0)); x++) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    lastFpsTime += updateLength;
			    if (lastFpsTime >= 1000000000) {
			        lastFpsTime = 0;
			    }
			    for (int y = 0; y < scene.object.length; y++) {
			    	scene.object[y].movePosRel(xIteration, yIteration, zIteration, display);
			    }
			    try {Thread.sleep((lastLoopTime-System.nanoTime()+OPTIMAL_TIME)/1000000);} catch (InterruptedException ex) {ex.printStackTrace();}
			}
			camPos.x += xt;
			camPos.y += yt;
			camPos.z += zt;
		}
	}
	public Display enableScrollWheel() {
		scrollWheel = true;
		return this;
	}
	public Display disableScrollWheel() {
		scrollWheel = false;
		return this;
	}
	public Display setCameraDistance(double distance) {
		scene.camDist = distance;
		return this;
	}
	public Display setCameraMode(CameraMode mode) {
		this.mode = mode;
		return this;
	}
	public CameraMode getCameraMode() {
		return mode;
	}
	protected static Color invertColor(Color color) {
		return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue(), color.getAlpha());
	}
	public Point3D getCameraPositionActual() {
		double x = (Math.sin(viewAngleX)*Math.cos(viewAngleY)*scene.camDist) + camPos.x;
		double y = -((Math.sin(viewAngleY)*scene.camDist) + camPos.y);
		double z = (Math.cos(viewAngleX)*Math.cos(viewAngleY)*scene.camDist) + camPos.z;
		return new Point3D(x, y, z);
	}
	public Display enableCameraPositionPrinting() {
		camPosPrint = true;
		return this;
	}
	public Display disableCameraPositionPrinting() {
		camPosPrint = false;
		return this;
	}
	public double getFOVRadians() {
		return viewAngle;
	}
	public Display setFOVRadians(double viewAngle) {
		if (Math.abs(viewAngle) < Math.PI) {
			this.viewAngle = viewAngle;
		}
		return this;
	}
	public double getFOVDegrees() {
		return Math.toDegrees(viewAngle);
	}
	public Display setFOVDegrees(double degrees) {
		setFOVRadians(Math.toRadians(degrees));
		return this;
	}
	public Display enableYAxisClamping() {
		yAxisClamp = true;
		return this;
	}
	public Display disableYAxisClamping() {
		yAxisClamp = false;
		return this;
	}
	public Display setRenderMode(RenderMode renderMode) {
		this.renderMode = renderMode;
		return this;
	}
	public RenderMode getRenderMode() {
		return renderMode;
	}
}