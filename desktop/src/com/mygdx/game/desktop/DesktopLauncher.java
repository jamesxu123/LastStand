package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.LastStand;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = screenW;
		config.height = screenH;
		config.resizable = false;
		new LwjglApplication(new LastStand(), config);
	}
}
