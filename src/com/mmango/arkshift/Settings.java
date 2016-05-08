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
	public static int[] highscores = new int[] { 0, 0, 0, 0, 0 };
	public static boolean[] levelEnabled = new boolean[] {true, false, false, false, false, false, false, false};


	
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
		int l;
		SharedPreferences settings = glGame.getSharedPreferences(
				GAME_PREFERENCES, 0);
		appName = settings.getString("AppName", "none :(");
		soundEnabled = settings.getBoolean("SoundEnabled", true);
		controlType = settings.getInt("ControlType", CONTROL_BY_TOUCH);
		Log.d("Settings:readPrefs", "AppName = " + appName);
		Log.d("Settings:readPrefs", "soundEnabled = " + soundEnabled);
		Log.d("Settings:readPrefs", "controlType = " + controlType);
		for (int i = 0; i < 5; i++) {
			highscores[i] = settings.getInt(Integer.toString(i), 0);
			Log.d("Settings:readPrefs", "Now highscores[" + i + "] = "	+  highscores[i]);
		}
		
		for (int i = 0; i < 8; i++) {
			l = i + 1;
			if (l == 1) {
				levelEnabled[i] = settings.getBoolean("Level_" + Integer.toString(l), true); //for Level_1 the default is Enabled
			} else {
				levelEnabled[i] = settings.getBoolean("Level_" + Integer.toString(l), false); //for Level_2 to _8 the default is Disabled
			}
			Log.d("Settings:readPrefs", "Now Level_" + l + " = " + levelEnabled[i]);
		}

	}

	public static void savePrefs(GLGame glGame) {
		int l;
		SharedPreferences settings = glGame.getSharedPreferences(
				GAME_PREFERENCES, 0);
		SharedPreferences.Editor prefEditor = settings.edit();
		Log.d("Settings:savePrefs", "appName = " + appName);
		Log.d("Settings:savePrefs", "soundEnabled = " + soundEnabled);
		Log.d("Settings:savePrefs", "controlType = " + controlType);

		prefEditor.putString("AppName", "ArkShift by mmango");
		prefEditor.putBoolean("SoundEnabled", soundEnabled);
		prefEditor.putInt("ControlType", controlType);
		for (int i = 0; i < 5; i++) {
			Log.d("Settings:savePrefs", "Preferences highscores[" + i + "] = "
					+ highscores[i]);
			prefEditor.putInt(Integer.toString(i), highscores[i]);
		}
		for (int i = 0; i < 8; i++) {
			l = i + 1;
			Log.d("Settings:savePrefs", "Preferences Level_" + l + " = " + levelEnabled[i]);
			prefEditor.putBoolean("Level_" + Integer.toString(l), levelEnabled[i]);
		}
		
		prefEditor.commit();
	}
	
	public static void resetHighscores(GLGame glGame) {
		int l;
		Log.d("Settings:resetHighscores", "Resetting highscores");
		SharedPreferences settings = glGame.getSharedPreferences(
				GAME_PREFERENCES, 0);
		SharedPreferences.Editor prefEditor = settings.edit();
		for (int i = 0; i < 5; i++) {
			prefEditor.putInt(Integer.toString(i), 0);
			highscores[i] = 0;
			Log.d("Settings:resetHighscores", "Preferences highscores[" + i + "] = " + highscores[i]);
		}
		for (int i = 0; i < 8; i++) {
			l = i + 1;
			levelEnabled[i] = (l == 1) ? true : false; //after resetting the first level is enabled, the rest - disabled;
			prefEditor.putBoolean("Level_" + Integer.toString(l), levelEnabled[i]);
		}
		prefEditor.commit();
	}

	
	public static void unlockAllLevels(GLGame glGame) {
		int l;
		SharedPreferences settings = glGame.getSharedPreferences(
				GAME_PREFERENCES, 0);
		SharedPreferences.Editor prefEditor = settings.edit();
		for (int i = 0; i < 8; i++) {
			l = i + 1;
			levelEnabled[i] = true; //unlock all levels
			prefEditor.putBoolean("Level_" + Integer.toString(l), levelEnabled[i]);
		}	
		prefEditor.commit();
	}
	
	public static void clearAllPreferences(GLGame glGame) {
		Log.d("Settings:clearAllPreferences", "Clear all preferences!");
		SharedPreferences settings = glGame.getSharedPreferences(GAME_PREFERENCES, 0);
		settings.edit().clear().commit();
	}
}
