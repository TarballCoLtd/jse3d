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
		sinViewAngleX = Math.sin(viewAngleX);
		cosViewAngleX = Math.cos(viewAngleX);
		sinViewAngleY = Math.sin(viewAngleY);
		cosViewAngleY = Math.cos(viewAngleY);
	}
}