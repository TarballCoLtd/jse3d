package com.emeryferrari.jse3d;
public class JSE3DConst {
	public static final String NAME = "jse3d";
	public static final String VERSION = "v1.2.4 beta";
	public static final String FULL_NAME = JSE3DConst.NAME + " " + JSE3DConst.VERSION;
	public static final int PHYSICS_TIMESTEP = 60;
	public static int TARGET_FPS = 60;
	public static long OPTIMAL_TIME = 1000000000 / JSE3DConst.TARGET_FPS;
}