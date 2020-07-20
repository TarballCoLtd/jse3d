package com.alyxferrari.jse3d.gfx;
class ViewAngle {
	final double viewAngleX;
	final double viewAngleY;
	final double sinViewAngleX;
	final double cosViewAngleX;
	final double sinViewAngleY;
	final double cosViewAngleY;
	public ViewAngle(double viewAngleX, double viewAngleY) {
		this.viewAngleX = viewAngleX;
		this.viewAngleY = viewAngleY;
		sinViewAngleX = StrictMath.sin(viewAngleX);
		cosViewAngleX = StrictMath.cos(viewAngleX);
		sinViewAngleY = StrictMath.sin(viewAngleY);
		cosViewAngleY = StrictMath.cos(viewAngleY);
	}
}