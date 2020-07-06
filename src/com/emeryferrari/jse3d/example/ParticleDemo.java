package com.emeryferrari.jse3d.example;
import com.emeryferrari.jse3d.gfx.*;
import com.emeryferrari.jse3d.enums.*;
import com.emeryferrari.jse3d.obj.*;
import com.emeryferrari.jse3d.interfaces.*;
import java.awt.*;
public class ParticleDemo {
	public static void main(String[] args) {
		Object3D[] objects = new Object3D[1];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = ObjectTemplate.getCube();
			objects[i].movePosRel(new Vector3(i*2-objects.length/2, i*2-objects.length/2, i*2-objects.length/2), new Vector3(0, 0, 0));
		}
		Scene scene = new Scene(objects, 7.5);
		Display display = new Display(scene, "jse3d particle demo", Math.toRadians(60), ObjectTemplate.getCube().points.length*objects.length, ObjectTemplate.getCube().points.length, objects.length);
		display.enableFPSLogging();
		display.enableCameraPositionPrinting();
		display.setRenderTarget(RenderTarget.GPU);
		display.setRenderQuality(RenderMode.QUALITY);
		display.setPointSize(new Dimension(40, 40));
		display.startRender();
		Trajectory trajectory = new Trajectory();
		Particle particle = new Particle(new Vector3(0, 0, 0), trajectory);
		Updatable runnable = new Updatable() {
			private Vector3 increment;
			private Vector3 currentPos;
			@Override
			public void start() {
				increment = new Vector3(0, 4, 0);
				currentPos = particle.getPosition();
			}
			@Override
			public void update() {
				if (currentPos.getY() > Math.PI*2) {
					increment.setY(-Math.abs(increment.getY()));
				} else if (currentPos.getY() < -Math.PI*2) {
					increment.setY(Math.abs(increment.getY()));
				}
			}
			@Override
			public void fixedUpdate() {
				currentPos = particle.getPosition();
				double y = currentPos.getY()+(increment.getY()*display.getTime().fixedDeltaTime);
				particle.setPosition(new Vector3(Math.sin(y)*3, y, 0));
			}
		};
		particle.getTrajectory().setScript(runnable);
		display.addParticle(particle);
	}
}