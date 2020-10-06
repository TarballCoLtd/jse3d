package com.alyxferrari.jse3d.gfx;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import com.alyxferrari.jse3d.gfx.Display.DisplayRenderer;
import com.alyxferrari.jse3d.interfaces.RenderScript;
import com.alyxferrari.jse3d.obj.Distance;
import com.alyxferrari.jse3d.obj.Scene;
import com.alyxferrari.jse3d.obj.Vector3;
/** Used internally. Do not edit.
 * @author Alyx Ferrari
 */
public class DisplayFields {
	DisplayFields() {}
	int maxFps;
	int secondsOpen;
	long frameFps;
	Image buffer; // used for double buffering
	Graphics2D graphics; // set to the above Image's Graphics component every frame
	DisplayRenderer renderer; // a JComponent that handles rendering
	Scene scene; // the current scene
	JFrame frame; // the frame that the scene is rendered in
	boolean rendering; // true if the startRender() method has been called
	boolean rendererStarted; // true if the startRender() method has been called
	long optimalTime; // time variable used by FPS timer
	Point lastMousePos; // mouse variable used by view angle changers
	boolean mouseClicked; // mouse variable used by view angle changers
	Point mouseDiff; // mouse variable used by view angle changers
	Vector3 camPos; // current camera position in relation to (0,0,0)
	Distance[][] distance; // used internally by scene renderer, represents distances between points and the camera
	double[][] camScale; // used internally by scene renderer
	double xTransform = 0; // used internally by scene renderer
	double yTransform = 0; // used internally by scene renderer
	ViewAngle sphere; // used internally by scene renderer, represents a point on a sphere around the current camera position
	int fps = 0; // time variable used by FPS timer
	RenderingHints hints; // current rendering hints
	Point mouse;
	Point[][] pointArrays; // array of 2D point arrays where 3D points should be rendered on the frame
	Vector3 localCamPos;
	Time time; // controls delta time and fixed delta time for this Display instance
	ParticleKernel particleKernel; // kernel responsible for calculating particle positions with OpenCL
	ObjectKernel objKernel; // kernel responsible for calculating point positions with OpenCL
	/** Used internally. Do not modify.
	 */
	public BakedLightingKernel bakedKernel;
	RenderScript script; // called before and after 3D rendering
	DisplaySettings settings; // display settings
	int zAngleLength;
}