package com.emeryferrari.jse3d.example;
import com.emeryferrari.jse3d.*;
public class JSE3DExample {
	public static void main(String[] args) throws InterruptedException {
		Scene scene = Scene.getInstance(ObjectTemplate.TRIANGLE, 5.0);
		Display display = new Display(scene, "jse3d demo", true, false);
		display.enableFPSLogging();
		display.startRender();
	}
}