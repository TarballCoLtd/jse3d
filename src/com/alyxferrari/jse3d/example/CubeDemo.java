package com.alyxferrari.jse3d.example;
import com.alyxferrari.jse3d.gfx.*;
import java.awt.*;
import com.alyxferrari.jse3d.enums.*;
import com.alyxferrari.jse3d.obj.*;
/** Demo for cube rendering.
 * @author Alyx Ferrari
 * @since 2.5
 */
public class CubeDemo {
	public static void main(String[] args) {
		Object3D[] objects = new Object3D[1];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = ObjectTemplate.getCube();
			objects[i].movePosRel(new Vector3((i*2)-objects.length/2, (i*2)-objects.length/2, (i*2)-objects.length/2), new Vector3(0, 0, 0));
		}
		Scene scene = new Scene(objects, 5.0+objects.length);
		Display display = new Display(scene, "jse3d cube demo", Math.toRadians(60), ObjectTemplate.getCube().points.length*objects.length, ObjectTemplate.getCube().points.length, objects.length);
		display.setPointSize(new Dimension(40, 40));
		display.enableFPSLogging();
		display.enableCameraPositionPrinting();
		display.setRenderTarget(RenderTarget.GPU);
		display.startRender();
	}
}