package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import com.mygdx.game.LastStand;

import static com.mygdx.game.LastStand.screenH;
import static com.mygdx.game.LastStand.screenW;

class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(screenW, screenH);
		config.setHdpiMode(Lwjgl3ApplicationConfiguration.HdpiMode.Logical);
		config.setResizable(false);
		config.setIdleFPS(30);
		new Lwjgl3Application(new LastStand(), config);
	}
}
