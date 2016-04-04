package com.mmango.arkshift;

import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
	public static Texture mainScreenBackground;
	public static TextureRegion mainScreenBackgroundRegion;
	public static Texture mainScreenMenu;
	public static TextureRegion mainScreenMenuRegion;
	public static Texture gameScreenBackground;
	public static TextureRegion gameScreenBackgroundRegion;
	public static Texture readyBanner;
	public static TextureRegion readyBannerRegion;
	public static Texture resumeQuitMenu;
	public static TextureRegion resumeQuitMenuRegion;
	public static Texture gameOverBanner;
	public static TextureRegion gameOverBannerRegion;	
	public static Texture gameScreenElements;
	public static TextureRegion racquet;
	public static TextureRegion ballWhite;
	public static TextureRegion ballYellow;
	public static TextureRegion ballRed;
	public static TextureRegion brickGold;
	public static TextureRegion brickGreen;
	public static TextureRegion brickBlue;
	public static TextureRegion brickOrange;
	public static TextureRegion brickGrey;
	public static TextureRegion brickRed;
	public static TextureRegion brickPink;
	public static TextureRegion brickWheat;
	public static TextureRegion brickViolet;
	public static TextureRegion brickPurple;
	public static TextureRegion buttonPause;

	public static Texture fontTexture;
	public static Font font;

	public static Music music;
	public static Sound clickSound;
	public static Sound knockSound;
	public static Sound shiftSound;
	public static Sound gameOverSound;
	public static Sound gameOverLongSound;


	public static void load(GLGame game) {
		mainScreenBackground = new Texture(game,
				"background_mainscreen2_1080x1920.png");
		mainScreenBackgroundRegion = new TextureRegion(mainScreenBackground, 0,
				0, 1080, 1920);
		mainScreenMenu = new Texture(game, "main_menu.png");
		mainScreenMenuRegion = new TextureRegion(mainScreenMenu, 0, 0, 700,
				1000);

		gameScreenBackground = new Texture(game, "atlas_background.png");
		gameScreenBackgroundRegion = new TextureRegion(gameScreenBackground, 0,
				0, 1080, 1920);
		
		readyBanner = new Texture(game, "ready_banner.png");
		readyBannerRegion = new TextureRegion(readyBanner, 0,
				0, 700, 250);
		
		resumeQuitMenu = new Texture(game, "resume_quit_menu.png");
		resumeQuitMenuRegion = new TextureRegion(resumeQuitMenu, 0,	0, 700, 500);

		gameOverBanner = new Texture(game, "game_over_banner.png");
		gameOverBannerRegion = new TextureRegion(gameOverBanner, 0,	0, 700, 500);
		
		gameScreenElements = new Texture(game, "atlas_gamescreen_elements.png");
		racquet = new TextureRegion(gameScreenElements, 256, 128, 512, 64);
		ballWhite = new TextureRegion(gameScreenElements, 256, 192, 64, 64);
		ballYellow = new TextureRegion(gameScreenElements, 320, 192, 64, 64);
		ballRed = new TextureRegion(gameScreenElements, 384, 192, 64, 64);
		brickGold = new TextureRegion(gameScreenElements, 0, 0, 128, 128);
		brickGreen = new TextureRegion(gameScreenElements, 128, 0, 128, 128);
		brickBlue = new TextureRegion(gameScreenElements, 256, 0, 128, 128);
		brickOrange = new TextureRegion(gameScreenElements, 384, 0, 128, 128);
		brickGrey = new TextureRegion(gameScreenElements, 512, 0, 128, 128);
		brickRed = new TextureRegion(gameScreenElements, 640, 0, 128, 128);
		brickPink = new TextureRegion(gameScreenElements, 768, 0, 128, 128);
		brickWheat = new TextureRegion(gameScreenElements, 896, 0, 128, 128);
		brickViolet = new TextureRegion(gameScreenElements, 0, 128, 128, 128);
		brickPurple = new TextureRegion(gameScreenElements, 128, 128, 128, 128);
		buttonPause = new TextureRegion(gameScreenElements, 768, 128, 128, 128);

		fontTexture = new Texture(game, "goodbyeDespair.bmp");
		font = new Font(fontTexture, 0, 0, 16, 32, 32);

		music = game.getAudio().newMusic("jewelbeat_-_electrify.ogg");
		/* http://www.jewelbeat.com/free */
		/*
		 * Use these free royalty free music tracks & free sound effects for any
		 * production - advertising, education, video, photos, YouTube...etc.
		 * 
		 * These royalty free instrumental music tracks can be looped seamlessly
		 * & repeated to create a longer background music track for your
		 * projects.
		 * 
		 * You only need to provide an active credit link to our website from
		 * yours stating: Royalty free production music by JewelBeat
		 * 
		 * or use the following html code: <a
		 * href="http://www.jewelbeat.com/">Royalty free production music by
		 * JewelBeat</a>
		 */
		music.setLooping(true);
		music.setVolume(0.5f);
		if (Settings.soundEnabled)
			music.play();
		clickSound = game.getAudio().newSound("click.ogg");
		knockSound = game.getAudio().newSound("knock_8b_22050Hz.ogg");
		shiftSound = game.getAudio().newSound("shift_8b_22050.ogg");
		gameOverSound = game.getAudio().newSound("game_over.ogg");
		gameOverLongSound = game.getAudio().newSound(
				"game_over_long_8b_22050Hz.ogg");
	}

	public static void reload() {
		mainScreenBackground.reload();
		mainScreenMenu.reload();
		gameScreenBackground.reload();
		gameScreenElements.reload();
		fontTexture.reload();

	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}

}
