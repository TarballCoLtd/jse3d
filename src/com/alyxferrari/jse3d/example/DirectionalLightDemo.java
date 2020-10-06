package com.alyxferrari.jse3d.example;
import com.alyxferrari.jse3d.obj.*;
import com.alyxferrari.jse3d.enums.*;
import com.alyxferrari.jse3d.gfx.*;
import java.awt.*;
public class DirectionalLightDemo {
	public static final String OBJ_PATH = "C:/cat.obj"; // change this if you want to use this example
	public static void main(String[] args) throws Exception {
		Object3D[] objects = {Object3D.createFromObj(OBJ_PATH, new Color(255, 20, 20, 255))};
		Scene scene = new Scene(objects, 550);
		DirectionalLight[] lights = {new DirectionalLight(new Vector3(0, 0.8, -1), 0.7f, true), new DirectionalLight(new Vector3(0, -0.8, 1), 0.4f, true)};
		scene.setDirectionalLights(lights);
		Display display = new Display(scene, "jse3d obj model demo", Math.toRadians(60), objects[0].points.length, objects[0].points.length, 1);
		display.disableFPSLimit();
		display.disableLineRendering();
		display.enableFPSLogging();
		display.enableCameraPositionPrinting();
		display.setCameraPositionPrecision(PrecisionMode.INTEGER);
		display.setRenderTarget(RenderTarget.GPU);
		display.setRenderQuality(RenderMode.PERFORMANCE);
		display.startRender();
		System.out.println("Took " + display.getScene().object[0].setStatic(display) + " ms to generate a lightmap");
	}
}