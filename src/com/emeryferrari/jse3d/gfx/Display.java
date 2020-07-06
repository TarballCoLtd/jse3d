package com.emeryferrari.jse3d.gfx;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import com.aparapi.*;
import com.aparapi.device.*;
import com.emeryferrari.jse3d.*;
import com.emeryferrari.jse3d.enums.*;
import com.emeryferrari.jse3d.exc.*;
import com.emeryferrari.jse3d.obj.*;
/** Represents a jse3d frame.
 * @author Emery Ferrari
 * @author Sam Krug
 * 
 */
public class Display extends Kernel { // kernel extension necessary for OpenCL rendering
	protected DisplayRenderer renderer; // a JComponent that handles rendering
	protected Scene scene; // the current scene
	protected JFrame frame; // the frame that the scene is rendered in
	protected boolean rendering; // true if the startRender() method has been called
	protected boolean rendererStarted; // true if the startRender() method has been called
	protected long optimalTime; // time variable used by FPS timer
	protected Point lastMousePos; // mouse variable used by view angle changers
	protected boolean mouseClicked; // mouse variable used by view angle changers
	protected Point mouseDiff; // mouse variable used by view angle changers
	protected Vector3 camPos; // current camera position in relation to (0,0,0)
	protected Distance[][] distance; // used internally by scene renderer, represents distances between points and the camera
	protected double[][] camScale; // used internally by scene renderer
	protected double xTransform = 0; // used internally by scene renderer
	protected double yTransform = 0; // used internally by scene renderer
	protected ViewAngle sphere; // used internally by scene renderer, represents a point on a sphere around the current camera position
	protected int fps = 0; // time variable used by FPS timer
	protected RenderingHints hints; // current rendering hints
	protected Point mouse;
	protected Point[][] pointArrays; // array of 2D point arrays where 3D points should be rendered on the frame
	protected Vector3 localCamPos;
	protected Time time; // controls delta time and fixed delta time for this Display instance
	protected ParticleKernel particleKernel; // kernel responsible for calculating particle positions with OpenCL
	private DisplaySettings settings; // display settings
	// OpenCL OBJECT VARIABLES
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
	protected int zAngleLength;
	// OpenCL PARTICLE VARIABLES
	// coming soon
	public Display(Scene scene) {
		this(scene, "");
	}
	public Display(Scene scene, String frameTitle) {
		this(scene, frameTitle, true);
	}
	public Display(Scene scene, String frameTitle, int maxPointsTotal,int maxPointsObject,  int maxObjects) {
		this(scene, frameTitle, true, maxPointsTotal, maxPointsObject, maxObjects);
	}
	public Display(Scene scene, String frameTitle, double fovRadians) {
		this(scene, frameTitle, true, fovRadians);
	}
	public Display(Scene scene, String frameTitle, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this(scene, frameTitle, true, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible) {
		this (scene, frameTitle, frameVisible, false);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this (scene, frameTitle, frameVisible, false, maxPointsTotal, maxPointsObject, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, double fovRadians) {
		this (scene, frameTitle, frameVisible, false, fovRadians);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this (scene, frameTitle, frameVisible, false, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500, fovRadians);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight) {
		this(scene, frameTitle, frameVisible, renderPoints, new Dimension(5, 5), frameWidth, frameHeight);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, new Dimension(5, 5), frameWidth, frameHeight, fovRadians);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, new Dimension(5, 5), frameWidth, frameHeight, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, Dimension pointSize, int frameWidth, int frameHeight) {
		this(scene, frameTitle, frameVisible, renderPoints, pointSize, frameWidth, frameHeight, Math.toRadians(80));
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, Dimension pointSize, int frameWidth, int frameHeight, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, pointSize, frameWidth, frameHeight, 60, fovRadians, 4096, 32, 128);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, Dimension pointSize, int frameWidth, int frameHeight, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, pointSize, frameWidth, frameHeight, 60, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, Dimension pointSize, int frameWidth, int frameHeight, int fps, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		time = new Time();
		settings = new DisplaySettings();
		calculateRenderingHints();
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
		renderer = new DisplayRenderer();
		this.scene = scene;
		frame = new JFrame((frameTitle.equals("") ? JSE3DConst.FULL_NAME : frameTitle + " // " + JSE3DConst.FULL_NAME) + (System.getProperty("user.dir").equals("X:\\Libraries\\Documents\\GitHub\\jse3d") ? " development build" : ""));
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(frameVisible);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rendering = false;
		frame.getContentPane().add(BorderLayout.CENTER, renderer);
		distance = new Distance[maxObjects][maxPointsObject];
		camScale = new double[maxObjects][maxPointsObject];
		settings.renderPoints = renderPoints;
		settings.pointSize = pointSize;
		rendererStarted = false;
		settings.targetFps = fps;
		optimalTime = 1000000000/settings.targetFps;
		renderer.addMouseListener(new ClickListener());
		renderer.addMouseWheelListener(new ScrollListener());
		mouseClicked = false;
		
		camPos = new Vector3(0, 0, 0);
		mouseDiff = new Point(0, 0);
		settings.viewAngle = fovRadians;
	}
	public Display startRender() { // call this to make the frame visible and start rendering
		if (!rendererStarted) {
			particleKernel = new ParticleKernel(this);
			for (int i = 0; i < scene.object.length; i++) {
				try {scene.object[i].start();} catch (NullPointerException ex) {}
			}
			for (int i = 0; i < scene.particles.size(); i++) {
				try {scene.particles.get(i).start();} catch (NullPointerException ex) {}
			}
			lastMousePos = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
			rendering = true;
			Thread renderer = new Thread(new Renderer());
			renderer.start();
			Thread physics = new Thread(new Physics());
			physics.start();
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
	protected class DisplayRenderer extends JComponent { // scene renderer
		protected static final long serialVersionUID = 1L;
		@Override
		public void paintComponent(Graphics gfx) {
			if (rendering) {
				renderFrame((Graphics2D)gfx, this);
			}
		}
	}
	protected void renderFrame(Graphics2D graphics, DisplayRenderer renderer) {
		localCamPos = new Vector3(0, 0, 0);
		try {localCamPos = getCameraPositionActual();} catch (NullPointerException ex) {}
		try {graphics.setRenderingHints(hints);} catch (ConcurrentModificationException ex) {} // sets rendering hints
		Dimension size = renderer.getSize();
		Point location = renderer.getLocation();
		if (settings.renderTarget == RenderTarget.CPU_SINGLETHREADED) { // this will be called if the render target is the CPU in singlethreaded mode, this does not require any dependencies
			pointArrays = new Point[scene.object.length][];
			renderBackground(graphics, size, location);
			calculateMouse();
			calculateViewAngles(size, location);
			for (int a = 0; a < scene.object.length; a++) {
				Point[] points = new Point[scene.object[a].points.length];
				for (int i = 0; i < scene.object[a].points.length; i++) {
					points[i] = calculatePoint(a, i, size, location);
					if (settings.renderPoints) {
						renderPoint(graphics, points[i], a, i);
					}
				}
				if (settings.faceRender) { // sorts faces so that they're rendered back to front
					sortFaces(a, points);
				}
			}					
			renderExtras(graphics, size, location);
		} else { // called if OpenCL should be used, whether its on a graphics card or CPU in multithreaded mode
			// note: AMD discontinued OpenCL for their CPUs in a 2018 revision of their driver software
			calculateMouse();
			calculateViewAngles(size, location);
			prepareGPU(localCamPos);
			calculateOnGPU();
			pointArrays = new Point[scene.object.length][];
			renderBackground(graphics, size, location);
			for (int a = 0; a < scene.object.length; a++) {
				Point[] points = new Point[scene.object[a].points.length];
				for (int i = 0; i < scene.object[a].points.length; i++) {
					points[i] = calculatePointGPU(a, i, size, location);
					if (settings.renderPoints) {
						renderPoint(graphics, points[i], a, i);
					}
				}
				if (settings.faceRender) { // sorts faces so that they're rendered from back to front
					sortFaces(a, points);
				}
			}
			renderExtras(graphics, size, location);
		}
		fps++;
		renderer.revalidate();
	}
	
	protected void renderExtras(Graphics2D graphics, Dimension size, Point location) {
		if (settings.camPosPrint) {
			printCameraPosition(graphics);
		}
		if (settings.faceRender) { // sorts faces so that they're rendered from back to front
			renderFaces(graphics);
		}
		if (settings.lineRender) {
			renderLines(graphics);
		}
		for (int a = 0; a < scene.particles.size(); a++) {
			renderParticle(graphics, calculateParticle(a, size, location), scene.particles.get(a).getPosition());
		}
	}
	protected Point calculateParticle(int particleID, Dimension size, Point location) {
		// the following if statement checks if this point is in front of the camera, not behind, and hence, if it should be rendered or not
		if (scene.particles.get(particleID).getPosition().getZ()*sphere.cosViewAngleX*sphere.cosViewAngleY + scene.particles.get(particleID).getPosition().getX()*sphere.sinViewAngleX*sphere.cosViewAngleY - scene.particles.get(particleID).getPosition().getY()*sphere.sinViewAngleY < scene.camDist) {
			// 3D to 2D point conversion
			double zAngle = Math.atan(scene.particles.get(particleID).getPosition().getZ()/scene.particles.get(particleID).getPosition().getX());
			if (scene.particles.get(particleID).getPosition().getX() == 0 && scene.particles.get(particleID).getPosition().getZ() == 0) {
				zAngle = 0;
			}
			double mag = Math.hypot(scene.particles.get(particleID).getPosition().getX(), scene.particles.get(particleID).getPosition().getZ());
			if (scene.particles.get(particleID).getPosition().getX() < 0) {
				xTransform = -mag*DisplaySettings.SCALE*Math.cos(sphere.viewAngleX+zAngle);
				yTransform = -mag*DisplaySettings.SCALE*Math.sin(sphere.viewAngleX+zAngle)*sphere.sinViewAngleY+(scene.particles.get(particleID).getPosition().getY())*DisplaySettings.SCALE*sphere.cosViewAngleY;
			} else {
				xTransform = mag*DisplaySettings.SCALE*Math.cos(sphere.viewAngleX+zAngle);
				yTransform = mag*DisplaySettings.SCALE*Math.sin(sphere.viewAngleX+zAngle)*sphere.sinViewAngleY+(scene.particles.get(particleID).getPosition().getY())*DisplaySettings.SCALE*sphere.cosViewAngleY;
			}
			double distance = Math3D.hypot3(localCamPos.getX()-scene.particles.get(particleID).getPosition().getX(), localCamPos.getY()-scene.particles.get(particleID).getPosition().getY(), localCamPos.getZ()-scene.particles.get(particleID).getPosition().getZ());
			double theta = Math.asin((Math.hypot(xTransform, yTransform)/DisplaySettings.SCALE)/distance);
			double camScale = distance*Math.cos(theta)*Math.sin(settings.viewAngle/2);
			return new Point((int)((size.width+location.x)/2+xTransform/camScale), (int)((size.height+location.y)/2-yTransform/camScale));
		}
		return null;
	}
	protected Point calculatePoint(int a, int i, Dimension size, Point location) {
		// the following if statement checks if this point is in front of the camera, not behind, and hence, if it should be rendered or not
		if (scene.object[a].points[i].getZ()*sphere.cosViewAngleX*sphere.cosViewAngleY + scene.object[a].points[i].getX()*sphere.sinViewAngleX*sphere.cosViewAngleY - scene.object[a].points[i].getY()*sphere.sinViewAngleY < scene.camDist) {
			// 3D to 2D point conversion
			double zAngle = Math.atan((scene.object[a].points[i].getZ())/(scene.object[a].points[i].getX()));
			if (scene.object[a].points[i].getX() == 0 && scene.object[a].points[i].getZ() == 0) {
				zAngle = 0;
			}
			double mag = Math.hypot(scene.object[a].points[i].getX(), scene.object[a].points[i].getZ());
			if (scene.object[a].points[i].getX() < 0) {
				xTransform = -mag*DisplaySettings.SCALE*Math.cos(sphere.viewAngleX+zAngle);
				yTransform = -mag*DisplaySettings.SCALE*Math.sin(sphere.viewAngleX+zAngle)*sphere.sinViewAngleY+(scene.object[a].points[i].getY())*DisplaySettings.SCALE*sphere.cosViewAngleY;
			} else {
				xTransform = mag*DisplaySettings.SCALE*Math.cos(sphere.viewAngleX+zAngle);
				yTransform = mag*DisplaySettings.SCALE*Math.sin(sphere.viewAngleX+zAngle)*sphere.sinViewAngleY+(scene.object[a].points[i].getY())*DisplaySettings.SCALE*sphere.cosViewAngleY;
			}
			distance[a][i] = new Distance(Math3D.hypot3(localCamPos.getX()-scene.object[a].points[i].getX(), localCamPos.getY()-scene.object[a].points[i].getY(), localCamPos.getZ()-scene.object[a].points[i].getZ()), i);
			double theta = Math.asin((Math.hypot(xTransform, yTransform)/DisplaySettings.SCALE)/distance[a][i].distance);
			camScale[a][i] = distance[a][i].distance*Math.cos(theta)*Math.sin(settings.viewAngle/2);
			return new Point((int)((size.width+location.x)/2+xTransform/camScale[a][i]), (int)((size.height+location.y)/2-yTransform/camScale[a][i]));
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	protected void calculateOnGPU() {
		Device chosen = null;
		if (settings.renderTarget == RenderTarget.CPU_MULTITHREADED) { // checks which device should be used for rendering
			chosen = Device.firstCPU();
			if (chosen == null) {
				System.err.println("FATAL ERROR: The OpenCL driver for your CPU is not installed, but it is required for the CPU multithreading feature. Either install the OpenCL driver for the selected device, or set the render mode to RenderMode.CPU_SINGLETHREADED.");
				throw new CPU_OpenCLDriverNotFoundError();
			}
		} else {
			chosen = Device.bestGPU();
			if (chosen == null) {
				System.err.println("FATAL ERROR: The OpenCL driver for your GPU is not installed, but it is required for the GPU rendering feature. Either install the OpenCL driver for the selected device, or set the render mode to RenderMode.CPU_SINGLETHREADED.");
				throw new GPU_OpenCLDriverNotFoundError();
			}
		}
		try { // calculates multiple points concurrently on the selected OpenCL device
			Range range = chosen.createRange(zAngleLength);
			range.setLocalSize_0(zAngleLength);
			execute(range);
		} catch (AssertionError err) {
			if (!settings.assertion) {
				System.err.println("java.lang.AssertionError: Selected OpenCL device not available. This is a bug with jse3d. In the meantime, try setting the number of points in the scene to a multiple of how many cores the selected device has as a temporary workaround. Normally this is a power of 2, however that is not always true.");
				settings.assertion = true;
			}
		}
	}
	protected void prepareGPU(Vector3 localCamPos) {
		localCamPosX[0] = (float) localCamPos.getX();
		localCamPosY[0] = (float) localCamPos.getY();
		localCamPosZ[0] = (float) localCamPos.getZ();
		gpuViewAngle[0] = (float) settings.viewAngle;
		viewAngleXInput[0] = (float) sphere.viewAngleX;
		viewAngleYInput[0] = (float) sphere.viewAngleY;
		zAngleLength = 0;
		for (int x = 0; x < scene.object.length; x++) {
			zAngleLength += scene.object[x].points.length;
		}
		for (int x = 0; x < scene.object.length; x++) {
			for (int y = 0; y < scene.object[x].points.length; y++) {
				int index = (scene.object[x].points.length*x)+y;
				zAngleX[index] = (float) scene.object[x].points[y].getX();
				zAngleY[index] = (float) scene.object[x].points[y].getY();
				zAngleZ[index] = (float) scene.object[x].points[y].getZ();
			}
		}
	}
	protected void renderBackground(Graphics2D graphics, Dimension size, Point location) {
		graphics.setColor(settings.backgroundColor);
		graphics.fillRect(0, 0, size.width+location.x, size.height+location.y);
	}
	protected void renderPoint(Graphics2D graphics, Point point, int a, int i) {
		graphics.setColor(Color.BLACK);
		double reciprocal = 0.0;
		try {reciprocal = 1.0/distance[a][i].distance;} catch (NullPointerException ex) {}
		int width = (int)(settings.pointSize.width*reciprocal);
		int height = (int)(settings.pointSize.height*reciprocal);
		try {graphics.fillOval(point.x-(width/2), point.y-(height/2), width, height);} catch (NullPointerException ex) {}
	}
	protected void renderParticle(Graphics2D graphics, Point point, Vector3 position) {
		graphics.setColor(Color.BLACK);
		double reciprocal = 1.0/Math3D.hypot3(localCamPos.getX()-position.getX(), localCamPos.getY()-position.getY(), localCamPos.getZ()-position.getZ());
		try {graphics.fillOval(point.x, point.y, (int)(settings.pointSize.width*reciprocal), (int)(settings.pointSize.height*reciprocal));} catch (NullPointerException ex) {}
	}
	protected void printCameraPosition(Graphics2D graphics) {
		Vector3 cameraPos = getCameraPositionActual();
		graphics.setColor(invertColor(settings.backgroundColor));
		graphics.drawString("x: " + cameraPos.getX() + " // y: " + cameraPos.getY() + " // z: " + cameraPos.getZ(), 0, 11);
	}
	protected static Color invertColor(Color color) {
		int r = 255-color.getRed();
		int g = 255-color.getGreen();
		int b = 255-color.getGreen();
		return new Color(r, g, b, color.getAlpha());
	}
	protected void calculateViewAngles(Dimension size, Point location) {
		double viewAngleX = 0;
		double viewAngleY = 0;
		try {
			viewAngleY = -((location.y+mouse.y-size.height)/2)/DisplaySettings.SENSITIVITY;
			if (settings.yAxisClamp) {
				if (Math.abs((location.y+mouse.y-size.height)/2)>Math.PI/2*DisplaySettings.SENSITIVITY) {
					if (viewAngleY < 0) {
						viewAngleY = -Math.PI/2*DisplaySettings.SENSITIVITY;
					} else {
						viewAngleY = Math.PI/2*DisplaySettings.SENSITIVITY;
					}
				}
			}
			viewAngleX = -((location.x+mouse.x-size.width)/2)/DisplaySettings.SENSITIVITY;
		} catch (NullPointerException ex) {}
		sphere = new ViewAngle(viewAngleX, viewAngleY);
	}
	protected void renderFaces(Graphics2D graphics) {
		for (int a = 0; a < scene.object.length; a++) {
			for (int x = a+1; x < scene.object.length; x++) {
				if (scene.object[a].camDist < scene.object[x].camDist) {
					Point[] temp = pointArrays[a];
					pointArrays[a] = pointArrays[x];
					pointArrays[x] = temp;
				}
			}
			for (int x = 0; x < scene.object[a].faces.length; x++) {
				for (int y = 0; y < scene.object[a].faces[x].triangles.length; y++) {
					int[] xs = {0, 0, 0};
					int[] ys = {0, 0, 0};
					try {
						int[] xs2 = {pointArrays[a][scene.object[a].faces[x].triangles[y].pointID1].x, pointArrays[a][scene.object[a].faces[x].triangles[y].pointID2].x, pointArrays[a][scene.object[a].faces[x].triangles[y].pointID3].x};
						int[] ys2 = {pointArrays[a][scene.object[a].faces[x].triangles[y].pointID1].y, pointArrays[a][scene.object[a].faces[x].triangles[y].pointID2].y, pointArrays[a][scene.object[a].faces[x].triangles[y].pointID3].y};
						xs = xs2;
						ys = ys2;
					} catch (NullPointerException ex) {}
					graphics.setColor(scene.object[a].faces[x].triangles[y].color);
					graphics.fillPolygon(xs, ys, 3);
				}
			}
		}
	}
	protected void sortFaces(int a, Point[] points) {
		double objDist = 0.0;
		int length = distance[a].length;
		for (int x = 0; x < distance[a].length; x++) {
			try {
				objDist += distance[a][x].distance;
			} catch (NullPointerException ex) {
				length--;
			}
		}
		objDist /= (double) length;
		scene.object[a].camDist = objDist;
		for (int x = 0; x < scene.object[a].faces.length; x++) {
			int[] pointIDs = scene.object[a].faces[x].getPointIDs();
			double[] distances = new double[pointIDs.length];
			for (int y = 0; y < pointIDs.length; y++) {
				for (int z = 0; z < distance[a].length; z++) {
					try {
						if (distance[a][z].pointID == pointIDs[y]) {
							distances[y] = distance[a][z].distance;
						}
					} catch (NullPointerException ex) {}
				}
			}
			double average = 0.0;
			for (int i = 0; i < distances.length; i++) {
				average += distances[i];
			}
			average /= (double) distances.length;
			scene.object[a].faces[x].camDist = average;
		}
		Arrays.sort(scene.object[a].faces, Collections.reverseOrder());
		pointArrays[a] = points;
	}
	protected void renderLines(Graphics2D graphics) {
		for (int a = 0; a < scene.object.length; a++) {
			graphics.setColor(settings.lineColor);
			for (int i = 0; i < scene.object[a].edges.length; i++) {
				int point1 = scene.object[a].edges[i].pointID1;
				int point2 = scene.object[a].edges[i].pointID2;
				try {graphics.drawLine(pointArrays[a][point1].x, pointArrays[a][point1].y, pointArrays[a][point2].x, pointArrays[a][point2].y);} catch (NullPointerException ex) {} catch (IndexOutOfBoundsException ex) {}
			}
		}
	}
	protected Point calculatePointGPU(int a, int i, Dimension size, Point location) {
		int id = (scene.object[a].points.length*a)+i;
		distance[a][i] = new Distance(0.0, -1);
		if (scene.object[a].points[i].getZ()*cosViewAngleX[0]*cosViewAngleY[0] + scene.object[a].points[i].getX()*sinViewAngleX[0]*cosViewAngleY[0] - scene.object[a].points[i].getY()*sinViewAngleY[0] < scene.camDist) {
			xTransform = xTransforms[id];
			yTransform = yTransforms[id];
			distance[a][i] = new Distance(maths[id], i);
			camScale[a][i] = distance[a][i].distance*cosThetas[id]*sinViewAngles[id];
			return new Point((int)((size.width+location.x)/2+xTransform/camScale[a][i]), (int)((size.height+location.y)/2-yTransform/camScale[a][i]));
		}
		return null;
	}
	protected void calculateMouse() {
		if (settings.mode == CameraMode.DRAG) {
			if (mouseClicked) {
				Point temp = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
				mouse = new Point(temp.x-mouseDiff.x, temp.y-mouseDiff.y);
			} else {
				mouse = lastMousePos;
			}
		} else {
			mouse = new Point(MouseInfo.getPointerInfo().getLocation().x-frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-frame.getLocationOnScreen().y);
		}
	}
	protected class Renderer implements Runnable {
		public void run() {
			calculateRenderingHints();
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			while (true) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    lastFpsTime += updateLength;
			    if (lastFpsTime >= 1000000000) {
			    	if (settings.fpsLogging) {
			    		System.out.println("FPS: " + (fps < 1 ? 0 : fps-1));
			    	}
			        lastFpsTime = 0;
			        fps = 0;
			    }
			    renderFrame();
			    time.reset();
			    for (int i = 0; i < scene.object.length; i++) {
			    	try {scene.object[i].update();} catch (NullPointerException ex) {}
			    }
			    for (int i = 0; i < scene.particles.size(); i++) {
			    	try {scene.particles.get(i).update();} catch (NullPointerException ex) {}
			    }
			    if (settings.fpsLimit) {
			    	long tmp = (lastLoopTime-System.nanoTime()+optimalTime)/1000000;
			    	try {Thread.sleep(tmp > 0 ? tmp : 0);} catch (InterruptedException ex) {ex.printStackTrace();}
			    }
			}
		}
		private void renderFrame() {
			getFrame().repaint();
		}
	}
	protected class Physics implements Runnable {
		public void run() {
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			long OPTIMAL_TIME = 1000000000/settings.physicsTimestep;
			while (true) {
				long now = System.nanoTime();
				long updateLength = now-lastLoopTime;
				lastLoopTime = now;
				lastFpsTime += updateLength;
				if (lastFpsTime >= 1000000000) {
					lastFpsTime = 0L;
					OPTIMAL_TIME = 1000000000/settings.physicsTimestep;
				}
				for (int i = 0; i < scene.object.length; i++) {
					try {scene.object[i].fixedUpdate();} catch (NullPointerException ex) {}
				}
				for (int i = 0; i < scene.particles.size(); i++) {
					try {scene.particles.get(i).fixedUpdate();} catch (NullPointerException ex) {}
				}
				time.fixedReset();
				long tmp = (lastLoopTime-System.nanoTime()+OPTIMAL_TIME)/1000000;
				try {Thread.sleep(tmp > 0 ? tmp : 0);} catch (InterruptedException ex) {ex.printStackTrace();}
			}
		}
	}
	protected void calculateRenderingHints() { // creates a rendering hints object based on the settings currently in place, this is applied at the start of every new frame, and recalculated whenever a rendering hint is changed
		if (settings.antialiasingHint) {
			hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		if (settings.renderingHint == RenderMode.PERFORMANCE) {
			hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED));
		} else {
			hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		}
		if (settings.ditheringHint) {
			hints.add(new RenderingHints(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE));
		} else {
			hints.add(new RenderingHints(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE));
		}
		if (settings.colorRenderingHint == RenderMode.PERFORMANCE) {
			hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED));
		} else {
			hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY));
		}
		if (settings.fractionalMetricsHint) {
			hints.add(new RenderingHints(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON));
		} else {
			hints.add(new RenderingHints(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF));
		}
		if (settings.textAntialiasingHint) {
			hints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
		} else {
			hints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF));
		}
		if (settings.interpolationHint == InterpolationMode.BICUBIC) {
			hints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
		} else if (settings.interpolationHint == InterpolationMode.BILINEAR) {
			hints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR));
		} else {
			hints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR));
		}
		if (settings.alphaInterpolationHint == RenderMode.PERFORMANCE) {
			hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED));
		} else {
			hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));
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
			xTransforms[id] = -mags*DisplaySettings.SCALE*cosViewAngleXzAngle[id];
			yTransforms[id] = -mags*DisplaySettings.SCALE*sinViewAngleXzAngle[id]*sinViewAngleY[0]+zAngleY[id]*DisplaySettings.SCALE*cosViewAngleY[0];
		} else {
			xTransforms[id] = mags*DisplaySettings.SCALE*cosViewAngleXzAngle[id];
			yTransforms[id] = mags*DisplaySettings.SCALE*sinViewAngleXzAngle[id]*sinViewAngleY[0]+zAngleY[id]*DisplaySettings.SCALE*cosViewAngleY[0];
		}
		maths[id] = sqrt(pow(localCamPosX[0]-zAngleX[id], 2)+pow(localCamPosY[0]-zAngleY[id], 2)+pow(localCamPosZ[0]-zAngleZ[id], 2));
		cosThetas[id] = cos(asin((hypot(xTransforms[id], yTransforms[id])/DisplaySettings.SCALE)/maths[id]));
		sinViewAngles[id] = sin(gpuViewAngle[0]/2);
	}
	protected class ClickListener implements MouseListener { // calculations for CameraMode.DRAG
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
	protected class ScrollListener implements MouseWheelListener { // controls scroll wheel camera distance changes
		public void mouseWheelMoved(MouseWheelEvent ev) {
			if (settings.scrollWheel) {
				if (ev.getWheelRotation() > 0) {
					scene.camDist *= 1.2;
				} else {
					scene.camDist /= 1.2;
				}
			}
		}
	}
	public Display setTargetFPS(int fps) {
		settings.targetFps = fps;
		optimalTime = 1000000000 / settings.targetFps;
		return this;
	}
	public Display enableFPSLimit() {
		settings.fpsLimit = true;
		return this;
	}
	public Display disableFPSLimit() {
		settings.fpsLimit = false;
		return this;
	}
	public Display enableFPSLogging() {
		settings.fpsLogging = true;
		return this;
	}
	public Display disableFPSLogging() {
		settings.fpsLogging = false;
		return this;
	}
	public Display enableLineRendering() {
		settings.lineRender = true;
		return this;
	}
	public Display disableLineRendering() {
		settings.lineRender = false;
		return this;
	}
	public Display enableFaceRendering() {
		settings.faceRender = true;
		return this;
	}
	public Display disableFaceRendering() {
		settings.faceRender = false;
		return this;
	}
	public Display setLineColor(Color color) {
		settings.lineColor = color;
		return this;
	}
	public Display setBackgroundColor(Color color) {
		settings.backgroundColor = color;
		return this;
	}
	public Scene getScene() {
		return scene;
	}
	public Display setScene(Scene scene) {
		for (int i = 0; i < scene.object.length; i++) {
			try {scene.object[i].start();} catch (NullPointerException ex) {}
		}
		for (int i = 0; i < scene.particles.size(); i++) {
			try {scene.particles.get(i).start();} catch (NullPointerException ex) {}
		}
		this.scene = scene;
		return this;
	}
	public Display setPhysicsTimestep(int timestep) { // not an actual timestep, the precision of physics movements is proportional to the timestep, rather than inversely proportional
		settings.physicsTimestep = timestep;
		return this;
	}
	public int getPhysicsTimestep() {
		return settings.physicsTimestep;
	}
	public Display setCameraPositionRel(Vector3 point) {
		Thread cameraPos = new CameraPos(point, this);
		cameraPos.start();
		return this;
	}
	public Display transitionCameraPositionRel(Vector3 point, int millis) {
		Thread transition = new Transition(point, millis, this);
		transition.start();
		return this;
	}
	public Vector3 getCameraPosition() {
		return camPos;
	}
	protected class CameraPos extends Thread {
		protected double xt;
		protected double yt;
		protected double zt;
		protected Display display;
		protected CameraPos(Vector3 point, Display display) {
			this.xt = point.getX();
			this.yt = point.getY();
			this.zt = point.getZ();
			this.display = display;
		}
		@Override
		public void run() {
			for (int i = 0; i < scene.object.length; i++) {
				scene.object[i].movePosRel(-xt, -yt, -zt, display);
			}
			camPos.set(camPos.getX()+xt, camPos.getY()+yt, camPos.getZ()+zt);
		}
	}
	protected class Transition extends Thread {
		protected double xt;
		protected double yt;
		protected double zt;
		protected int millis;
		protected Display display;
		protected Transition(Vector3 point, int millis, Display display) {
			this.xt = point.getX();
			this.yt = point.getY();
			this.zt = point.getZ();
			this.millis = millis;
			this.display = display;
		}
		@Override
		public void run() {
			double xIteration = -(xt/(double)settings.physicsTimestep*((double)millis/1000.0));
			double yIteration = -(yt/(double)settings.physicsTimestep*((double)millis/1000.0));
			double zIteration = -(zt/(double)settings.physicsTimestep*((double)millis/1000.0));
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			long OPTIMAL_TIME = 1000000000 / settings.physicsTimestep;
			for (int x = 0; x < (int)(settings.physicsTimestep*((double)millis/1000.0)); x++) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    lastFpsTime += updateLength;
			    if (lastFpsTime >= 1000000000) {
			    	xIteration = xt/(double)settings.physicsTimestep*((double)millis/1000.0);
			    	yIteration = yt/(double)settings.physicsTimestep*((double)millis/1000.0);
			    	zIteration = zt/(double)settings.physicsTimestep*((double)millis/1000.0);
			    	OPTIMAL_TIME = 1000000000 / settings.physicsTimestep;
			        lastFpsTime = 0;
			    }
			    for (int y = 0; y < scene.object.length; y++) {
			    	scene.object[y].movePosRel(xIteration, yIteration, zIteration, display);
			    }
			    try {Thread.sleep((lastLoopTime-System.nanoTime()+OPTIMAL_TIME)/1000000);} catch (InterruptedException ex) {ex.printStackTrace();}
			}
			camPos.set(camPos.getX()+xt, camPos.getY()+yt, camPos.getZ()+zt);
		}
	}
	public Display enableScrollWheel() {
		settings.scrollWheel = true;
		return this;
	}
	public Display disableScrollWheel() {
		settings.scrollWheel = false;
		return this;
	}
	public Display setCameraDistance(double distance) {
		scene.camDist = distance;
		return this;
	}
	public Display setCameraMode(CameraMode mode) {
		settings.mode = mode;
		return this;
	}
	public CameraMode getCameraMode() {
		return settings.mode;
	}
	public Vector3 getCameraPositionActual() {
		double x = (sphere.sinViewAngleX*sphere.cosViewAngleY*scene.camDist) + camPos.getX();
		double y = -((sphere.sinViewAngleY*scene.camDist) + camPos.getY());
		double z = (sphere.cosViewAngleX*sphere.cosViewAngleY*scene.camDist) + camPos.getZ();
		return new Vector3(x, y, z);
	}
	public Display enableCameraPositionPrinting() {
		settings.camPosPrint = true;
		return this;
	}
	public Display enableCameraPositionPrinting(Point pos) {
		settings.camPosPrintPoint = pos;
		return enableCameraPositionPrinting();
	}
	public Display disableCameraPositionPrinting() {
		settings.camPosPrint = false;
		return this;
	}
	public Display setCameraPositionPrintingPosition(Point pos) {
		settings.camPosPrintPoint = pos;
		return this;
	}
	public double getFOVRadians() {
		return settings.viewAngle;
	}
	public Display setFOVRadians(double viewAngle) {
		if (Math.abs(viewAngle) < Math.PI) {
			settings.viewAngle = viewAngle;
		} else {
			double newAngle = viewAngle;
			while (newAngle > Math.PI) {
				newAngle -= Math.PI;
			}
			while (newAngle < -Math.PI) {
				newAngle += Math.PI;
			}
			settings.viewAngle = newAngle;
		}
		return this;
	}
	public double getFOVDegrees() {
		return Math.toDegrees(settings.viewAngle);
	}
	public Display setFOVDegrees(double degrees) {
		setFOVRadians(Math.toRadians(degrees));
		return this;
	}
	public Display enableYAxisClamping() {
		settings.yAxisClamp = true;
		return this;
	}
	public Display disableYAxisClamping() {
		settings.yAxisClamp = false;
		return this;
	}
	public Display setRenderTarget(RenderTarget renderMode) {
		settings.renderTarget = renderMode;
		return this;
	}
	public RenderTarget getRenderTarget() {
		return settings.renderTarget;
	}
	public Display enableAntialiasing() {
		settings.antialiasingHint = true;
		return this;
	}
	public Display disableAntialiasing() {
		settings.antialiasingHint = false;
		return this;
	}
	public Display setRenderingMode(RenderMode mode) {
		settings.renderingHint = mode;
		return this;
	}
	public RenderMode getRenderingMode() {
		return settings.renderingHint;
	}
	public Display enableDithering() {
		settings.ditheringHint = true;
		return this;
	}
	public Display disableDithering() {
		settings.ditheringHint = false;
		return this;
	}
	public Display setColorRenderingMode(RenderMode mode) {
		settings.colorRenderingHint = mode;
		return this;
	}
	public RenderMode getColorRenderingMode() {
		return settings.colorRenderingHint;
	}
	public Display enableFractionalMetrics() {
		settings.fractionalMetricsHint = true;
		return this;
	}
	public Display disableFractionalMetrics() {
		settings.fractionalMetricsHint = false;
		return this;
	}
	public Display enableTextAntialiasing() {
		settings.textAntialiasingHint = true;
		return this;
	}
	public Display disableTextAntialiasing() {
		settings.textAntialiasingHint = false;
		return this;
	}
	public Display setInterpolationMode(InterpolationMode mode) {
		settings.interpolationHint = mode;
		return this;
	}
	public InterpolationMode getInterpolationMode() {
		return settings.interpolationHint;
	}
	public Display setAlphaInterpolationMode(RenderMode mode) {
		settings.alphaInterpolationHint = mode;
		return this;
	}
	public RenderMode getAlphaInterpolationMode() {
		return settings.alphaInterpolationHint;
	}
	public Display setRenderQuality(RenderMode mode) { // sets rendering hints to either the best or worst settings
		if (mode == RenderMode.PERFORMANCE) {
			settings.antialiasingHint = false;
			settings.renderingHint = RenderMode.PERFORMANCE;
			settings.ditheringHint = false;
			settings.colorRenderingHint = RenderMode.PERFORMANCE;
			settings.fractionalMetricsHint = false;
			settings.textAntialiasingHint = false;
			settings.interpolationHint = InterpolationMode.NEAREST_NEIGHBOR;
			settings.alphaInterpolationHint = RenderMode.PERFORMANCE;
		} else {
			settings.antialiasingHint = true;
			settings.renderingHint = RenderMode.QUALITY;
			settings.ditheringHint = true;
			settings.colorRenderingHint = RenderMode.QUALITY;
			settings.fractionalMetricsHint = true;
			settings.textAntialiasingHint = true;
			settings.interpolationHint = InterpolationMode.BICUBIC;
			settings.alphaInterpolationHint = RenderMode.QUALITY;
		}
		return this;
	}
	public int addParticle(Particle particle) {
		try {particle.start();} catch (NullPointerException ex) {}
		scene.particles.add(particle);
		return scene.particles.size()-1;
	}
	public void removeParticle(int particleID) {
		scene.particles.set(particleID, null);
	}
	public ArrayList<Particle> getParticles() {
		return scene.particles;
	}
	public Display setPointSize(Dimension pointSize) {
		settings.pointSize = pointSize;
		return this;
	}
	public Dimension getPointSize() {
		return settings.pointSize;
	}
	public Time getTime() {
		return time;
	}
	public Display enablePointRendering() {
		settings.renderPoints = true;
		return this;
	}
	public Display disablePointRendering() {
		settings.renderPoints = false;
		return this;
	}
}