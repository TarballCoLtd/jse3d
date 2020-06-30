package com.emeryferrari.jse3d.example;
import com.emeryferrari.jse3d.*;
import com.emeryferrari.jse3d.enums.*;
import com.emeryferrari.jse3d.obj.*;
import com.emeryferrari.jse3d.network.*;
public class JSE3DExample {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("javax.net.ssl.trustStore", "alx.store");
		System.setProperty("javax.net.ssl.keyStore", "alx.store");
		Client3D client = new Client3D("127.0.0.1", 5107, "user1");
		client.start();
		/*
		Object3D[] objects = new Object3D[1];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = ObjectTemplate.getCube();
			objects[i].movePosRel(new Vector3(i, i, i), new Vector3(0, 0, 0));
		}
		Scene scene = new Scene(objects, 5.0);
		Display display = new Display(scene, "jse3d demo", Math.toRadians(60), ObjectTemplate.getCube().points.length*objects.length, ObjectTemplate.getCube().points.length, objects.length);
		display.enableFPSLogging();
		display.enableCameraPositionPrinting();
		display.setRenderTarget(RenderTarget.CPU_SINGLETHREADED);
		display.setRenderQuality(RenderMode.QUALITY);
		display.startRender();
		*/
	}
}