# jse3d

by Tarball and Samuel Krug

## Dependencies

If you would like a version with no dependencies, try jse3d v1.7.2, however that is an outdated version and is not guaranteed to be bug free or as feature-rich as newer versions.<br/>
-`Aparapi 2.0.0`<br/>
-`Aparapi JNI 1.4.2`<br/>
-`Apache Commons BCEL 6.4.1` (version 6.5.0 also works fine)<br/>
-`Java Data Front v2.0.0`<br/>

## Documentation

Javadocs are being worked on. They are being introduced slowly, with a commit after the completion of a class.<br/>
jse3d's Javadoc website [can be found here](https://alyxferrari.github.io/jse3d/javadoc/index.html/).<br/>
Check `src/com/alyxferrari/jse3d/example` for examples on how to use jse3d.

## Compilation/building

A JAR for this project can be built by running `mvn package` in the root directory of this project. The JARs in the Releases tab were also built by Maven.

## Including this project in yours

JitPack is a great way to incorporate GitHub Maven projects which are not on the Maven Central Repository.<br/>
You can include jse3d in your project by putting the following in your pomfile:<br/>
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
<dependencies>
	<dependency>
		<groupId>com.github.alyxferrari</groupId>
		<artifactId>jse3d</artifactId>
		<version>v3.0</version>
	</dependency>
</dependencies>
```
Please substitute the version number with the latest version of jse3d.
