package com.alyxferrari.jse3d.gfx;
import com.aparapi.*;
class ObjectKernel extends Kernel {
	final float[] zAngleX;
	final float[] zAngleY;
	final float[] zAngleZ;
	final float[] viewAngleXInput = new float[1];
	final float[] viewAngleYInput = new float[1];
	final float[] sinViewAngleX = new float[1];
	final float[] sinViewAngleY = new float[1];
	final float[] cosViewAngleX = new float[1];
	final float[] cosViewAngleY = new float[1];
	final float[] sinViewAngleXzAngle;
	final float[] cosViewAngleXzAngle;
	final float[] xTransforms;
	final float[] yTransforms;
	final float[] localCamPosX = new float[1];
	final float[] localCamPosY = new float[1];
	final float[] localCamPosZ = new float[1];
	final float[] maths;
	final float[] cosThetas;
	final float[] gpuViewAngle = new float[1];
	final float[] sinViewAngles;
	ObjectKernel(Display display) {
		zAngleX = new float[display.fields.settings.maxPointsTotal];
		zAngleY = new float[display.fields.settings.maxPointsTotal];
		zAngleZ = new float[display.fields.settings.maxPointsTotal];
		sinViewAngleXzAngle = new float[display.fields.settings.maxPointsTotal];
		cosViewAngleXzAngle = new float[display.fields.settings.maxPointsTotal];
		xTransforms = new float[display.fields.settings.maxPointsTotal];
		yTransforms = new float[display.fields.settings.maxPointsTotal];
		maths = new float[display.fields.settings.maxPointsTotal];
		cosThetas = new float[display.fields.settings.maxPointsTotal];
		sinViewAngles = new float[display.fields.settings.maxPointsTotal];
	}
	// ORIGINAL CPU CODE BY SAM KRUG, GPU ADAPTATION BY ALYX FERRARI
	/** Code to be recompiled to OpenCL and run on GPU or multithreaded CPU by Aparapi. Do not execute this method.
	*/
	public void run() {
		int id = getGlobalId();
		if (id == 0) {
			sinViewAngleX[0] = sin(viewAngleXInput[0]);
			sinViewAngleY[0] = sin(viewAngleYInput[0]);
			cosViewAngleX[0] = cos(viewAngleXInput[0]);
			cosViewAngleY[0] = cos(viewAngleYInput[0]);
		}
		float zAngles = 0f;
		float mags = 0f;
		if (!(zAngleX[id] == 0f && zAngleZ[id] == 0f)) {
			zAngles = atan(zAngleZ[id]/zAngleX[id]);
		}
		sinViewAngleXzAngle[id] = sin(viewAngleXInput[0]+zAngles);
		cosViewAngleXzAngle[id] = cos(viewAngleXInput[0]+zAngles);
		mags = hypot(zAngleX[id], zAngleZ[id]);
		if (zAngleX[id] < 0) {
			xTransforms[id] = -mags*DisplaySettings.SCALE*cosViewAngleXzAngle[id];
			yTransforms[id] = -mags*DisplaySettings.SCALE*sinViewAngleXzAngle[id]*sinViewAngleY[0]+zAngleY[id]*DisplaySettings.SCALE*cosViewAngleY[0];
		} else {
			xTransforms[id] = mags*DisplaySettings.SCALE*cosViewAngleXzAngle[id];
			yTransforms[id] = mags*DisplaySettings.SCALE*sinViewAngleXzAngle[id]*sinViewAngleY[0]+zAngleY[id]*DisplaySettings.SCALE*cosViewAngleY[0];
		}
		maths[id] = sqrt(pow(localCamPosX[0]-zAngleX[id], 2)+pow(localCamPosY[0]-zAngleY[id], 2)+pow(localCamPosZ[0]-zAngleZ[id], 2));
		cosThetas[id] = cos(asin((hypot(xTransforms[id], yTransforms[id])/DisplaySettings.SCALE)/maths[id]));
		sinViewAngles[id] = sin(gpuViewAngle[0]/2);
	}
	public void test() {
		
	}
}