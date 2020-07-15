package com.emeryferrari.jse3d.exc;
/** Thrown if RenderTarget.GPU is the current render target, but the GPU's OpenCL driver is not installed.
 * @author Alyx Ferrari
 * @since 2.0
 */
public class GPU_OpenCLDriverNotFoundError extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/** Default constructor for GPU_OpenCLDriverNotFoundError.
	 */
	public GPU_OpenCLDriverNotFoundError() {
		printStackTrace();
		System.exit(18);
	}
}