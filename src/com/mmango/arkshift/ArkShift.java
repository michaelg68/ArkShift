package com.mmango.arkshift;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//import android.content.SharedPreferences;
import android.util.Log;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;

public class ArkShift extends GLGame {
//	public static final String GAME_PREFERENCES = "ArkShiftPrefs";

	boolean firstTimeCreate = true;
	

	@Override
	public Screen getStartScreen() {
		return new MainMenuScreen(this);
	}


	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if(firstTimeCreate) {
			Log.d("ArkShift:onSurfaceCreated", "Running Settings.readPrefs(glGame)");
			Settings.readPrefs(this);
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
		}
	}


	@Override
	public void onPause() {
		super.onPause();
		Log.d("ArkShift:onPause", "Running Settings.savePrefs(glGame)");
		Settings.savePrefs(this);
		//Settings.save(getFileIO());
//		if (Settings.soundEnabled)
//			Assets.music.pause();
	}

}
