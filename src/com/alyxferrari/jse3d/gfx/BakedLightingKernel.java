package com.alyxferrari.jse3d.gfx;
import com.aparapi.*;
/** Used internally. Do not instantiate.
 * @author Alyx Ferrari
 * @since 3.3.2
 */
public class BakedLightingKernel extends Kernel {
	public final float[] lightXs;
	public final float[] lightYs;
	public final float[] lightZs;
	public final float[] lightStrengths;
	public final int[] lightCount = new int[1];
	public final float[] ambientLight = new float[1];
	public final float[] point1Xs;
	public final float[] point1Ys;
	public final float[] point1Zs;
	public final float[] point2Xs;
	public final float[] point2Ys;
	public final float[] point2Zs;
	public final float[] point3Xs;
	public final float[] point3Ys;
	public final float[] point3Zs;
	public final int[] inColorR;
	public final int[] inColorG;
	public final int[] inColorB;
	public final int[] outColorR;
	public final int[] outColorG;
	public final int[] outColorB;
	/** Used internally. Do not instantiate.
	 */
	public BakedLightingKernel(Display display) {
		point1Xs = new float[display.fields.settings.maxPointsTotal];
		point1Ys = new float[display.fields.settings.maxPointsTotal];
		point1Zs = new float[display.fields.settings.maxPointsTotal];
		point2Xs = new float[display.fields.settings.maxPointsTotal];
		point2Ys = new float[display.fields.settings.maxPointsTotal];
		point2Zs = new float[display.fields.settings.maxPointsTotal];
		point3Xs = new float[display.fields.settings.maxPointsTotal];
		point3Ys = new float[display.fields.settings.maxPointsTotal];
		point3Zs = new float[display.fields.settings.maxPointsTotal];
		inColorR = new int[display.fields.settings.maxPointsTotal];
		inColorG = new int[display.fields.settings.maxPointsTotal];
		inColorB = new int[display.fields.settings.maxPointsTotal];
		outColorR = new int[display.fields.settings.maxPointsTotal];
		outColorG = new int[display.fields.settings.maxPointsTotal];
		outColorB = new int[display.fields.settings.maxPointsTotal];
		lightXs = new float[display.fields.settings.maxPointsTotal];
		lightYs = new float[display.fields.settings.maxPointsTotal];
		lightZs = new float[display.fields.settings.maxPointsTotal];
		lightStrengths = new float[display.fields.settings.maxPointsTotal];
	}
	public void run() {
		int id = getGlobalId();/*
		outColorR[id] = (int)(inColorR[id]*ambientLight[0]);
		outColorG[id] = (int)(inColorG[id]*ambientLight[0]);
		outColorB[id] = (int)(inColorB[id]*ambientLight[0]);*/
		outColorR[id] = 0;
		outColorG[id] = 0;
		outColorB[id] = 0;
		for (int i = 0; i < lightCount[0]; i++) {
			float crossX = crossXTriangle(point1Xs[id], point1Ys[id], point1Zs[id], point2Xs[id], point2Ys[id], point2Zs[id], point3Xs[id], point3Ys[id], point3Zs[id]);
			float crossY = crossYTriangle(point1Xs[id], point1Ys[id], point1Zs[id], point2Xs[id], point2Ys[id], point2Zs[id], point3Xs[id], point3Ys[id], point3Zs[id]);
			float crossZ = crossZTriangle(point1Xs[id], point1Ys[id], point1Zs[id], point2Xs[id], point2Ys[id], point2Zs[id], point3Xs[id], point3Ys[id], point3Zs[id]);
			float dot = dot(crossX, crossY, crossZ, lightXs[i], lightYs[i], lightZs[i]);
			dot *= lightStrengths[i];
			if (dot > 0) {
				int red = (int)((inColorR[id]*dot)+(inColorR[id]*ambientLight[0]));
				int green = (int)((inColorG[id]*dot)+(inColorG[id]*ambientLight[0]));
				int blue = (int)((inColorB[id]*dot)+(inColorB[id]*ambientLight[0]));
				if (red > 255) {
					red = 255;
				}
				if (green > 255) {
					green = 255;
				}
				if (blue > 255) {
					blue = 255;
				}/*
				red = red > 255 ? 255 : red;
				green = green > 255 ? 255 : red;
				blue = blue > 255 ? 255 : blue;*/
				if (red > outColorR[id]) {
					outColorR[id] = red;
				}
				if (green > outColorG[id]) {
					outColorG[id] = green;
				}
				if (blue > outColorB[id]) {
					outColorB[id] = blue;
				}/*
				outColorR[id] = red > outColorR[id] ? red : outColorR[id];
				outColorG[id] = green > outColorG[id] ? green : outColorG[id];
				outColorB[id] = blue > outColorB[id] ? blue : outColorB[id];*/
			}
		}
	}
	public float crossX(float y1, float z1, float y2, float z2) {
		return (y1*z2)-(z1*y2);
	}
	public float crossY(float x1, float z1, float x2, float z2) {
		return (z1*x2)-(x1*z2);
	}
	public float crossZ(float x1, float y1, float x2, float y2) {
		return (x1*y2)-(y1*x2);
	}
	public float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
		return (x1*x2)+(y1*y2)+(z1*z2);
	}
	public float crossXTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
		return crossX(y2-y1, z2-z1, y3-y1, z3-z1);
	}
	public float crossYTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
		return crossY(x2-x1, z2-z1, x3-x1, z3-z1);
	}
	public float crossZTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
		return crossZ(x2-x1, y2-y1, x3-x1, y3-y1);
	}
}