package com.emeryferrari.jse3d;
import java.io.*;
public class Serializer {
	private Serializer() {}
	public static void saveObject3D(Object3D object, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(object);
		oos.flush();
		oos.close();
	}
	public static void saveObject3D(File file, Object3D object) throws IOException {
		saveObject3D(object, file);
	}
	public static void saveObject3D(Object3D object, String file) throws IOException {
		saveObject3D(object, new File(file));
	}
	public static void saveObject3D(String file, Object3D object) throws IOException {
		saveObject3D(object, file);
	}
	public static Object3D loadObject3D(File file) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object3D ret = (Object3D) ois.readObject();
		ois.close();
		return ret;
	}
	public static Object3D loadObject3D(String file) throws IOException, ClassNotFoundException {
		return loadObject3D(new File(file));
	}
	public static void saveScene(Scene scene, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(scene);
		oos.flush();
		oos.close();
	}
	public static void saveScene(File file, Scene scene) throws IOException {
		saveScene(scene, file);
	}
	public static void saveScene(Scene scene, String file) throws IOException {
		saveScene(scene, new File(file));
	}
	public static void saveScene(String file, Scene scene) throws IOException {
		saveScene(scene, file);
	}
	public static Scene loadScene(File file) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Scene ret = (Scene) ois.readObject();
		ois.close();
		return ret;
	}
	public static Scene loadScene(String file) throws IOException, ClassNotFoundException {
		return loadScene(new File(file));
	}
}