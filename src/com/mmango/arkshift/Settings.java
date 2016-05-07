package com.mmango.arkshift;


import com.badlogic.androidgames.framework.impl.GLGame;

import android.content.SharedPreferences;
import android.util.Log;


public class Settings {
	public static final String GAME_PREFERENCES = "ArkShiftPrefs";
	public static final int CONTROL_BY_TOUCH = 0;
	public static final int CONTROL_BY_SWIPE = 1;
	public static final int CONTROL_BY_TILT = 2;
	public static String appName = "ArkShift by mmango";
	public static boolean soundEnabled = true;
	public static int controlType = CONTROL_BY_SWIPE;
	public static int[] highscores = new int[] { 118, 99, 7, 0, 0 };


	
	public static void addScore (int score) {
		for (int i = 0; i < 5; i++) {
			if (highscores[i] == score)
				break;
			if (highscores[i] < score) {
				for (int j = 4; j > i; j--)
					highscores[j] = highscores[j - 1];
				highscores[i] = score;
				break;
			}
		}
	}

	public static void readPrefs(GLGame glGame) {
		SharedPreferences settings = glGame.getSharedPreferences(
				GAME_PREFERENCES, 0);
		appName = settings.getString("AppName", "none :(");
		soundEnabled = settings.getBoolean("SoundEnabled", true);
		controlType = settings.getInt("ControlType", CONTROL_BY_TOUCH);
		Log.e("Settings:readPrefs", "AppName = " + appName);
		Log.e("Settings:readPrefs", "soundEnabled = " + soundEnabled);
		Log.e("Settings:readPrefs", "controlType = " + controlType);
		for (int i = 0; i < 5; i++) {
			highscores[i] = settings.getInt(Integer.toString(i), 0);
			Log.e("Settings:readPrefs", "Now highscores[" + i + "] = "	+  highscores[i]);
		}

	}

	public static void savePrefs(GLGame glGame) {
		SharedPreferences settings = glGame.getSharedPreferences(
				GAME_PREFERENCES, 0);
		SharedPreferences.Editor prefEditor = settings.edit();
		Log.e("Settings:savePrefs", "appName = " + appName);
		Log.e("Settings:savePrefs", "soundEnabled = " + soundEnabled);
		Log.e("Settings:savePrefs", "controlType = " + controlType);

		prefEditor.putString("AppName", "ArkShift by mmango");
		prefEditor.putBoolean("SoundEnabled", soundEnabled);
		prefEditor.putInt("ControlType", controlType);
		for (int i = 0; i < 5; i++) {
			Log.e("Settings:save", "Preferences highscores[" + i + "] = "
					+ highscores[i]);
			prefEditor.putInt(Integer.toString(i), highscores[i]);
		}
		prefEditor.commit();
	}
	
	public static void resetHighscores(GLGame glGame) {
		Log.e("Settings:resetHighscores", "Resetting highscores");
		SharedPreferences settings = glGame.getSharedPreferences(
				GAME_PREFERENCES, 0);
		SharedPreferences.Editor prefEditor = settings.edit();
		for (int i = 0; i < 5; i++) {
			prefEditor.putInt(Integer.toString(i), 0);
			highscores[i] = 0;
			Log.e("Settings:resetHighscores", "Preferences highscores[" + i + "] = " + highscores[i]);

		}
		prefEditor.commit();
	}

}
