package com.mmango.arkshift;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.util.Log;

import com.badlogic.androidgames.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static boolean touchEnabled = true;
	//public static int[] highscores = new int[] { 100, 80, 50, 30, 10 };
	public static int[] highscores = new int[] { 9, 8, 5, 3, 1 };
	public final static String file = ".arkshift2";

	public static void load(FileIO files) {
		BufferedReader in = null;
		Log.e("Settings:load", "before reading from file. soundEnabled = " + soundEnabled);

		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			touchEnabled = Boolean.parseBoolean(in.readLine());
			Log.e("Settings:load", "Reading the file Boolean.parseBoolean: soundEnabled = " + soundEnabled);
			Log.e("Settings:load", "Reading the file Boolean.parseBoolean: touchEnabled = " + touchEnabled);
			Log.e("Settings:load", "Reading HighScores from the file:" + file);

			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(in.readLine());
				Log.e("Settings:save", "i = " + i);
				Log.e("Settings:save", "highscores[i] = " + highscores[i]);
			}
		} catch (IOException e) {
			// it's OK, we have the defaults
			Log.e("Settings:save", "IOException");
		} catch (NumberFormatException e) {
			// still do nothing, we have defaults
			Log.e("Settings:save", "NumberFormatException");
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				Log.e("Settings:save", "IOException. can't close file");


			}
		}
		//temporary
		soundEnabled = false;
	}

	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					files.writeFile(file)));
			out.write(Boolean.toString(soundEnabled));
			out.write(Boolean.toString(touchEnabled));
			out.write("\n");
			// should also work: out.write(String.valueOf(soundEnabled));
			Log.e("Settings:save", "Writing HighScores to the file:");
			for (int i = 0; i < 5; i++) {
				out.write(Integer.toString(highscores[i]));
				out.write("\n");
				Log.e("Settings:save", "highscores[i] = " + highscores[i]);
			}
		} catch (IOException e) {
			// could not save the settings file
			Log.e("Settings: ", "Error! Could not write into file /sdcard/.arkshift");
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}

	}

	public static void addScore(int score) {
		for (int i = 0; i < 5; i++) {
			if (highscores[i] < score) {
				for (int j = 4; j > i; j--)
					highscores[j] = highscores[j - 1];
				highscores[i] = score;
				break;
			}
		}
	}

}
