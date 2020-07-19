package com.alyxferrari.jse3d.exc;
/** Thrown if RenderTarget.CPU_MULTITHREADED is the current render target, but the CPU's OpenCL driver is not installed.
 * @author Alyx Ferrari
 * @since 2.0
 */
public class CPU_OpenCLDriverNotFoundError extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/** Default constructor for CPU_OpenCLDriverNotFoundError.
	 */
	public CPU_OpenCLDriverNotFoundError() {
		printStackTrace();
		System.exit(17);
	}
}