package com.emeryferrari.jse3d;
public class CPU_OpenCLDriverNotFoundError extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public CPU_OpenCLDriverNotFoundError() {
		printStackTrace();
		System.exit(17);
	}
}