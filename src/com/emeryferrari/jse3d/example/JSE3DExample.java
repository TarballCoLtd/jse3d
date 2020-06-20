package com.emeryferrari.jse3d.example;
import com.emeryferrari.jse3d.*;
import com.emeryferrari.jse3d.enums.*;
import com.emeryferrari.jse3d.obj.*;
public class JSE3DExample {
	public static void main(String[] args) {
		Object3D[] objects = new Object3D[32];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = ObjectTemplate.getCube();
			objects[i].movePosRel(new Point3D(i, i, i), new Point3D(0, 0, 0));
		}
		Scene scene = new Scene(objects, 5.0);
		Display display = new Display(scene, "jse3d demo", Math.toRadians(60), ObjectTemplate.getCube().points.length*objects.length, ObjectTemplate.getCube().points.length, objects.length);
		display.enableFPSLogging();
		display.disableFPSLimit();
		display.enableCameraPositionPrinting();
		display.enableFaceRendering();
		display.enableAlternateTrigonometry();
		display.setRenderTarget(RenderTarget.CPU_SINGLETHREADED);
		display.setRenderQuality(RenderMode.PERFORMANCE);
		display.startRender();
	}
}