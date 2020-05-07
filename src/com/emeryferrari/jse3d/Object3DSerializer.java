package com.emeryferrari.jse3d;
import java.io.*;
import java.util.*;
public class Object3DSerializer {
	private Object3DSerializer() {}
	public static void save(Object3D[] object, File file) throws IOException {
		if (object.length == 0) {
			throw new IOException("Object3D array cannot be empty.");
		}
		FileOutputStream fos = new FileOutputStream(file+".jse");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		for (int i = 0; i < object.length; i++) {
			oos.writeObject(object[i]);
		}
		oos.flush();
		oos.close();
	}
	public static void save(File file, Object3D[] object) throws IOException {
		save(object, file);
	}
	public static void save(Object3D[] object, String file) throws IOException {
		save(object, new File(file));
	}
	public static void save(String file, Object3D[] object) throws IOException {
		save(object, file);
	}
	public static Object3D[] load(File file) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		boolean done = false;
		ArrayList<Object3D> temp = new ArrayList<Object3D>();
		while (!done) {
			try {
				temp.add((Object3D)ois.readObject());
			} catch (EOFException ex) {
				done = true;
			}
		}
		Object3D[] ret = new Object3D[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			ret[i] = temp.get(i);
		}
		return ret;
	}
	public static Object3D[] load(String file) throws IOException, ClassNotFoundException {
		return load(new File(file));
	}
}