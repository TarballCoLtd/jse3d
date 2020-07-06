package com.emeryferrari.jse3d.gfx;
import java.awt.*;
import com.emeryferrari.jse3d.enums.*;
class DisplaySettings {
	boolean renderPoints; // true if points are to be rendered
	Dimension pointSize; // width and height of drawn points
	boolean fpsLimit; // true if FPS is being capped to a certain framerate
	boolean fpsLogging; // true if the user wants to log FPS counts to the console
	Color lineColor; // specifies color lines should be rendered in
	boolean lineRender; // true if lines are being rendered
	boolean faceRender; // true if faces are being rendered
	int targetFps; // FPS cap
	Color backgroundColor; // skybox color
	boolean scrollWheel; // mouse variable used by view angle changers
	int physicsTimestep = 60; // physics timestep for transition operations
	CameraMode mode; // current camera mode
	static final float SCALE = 150; // used internally by scene renderer
	static final double SENSITIVITY = 125; // used internally by scene renderer
	boolean camPosPrint = false; // true if camera position is to be printed to the frame
	boolean yAxisClamp; // true if viewAngleY is being clamped to [-pi, pi]
	double viewAngle;
	RenderTarget renderTarget; // current render target
	boolean antialiasingHint; // true if antialiasing should be used
	RenderMode renderingHint; // specifies quality of render
	boolean ditheringHint; // true if dithering should be enabled
	RenderMode colorRenderingHint; // specifies quality of colors
	boolean fractionalMetricsHint; // true if fractional metrics should be enabled
	boolean textAntialiasingHint; // true if text antialiasing should be used
	InterpolationMode interpolationHint; // image interpolation settings
	RenderMode alphaInterpolationHint; // alpha interpolation settings
	boolean assertion; // makes sure AssertionError's stack trace is only printed once
	Point camPosPrintPoint; // where the camera position should be printed to on the frame
	public DisplaySettings() {
		renderPoints = false;
		camPosPrintPoint = new Point(0, 11);
		assertion = false;
		antialiasingHint = true;
		renderingHint = RenderMode.PERFORMANCE;
		ditheringHint = false;
		colorRenderingHint = RenderMode.QUALITY;
		fractionalMetricsHint = false;
		textAntialiasingHint = false;
		interpolationHint = InterpolationMode.BILINEAR;
		alphaInterpolationHint = RenderMode.QUALITY;
		renderTarget = RenderTarget.GPU;
		fpsLimit = true;
		fpsLogging = false;
		lineRender = true;
		faceRender = true;
		lineColor = Color.BLACK;
		backgroundColor = Color.WHITE;
		scrollWheel = true;
		mode = CameraMode.DRAG;
		yAxisClamp = true;
	}
}