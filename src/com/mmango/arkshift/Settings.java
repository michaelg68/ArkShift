package com.mmango.arkshift;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.androidgames.framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static int[] highscores = new int[] { 100, 80, 50, 30, 10 };
	// public static int[] highscores = new int[] { 9, 8, 5, 3, 1 };
	public final static String file = ".arkshift";

	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(in.readLine());
			}
		} catch (IOException e) {
			// it's OK, we have the defaults
		} catch (NumberFormatException e) {
			// still do nothing, we have defaults
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {

			}
		}
	}

	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					files.writeFile(file)));
			out.write(Boolean.toString(soundEnabled));
			out.write("\n");
			// should also work: out.write(String.valueOf(soundEnabled));
			for (int i = 0; i < 5; i++) {
				out.write(Integer.toString(highscores[i]));
				out.write("\n");
			}
		} catch (IOException e) {
			// could not save the settings file
			// Log.e("Settings: ",
			// "Error! Could not write into file /sdcard/.arkshift");
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
