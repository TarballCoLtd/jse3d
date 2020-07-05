package com.emeryferrari.jse3d.example;
import com.emeryferrari.jse3d.*;
import com.emeryferrari.jse3d.enums.*;
import com.emeryferrari.jse3d.obj.*;
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
		display.setRenderTarget(RenderTarget.CPU_SINGLETHREADED);
		display.setPointSize(new java.awt.Dimension(40, 40));
		display.startRender();
		Trajectory trajectory = new Trajectory();
		Particle particle = new Particle(new Vector3(0, 0, 0), trajectory);
		Runnable runnable = new Runnable() {
			private Vector3 increment = new Vector3(0, 4, 0);
			@Override
			public void run() {
				Vector3 currentPos = particle.getPosition();
				if (currentPos.getY() > 4) {
					increment.setY(-4.0);
				} else if (currentPos.getY() < -4) {
					increment.setY(4.0);
				}
				particle.setPosition(new Vector3(currentPos.getX()+(increment.getX()*Time.fixedDeltaTime), currentPos.getY()+(increment.getY()*Time.fixedDeltaTime), currentPos.getZ()+(increment.getZ()*Time.fixedDeltaTime)));
			}
		};
		particle.getTrajectory().setRunnable(runnable);
		display.addParticle(particle);
	}
}