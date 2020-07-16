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
 * @author Alyx Ferrari
 * @author Sam Krug
 * @since 1.0 beta
 */
public class Display {
	private Image buffer; // used for double buffering
	protected Graphics2D graphics; // set to the above Image's Graphics component every frame
	protected final DisplayRenderer renderer; // a JComponent that handles rendering
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
	protected final Time time; // controls delta time and fixed delta time for this Display instance
	protected ParticleKernel particleKernel; // kernel responsible for calculating particle positions with OpenCL
	protected ObjectKernel objKernel;
	final DisplaySettings settings; // display settings
	protected int zAngleLength;
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 */
	public Display(Scene scene) {
		this(scene, "");
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 */
	public Display(Scene scene, String frameTitle) {
		this(scene, frameTitle, true);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param maxPointsTotal The maximum number of points that will ever be in any Scene rendered.
	 * @param maxPointsObject The maximum number of points that will ever be in any single Object3D.
	 * @param maxObjects The maximum number of Object3Ds that will ever be in any Scene rendered.
	 */
	public Display(Scene scene, String frameTitle, int maxPointsTotal,int maxPointsObject,  int maxObjects) {
		this(scene, frameTitle, true, maxPointsTotal, maxPointsObject, maxObjects);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 */
	public Display(Scene scene, String frameTitle, double fovRadians) {
		this(scene, frameTitle, true, fovRadians);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 * @param maxPointsTotal The maximum number of points that will ever be in any Scene rendered.
	 * @param maxPointsObject The maximum number of points that will ever be in any single Object3D.
	 * @param maxObjects The maximum number of Object3Ds that will ever be in any Scene rendered.
	 */
	public Display(Scene scene, String frameTitle, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this(scene, frameTitle, true, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible) {
		this (scene, frameTitle, frameVisible, false);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param maxPointsTotal The maximum number of points that will ever be in any Scene rendered.
	 * @param maxPointsObject The maximum number of points that will ever be in any single Object3D.
	 * @param maxObjects The maximum number of Object3Ds that will ever be in any Scene rendered.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this (scene, frameTitle, frameVisible, false, maxPointsTotal, maxPointsObject, maxObjects);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, double fovRadians) {
		this (scene, frameTitle, frameVisible, false, fovRadians);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 * @param maxPointsTotal The maximum number of points that will ever be in any Scene rendered.
	 * @param maxPointsObject The maximum number of points that will ever be in any single Object3D.
	 * @param maxObjects The maximum number of Object3Ds that will ever be in any Scene rendered.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this (scene, frameTitle, frameVisible, false, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500, fovRadians);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 * @param maxPointsTotal The maximum number of points that will ever be in any Scene rendered.
	 * @param maxPointsObject The maximum number of points that will ever be in any single Object3D.
	 * @param maxObjects The maximum number of Object3Ds that will ever be in any Scene rendered.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, 500, 500, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param frameWidth The preferred width of the frame.
	 * @param frameHeight The preferred height of the frame.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight) {
		this(scene, frameTitle, frameVisible, renderPoints, new Dimension(5, 5), frameWidth, frameHeight);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param frameWidth The preferred width of the frame.
	 * @param frameHeight The preferred height of the frame.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, new Dimension(5, 5), frameWidth, frameHeight, fovRadians);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param frameWidth The preferred width of the frame.
	 * @param frameHeight The preferred height of the frame.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 * @param maxPointsTotal The maximum number of points that will ever be in any Scene rendered.
	 * @param maxPointsObject The maximum number of points that will ever be in any single Object3D.
	 * @param maxObjects The maximum number of Object3Ds that will ever be in any Scene rendered.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, int frameWidth, int frameHeight, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, new Dimension(5, 5), frameWidth, frameHeight, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param pointSize The size in 2D of points rendered onto the frame.
	 * @param frameWidth The preferred width of the frame.
	 * @param frameHeight The preferred height of the frame.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, Dimension pointSize, int frameWidth, int frameHeight) {
		this(scene, frameTitle, frameVisible, renderPoints, pointSize, frameWidth, frameHeight, Math.toRadians(80));
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param pointSize The size in 2D of points rendered onto the frame.
	 * @param frameWidth The preferred width of the frame.
	 * @param frameHeight The preferred height of the frame.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, Dimension pointSize, int frameWidth, int frameHeight, double fovRadians) {
		this(scene, frameTitle, frameVisible, renderPoints, pointSize, frameWidth, frameHeight, 60, fovRadians, 4096, 32, 128);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param pointSize The size in 2D of points rendered onto the frame.
	 * @param frameWidth The preferred width of the frame.
	 * @param frameHeight The preferred height of the frame.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 * @param maxPointsTotal The maximum number of points that will ever be in any Scene rendered.
	 * @param maxPointsObject The maximum number of points that will ever be in any single Object3D.
	 * @param maxObjects The maximum number of Object3Ds that will ever be in any Scene rendered.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, Dimension pointSize, int frameWidth, int frameHeight, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		this(scene, frameTitle, frameVisible, renderPoints, pointSize, frameWidth, frameHeight, 60, fovRadians, maxPointsTotal, maxPointsObject, maxObjects);
	}
	/** Constructs a Display object with the specified parameters.
	 * @param scene The scene that should be rendered by this Display object.
	 * @param frameTitle The title of the frame created by this Display object.
	 * @param frameVisible Whether the frame should be visible or not immediately after creation of this Display.
	 * @param renderPoints Whether individual points should be rendered.
	 * @param pointSize The preferred size in 2D of points rendered onto the frame.
	 * @param frameWidth The preferred width of the frame.
	 * @param frameHeight The preferred height of the frame.
	 * @param fps The maximum FPS this Display should render the Scene in.
	 * @param fovRadians The FOV the scene should be rendered in, in radians.
	 * @param maxPointsTotal The maximum number of points that will ever be in any Scene rendered.
	 * @param maxPointsObject The maximum number of points that will ever be in any single Object3D.
	 * @param maxObjects The maximum number of Object3Ds that will ever be in any Scene rendered.
	 */
	public Display(Scene scene, String frameTitle, boolean frameVisible, boolean renderPoints, Dimension pointSize, int frameWidth, int frameHeight, int fps, double fovRadians, int maxPointsTotal, int maxPointsObject, int maxObjects) {
		time = new Time();
		settings = new DisplaySettings();
		calculateRenderingHints();
		renderer = new DisplayRenderer();
		setRenderTarget(RenderTarget.GPU);
		settings.maxPointsTotal = maxPointsTotal;
		this.scene = scene;
		frame = new JFrame((frameTitle.equals("") ? JSE3DConst.FULL_NAME : frameTitle + " // " + JSE3DConst.FULL_NAME) + (System.getProperty("user.dir").equals("X:\\Libraries\\Documents\\GitHub\\jse3d") || System.getProperty("user.dir").equals("D:\\documents\\GitHub\\jse3d") ? " development build" : ""));
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
	/** Starts render of the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display startRender() { // call this to make the frame visible and start rendering
		if (!rendererStarted) {
			particleKernel = new ParticleKernel(this);
			objKernel = new ObjectKernel(this);
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
	/** Pauses render of the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display pauseRender() {
		rendering = false;
		return this;
	}
	/** Resumes previously paused render of the scene.
	 * @return The Display object on which this method was called.
	 */
	public Display resumeRender() {
		rendering = true;
		return this;
	}
	/** Sets the frame's visibility.
	 * @param visible Whether the frame should be visible.
	 * @return The Display object on which this method was called.
	 */
	public Display setVisible(boolean visible) {
		frame.setVisible(visible);
		return this;
	}
	/** Returns the Display's JFrame.
	 * @return The Display's JFrame.
	 */
	public JFrame getFrame() {
		return frame;
	}
	protected class DisplayRenderer extends JComponent { // scene renderer
		private static final long serialVersionUID = 1L;
		public Runnable renderScript;
		public Dimension size;
		public Point location;
		public DisplayRenderer() {
			renderScript = new Runnable() {
				@Override
				public void run() {}
			};
		}
		public void render() {
			size = getSize();
			location = getLocation();
			buffer = createImage(size.width, size.height);
			graphics = (Graphics2D) buffer.getGraphics();
			if (rendering) {
				renderFrame(size, location);
				getGraphics().drawImage(buffer, 0, 0, null);
			}
		}
	}
	protected void renderFrame(Dimension size, Point location) {
		localCamPos = new Vector3(0, 0, 0);
		try {localCamPos = getCameraPositionActual();} catch (NullPointerException ex) {}
		try {graphics.setRenderingHints(hints);} catch (ConcurrentModificationException ex) {} // sets rendering hints
		renderBackground(graphics, size, location);
		calculateMouse();
		calculateViewAngles(size, location);
		renderer.renderScript.run();
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
			objKernel.execute(range);
		} catch (AssertionError err) {
			if (!settings.assertion) {
				System.err.println("java.lang.AssertionError: Selected OpenCL device not available. This is a bug with jse3d. In the meantime, try setting the number of points in the scene to a multiple of how many cores the selected device has as a temporary workaround. Normally this is a power of 2, however that is not always true.");
				settings.assertion = true;
			}
		}
	}
	protected void prepareGPU(Vector3 localCamPos) {
		objKernel.localCamPosX[0] = (float) localCamPos.getX();
		objKernel.localCamPosY[0] = (float) localCamPos.getY();
		objKernel.localCamPosZ[0] = (float) localCamPos.getZ();
		objKernel.gpuViewAngle[0] = (float) settings.viewAngle;
		objKernel.viewAngleXInput[0] = (float) sphere.viewAngleX;
		objKernel.viewAngleYInput[0] = (float) sphere.viewAngleY;
		zAngleLength = 0;
		for (int x = 0; x < scene.object.length; x++) {
			zAngleLength += scene.object[x].points.length;
		}
		for (int x = 0; x < scene.object.length; x++) {
			for (int y = 0; y < scene.object[x].points.length; y++) {
				int index = (scene.object[x].points.length*x)+y;
				objKernel.zAngleX[index] = (float) scene.object[x].points[y].getX();
				objKernel.zAngleY[index] = (float) scene.object[x].points[y].getY();
				objKernel.zAngleZ[index] = (float) scene.object[x].points[y].getZ();
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
		return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue(), color.getAlpha());
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
		if (scene.object[a].points[i].getZ()*objKernel.cosViewAngleX[0]*objKernel.cosViewAngleY[0] + scene.object[a].points[i].getX()*objKernel.sinViewAngleX[0]*objKernel.cosViewAngleY[0] - scene.object[a].points[i].getY()*objKernel.sinViewAngleY[0] < scene.camDist) {
			xTransform = objKernel.xTransforms[id];
			yTransform = objKernel.yTransforms[id];
			distance[a][i] = new Distance(objKernel.maths[id], i);
			camScale[a][i] = distance[a][i].distance*objKernel.cosThetas[id]*objKernel.sinViewAngles[id];
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
			    renderer.render();
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
	/** Sets the Display's FPS cap.
	 * @param fps The new FPS cap.
	 * @return The Display object on which this method was called.
	 */
	public Display setTargetFPS(int fps) {
		settings.targetFps = fps;
		optimalTime = 1000000000 / settings.targetFps;
		return this;
	}
	/** Enables usage of the FPS cap.
	 * @return The Display object on which this method was called.
	 */
	public Display enableFPSLimit() {
		settings.fpsLimit = true;
		return this;
	}
	/** Disables usage of the FPS cap.
	 * @return The Display object on which this method was called.
	 */
	public Display disableFPSLimit() {
		settings.fpsLimit = false;
		return this;
	}
	/** Enables printing the current FPS to the console.
	 * @return The Display object on which this method was called.
	 */
	public Display enableFPSLogging() {
		settings.fpsLogging = true;
		return this;
	}
	/** Disables printing the current FPS to the console.
	 * @return The Display object on which this method was called.
	 */
	public Display disableFPSLogging() {
		settings.fpsLogging = false;
		return this;
	}
	/** Enables rendering Lines in the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display enableLineRendering() {
		settings.lineRender = true;
		return this;
	}
	/** Disables rendering Lines in the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display disableLineRendering() {
		settings.lineRender = false;
		return this;
	}
	/** Enables rendering Faces in the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display enableFaceRendering() {
		settings.faceRender = true;
		return this;
	}
	/** Disables rendering Faces in the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display disableFaceRendering() {
		settings.faceRender = false;
		return this;
	}
	/** Sets the color in which Lines should be drawn.
	 * @param color The color in which Lines should be drawn.
	 * @return The Display object on which this method was called.
	 */
	public Display setLineColor(Color color) {
		settings.lineColor = color;
		return this;
	}
	/** Sets the color in which the background should be drawn.
	 * @param color The color in which the background should be drawn.
	 * @return The Display object on which this method was called.
	 */
	public Display setBackgroundColor(Color color) {
		settings.backgroundColor = color;
		return this;
	}
	/** Returns the current Scene being rendered.
	 * @return The current Scene being rendered.
	 */
	public Scene getScene() {
		return scene;
	}
	/** Switches rendering to the Scene parameter.
	 * @param scene Scene to which to switch rendering.
	 * @return The Display object on which this method was called.
	 */
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
	/** Sets physics timestep.
	 * @param timestep The new physics timestep.
	 * @return The Display object on which this method was called.
	 */
	public Display setPhysicsTimestep(int timestep) { // not an actual timestep, the precision of physics movements is proportional to the timestep, rather than inversely proportional
		settings.physicsTimestep = timestep;
		return this;
	}
	/** Returns the current physics timestep.
	 * @return The current physics timestep.
	 */
	public int getPhysicsTimestep() {
		return settings.physicsTimestep;
	}
	/** Sets the camera's position relative to its current position.
	 * @param point New relative camera position.
	 * @return The Display object on which this method was called.
	 */
	public Display setCameraPositionRel(Vector3 point) {
		Thread cameraPos = new CameraPos(point, this);
		cameraPos.start();
		return this;
	}
	/** Transitions the camera's position relative to its current position over a user-specified amount of time.
	 * @param point New relative camera position.
	 * @param millis How long it should take to complete this operation in milliseconds.
	 * @return The Display object on which this method was called.
	 */
	public Display transitionCameraPositionRel(Vector3 point, int millis) {
		Thread transition = new Transition(point, millis, this);
		transition.start();
		return this;
	}
	/** Returns the camera's current position without taking into account the camera's distance to the position.
	 * @return The camera's current position.
	 */
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
	/** Enables the ability to change the camera's distance to its position with the scroll wheel.
	 * @return The Display object on which this method was called.
	 */
	public Display enableScrollWheel() {
		settings.scrollWheel = true;
		return this;
	}
	/** Disables the ability to change the camera's distance to its position with the scroll wheel.
	 * @return The Display object on which this method was called.
	 */
	public Display disableScrollWheel() {
		settings.scrollWheel = false;
		return this;
	}
	/** Sets the camera's distance to its position.
	 * @param distance The new distance to the camera's position.
	 * @return The Display object on which this method was called.
	 */
	public Display setCameraDistance(double distance) {
		scene.camDist = distance;
		return this;
	}
	/** Sets whether the user should have to press down on the left mouse button to change perspective. 
	 * @param mode The new camera mode.
	 * @return The Display object on which this method was called.
	 */
	public Display setCameraMode(CameraMode mode) {
		settings.mode = mode;
		return this;
	}
	/** Returns the current camera mode.
	 * @return The current camera mode.
	 */
	public CameraMode getCameraMode() {
		return settings.mode;
	}
	/** Returns the camera's current position while taking into account its distance to its position.
	 * @return The camera's current position while taking into account its distance to its position.
	 */
	public Vector3 getCameraPositionActual() {
		double x = (sphere.sinViewAngleX*sphere.cosViewAngleY*scene.camDist) + camPos.getX();
		double y = -((sphere.sinViewAngleY*scene.camDist) + camPos.getY());
		double z = (sphere.cosViewAngleX*sphere.cosViewAngleY*scene.camDist) + camPos.getZ();
		return new Vector3(x, y, z);
	}
	/** Enables printing the camera's actual position to (0,11) or a user-specified point on the frame.
	 * @return The Display object on which this method was called.
	 */
	public Display enableCameraPositionPrinting() {
		settings.camPosPrint = true;
		return this;
	}
	/** Enables printing the camera's actual position to the user-specified point on the frame.
	 * @param pos The point at which the camera's actual position should be printed.
	 * @return The Display object on which this method was called.
	 */
	public Display enableCameraPositionPrinting(Point pos) {
		settings.camPosPrintPoint = pos;
		return enableCameraPositionPrinting();
	}
	/** Disables printing the camera's actual position to the frame.
	 * @return The Display object on which this method was called.
	 */
	public Display disableCameraPositionPrinting() {
		settings.camPosPrint = false;
		return this;
	}
	/** Sets the position on the frame at which the camera's actual position should be printed.
	 * @param pos The position on the frame at which the camera's actual position should be printed.
	 * @return The Display object on which this method was called.
	 */
	public Display setCameraPositionPrintingPosition(Point pos) {
		settings.camPosPrintPoint = pos;
		return this;
	}
	/** Returns the current FOV in radians.
	 * @return The current FOV in radians.
	 */
	public double getFOVRadians() {
		return settings.viewAngle;
	}
	/** Sets the current FOV in radians.
	 * @param viewAngle The new FOV in radians.
	 * @return The Display object on which this method was called.
	 */
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
	/** Returns the current FOV in degrees.
	 * @return The current FOV in degrees.
	 */
	public double getFOVDegrees() {
		return Math.toDegrees(settings.viewAngle);
	}
	/** Sets the current FOV in degrees.
	 * @param degrees The new FOV in degrees.
	 * @return The Display object on which this method was called.
	 */
	public Display setFOVDegrees(double degrees) {
		setFOVRadians(Math.toRadians(degrees));
		return this;
	}
	/** Enables clamping the y-axis viewing angle such that the user cannot move the camera past the y-axis.
	 * @return The Display object on which this method was called.
	 */
	public Display enableYAxisClamping() {
		settings.yAxisClamp = true;
		return this;
	}
	/** Disables clamping the y-axis viewing angle such that the user cannot move the camera past the y-axis.
	 * @return The Display object on which this method was called.
	 */
	public Display disableYAxisClamping() {
		settings.yAxisClamp = false;
		return this;
	}
	/** Sets the device on which the current Scene should be computed. This can be set at any time with seamless switching.
	 * @param renderMode The new compute device.
	 * @return The Display object on which this method was called.
	 */
	public Display setRenderTarget(RenderTarget renderMode) {
		settings.renderTarget = renderMode;
		if (settings.renderTarget == RenderTarget.CPU_SINGLETHREADED) {
			renderer.renderScript = new Runnable() {
				@Override
				public void run() {
					pointArrays = new Point[scene.object.length][];
					for (int a = 0; a < scene.object.length; a++) {
						Point[] points = new Point[scene.object[a].points.length];
						for (int i = 0; i < scene.object[a].points.length; i++) {
							points[i] = calculatePoint(a, i, renderer.size, renderer.location);
							if (settings.renderPoints) {
								renderPoint(graphics, points[i], a, i);
							}
						}
						if (settings.faceRender) { // sorts faces so that they're rendered back to front
							sortFaces(a, points);
						}
					}					
					renderExtras(graphics, renderer.size, renderer.location);
				}
			};
		} else {
			renderer.renderScript = new Runnable() {
				@Override
				public void run() {
					prepareGPU(localCamPos);
					calculateOnGPU();
					pointArrays = new Point[scene.object.length][];
					for (int a = 0; a < scene.object.length; a++) {
						Point[] points = new Point[scene.object[a].points.length];
						for (int i = 0; i < scene.object[a].points.length; i++) {
							points[i] = calculatePointGPU(a, i, renderer.size, renderer.location);
							if (settings.renderPoints) {
								renderPoint(graphics, points[i], a, i);
							}
						}
						if (settings.faceRender) { // sorts faces so that they're rendered from back to front
							sortFaces(a, points);
						}
					}
					renderExtras(graphics, renderer.size, renderer.location);
				}
			};
		}
		return this;
	}
	/** Returns the current compute device.
	 * @return The current compute device.
	 */
	public RenderTarget getRenderTarget() {
		return settings.renderTarget;
	}
	/** Enables anti-aliasing (disables aliasing).
	 * @return The Display object on which this method was called.
	 */
	public Display enableAntialiasing() {
		settings.antialiasingHint = true;
		return this;
	}
	/** Disables anti-aliasing (enables aliasing).
	 * @return The Display object on which this method was called.
	 */
	public Display disableAntialiasing() {
		settings.antialiasingHint = false;
		return this;
	}
	/** Enables aliasing (disables anti-aliasing).
	 * @return The Display object on which this method was called.
	 * @since 2.5.5
	 */
	public Display enableAliasing() {
		return disableAntialiasing();
	}
	/** Disables aliasing (enables anti-aliasing).
	 * @return The Display object on which this method was called.
	 * @since 2.5.5
	 */
	public Display disableAliasing() {
		return enableAntialiasing();
	}
	/** Sets general render quality.
	 * @param mode The new render quality.
	 * @return The Display object on which this method was called.
	 */
	public Display setRenderingMode(RenderMode mode) {
		settings.renderingHint = mode;
		return this;
	}
	/** Returns the current render quality.
	 * @return The current render quality.
	 */
	public RenderMode getRenderingMode() {
		return settings.renderingHint;
	}
	/** Enables dithering.
	 * @return The Display object on which this method was called.
	 */
	public Display enableDithering() {
		settings.ditheringHint = true;
		return this;
	}
	/** Disables dithering.
	 * @return The Display object on which this method was called.
	 */
	public Display disableDithering() {
		settings.ditheringHint = false;
		return this;
	}
	/**
	 * @param mode Sets color render quality.
	 * @return The Display object on which this method was called.
	 */
	public Display setColorRenderingMode(RenderMode mode) {
		settings.colorRenderingHint = mode;
		return this;
	}
	/** Returns the current color render quality.
	 * @return The current color render quality.
	 */
	public RenderMode getColorRenderingMode() {
		return settings.colorRenderingHint;
	}
	/** Enables fractional metrics.
	 * @return The Display object on which this method was called.
	 */
	public Display enableFractionalMetrics() {
		settings.fractionalMetricsHint = true;
		return this;
	}
	/** Disables fractional metrics.
	 * @return The Display object on which this method was called.
	 */
	public Display disableFractionalMetrics() {
		settings.fractionalMetricsHint = false;
		return this;
	}
	/** Enables text anti-aliasing (disables text aliasing).
	 * @return The Display object on which this method was called.
	 */
	public Display enableTextAntialiasing() {
		settings.textAntialiasingHint = true;
		return this;
	}
	/** Disables text anti-aliasing (enables text aliasing).
	 * @return The Display object on which this method was called.
	 */
	public Display disableTextAntialiasing() {
		settings.textAntialiasingHint = false;
		return this;
	}
	/** Enables text aliasing (disables text anti-aliasing).
	 * @return The Display object on which this method was called.
	 * @since 2.5.5
	 */
	public Display enableTextAliasing() {
		return disableTextAntialiasing();
	}
	/** Disables text aliasing (enables text anti-aliasing);
	 * @return The Display object on which this method was called.
	 * @since 2.5.5
	 */
	public Display disableTextAliasing() {
		return enableTextAntialiasing();
	}
	/** Sets image interpolation mode.
	 * @param mode The new image interpolation mode.
	 * @return The Display object on which this method was called.
	 */
	public Display setInterpolationMode(InterpolationMode mode) {
		settings.interpolationHint = mode;
		return this;
	}
	/** Returns the current image interpolation mode.
	 * @return The Display object on which this method was called.
	 */
	public InterpolationMode getInterpolationMode() {
		return settings.interpolationHint;
	}
	/** Sets alpha interpolation quality.
	 * @param mode The new alpha interpolation quality.
	 * @return The Display object on which this method was called.
	 */
	public Display setAlphaInterpolationMode(RenderMode mode) {
		settings.alphaInterpolationHint = mode;
		return this;
	}
	/** Returns the current alpha interpolation quality.
	 * @return The Display object on which this method was called.
	 */
	public RenderMode getAlphaInterpolationMode() {
		return settings.alphaInterpolationHint;
	}
	/** Sets overall render quality.
	 * @param mode The new overall render quality.
	 * @return The Display object on which this method was called.
	 */
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
	/** Adds a particle to the particle system.
	 * @param particle The new particle to be added.
	 * @return The particle ID needed to remove the particle.
	 */
	public int addParticle(Particle particle) {
		try {particle.start();} catch (NullPointerException ex) {}
		scene.particles.add(particle);
		return scene.particles.size()-1;
	}
	/** Removes a particle from the particle system.
	 * @param particleID The particle ID of the particle you wish to remove.
	 * @return The Display object on which this method was called.
	 */
	public Display removeParticle(int particleID) {
		scene.particles.set(particleID, null);
		return this;
	}
	/** Returns the current particle array.
	 * @return The current particle array.
	 */
	public ArrayList<Particle> getParticles() {
		return scene.particles;
	}
	/** Sets the size in 2D in which points should be rendered.
	 * @param pointSize The new size of points.
	 * @return The Display object on which this method was called.
	 */
	public Display setPointSize(Dimension pointSize) {
		settings.pointSize = pointSize;
		return this;
	}
	/** Returns the size in 2D in which points are being rendered.
	 * @return The Display object on which this method was called.
	 */
	public Dimension getPointSize() {
		return settings.pointSize;
	}
	/** Returns this Display's Time object. Used for timing physics operations.
	 * @return The Display object on which this method was called.
	 */
	public Time getTime() {
		return time;
	}
	/** Enables rendering points to the frame.
	 * @return The Display object on which this method was called.
	 */
	public Display enablePointRendering() {
		settings.renderPoints = true;
		return this;
	}
	/** Disables rendering points to the frame.
	 * @return The Display object on which this method was called.
	 */
	public Display disablePointRendering() {
		settings.renderPoints = false;
		return this;
	}
}