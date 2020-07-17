package com.emeryferrari.jse3d.network;
import java.io.*;
public enum DisconnectType implements Serializable {
	USERNAME_TAKEN, USERNAME_INVALID, KICKED, GENERAL_DISCONNECT;
}