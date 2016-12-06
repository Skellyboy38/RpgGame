package com.mygdx.game.desktop;

//import java.awt.Dimension;
//import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.RpgGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RPG";	//The title that appears when the game opens
		int width = 1000;
		int height = 600;
		config.width = (int)width;		//Sets the width and height of the game 
		config.height = (int)height;	//This is the height of the screen
		new LwjglApplication(new RpgGame(), config);
	}
}
