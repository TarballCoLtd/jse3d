package com.emeryferrari.jse3d.obj;
import java.io.*;
/** Saves various jse3d classes to files.
 * @author Alyx Ferrari
 * @since 1.3 beta 2
 */
public class JSE3DSerializer {
	private JSE3DSerializer() {}
	/** Saves an Object3D to a file.
	 * @param object The Object3D to save.
	 * @param file The location at which the Object3D should be saved.
	 * @throws IOException If an error occurred while writing the file.
	 */
	public static void saveObject3D(Object3D object, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(object);
		oos.flush();
		oos.close();
	}
	/** Saves an Object3D to a file.
	 * @param file The location at which the Object3D should be saved.
	 * @param object The Object3D to save.
	 * @throws IOException If an error occurred while writing the file.
	 */
	public static void saveObject3D(File file, Object3D object) throws IOException {
		saveObject3D(object, file);
	}
	/** Saves an Object3D to a file.
	 * @param object The Object3D to save.
	 * @param file The location at which the Object3D should be saved.
	 * @throws IOException If an error occurred while writing the file.
	 */
	public static void saveObject3D(Object3D object, String file) throws IOException {
		saveObject3D(object, new File(file));
	}
	/** Saves an Object3D to a file.
	 * @param file The location at which the Object3D should be saved.
	 * @param object The Object3D to save.
	 * @throws IOException If an error occurred while writing the file.
	 */
	public static void saveObject3D(String file, Object3D object) throws IOException {
		saveObject3D(object, file);
	}
	/** Loads an Object3D from a file.
	 * @param file The location from which to load the Object3D.
	 * @return The Object3D contained in the file.
	 * @throws IOException If an error occurred while reading the file.
	 * @throws ClassNotFoundException If com.emeryferrari.jse3d.obj.Object3D cannot be found.
	 */
	public static Object3D loadObject3D(File file) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object3D ret = (Object3D) ois.readObject();
		ois.close();
		return ret;
	}
	/** Loads an Object3D from a file.
	 * @param file The location from which to load the Object3D.
	 * @return The Object3D contained in the file.
	 * @throws IOException If an error occurred while reading the file.
	 * @throws ClassNotFoundException If com.emeryferrari.jse3d.obj.Object3D cannot be found.
	 */
	public static Object3D loadObject3D(String file) throws IOException, ClassNotFoundException {
		return loadObject3D(new File(file));
	}
	/** Saves a Scene to a file.
	 * @param scene The Scene to save.
	 * @param file The location at which the Scene should be saved.
	 * @throws IOException If an error occured while writing the file.
	 */
	public static void saveScene(Scene scene, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(scene);
		oos.flush();
		oos.close();
	}
	/** Saves a Scene to a file.
	 * @param file The location at which the Scene should be saved.
	 * @param scene The Scene to save.
	 * @throws IOException If an error occurred while writing the file.
	 */
	public static void saveScene(File file, Scene scene) throws IOException {
		saveScene(scene, file);
	}
	/** Saves a Scene to a file.
	 * @param scene The Scene to save.
	 * @param file The location at which the Scene should be saved.
	 * @throws IOException If an error occurred while writing the file.
	 */
	public static void saveScene(Scene scene, String file) throws IOException {
		saveScene(scene, new File(file));
	}
	/** Saves a Scene to a file.
	 * @param file The location at which the Scene should be saved.
	 * @param scene The Scene to save.
	 * @throws IOException If an error occurred while writing the file.
	 */
	public static void saveScene(String file, Scene scene) throws IOException {
		saveScene(scene, file);
	}
	/** Loads a Scene from a file.
	 * @param file The location from which to load the Scene.
	 * @return The Scene contained in the file.
	 * @throws IOException If an error occurred while reading the file.
	 * @throws ClassNotFoundException If com.emeryferrari.jse3d.obj.Object3D cannot be found.
	 */
	public static Scene loadScene(File file) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Scene ret = (Scene) ois.readObject();
		ois.close();
		return ret;
	}
	/** Loads a Scene from a file.
	 * @param file The location from which to load the Scene.
	 * @return The Scene contained in the file.
	 * @throws IOException If an error occurred while reading the file.
	 * @throws ClassNotFoundException If com.emeryferrari.jse3d.obj.Object3D cannot be found.
	 */
	public static Scene loadScene(String file) throws IOException, ClassNotFoundException {
		return loadScene(new File(file));
	}
}