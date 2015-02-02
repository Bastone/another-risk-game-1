package com.randomj.risk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.randomj.risk.Risk;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Risk";
		config.width = 1024;
		config.height = 550;
		new LwjglApplication(new Risk(), config);
	}
}
