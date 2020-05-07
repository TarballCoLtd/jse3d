package com.emeryferrari.jse3d;
import java.io.*;
public class Object3DSerializer {
	private Object3DSerializer() {}
	public static void save(Object3D object, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file+".jse");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(object);
		oos.flush();
		oos.close();
	}
	public static void save(File file, Object3D object) throws IOException {
		save(object, file);
	}
	public static void save(Object3D object, String file) throws IOException {
		save(object, new File(file));
	}
	public static void save(String file, Object3D object) throws IOException {
		save(object, file);
	}
	public static Object3D load(File file) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object3D ret = (Object3D) ois.readObject();
		ois.close();
		return ret;
	}
	public static Object3D load(String file) throws IOException, ClassNotFoundException {
		return load(new File(file));
	}
}