package com.emeryferrari.jse3d.network;
import java.io.*;
enum DisconnectType implements Serializable {
	USERNAME_TAKEN, USERNAME_INVALID, KICKED, GENERAL_DISCONNECT;
}