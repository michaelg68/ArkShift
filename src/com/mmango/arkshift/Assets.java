package com.mmango.arkshift;

//import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
	//public static Texture atlasGameScreenBackground;
	//public static TextureRegion gameScreenBackgroundRegion;
	
	public static Texture atlasUIElements;
	public static TextureRegion mainMenuLogo;
	public static TextureRegion backgroundUIRegion;
	public static TextureRegion buttonLevel1;
	public static TextureRegion buttonLevel2;
	public static TextureRegion buttonLevel3;
	public static TextureRegion buttonLevel4;
	public static TextureRegion buttonLevel5;
	public static TextureRegion buttonLevel6;
	public static TextureRegion buttonLevel7;
	public static TextureRegion buttonLevel8;
	public static TextureRegion buttonUnavailable;
	public static TextureRegion mainMenuButtonControlSwipe;
	public static TextureRegion mainMenuButtonControlTilt;
	public static TextureRegion mainMenuButtonControlTouch;
	public static TextureRegion mainMenuButtonHelp;
	public static TextureRegion mainMenuButtonHome;
	public static TextureRegion mainMenuButtonNo;
	public static TextureRegion mainMenuButtonPlay;
	public static TextureRegion mainMenuButtonReset;
	public static TextureRegion mainMenuButtonScore;
	public static TextureRegion mainMenuButtonSoundDisabled;
	public static TextureRegion mainMenuButtonSoundEnabled;
	public static TextureRegion mainMenuButtonYes;
	public static TextureRegion mainMenuTextControl;
	public static TextureRegion mainMenuTextHelp;
	public static TextureRegion mainMenuTextHighscores;
	public static TextureRegion mainMenuTextNo;
	public static TextureRegion mainMenuTextPlay;
	public static TextureRegion mainMenuTextQuit;
	public static TextureRegion mainMenuTextReset;
	public static TextureRegion mainMenuTextResume;
	public static TextureRegion mainMenuTextRetry;
	public static TextureRegion mainMenuTextSound;
	public static TextureRegion mainMenuTextYes;
	public static TextureRegion resetHighscoresMessage;
	public static TextureRegion selectLevelMessage;
	
	public static ScoreFont scoreFont;

	//public static Music music;
	public static Sound clickSound;
	public static Sound racquetHitSound;
	public static Sound frameHitSound;
	public static Sound brickHitSound;
//	public static Sound levelStartsSound;
	public static Sound ballRollingAndKnockSound;
//	public static Sound shiftSound;
//	public static Sound gameOverSound;
//	public static Sound gameOverLongSound;
//	public static Sound levelPassedSound;


	public static void load(GLGame game) {
		//atlasBackgroundUI = new Texture(game, "background_ui.jpg");
		
		//atlasGameScreenBackground = new Texture(game, "background_with_transp_gamefield_1.png");
		
		
		atlasUIElements = new Texture(game, "atlas_ui.png");

		mainMenuLogo = new TextureRegion(atlasUIElements, 0, 0, 625, 194);
		backgroundUIRegion = new TextureRegion(atlasUIElements, 625, 0, 360, 640);
		buttonLevel1 = new TextureRegion(atlasUIElements, 985, 128, 128, 128);
		buttonLevel2 = new TextureRegion(atlasUIElements, 1113, 128, 128, 128);
		buttonLevel3 = new TextureRegion(atlasUIElements, 985, 256, 128, 128);
		buttonLevel4 = new TextureRegion(atlasUIElements, 1241, 0, 128, 128);
		buttonLevel5 = new TextureRegion(atlasUIElements, 1241, 128, 128, 128);
		buttonLevel6 = new TextureRegion(atlasUIElements, 985, 384, 128, 128);
		buttonLevel7 = new TextureRegion(atlasUIElements, 1369, 0, 128, 128);
		buttonLevel8 = new TextureRegion(atlasUIElements, 1113, 256, 128, 128);
		buttonUnavailable = new TextureRegion(atlasUIElements, 512, 194, 64, 64);
		mainMenuButtonControlSwipe = new TextureRegion(atlasUIElements, 1113, 384, 128, 128);
		mainMenuButtonControlTilt = new TextureRegion(atlasUIElements, 985, 512, 128, 128);
		mainMenuButtonControlTouch = new TextureRegion(atlasUIElements, 1497, 0, 128, 128);
		mainMenuButtonHelp = new TextureRegion(atlasUIElements, 1369, 128, 128, 128);
		mainMenuButtonHome = new TextureRegion(atlasUIElements, 1241, 256, 128, 128);
		mainMenuButtonNo = new TextureRegion(atlasUIElements, 1625, 0, 128, 128);
		mainMenuButtonPlay = new TextureRegion(atlasUIElements, 1497, 128, 128, 128);
		mainMenuButtonReset = new TextureRegion(atlasUIElements, 1369, 256, 128, 128);
		mainMenuButtonScore = new TextureRegion(atlasUIElements, 1241, 384, 128, 128);
		mainMenuButtonSoundDisabled = new TextureRegion(atlasUIElements, 1113, 512, 128, 128);
		mainMenuButtonSoundEnabled = new TextureRegion(atlasUIElements, 1024, 640, 128, 128);
		mainMenuButtonYes = new TextureRegion(atlasUIElements, 1241, 512, 128, 128);
		mainMenuTextControl = new TextureRegion(atlasUIElements, 0, 508, 256, 128);
		mainMenuTextHelp = new TextureRegion(atlasUIElements, 0, 636, 256, 128);
		mainMenuTextHighscores = new TextureRegion(atlasUIElements, 0, 764, 256, 128);
		mainMenuTextNo = new TextureRegion(atlasUIElements, 256, 508, 256, 128);
		mainMenuTextPlay = new TextureRegion(atlasUIElements, 256, 636, 256, 128);
		mainMenuTextQuit = new TextureRegion(atlasUIElements, 256, 764, 256, 128);
		mainMenuTextReset = new TextureRegion(atlasUIElements, 512, 640, 256, 128);
		mainMenuTextResume = new TextureRegion(atlasUIElements, 512, 768, 256, 128);
		mainMenuTextRetry = new TextureRegion(atlasUIElements, 768, 640, 256, 128);
		mainMenuTextSound = new TextureRegion(atlasUIElements, 768, 768, 256, 128);
		mainMenuTextYes = new TextureRegion(atlasUIElements, 985, 0, 256, 128);
		resetHighscoresMessage = new TextureRegion(atlasUIElements, 0, 194, 512, 256);
		selectLevelMessage = new TextureRegion(atlasUIElements, 0, 450, 328, 58);

		/*
		score0 = new TextureRegion(atlasUIElements, 512, 258, 64, 64);
		score1 = new TextureRegion(atlasUIElements, 512, 322, 64, 64);
		score2 = new TextureRegion(atlasUIElements, 0, 892, 64, 64);
		score3 = new TextureRegion(atlasUIElements, 512, 386, 64, 64);
		score4 = new TextureRegion(atlasUIElements, 64, 892, 64, 64);
		score5 = new TextureRegion(atlasUIElements, 512, 450, 64, 64);
		score6 = new TextureRegion(atlasUIElements, 128, 892, 64, 64);
		score7 = new TextureRegion(atlasUIElements, 512, 514, 64, 64);
		score8 = new TextureRegion(atlasUIElements, 192, 892, 64, 64);
		score9 = new TextureRegion(atlasUIElements, 256, 892, 64, 64);
		scoreDot = new TextureRegion(atlasUIElements, 320, 892, 64, 64);
		scoreMinus = new TextureRegion(atlasUIElements, 384, 892, 64, 64);
		scoreNone = new TextureRegion(atlasUIElements, 448, 892, 64, 64);
		*/

		scoreFont = new ScoreFont(atlasUIElements, 128, 128);
		


		clickSound = game.getAudio().newSound("click.ogg");
//		racquetHitSound = game.getAudio().newSound("Hit_Hurt26.wav");
//		frameHitSound = game.getAudio().newSound("Hit_Hurt26.wav");
//		brickHitSound = game.getAudio().newSound("Hit_Hurt26.wav");
		//levelStartsSound = game.getAudio().newSound("level_starts_sound.ogg");
		ballRollingAndKnockSound = game.getAudio().newSound("ball_rolling_and_knock_sound.ogg");
	
		//shiftSound = game.getAudio().newSound("shift_8b_22050.ogg");
		//gameOverSound = game.getAudio().newSound("game_over.ogg");
		//gameOverLongSound = game.getAudio().newSound("game_over_long_8b_22050Hz.ogg");
		//levelPassedSound = game.getAudio().newSound("level_passed.ogg");
	}

	public static void reload() {
		//!!!!!    Note: very important - if you add a new texture, don't forget to add it in this method. otherwise you will face 
		// bugs when GL ES loses context!
		//atlasBackgroundUI.reload();
		//atlasGameScreenBackground.reload();
		atlasUIElements.reload();
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}

}
