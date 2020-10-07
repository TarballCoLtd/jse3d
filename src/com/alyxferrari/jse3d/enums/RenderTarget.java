package com.alyxferrari.jse3d.enums;
/** Represents the device being used to render a Scene.
 * @author Alyx Ferrari
 * @since 2.0
 */
public enum RenderTarget {
	/** Runs the operation on a single CPU thread. Works every time. Can be faster on small workloads.
	 */
	CPU_SINGLETHREADED,
	/** Runs the operation on multiple CPU threads. Works only if your CPU's OpenCL driver is installed, so this will not work on AMD processors.
	 */
	CPU_MULTITHREADED,
	/** Runs the operation on multiple GPU cores. Works only if your GPU's OpenCL driver is installed, which it probably is if you have one.
	 */
	GPU;
}