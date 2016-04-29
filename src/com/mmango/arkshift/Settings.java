package com.mmango.arkshift;


import com.badlogic.androidgames.framework.impl.GLGame;

import android.content.SharedPreferences;
import android.util.Log;


public class Settings {
	public static final String GAME_PREFERENCES = "ArkShiftPrefs";

	public static String appName = "ArkShift by mmango";
	public static boolean soundEnabled = true;
	public static boolean touchEnabled = false;
	public static int[] highscores = new int[] { 0, 0, 0, 0, 0 };


	
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

	public static void readPrefs(GLGame game) {
		SharedPreferences settings = game.getSharedPreferences(
				GAME_PREFERENCES, 0);
		appName = settings.getString("AppName", "none :(");
		soundEnabled = settings.getBoolean("SoundEnabled", true);
		//touchEnabled = settings.getBoolean("TouchEnabled", true);
		Log.e("Settings:readPrefs",
				"from Prefs: AppName = "
						+ settings.getString("AppName", "none :("));
		Log.e("Settings:readPrefs",
				"from Prefs: soundEnabled = "
						+ settings.getBoolean("SoundEnabled", true));
		Log.e("Settings:readPrefs",
				"from Prefs: touchEnabled = "
						+ settings.getBoolean("TouchEnabled", true));
		for (int i = 0; i < 5; i++) {
			highscores[i] = highscores[i];
			Log.e("Settings:readPrefs", "Preferences i = " + i);
			Log.e("Settings:readPrefs", "from Prefs: highscores[i] = "
					+ settings.getInt(Integer.toString(i), highscores[i]));
		}

	}

	public static void savePrefs(GLGame game) {
		SharedPreferences settings = game.getSharedPreferences(
				GAME_PREFERENCES, 0);
		SharedPreferences.Editor prefEditor = settings.edit();
		Log.e("Settings:savePrefs", "appName = " + appName);
		Log.e("Settings:savePrefs", "soundEnabled = " + soundEnabled);
		Log.e("Settings:savePrefs", "touchEnabled = " + touchEnabled);

		prefEditor.putString("AppName", "ArkShift by mmango");
		prefEditor.putBoolean("SoundEnabled", soundEnabled);
		prefEditor.putBoolean("TouchEnabled", touchEnabled);
		for (int i = 0; i < 5; i++) {
			Log.e("Settings:save", "Preferences i = " + i);
			Log.e("Settings:save", "Preferences highscores[i] = "
					+ highscores[i]);
			prefEditor.putInt(Integer.toString(i), highscores[i]);
		}
		prefEditor.commit();
	}

}
