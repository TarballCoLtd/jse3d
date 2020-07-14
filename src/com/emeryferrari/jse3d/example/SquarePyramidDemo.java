package com.emeryferrari.jse3d.example;
import java.awt.*;
import com.emeryferrari.jse3d.enums.*;
import com.emeryferrari.jse3d.gfx.*;
import com.emeryferrari.jse3d.obj.*;
public class SquarePyramidDemo {
	public static void main(String[] args) {
		Object3D[] objects = new Object3D[1];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = ObjectTemplate.getSquarePyramid();
			objects[i].movePosRel(new Vector3(i*2-objects.length/2, i*2-objects.length/2, i*2-objects.length/2), new Vector3(0, 0, 0));
		}
		Scene scene = new Scene(objects, 5.0);
		Display display = new Display(scene, "jse3d cube demo", Math.toRadians(60), ObjectTemplate.getCube().points.length*objects.length, ObjectTemplate.getCube().points.length, objects.length);
		display.setPointSize(new Dimension(40, 40));
		display.disableFPSLimit();
		display.enableFPSLogging();
		display.enableCameraPositionPrinting();
		display.setRenderTarget(RenderTarget.GPU);
		display.startRender();
	}
}