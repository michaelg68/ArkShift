package com.mmango.arkshift;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

//import android.util.Log;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;

public class HelpActivity extends GLGame {

	boolean firstTimeCreate = true;

	@Override
	public Screen getStartScreen() {
		return new HelpScreen(this);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
			//Log.d("HelpActivity:onSurfaceCreated", ".....");
			AssetsHelp.load(this);
			firstTimeCreate = false;
		} else {
			AssetsHelp.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		//Log.d("HelpActivity:onPause", ".......");
		//Settings.savePrefs(this);
		// if (Settings.soundEnabled)
		// Assets.music.pause();
	}
}
