package com.mmango.arkshift;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//import android.util.Log;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;

public class ArkShift extends GLGame {

	boolean firstTimeCreate = true;

	@Override
	public Screen getStartScreen() {
		return new MainMenuScreen(this);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
			//Log.d("ArkShift:onSurfaceCreated",
			//		"Running Settings.readPrefs(glGame)");
			// Settings.clearAllPreferences(this); //total clearing of all
			// preferences
			Settings.readPrefs(this);
			Assets.load(this);
			//AssetsGame.load(this);
			//Assets.ballRollingAndKnockSound.play(1);
			firstTimeCreate = false;
		} else {
			Assets.reload();
			//AssetsGame.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		//Log.d("ArkShift:onPause", "Running Settings.savePrefs(glGame)");
		Settings.savePrefs(this);
		// Settings.save(getFileIO());
		// if (Settings.soundEnabled)
		// Assets.music.pause();
	}

	@Override
	public void onBackPressed() {
		getCurrentScreen();
		String className = getCurrentScreen().getClass().getName();
		// className = currentScreen = com.mmango.arkshift.HighscoreScreen
		int len = className.split("\\.").length;
		//Log.d("ArkShift:onBackPressed", "len = " + len);
		className = className.split("\\.")[len - 1];
		//Log.d("ArkShift:onBackPressed", "currentScreen = " + className);
		if (!className.equals("GameScreen")) {
			setScreen(new MainMenuScreen(this));
		}
	}
}
