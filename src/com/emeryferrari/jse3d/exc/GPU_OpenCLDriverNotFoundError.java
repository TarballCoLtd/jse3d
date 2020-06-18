package com.emeryferrari.jse3d.exc;
public class GPU_OpenCLDriverNotFoundError extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public GPU_OpenCLDriverNotFoundError() {
		printStackTrace();
		System.exit(18);
	}
}