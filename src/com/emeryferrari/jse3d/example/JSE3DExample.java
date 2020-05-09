package com.emeryferrari.jse3d.example;
import com.emeryferrari.jse3d.*;
public class JSE3DExample {
	public static void main(String[] args) throws InterruptedException {
		Scene scene = Scene.getDemoInstance();
		Display display = new Display(scene, "jse3d demo", true, false);
		display.enableFPSLogging();
		display.startRender();
		display.getScene().getObjects()[0].transitionPosRel(0, -2, 0, 20000);
	}
}