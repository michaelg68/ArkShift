package com.mmango.arkshift;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;

public class GameActivity extends GLGame {
	boolean firstTimeCreate = true;

	// public GameActivity(int level) {
	// super();
	// this.level = level;
	// }

	@Override
	public Screen getStartScreen() {
		Bundle b = getIntent().getExtras();
		int level = -1; // or other values
		if (b != null)
			level = b.getInt("runLevel");
		AssetsGame.load(this);
		return new GameScreen(this, level);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
			//Log.d("GameActivity:onSurfaceCreated", ".....");
			AssetsGame.load(this);
			firstTimeCreate = false;
		} else {
			AssetsGame.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("GameActivity:onPause", ".......");
		Settings.savePrefs(this);
		// if (Settings.soundEnabled)
		// AssetsGame.music.pause();
	}

	@Override
	public void onBackPressed() {
//		getCurrentScreen();
//		String className = getCurrentScreen().getClass().getName();
//		int len = className.split("\\.").length;
//		className = className.split("\\.")[len - 1];
//		if (className.equals("GameScreen")) {
			if (GameScreen.state == GameScreen.GAME_RUNNING) {
				GameScreen.state = GameScreen.GAME_PAUSED;
			} else if (GameScreen.state == GameScreen.GAME_READY) {
				super.onBackPressed();
			}

	//	}
	}
}
