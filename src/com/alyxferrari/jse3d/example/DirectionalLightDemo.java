package com.alyxferrari.jse3d.example;
import com.alyxferrari.jse3d.obj.*;
import com.alyxferrari.jse3d.enums.*;
import com.alyxferrari.jse3d.gfx.*;
public class DirectionalLightDemo {
	public static void main(String[] args) throws Exception {
		/*
		Object3D[] objects = new Object3D[1];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = ObjectTemplate.getCube();
		}
		*/
		Object3D[] objects = {Object3D.createFromObj("C:/Users/rubiq/Downloads/cat.obj", java.awt.Color.RED)};
		Scene scene = new Scene(objects, 5.0+objects.length);
		scene.setDirectionalLight(new DirectionalLight(new Vector3(0, 0, 1), 1));
		Display display = new Display(scene, "jse3d directional light demo", Math.toRadians(60), objects[0].points.length*objects.length, objects[0].points.length, objects.length);
		display.setPointSize(new java.awt.Dimension(40, 40));
		display.setLineColor(java.awt.Color.CYAN);
		display.enableFPSLogging();
		display.disableFPSLimit();
		display.setRenderTarget(RenderTarget.GPU);
		display.setRenderQuality(RenderMode.PERFORMANCE);
		display.startRender();
	}
}