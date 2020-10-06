package com.alyxferrari.jse3d.gfx;
import com.aparapi.*;
class BakedLightingKernel extends Kernel {
	public void run() {
		int id = getGlobalId();
	}
	public float crossX(float y1, float y2, float z1, float z2) {
		return (y1*z2)-(z1*y2);
	}
	public float crossY(float x1, float x2, float z1, float z2) {
		return (z1*x2)-(x1*z2);
	}
	public float crossZ(float x1, float x2, float y1, float y2) {
		return (x1*y2)-(y1*x2);
	}
	public float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
		return (x1*x2)+(y1*y2)+(z1*z2);
	}
}