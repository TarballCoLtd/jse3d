package com.alyxferrari.jse3d.gfx;
import java.awt.Graphics2D;
import java.awt.Graphics;
import com.alyxferrari.jse3d.obj.Scene;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.Point;
import com.alyxferrari.jse3d.obj.Vector3;
import com.alyxferrari.jse3d.obj.Distance;
import com.alyxferrari.jse3d.obj.Object3D;
import java.awt.RenderingHints;
import java.awt.Dimension;
import com.alyxferrari.jse3d.enums.RenderTarget;
import java.awt.MouseInfo;
import java.awt.BorderLayout;
import com.alyxferrari.jse3d.JSE3DConst;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import com.aparapi.device.Device;
import java.util.ConcurrentModificationException;
import com.aparapi.Range;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import com.alyxferrari.jse3d.enums.CameraMode;
import com.alyxferrari.jse3d.enums.RenderMode;
import com.alyxferrari.jse3d.enums.InterpolationMode;
import com.alyxferrari.jse3d.exc.CPU_OpenCLDriverNotFoundError;
import com.alyxferrari.jse3d.exc.GPU_OpenCLDriverNotFoundError;
import java.util.ArrayList;
import com.alyxferrari.jse3d.obj.Particle;
import com.alyxferrari.jse3d.interfaces.RenderScript;
/** Represents a jse3d frame.
 * @author Alyx Ferrari
 * @author Sam Krug
 * @since 1.0 beta
 */
public class Display {
	DisplayFields fields;
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
		fields = new DisplayFields();
		fields.maxFps = 0;
		fields.frameFps = 0;
		fields.secondsOpen = 0;
		fields.time = new Time();
		fields.settings = new DisplaySettings();
		calculateRenderingHints();
		fields.renderer = new DisplayRenderer();
		setRenderTarget(RenderTarget.GPU);
		fields.settings.maxPointsTotal = maxPointsTotal;
		fields.scene = scene;
		fields.frame = new JFrame((frameTitle.equals("") ? JSE3DConst.FULL_NAME : frameTitle + " // " + JSE3DConst.FULL_NAME) + (System.getProperty("user.dir").equals("X:\\Libraries\\Documents\\GitHub\\jse3d") || System.getProperty("user.dir").equals("D:\\documents\\GitHub\\jse3d") ? " development build" : ""));
		fields.frame.setSize(frameWidth, frameHeight);
		fields.frame.setVisible(frameVisible);
		fields.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fields.rendering = false;
		fields.frame.getContentPane().add(BorderLayout.CENTER, fields.renderer);
		fields.distance = new Distance[maxObjects][maxPointsObject];
		fields.camScale = new double[maxObjects][maxPointsObject];
		fields.settings.renderPoints = renderPoints;
		fields.settings.pointSize = pointSize;
		fields.rendererStarted = false;
		fields.settings.targetFps = fps;
		fields.optimalTime = 1000000000/fields.settings.targetFps;
		fields.renderer.addMouseListener(new ClickListener());
		fields.renderer.addMouseWheelListener(new ScrollListener());
		fields.mouseClicked = false;
		fields.camPos = new Vector3(0, 0, 0);
		fields.mouseDiff = new Point(0, 0);
		fields.settings.viewAngle = fovRadians;
		recalculateExtrasScript();
		fields.frame.addWindowListener(new FrameListener());
		fields.script = new RenderScript() {
			@Override public void preRender(Graphics graphics) {}
			@Override public void postRender(Graphics graphics) {}
		};
		setCameraMode(CameraMode.DRAG);
	}
	protected class FrameListener implements WindowListener {
		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {}
		@Override
		public void windowClosing(WindowEvent arg0) {
			for (int i = 0; i < fields.scene.object.length; i++) {
		    	try {fields.scene.object[i].stop();} catch (NullPointerException ex) {}
		    }
		    for (int i = 0; i < fields.scene.particles.size(); i++) {
		    	try {fields.scene.particles.get(i).stop();} catch (NullPointerException ex) {}
		    }
		    if (fields.settings.fpsLogging) {
		    	System.out.println("\nAverage over " + fields.secondsOpen + " seconds: " + (fields.frameFps/(long)fields.secondsOpen) + " FPS");
		    	System.out.println("Best FPS count: " + (fields.maxFps-1));
		    	System.exit(0);
		    }
		}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}
	}
	/** Starts render of the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display startRender() { // call this to make the frame visible and start rendering
		if (!fields.rendererStarted) {
			fields.particleKernel = new ParticleKernel(this);
			fields.objKernel = new ObjectKernel(this);
			for (int i = 0; i < fields.scene.object.length; i++) {
				try {fields.scene.object[i].start();} catch (NullPointerException ex) {}
			}
			for (int i = 0; i < fields.scene.particles.size(); i++) {
				try {fields.scene.particles.get(i).start();} catch (NullPointerException ex) {}
			}
			fields.lastMousePos = new Point(MouseInfo.getPointerInfo().getLocation().x-fields.frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-fields.frame.getLocationOnScreen().y);
			fields.rendering = true;
			Thread renderer = new Thread(new Renderer());
			renderer.start();
			Thread physics = new Thread(new Physics());
			physics.start();
			fields.rendererStarted = true;
		}
		return this;
	}
	/** Pauses render of the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display pauseRender() {
		fields.rendering = false;
		return this;
	}
	/** Resumes previously paused render of the scene.
	 * @return The Display object on which this method was called.
	 */
	public Display resumeRender() {
		fields.rendering = true;
		return this;
	}
	/** Sets the frame's visibility.
	 * @param visible Whether the frame should be visible.
	 * @return The Display object on which this method was called.
	 */
	public Display setVisible(boolean visible) {
		fields.frame.setVisible(visible);
		return this;
	}
	/** Sets the frame's size.
	 * @param width The frame's new width.
	 * @param height The frame's new height.
	 * @return The Display object on which this method was called.
	 * @since 2.6.3
	 */
	public Display setSize(int width, int height) {
		fields.frame.setSize(width, height);
		return this;
	}
	/** Sets the frame's size.
	 * @param size The frame's new size.
	 * @return The Display object on which this method was called.
	 * @since 2.6.3
	 */
	public Display setSize(Dimension size) {
		fields.frame.setSize(size);
		return this;
	}
	/** Returns the Display's JFrame.
	 * @return The Display's JFrame.
	 */
	public JFrame getFrame() {
		return fields.frame;
	}
	protected class DisplayRenderer extends JComponent { // scene renderer
		private static final long serialVersionUID = 1L;
		public Runnable renderScript;
		public Runnable extrasRenderer;
		public Runnable mouseCalculator;
		public Dimension size;
		public Point location;
		public Device openCLDevice;		
		public DisplayRenderer() {
			openCLDevice = null;
			renderScript = new Runnable() {
				@Override
				public void run() {}
			};
			extrasRenderer = new Runnable() {
				@Override
				public void run() {}
			};
			mouseCalculator = new Runnable() {
				@Override
				public void run() {}
			};
		}
		public void render() {
			try {
				size = getSize();
				location = getLocation();
				fields.buffer = createImage(size.width, size.height);
				fields.graphics = (Graphics2D) fields.buffer.getGraphics();
				if (fields.rendering) {
					renderFrame();
					fields.script.postRender(fields.graphics);
					getGraphics().drawImage(fields.buffer, 0, 0, null);
				}
			} catch (IllegalArgumentException ex) {}
		}
	}
	protected void renderFrame() {
		fields.localCamPos = new Vector3(0, 0, 0);
		try {fields.localCamPos = getCameraPositionActual();} catch (NullPointerException ex) {}
		try {fields.graphics.setRenderingHints(fields.hints);} catch (ConcurrentModificationException ex) {} catch (NullPointerException ex) {} // sets rendering hints
		renderBackground();
		fields.script.preRender(fields.graphics);
		calculateMouse();
		calculateViewAngles();
		fields.renderer.renderScript.run();
		fields.fps++;
	}
	protected void renderExtras() {
		fields.renderer.extrasRenderer.run();
		for (int a = 0; a < fields.scene.particles.size(); a++) {
			renderParticle(calculateParticle(a), fields.scene.particles.get(a).getPosition());
		}
	}
	protected Point calculateParticle(int particleID) {
		// the following if statement checks if this particle is in front of the camera, not behind, and hence, if it should be rendered or not
		if (fields.scene.particles.get(particleID).getPosition().getZ()*fields.sphere.cosViewAngleX*fields.sphere.cosViewAngleY + fields.scene.particles.get(particleID).getPosition().getX()*fields.sphere.sinViewAngleX*fields.sphere.cosViewAngleY - fields.scene.particles.get(particleID).getPosition().getY()*fields.sphere.sinViewAngleY < fields.scene.camDist) {
			// 3D to 2D particle conversion
			double zAngle = StrictMath.atan(fields.scene.particles.get(particleID).getPosition().getZ()/fields.scene.particles.get(particleID).getPosition().getX());
			if (fields.scene.particles.get(particleID).getPosition().getX() == 0 && fields.scene.particles.get(particleID).getPosition().getZ() == 0) {
				zAngle = 0;
			}
			double mag = StrictMath.hypot(fields.scene.particles.get(particleID).getPosition().getX(), fields.scene.particles.get(particleID).getPosition().getZ());
			if (fields.scene.particles.get(particleID).getPosition().getX() < 0) {
				fields.xTransform = -mag*DisplaySettings.SCALE*StrictMath.cos(fields.sphere.viewAngleX+zAngle);
				fields.yTransform = -mag*DisplaySettings.SCALE*StrictMath.sin(fields.sphere.viewAngleX+zAngle)*fields.sphere.sinViewAngleY+(fields.scene.particles.get(particleID).getPosition().getY())*DisplaySettings.SCALE*fields.sphere.cosViewAngleY;
			} else {
				fields.xTransform = mag*DisplaySettings.SCALE*StrictMath.cos(fields.sphere.viewAngleX+zAngle);
				fields.yTransform = mag*DisplaySettings.SCALE*StrictMath.sin(fields.sphere.viewAngleX+zAngle)*fields.sphere.sinViewAngleY+(fields.scene.particles.get(particleID).getPosition().getY())*DisplaySettings.SCALE*fields.sphere.cosViewAngleY;
			}
			double distance = Math3D.hypot3(fields.localCamPos.getX()-fields.scene.particles.get(particleID).getPosition().getX(), fields.localCamPos.getY()-fields.scene.particles.get(particleID).getPosition().getY(), fields.localCamPos.getZ()-fields.scene.particles.get(particleID).getPosition().getZ());
			double theta = StrictMath.asin((Math.hypot(fields.xTransform, fields.yTransform)/DisplaySettings.SCALE)/distance);
			double camScale = distance*StrictMath.cos(theta)*StrictMath.sin(fields.settings.viewAngle/2);
			return new Point((int)((fields.renderer.size.width+fields.renderer.location.x)/2+fields.xTransform/camScale), (int)((fields.renderer.size.height+fields.renderer.location.y)/2-fields.yTransform/camScale));
		}
		return null;
	}
	protected Point calculatePoint(int a, int i) {
		// the following if statement checks if this point is in front of the camera, not behind, and hence, if it should be rendered or not
		if (fields.scene.object[a].points[i].getZ()*fields.sphere.cosViewAngleX*fields.sphere.cosViewAngleY + fields.scene.object[a].points[i].getX()*fields.sphere.sinViewAngleX*fields.sphere.cosViewAngleY - fields.scene.object[a].points[i].getY()*fields.sphere.sinViewAngleY < fields.scene.camDist) {
			// 3D to 2D point conversion
			double zAngle = StrictMath.atan((fields.scene.object[a].points[i].getZ())/(fields.scene.object[a].points[i].getX()));
			if (fields.scene.object[a].points[i].getX() == 0 && fields.scene.object[a].points[i].getZ() == 0) {
				zAngle = 0;
			}
			double mag = StrictMath.hypot(fields.scene.object[a].points[i].getX(), fields.scene.object[a].points[i].getZ());
			if (fields.scene.object[a].points[i].getX() < 0) {
				fields.xTransform = -mag*DisplaySettings.SCALE*StrictMath.cos(fields.sphere.viewAngleX+zAngle);
				fields.yTransform = -mag*DisplaySettings.SCALE*StrictMath.sin(fields.sphere.viewAngleX+zAngle)*fields.sphere.sinViewAngleY+(fields.scene.object[a].points[i].getY())*DisplaySettings.SCALE*fields.sphere.cosViewAngleY;
			} else {
				fields.xTransform = mag*DisplaySettings.SCALE*StrictMath.cos(fields.sphere.viewAngleX+zAngle);
				fields.yTransform = mag*DisplaySettings.SCALE*StrictMath.sin(fields.sphere.viewAngleX+zAngle)*fields.sphere.sinViewAngleY+(fields.scene.object[a].points[i].getY())*DisplaySettings.SCALE*fields.sphere.cosViewAngleY;
			}
			fields.distance[a][i] = new Distance(Math3D.hypot3(fields.localCamPos.getX()-fields.scene.object[a].points[i].getX(), fields.localCamPos.getY()-fields.scene.object[a].points[i].getY(), fields.localCamPos.getZ()-fields.scene.object[a].points[i].getZ()), i);
			double theta = StrictMath.asin((StrictMath.hypot(fields.xTransform, fields.yTransform)/DisplaySettings.SCALE)/fields.distance[a][i].distance);
			fields.camScale[a][i] = fields.distance[a][i].distance*StrictMath.cos(theta)*StrictMath.sin(fields.settings.viewAngle/2);
			return new Point((int)((fields.renderer.size.width+fields.renderer.location.x)/2+fields.xTransform/fields.camScale[a][i]), (int)((fields.renderer.size.height+fields.renderer.location.y)/2-fields.yTransform/fields.camScale[a][i]));
		}
		return null;
	}
	protected void calculateOnGPU() {
		try { // calculates multiple points concurrently on the selected OpenCL device
			Range range = fields.renderer.openCLDevice.createRange(fields.zAngleLength);
			range.setLocalSize_0(1);
			fields.objKernel.execute(range);
		} catch (AssertionError err) {
			if (!fields.settings.assertion) {
				System.err.println("java.lang.AssertionError: Selected OpenCL device not available. Create an issue on GitHub detailing your situation, as this was supposed to be fixed in jse3d v3.0.");
				fields.settings.assertion = true;
			}
		}
	}
	protected void prepareGPU(Vector3 localCamPos) {
		fields.objKernel.localCamPosX[0] = (float) localCamPos.getX();
		fields.objKernel.localCamPosY[0] = (float) localCamPos.getY();
		fields.objKernel.localCamPosZ[0] = (float) localCamPos.getZ();
		fields.objKernel.gpuViewAngle[0] = (float) fields.settings.viewAngle;
		fields.objKernel.viewAngleXInput[0] = (float) fields.sphere.viewAngleX;
		fields.objKernel.viewAngleYInput[0] = (float) fields.sphere.viewAngleY;
		fields.zAngleLength = 0;
		for (Object3D object : fields.scene.object) {
			fields.zAngleLength += object.points.length;
		}
		for (int x = 0; x < fields.scene.object.length; x++) {
			for (int y = 0; y < fields.scene.object[x].points.length; y++) {
				int index = (fields.scene.object[x].points.length*x)+y;
				fields.objKernel.zAngleX[index] = (float) fields.scene.object[x].points[y].getX();
				fields.objKernel.zAngleY[index] = (float) fields.scene.object[x].points[y].getY();
				fields.objKernel.zAngleZ[index] = (float) fields.scene.object[x].points[y].getZ();
			}
		}
	}
	protected void renderBackground() {
		fields.graphics.setColor(fields.settings.backgroundColor);
		fields.graphics.fillRect(0, 0, fields.renderer.size.width+fields.renderer.location.x, fields.renderer.size.height+fields.renderer.location.y);
	}
	protected void renderPoint(Point point, int a, int i) {
		fields.graphics.setColor(Color.BLACK);
		double reciprocal = 0.0;
		try {reciprocal = 1.0/fields.distance[a][i].distance;} catch (NullPointerException ex) {}
		int width = (int)(fields.settings.pointSize.width*reciprocal);
		int height = (int)(fields.settings.pointSize.height*reciprocal);
		try {fields.graphics.fillOval(point.x-(width/2), point.y-(height/2), width, height);} catch (NullPointerException ex) {}
	}
	protected void renderParticle(Point point, Vector3 position) {
		fields.graphics.setColor(Color.BLACK);
		double reciprocal = 1.0/Math3D.hypot3(fields.localCamPos.getX()-position.getX(), fields.localCamPos.getY()-position.getY(), fields.localCamPos.getZ()-position.getZ());
		try {fields.graphics.fillOval(point.x, point.y, (int)(fields.settings.pointSize.width*reciprocal), (int)(fields.settings.pointSize.height*reciprocal));} catch (NullPointerException ex) {}
	}
	protected void printCameraPosition() {
		Vector3 cameraPos = getCameraPositionActual();
		fields.graphics.setColor(invertColor(fields.settings.backgroundColor));
		fields.graphics.drawString("x: " + cameraPos.getX() + " // y: " + cameraPos.getY() + " // z: " + cameraPos.getZ(), 0, 11);
	}
	protected static Color invertColor(Color color) {
		return new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue(), color.getAlpha());
	}
	protected void calculateViewAngles() {
		double viewAngleX = 0;
		double viewAngleY = 0;
		try {
			viewAngleY = -((fields.renderer.location.y+fields.mouse.y-fields.renderer.size.height)/2)/DisplaySettings.SENSITIVITY;
			if (fields.settings.yAxisClamp) {
				if (Math.abs((fields.renderer.location.y+fields.mouse.y-fields.renderer.size.height)/2)>Math.PI/2*DisplaySettings.SENSITIVITY) {
					if (viewAngleY < 0) {
						viewAngleY = -Math.PI/2*DisplaySettings.SENSITIVITY;
					} else {
						viewAngleY = Math.PI/2*DisplaySettings.SENSITIVITY;
					}
				}
			}
			viewAngleX = -((fields.renderer.location.x+fields.mouse.x-fields.renderer.size.width)/2)/DisplaySettings.SENSITIVITY;
		} catch (NullPointerException ex) {}
		fields.sphere = new ViewAngle(viewAngleX, viewAngleY);
	}
	protected void renderFaces() {
		for (int a = 0; a < fields.scene.object.length; a++) {
			for (int x = a+1; x < fields.scene.object.length; x++) {
				if (fields.scene.object[a].camDist < fields.scene.object[x].camDist) {
					Point[] temp = fields.pointArrays[a];
					fields.pointArrays[a] = fields.pointArrays[x];
					fields.pointArrays[x] = temp;
				}
			}
			for (int x = 0; x < fields.scene.object[a].faces.length; x++) {
				for (int y = 0; y < fields.scene.object[a].faces[x].triangles.length; y++) {
					try {
						int[] xs2 = {fields.pointArrays[a][fields.scene.object[a].faces[x].triangles[y].pointID1].x, fields.pointArrays[a][fields.scene.object[a].faces[x].triangles[y].pointID2].x, fields.pointArrays[a][fields.scene.object[a].faces[x].triangles[y].pointID3].x};
						int[] ys2 = {fields.pointArrays[a][fields.scene.object[a].faces[x].triangles[y].pointID1].y, fields.pointArrays[a][fields.scene.object[a].faces[x].triangles[y].pointID2].y, fields.pointArrays[a][fields.scene.object[a].faces[x].triangles[y].pointID3].y};
						if (fields.scene.getDirectionalLight() == null) {
							fields.graphics.setColor(fields.scene.object[a].faces[x].triangles[y].color);
						} else {
							double dot = Vector3.dot(fields.scene.object[a].faces[x].triangles[y].cross(fields.scene.object[a]), fields.scene.getDirectionalLight().getDirection());
							dot *= fields.scene.getDirectionalLight().getLightStrength();
							if (dot > 0) {
								Color triColor = fields.scene.object[a].faces[x].triangles[y].color;
								int red = (int)((triColor.getRed()*dot)+(triColor.getRed()*fields.scene.getAmbientLight()));
								int green = (int)((triColor.getGreen()*dot)+(triColor.getGreen()*fields.scene.getAmbientLight()));
								int blue = (int)((triColor.getBlue()*dot)+(triColor.getBlue()*fields.scene.getAmbientLight()));
								Color color = new Color(red > 255 ? 255: red, green > 255 ? 255 : green, blue > 255 ? 255 : blue, triColor.getAlpha());
								fields.graphics.setColor(color);
							} else {
								Color base = fields.scene.object[a].faces[x].triangles[y].color;
								fields.graphics.setColor(new Color((int)(base.getRed()*fields.scene.getAmbientLight()), (int)(base.getGreen()*fields.scene.getAmbientLight()), (int)(base.getBlue()*fields.scene.getAmbientLight()), base.getAlpha()));
							}
						}
						fields.graphics.fillPolygon(xs2, ys2, 3);
					} catch (NullPointerException ex) {}
				}
			}
		}
	}
	protected void sortFaces(int a, Point[] points) {
		double objDist = 0.0;
		int length = fields.distance[a].length;
		for (int x = 0; x < fields.distance[a].length; x++) {
			try {
				objDist += fields.distance[a][x].distance;
			} catch (NullPointerException ex) {
				length--;
			}
		}
		objDist /= (double) length;
		fields.scene.object[a].camDist = objDist;
		for (int x = 0; x < fields.scene.object[a].faces.length; x++) {
			int[] pointIDs = fields.scene.object[a].faces[x].getPointIDs();
			double[] distances = new double[pointIDs.length];
			for (int y = 0; y < pointIDs.length; y++) {
				for (int z = 0; z < fields.distance[a].length; z++) {
					try {
						if (fields.distance[a][z].pointID == pointIDs[y]) {
							distances[y] = fields.distance[a][z].distance;
						}
					} catch (NullPointerException ex) {}
				}
			}
			double average = 0.0;
			for (int i = 0; i < distances.length; i++) {
				average += distances[i];
			}
			average /= (double) distances.length;
			fields.scene.object[a].faces[x].camDist = average;
		}
		Arrays.sort(fields.scene.object[a].faces, Collections.reverseOrder());
		fields.pointArrays[a] = points;
	}
	protected void renderLines() {
		fields.graphics.setColor(fields.settings.lineColor);
		for (int a = 0; a < fields.scene.object.length; a++) {
			for (int i = 0; i < fields.scene.object[a].edges.length; i++) {
				try {
					int pos1 = fields.pointArrays[a][fields.scene.object[a].edges[i].pointID1].x;
					int pos2 = fields.pointArrays[a][fields.scene.object[a].edges[i].pointID1].y;
					int pos3 = fields.pointArrays[a][fields.scene.object[a].edges[i].pointID2].x;
					int pos4 = fields.pointArrays[a][fields.scene.object[a].edges[i].pointID2].y;
					if (!(pos1 == 0 || pos2 == 0 || pos3 == 0 || pos4 == 0)) {
						fields.graphics.drawLine(pos1, pos2, pos3, pos4);
					}
				} catch (NullPointerException ex) {} catch (ArrayIndexOutOfBoundsException ex) {}
			}
		}
	}
	protected Point calculatePointGPU(int a, int i) {
		int id = (fields.scene.object[a].points.length*a)+i;
		fields.distance[a][i] = new Distance(0.0, -1);
		if (fields.scene.object[a].points[i].getZ()*fields.objKernel.cosViewAngleX[0]*fields.objKernel.cosViewAngleY[0] + fields.scene.object[a].points[i].getX()*fields.objKernel.sinViewAngleX[0]*fields.objKernel.cosViewAngleY[0] - fields.scene.object[a].points[i].getY()*fields.objKernel.sinViewAngleY[0] < fields.scene.camDist) {
			fields.xTransform = fields.objKernel.xTransforms[id];
			fields.yTransform = fields.objKernel.yTransforms[id];
			fields.distance[a][i] = new Distance(fields.objKernel.maths[id], i);
			fields.camScale[a][i] = fields.distance[a][i].distance*fields.objKernel.cosThetas[id]*fields.objKernel.sinViewAngles[id];
			return new Point((int)((fields.renderer.size.width+fields.renderer.location.x)/2+fields.xTransform/fields.camScale[a][i]), (int)((fields.renderer.size.height+fields.renderer.location.y)/2-fields.yTransform/fields.camScale[a][i]));
		}
		return null;
	}
	protected void calculateMouse() {
		fields.renderer.mouseCalculator.run();
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
			    	if (fields.settings.fpsLogging) {
			    		System.out.println("FPS: " + (fields.fps < 1 ? 0 : fields.fps-1));
			    		if (fields.fps > fields.maxFps) {
			    			fields.maxFps = fields.fps;
			    		}
			    		fields.secondsOpen++;
			    		fields.frameFps += fields.fps;
			    	}
			        lastFpsTime = 0;
			        fields.fps = 0;
			    }
			    fields.renderer.render();
			    fields.time.reset();
			    for (int i = 0; i < fields.scene.object.length; i++) {
			    	try {fields.scene.object[i].update();} catch (NullPointerException ex) {}
			    }
			    for (int i = 0; i < fields.scene.particles.size(); i++) {
			    	try {fields.scene.particles.get(i).update();} catch (NullPointerException ex) {}
			    }
			    if (fields.settings.fpsLimit) {
			    	long tmp = (lastLoopTime-System.nanoTime()+fields.optimalTime)/1000000;
			    	try {Thread.sleep(tmp > 0 ? tmp : 0);} catch (InterruptedException ex) {ex.printStackTrace();}
			    }
			}
		}
	}
	protected class Physics implements Runnable {
		public void run() {
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			long OPTIMAL_TIME = 1000000000/fields.settings.physicsTimestep;
			while (true) {
				long now = System.nanoTime();
				long updateLength = now-lastLoopTime;
				lastLoopTime = now;
				lastFpsTime += updateLength;
				if (lastFpsTime >= 1000000000) {
					lastFpsTime = 0L;
					OPTIMAL_TIME = 1000000000/fields.settings.physicsTimestep;
				}
				for (int i = 0; i < fields.scene.object.length; i++) {
					try {fields.scene.object[i].fixedUpdate();} catch (NullPointerException ex) {}
				}
				for (int i = 0; i < fields.scene.particles.size(); i++) {
					try {fields.scene.particles.get(i).fixedUpdate();} catch (NullPointerException ex) {}
				}
				fields.time.fixedReset();
				long tmp = (lastLoopTime-System.nanoTime()+OPTIMAL_TIME)/1000000;
				try {Thread.sleep(tmp > 0 ? tmp : 0);} catch (InterruptedException ex) {ex.printStackTrace();}
			}
		}
	}
	protected void calculateRenderingHints() { // creates a rendering hints object based on the settings currently in place, this is applied at the start of every new frame, and recalculated whenever a rendering hint is changed
		if (fields.settings.antialiasingHint) {
			fields.hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			fields.hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		if (fields.settings.renderingHint == RenderMode.PERFORMANCE) {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED));
		} else {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		}
		if (fields.settings.ditheringHint) {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE));
		} else {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE));
		}
		if (fields.settings.colorRenderingHint == RenderMode.PERFORMANCE) {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED));
		} else {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY));
		}
		if (fields.settings.fractionalMetricsHint) {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON));
		} else {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF));
		}
		if (fields.settings.textAntialiasingHint) {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
		} else {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF));
		}
		if (fields.settings.interpolationHint == InterpolationMode.BICUBIC) {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
		} else if (fields.settings.interpolationHint == InterpolationMode.BILINEAR) {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR));
		} else {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR));
		}
		if (fields.settings.alphaInterpolationHint == RenderMode.PERFORMANCE) {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED));
		} else {
			fields.hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));
		}
	}
	protected class ClickListener implements MouseListener { // calculations for CameraMode.DRAG
		public void mouseEntered(MouseEvent ev) {}
		public void mousePressed(MouseEvent ev) {
			fields.mouseClicked = true;
			Point temp = new Point(MouseInfo.getPointerInfo().getLocation().x-fields.frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-fields.frame.getLocationOnScreen().y);
			fields.mouseDiff = new Point(temp.x-fields.lastMousePos.x, temp.y-fields.lastMousePos.y);
		}
		public void mouseClicked(MouseEvent ev) {}
		public void mouseReleased(MouseEvent ev) {
			fields.mouseClicked = false;
			Point temp = new Point(MouseInfo.getPointerInfo().getLocation().x-fields.frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-fields.frame.getLocationOnScreen().y);
			fields.lastMousePos = new Point(temp.x-fields.mouseDiff.x, temp.y-fields.mouseDiff.y);
		}
		public void mouseExited(MouseEvent ev) {}
	}
	protected class ScrollListener implements MouseWheelListener { // controls scroll wheel camera distance changes
		public void mouseWheelMoved(MouseWheelEvent ev) {
			if (fields.settings.scrollWheel) {
				int rotation = ev.getWheelRotation();
				if (rotation > 0) {
					fields.scene.camDist *= fields.settings.scrollMultiplier;
				} else if (!(rotation == 0)) {
					fields.scene.camDist /= fields.settings.scrollMultiplier;
				}
			}
		}
	}
	/** Sets the Display's FPS cap.
	 * @param fps The new FPS cap.
	 * @return The Display object on which this method was called.
	 */
	public Display setTargetFPS(int fps) {
		fields.settings.targetFps = fps;
		fields.optimalTime = 1000000000 / fields.settings.targetFps;
		return this;
	}
	/** Enables usage of the FPS cap.
	 * @return The Display object on which this method was called.
	 */
	public Display enableFPSLimit() {
		fields.settings.fpsLimit = true;
		return this;
	}
	/** Disables usage of the FPS cap.
	 * @return The Display object on which this method was called.
	 */
	public Display disableFPSLimit() {
		fields.settings.fpsLimit = false;
		return this;
	}
	/** Enables printing the current FPS to the console.
	 * @return The Display object on which this method was called.
	 */
	public Display enableFPSLogging() {
		fields.settings.fpsLogging = true;
		return this;
	}
	/** Disables printing the current FPS to the console.
	 * @return The Display object on which this method was called.
	 */
	public Display disableFPSLogging() {
		fields.settings.fpsLogging = false;
		return this;
	}
	/** Enables rendering Lines in the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display enableLineRendering() {
		fields.settings.lineRender = true;
		recalculateExtrasScript();
		return this;
	}
	/** Disables rendering Lines in the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display disableLineRendering() {
		fields.settings.lineRender = false;
		recalculateExtrasScript();
		return this;
	}
	/** Enables rendering Faces in the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display enableFaceRendering() {
		fields.settings.faceRender = true;
		recalculateExtrasScript();
		return this;
	}
	/** Disables rendering Faces in the Scene.
	 * @return The Display object on which this method was called.
	 */
	public Display disableFaceRendering() {
		fields.settings.faceRender = false;
		recalculateExtrasScript();
		return this;
	}
	/** Sets the color in which Lines should be drawn.
	 * @param color The color in which Lines should be drawn.
	 * @return The Display object on which this method was called.
	 */
	public Display setLineColor(Color color) {
		fields.settings.lineColor = color;
		return this;
	}
	/** Sets the color in which the background should be drawn.
	 * @param color The color in which the background should be drawn.
	 * @return The Display object on which this method was called.
	 */
	public Display setBackgroundColor(Color color) {
		fields.settings.backgroundColor = color;
		return this;
	}
	/** Returns the current Scene being rendered.
	 * @return The current Scene being rendered.
	 */
	public Scene getScene() {
		return fields.scene;
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
		fields.scene = scene;
		return this;
	}
	/** Sets physics timestep.
	 * @param timestep The new physics timestep.
	 * @return The Display object on which this method was called.
	 */
	public Display setPhysicsTimestep(int timestep) { // not an actual timestep, the precision of physics movements is proportional to the timestep, rather than inversely proportional
		fields.settings.physicsTimestep = timestep;
		return this;
	}
	/** Returns the current physics timestep.
	 * @return The current physics timestep.
	 */
	public int getPhysicsTimestep() {
		return fields.settings.physicsTimestep;
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
		return fields.camPos;
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
			for (int i = 0; i < fields.scene.object.length; i++) {
				fields.scene.object[i].movePosRel(-xt, -yt, -zt, display);
			}
			fields.camPos.set(fields.camPos.getX()+xt, fields.camPos.getY()+yt, fields.camPos.getZ()+zt);
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
			double xIteration = -(xt/(double)fields.settings.physicsTimestep*((double)millis/1000.0));
			double yIteration = -(yt/(double)fields.settings.physicsTimestep*((double)millis/1000.0));
			double zIteration = -(zt/(double)fields.settings.physicsTimestep*((double)millis/1000.0));
			long lastFpsTime = 0L;
			long lastLoopTime = System.nanoTime();
			long OPTIMAL_TIME = 1000000000 / fields.settings.physicsTimestep;
			for (int x = 0; x < (int)(fields.settings.physicsTimestep*((double)millis/1000.0)); x++) {
				long now = System.nanoTime();
			    long updateLength = now - lastLoopTime;
			    lastLoopTime = now;
			    lastFpsTime += updateLength;
			    if (lastFpsTime >= 1000000000) {
			    	xIteration = xt/(double)fields.settings.physicsTimestep*((double)millis/1000.0);
			    	yIteration = yt/(double)fields.settings.physicsTimestep*((double)millis/1000.0);
			    	zIteration = zt/(double)fields.settings.physicsTimestep*((double)millis/1000.0);
			    	OPTIMAL_TIME = 1000000000 / fields.settings.physicsTimestep;
			        lastFpsTime = 0;
			    }
			    for (int y = 0; y < fields.scene.object.length; y++) {
			    	fields.scene.object[y].movePosRel(xIteration, yIteration, zIteration, display);
			    }
			    try {Thread.sleep((lastLoopTime-System.nanoTime()+OPTIMAL_TIME)/1000000);} catch (InterruptedException ex) {ex.printStackTrace();}
			}
			fields.camPos.set(fields.camPos.getX()+xt, fields.camPos.getY()+yt, fields.camPos.getZ()+zt);
		}
	}
	/** Enables the ability to change the camera's distance to its position with the scroll wheel.
	 * @return The Display object on which this method was called.
	 */
	public Display enableScrollWheel() {
		fields.settings.scrollWheel = true;
		return this;
	}
	/** Disables the ability to change the camera's distance to its position with the scroll wheel.
	 * @return The Display object on which this method was called.
	 */
	public Display disableScrollWheel() {
		fields.settings.scrollWheel = false;
		return this;
	}
	/** Sets the camera's distance to its position.
	 * @param distance The new distance to the camera's position.
	 * @return The Display object on which this method was called.
	 */
	public Display setCameraDistance(double distance) {
		fields.scene.camDist = distance;
		return this;
	}
	/** Sets whether the user should have to press down on the left mouse button to change perspective. 
	 * @param mode The new camera mode.
	 * @return The Display object on which this method was called.
	 */
	public Display setCameraMode(CameraMode mode) {
		fields.settings.mode = mode;
		if (fields.settings.mode == CameraMode.DRAG) {
			fields.renderer.mouseCalculator = new MouseDrag();
		} else {
			fields.renderer.mouseCalculator = new MouseMove();
		}
		return this;
	}
	/** Returns the current camera mode.
	 * @return The current camera mode.
	 */
	public CameraMode getCameraMode() {
		return fields.settings.mode;
	}
	/** Returns the camera's current position while taking into account its distance to its position.
	 * @return The camera's current position while taking into account its distance to its position.
	 */
	public Vector3 getCameraPositionActual() {
		double x = (fields.sphere.sinViewAngleX*fields.sphere.cosViewAngleY*fields.scene.camDist) + fields.camPos.getX();
		double y = -((fields.sphere.sinViewAngleY*fields.scene.camDist) + fields.camPos.getY());
		double z = (fields.sphere.cosViewAngleX*fields.sphere.cosViewAngleY*fields.scene.camDist) + fields.camPos.getZ();
		return new Vector3(x, y, z);
	}
	/** Enables printing the camera's actual position to (0,11) or a user-specified point on the frame.
	 * @return The Display object on which this method was called.
	 */
	public Display enableCameraPositionPrinting() {
		fields.settings.camPosPrint = true;
		recalculateExtrasScript();
		return this;
	}
	/** Enables printing the camera's actual position to the user-specified point on the frame.
	 * @param pos The point at which the camera's actual position should be printed.
	 * @return The Display object on which this method was called.
	 */
	public Display enableCameraPositionPrinting(Point pos) {
		fields.settings.camPosPrintPoint = pos;
		recalculateExtrasScript();
		return enableCameraPositionPrinting();
	}
	/** Disables printing the camera's actual position to the frame.
	 * @return The Display object on which this method was called.
	 */
	public Display disableCameraPositionPrinting() {
		fields.settings.camPosPrint = false;
		recalculateExtrasScript();
		return this;
	}
	/** Sets the position on the frame at which the camera's actual position should be printed.
	 * @param pos The position on the frame at which the camera's actual position should be printed.
	 * @return The Display object on which this method was called.
	 */
	public Display setCameraPositionPrintingPosition(Point pos) {
		fields.settings.camPosPrintPoint = pos;
		return this;
	}
	/** Returns the current FOV in radians.
	 * @return The current FOV in radians.
	 */
	public double getFOVRadians() {
		return fields.settings.viewAngle;
	}
	/** Sets the current FOV in radians.
	 * @param viewAngle The new FOV in radians.
	 * @return The Display object on which this method was called.
	 */
	public Display setFOVRadians(double viewAngle) {
		if (Math.abs(viewAngle) < Math.PI) {
			fields.settings.viewAngle = viewAngle;
		} else {
			double newAngle = viewAngle;
			while (newAngle > Math.PI) {
				newAngle -= Math.PI;
			}
			while (newAngle < -Math.PI) {
				newAngle += Math.PI;
			}
			fields.settings.viewAngle = newAngle;
		}
		return this;
	}
	/** Returns the current FOV in degrees.
	 * @return The current FOV in degrees.
	 */
	public double getFOVDegrees() {
		return Math.toDegrees(fields.settings.viewAngle);
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
		fields.settings.yAxisClamp = true;
		return this;
	}
	/** Disables clamping the y-axis viewing angle such that the user cannot move the camera past the y-axis.
	 * @return The Display object on which this method was called.
	 */
	public Display disableYAxisClamping() {
		fields.settings.yAxisClamp = false;
		return this;
	}
	@SuppressWarnings("deprecation")
	/** Sets the device on which the current Scene should be computed. This can be set at any time with seamless switching.
	 * @param renderMode The new compute device.
	 * @return The Display object on which this method was called.
	 */
	public Display setRenderTarget(RenderTarget renderMode) {
		fields.settings.renderTarget = renderMode;
		if (fields.settings.renderTarget == RenderTarget.CPU_SINGLETHREADED) {
			fields.renderer.renderScript = new CPURenderer();
		} else {
			if (fields.settings.renderTarget == RenderTarget.CPU_MULTITHREADED) { // checks which device should be used for rendering
				fields.renderer.openCLDevice = Device.firstCPU();
				if (fields.renderer.openCLDevice == null) {
					System.err.println("FATAL ERROR: The OpenCL driver for your CPU is not installed, but it is required for the CPU multithreading feature. Either install the OpenCL driver for the selected device, or set the render target to RenderTarget.CPU_SINGLETHREADED.");
					throw new CPU_OpenCLDriverNotFoundError();
				}
			} else {
				fields.renderer.openCLDevice = Device.bestGPU();
				if (fields.renderer.openCLDevice == null) {
					System.err.println("FATAL ERROR: The OpenCL driver for your GPU is not installed, but it is required for the GPU rendering feature. Either install the OpenCL driver for the selected device, or set the render target to RenderTarget.CPU_SINGLETHREADED.");
					throw new GPU_OpenCLDriverNotFoundError();
				}
			}
			fields.renderer.renderScript = new OpenCLRenderer();
		}
		return this;
	}
	/** Returns the current compute device.
	 * @return The current compute device.
	 */
	public RenderTarget getRenderTarget() {
		return fields.settings.renderTarget;
	}
	/** Enables anti-aliasing (disables aliasing).
	 * @return The Display object on which this method was called.
	 */
	public Display enableAntialiasing() {
		fields.settings.antialiasingHint = true;
		return this;
	}
	/** Disables anti-aliasing (enables aliasing).
	 * @return The Display object on which this method was called.
	 */
	public Display disableAntialiasing() {
		fields.settings.antialiasingHint = false;
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
		fields.settings.renderingHint = mode;
		return this;
	}
	/** Returns the current render quality.
	 * @return The current render quality.
	 */
	public RenderMode getRenderingMode() {
		return fields.settings.renderingHint;
	}
	/** Enables dithering.
	 * @return The Display object on which this method was called.
	 */
	public Display enableDithering() {
		fields.settings.ditheringHint = true;
		return this;
	}
	/** Disables dithering.
	 * @return The Display object on which this method was called.
	 */
	public Display disableDithering() {
		fields.settings.ditheringHint = false;
		return this;
	}
	/**
	 * @param mode Sets color render quality.
	 * @return The Display object on which this method was called.
	 */
	public Display setColorRenderingMode(RenderMode mode) {
		fields.settings.colorRenderingHint = mode;
		return this;
	}
	/** Returns the current color render quality.
	 * @return The current color render quality.
	 */
	public RenderMode getColorRenderingMode() {
		return fields.settings.colorRenderingHint;
	}
	/** Enables fractional metrics.
	 * @return The Display object on which this method was called.
	 */
	public Display enableFractionalMetrics() {
		fields.settings.fractionalMetricsHint = true;
		return this;
	}
	/** Disables fractional metrics.
	 * @return The Display object on which this method was called.
	 */
	public Display disableFractionalMetrics() {
		fields.settings.fractionalMetricsHint = false;
		return this;
	}
	/** Enables text anti-aliasing (disables text aliasing).
	 * @return The Display object on which this method was called.
	 */
	public Display enableTextAntialiasing() {
		fields.settings.textAntialiasingHint = true;
		return this;
	}
	/** Disables text anti-aliasing (enables text aliasing).
	 * @return The Display object on which this method was called.
	 */
	public Display disableTextAntialiasing() {
		fields.settings.textAntialiasingHint = false;
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
		fields.settings.interpolationHint = mode;
		return this;
	}
	/** Returns the current image interpolation mode.
	 * @return The Display object on which this method was called.
	 */
	public InterpolationMode getInterpolationMode() {
		return fields.settings.interpolationHint;
	}
	/** Sets alpha interpolation quality.
	 * @param mode The new alpha interpolation quality.
	 * @return The Display object on which this method was called.
	 */
	public Display setAlphaInterpolationMode(RenderMode mode) {
		fields.settings.alphaInterpolationHint = mode;
		return this;
	}
	/** Returns the current alpha interpolation quality.
	 * @return The Display object on which this method was called.
	 */
	public RenderMode getAlphaInterpolationMode() {
		return fields.settings.alphaInterpolationHint;
	}
	/** Sets overall render quality.
	 * @param mode The new overall render quality.
	 * @return The Display object on which this method was called.
	 */
	public Display setRenderQuality(RenderMode mode) { // sets rendering hints to either the best or worst settings
		if (mode == RenderMode.PERFORMANCE) {
			fields.settings.antialiasingHint = false;
			fields.settings.renderingHint = RenderMode.PERFORMANCE;
			fields.settings.ditheringHint = false;
			fields.settings.colorRenderingHint = RenderMode.PERFORMANCE;
			fields.settings.fractionalMetricsHint = false;
			fields.settings.textAntialiasingHint = false;
			fields.settings.interpolationHint = InterpolationMode.NEAREST_NEIGHBOR;
			fields.settings.alphaInterpolationHint = RenderMode.PERFORMANCE;
		} else {
			fields.settings.antialiasingHint = true;
			fields.settings.renderingHint = RenderMode.QUALITY;
			fields.settings.ditheringHint = true;
			fields.settings.colorRenderingHint = RenderMode.QUALITY;
			fields.settings.fractionalMetricsHint = true;
			fields.settings.textAntialiasingHint = true;
			fields.settings.interpolationHint = InterpolationMode.BICUBIC;
			fields.settings.alphaInterpolationHint = RenderMode.QUALITY;
		}
		return this;
	}
	/** Adds a particle to the particle system.
	 * @param particle The new particle to be added.
	 * @return The particle ID needed to remove the particle. -1 if the Particle was added unsuccessfully.
	 */
	public int addParticle(Particle particle) {
		try {particle.start();} catch (NullPointerException ex) {}
		if (fields.scene.particles.add(particle)) {
			return fields.scene.particles.size()-1;
		}
		return -1;
	}
	/** Removes a particle from the particle system.
	 * @param particleID The particle ID of the particle you wish to remove.
	 * @return The Display object on which this method was called.
	 */
	public Display removeParticle(int particleID) {
		fields.scene.particles.set(particleID, null);
		return this;
	}
	/** Returns the current particle array.
	 * @return The current particle array.
	 */
	public ArrayList<Particle> getParticles() {
		return fields.scene.particles;
	}
	/** Sets the size in 2D in which points should be rendered.
	 * @param pointSize The new size of points.
	 * @return The Display object on which this method was called.
	 */
	public Display setPointSize(Dimension pointSize) {
		fields.settings.pointSize = pointSize;
		return this;
	}
	/** Returns the size in 2D in which points are being rendered.
	 * @return The Display object on which this method was called.
	 */
	public Dimension getPointSize() {
		return fields.settings.pointSize;
	}
	/** Returns this Display's Time object. Used for timing physics operations.
	 * @return The Display object on which this method was called.
	 */
	public Time getTime() {
		return fields.time;
	}
	/** Enables rendering points to the frame.
	 * @return The Display object on which this method was called.
	 */
	public Display enablePointRendering() {
		fields.settings.renderPoints = true;
		return this;
	}
	/** Disables rendering points to the frame.
	 * @return The Display object on which this method was called.
	 */
	public Display disablePointRendering() {
		fields.settings.renderPoints = false;
		return this;
	}
	protected void recalculateExtrasScript() {
		int state = 0;
		if (fields.settings.camPosPrint) {
			state += 1;
		}
		if (fields.settings.faceRender) {
			state += 2;
		}
		if (fields.settings.lineRender) {
			state += 4;
		}
		if (state == 0) {
			fields.renderer.extrasRenderer = new Runnable() {@Override public void run() {}};
		} else if (state == 1) {
			fields.renderer.extrasRenderer = new Runnable() {
				@Override
				public void run() {
					printCameraPosition();
				}
			};
		} else if (state == 2) {
			fields.renderer.extrasRenderer = new Runnable() {
				@Override
				public void run() {
					renderFaces();
				}
			};
		} else if (state == 3) {
			fields.renderer.extrasRenderer = new Runnable() {
				@Override
				public void run() {
					printCameraPosition();
					renderFaces();
				}
			};
		} else if (state == 4) {
			fields.renderer.extrasRenderer = new Runnable() {
				@Override
				public void run() {
					renderLines();
				}
			};
		} else if (state == 5) {
			fields.renderer.extrasRenderer = new Runnable() {
				@Override
				public void run() {
					printCameraPosition();
					renderLines();
				}
			};
		} else if (state == 6) {
			fields.renderer.extrasRenderer = new Runnable() {
				@Override
				public void run() {
					renderFaces();
					renderLines();
				}
			};
		} else {
			fields.renderer.extrasRenderer = new Runnable() {
				@Override
				public void run() {
					printCameraPosition();
					renderFaces();
					renderLines();
				}
			};
		}
	}
	public Display setRenderScript(RenderScript script) {
		fields.script = script;
		return this;
	}
	protected class CPURenderer implements Runnable {
		@Override
		public void run() {
			fields.pointArrays = new Point[fields.scene.object.length][];
			for (int a = 0; a < fields.scene.object.length; a++) {
				Point[] points = new Point[fields.scene.object[a].points.length];
				for (int i = 0; i < fields.scene.object[a].points.length; i++) {
					points[i] = calculatePoint(a, i);
					if (fields.settings.renderPoints) {
						renderPoint(points[i], a, i);
					}
				}
				if (fields.settings.faceRender) { // sorts faces so that they're rendered back to front
					sortFaces(a, points);
				}
			}					
			renderExtras();
		}
	}
	protected class OpenCLRenderer implements Runnable {
		@Override
		public void run() {
			prepareGPU(fields.localCamPos);
			calculateOnGPU();
			fields.pointArrays = new Point[fields.scene.object.length][];
			for (int a = 0; a < fields.scene.object.length; a++) {
				Point[] points = new Point[fields.scene.object[a].points.length];
				for (int i = 0; i < fields.scene.object[a].points.length; i++) {
					points[i] = calculatePointGPU(a, i);
					if (fields.settings.renderPoints) {
						renderPoint(points[i], a, i);
					}
				}
				if (fields.settings.faceRender) { // sorts faces so that they're rendered from back to front
					sortFaces(a, points);
				}
			}
			renderExtras();
		}
	}
	/** Sets the multiplier for the camera distance change when the scroll wheel is 'clicked.'
	 * @param multiplier The new multiplier.
	 * @return The Display object on which this method was called.
	 * @since 3.0.1
	 */
	public Display setScrollWheelMultiplier(double multiplier) {
		fields.settings.scrollMultiplier = multiplier;
		return this;
	}
	/** Returns the current scroll wheel multiplier.
	 * @return The current scroll wheel multiplier.
	 * @since 3.0.1
	 */
	public double getScrollWheelMultiplier() {
		return fields.settings.scrollMultiplier;
	}
	protected class MouseDrag implements Runnable {
		@Override
		public void run() {
			if (fields.mouseClicked) {
				Point temp = new Point(MouseInfo.getPointerInfo().getLocation().x-fields.frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-fields.frame.getLocationOnScreen().y);
				fields.mouse = new Point(temp.x-fields.mouseDiff.x, temp.y-fields.mouseDiff.y);
			} else {
				fields.mouse = fields.lastMousePos;
			}
		}
	}
	protected class MouseMove implements Runnable {
		@Override
		public void run() {
			fields.mouse = new Point(MouseInfo.getPointerInfo().getLocation().x-fields.frame.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y-fields.frame.getLocationOnScreen().y);
		}
	}
	/** Sets the rendering hints directly. This has no effect on the output of any specific rendering hint getters.
	 * @param hints The new rendering hints.
	 * @return The Display object on which this method was called.
	 * @since 3.0.2
	 */
	public Display setRenderingHints(RenderingHints hints) {
		fields.hints = hints;
		return this;
	}
}