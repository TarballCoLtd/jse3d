package com.emeryferrari.jse3d.example;
import com.emeryferrari.jse3d.*;
import com.emeryferrari.jse3d.enums.*;
import com.emeryferrari.jse3d.obj.*;
public class JSE3DExample {
	public static void main(String[] args) {
		Object3D[] objects = {ObjectTemplate.getCube(), ObjectTemplate.getCube()};
		Scene scene = new Scene(objects, 5.0);
		Display display = new Display(scene, "jse3d demo", Math.toRadians(60), 16, 8, 2);
		display.enableFPSLogging();
		display.enableCameraPositionPrinting();
		display.enableFaceRendering();
		display.setRenderTarget(RenderTarget.GPU);
		display.setRenderQuality(RenderMode.PERFORMANCE);
		display.startRender();
		display.getScene().getObjects()[1].transitionPosRel(2.1, 2.1, 2.1, 2000, display);
	}
}