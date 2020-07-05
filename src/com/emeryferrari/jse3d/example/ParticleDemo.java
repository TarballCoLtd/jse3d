package com.emeryferrari.jse3d.example;
import com.emeryferrari.jse3d.gfx.*;
import com.emeryferrari.jse3d.enums.*;
import com.emeryferrari.jse3d.obj.*;
import com.emeryferrari.jse3d.interfaces.*;
public class ParticleDemo {
	public static void main(String[] args) {
		Object3D[] objects = new Object3D[1];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = ObjectTemplate.getCube();
			objects[i].movePosRel(new Vector3(i*2-objects.length/2, i*2-objects.length/2, i*2-objects.length/2), new Vector3(0, 0, 0));
		}
		Scene scene = new Scene(objects, 5.0);
		Display display = new Display(scene, "jse3d demo", Math.toRadians(60), ObjectTemplate.getCube().points.length*objects.length, ObjectTemplate.getCube().points.length, objects.length);
		display.enableFPSLogging();
		display.enableCameraPositionPrinting();
		display.setRenderTarget(RenderTarget.GPU);
		display.setPointSize(new java.awt.Dimension(40, 40));
		display.startRender();
		Trajectory trajectory = new Trajectory();
		Particle particle = new Particle(new Vector3(0, 0, 0), trajectory);
		Updatable runnable = new Updatable() {
			private Vector3 increment;
			@Override
			public void start() {
				increment = new Vector3(0, 4, 0);
			}
			@Override
			public void update() {}
			@Override
			public void fixedUpdate() {
				Vector3 currentPos = particle.getPosition();
				if (currentPos.getY() > 3) {
					increment.setY(-4.0);
				} else if (currentPos.getY() < -3) {
					increment.setY(4.0);
				}
				particle.setPosition(new Vector3(currentPos.getX()+(increment.getX()*display.getTime().fixedDeltaTime), currentPos.getY()+(increment.getY()*display.getTime().fixedDeltaTime), currentPos.getZ()+(increment.getZ()*display.getTime().fixedDeltaTime)));
			}
		};
		particle.getTrajectory().setScript(runnable);
		display.addParticle(particle);
	}
}