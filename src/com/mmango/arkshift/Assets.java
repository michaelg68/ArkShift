package com.mmango.arkshift;

//import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
	public static Texture mainScreenBackground;
	public static TextureRegion mainScreenBackgroundRegion;

	public static Texture UIGameElements;
	public static TextureRegion alphaOverGameField_60Opacity;
	public static TextureRegion mainMenuLogo;
	public static TextureRegion ballRed;
	public static TextureRegion ballWhite;
	public static TextureRegion ballYellow;
	public static TextureRegion ballsLeft0;
	public static TextureRegion ballsLeft1;
	public static TextureRegion ballsLeft2;
	public static TextureRegion ballsLeft3;
	public static TextureRegion ballsLeft4;
	public static TextureRegion ballsLeft5;
	public static TextureRegion ballsLeft6;
	public static TextureRegion ballsLeft7;
	public static TextureRegion ballsLeft8;
	public static TextureRegion ballsLeft9;
	public static TextureRegion ballsSymbol;
	public static TextureRegion brickBlue;
	public static TextureRegion brickBluesky;
	public static TextureRegion brickGold;
	public static TextureRegion brickGreen;
	public static TextureRegion brickGrey;
	public static TextureRegion brickLightviolet;
	public static TextureRegion brickOrange;
	public static TextureRegion brickPink;
	public static TextureRegion brickPurple;
	public static TextureRegion brickRed;
	public static TextureRegion brickViolet;
	public static TextureRegion brickWheat;
	public static TextureRegion buttonLevelEmpty;
	public static TextureRegion buttonLevel1;
	public static TextureRegion buttonLevel2;
	public static TextureRegion buttonLevel3;
	public static TextureRegion buttonLevel4;
	public static TextureRegion buttonLevel5;
	public static TextureRegion buttonLevel6;
	public static TextureRegion buttonLevel7;
	public static TextureRegion buttonLevel8;
	public static TextureRegion buttonUnavailable;
	public static TextureRegion gameOverMessage;
	public static TextureRegion levelPassedMessage;
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
	public static TextureRegion mainMenuTextGoon;
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
	public static TextureRegion pauseButton;
	public static TextureRegion pauseMessage;
	public static TextureRegion racquet;
	public static TextureRegion readyMessage;
	public static TextureRegion resetHighscoresMessage;
	public static TextureRegion selectLevelMessage;

	/*
	public static TextureRegion score0;
	public static TextureRegion score1;
	public static TextureRegion score2;
	public static TextureRegion score3;
	public static TextureRegion score4;
	public static TextureRegion score5;
	public static TextureRegion score6;
	public static TextureRegion score7;
	public static TextureRegion score8;
	public static TextureRegion score9;
	public static TextureRegion scoreDot;
	public static TextureRegion scoreMinus;
	public static TextureRegion scoreNone;
	*/

	public static Font fontBebasneue64x64White;
	public static ScoreFont scoreFont;
 
	public static Texture gameScreenBackground;
	public static TextureRegion gameScreenBackgroundRegion;


	//public static Music music;
	public static Sound clickSound;
	public static Sound racquetHitSound;
	public static Sound frameHitSound;
	public static Sound brickHitSound;
//	public static Sound levelStartsSound;
//	public static Sound ballRollingAndKnockSound;
//	public static Sound shiftSound;
//	public static Sound gameOverSound;
//	public static Sound gameOverLongSound;
//	public static Sound levelPassedSound;


	public static void load(GLGame game) {
		//mainScreenBackground = new Texture(game, "background_marble_light_flattened.jpg");
		//mainScreenBackground = new Texture(game, "background_mainscreen.png");
		mainScreenBackground = new Texture(game, "background_ui.png");
		mainScreenBackgroundRegion = new TextureRegion(mainScreenBackground, 0,	0, 1080, 1920);
		
		UIGameElements = new Texture(game, "atlas.png");
		alphaOverGameField_60Opacity = new TextureRegion(UIGameElements, 953, 0, 67, 111);
		mainMenuLogo = new TextureRegion(UIGameElements, 0, 0, 625, 194);
		ballRed = new TextureRegion(UIGameElements, 894, 58, 32, 32);
		ballWhite = new TextureRegion(UIGameElements, 1345, 128, 32, 32);
		ballYellow = new TextureRegion(UIGameElements, 1152, 786, 32, 32);
		ballsLeft0 = new TextureRegion(UIGameElements, 625, 116, 64, 64);
		ballsLeft1 = new TextureRegion(UIGameElements, 689, 116, 64, 64);
		ballsLeft2 = new TextureRegion(UIGameElements, 768, 916, 64, 64);
		ballsLeft3 = new TextureRegion(UIGameElements, 832, 916, 64, 64);
		ballsLeft4 = new TextureRegion(UIGameElements, 1536, 256, 64, 64);
		ballsLeft5 = new TextureRegion(UIGameElements, 1408, 384, 64, 64);
		ballsLeft6 = new TextureRegion(UIGameElements, 896, 916, 64, 64);
		ballsLeft7 = new TextureRegion(UIGameElements, 1280, 570, 64, 64);
		ballsLeft8 = new TextureRegion(UIGameElements, 1536, 320, 64, 64);
		ballsLeft9 = new TextureRegion(UIGameElements, 1472, 384, 64, 64);
		ballsSymbol = new TextureRegion(UIGameElements, 1408, 448, 64, 64);
		//fontBebasneue64x64White = new TextureRegion(UIGameElements, 0, 194, 512, 512);
		brickBlue = new TextureRegion(UIGameElements, 1216, 722, 32, 32);
		brickBluesky = new TextureRegion(UIGameElements, 1877, 64, 32, 32);
		brickGold = new TextureRegion(UIGameElements, 1941, 0, 32, 32);
		brickGreen = new TextureRegion(UIGameElements, 1248, 722, 32, 32);
		brickGrey = new TextureRegion(UIGameElements, 1216, 754, 32, 32);
		brickLightviolet = new TextureRegion(UIGameElements, 1152, 818, 32, 32);
		brickOrange = new TextureRegion(UIGameElements, 1184, 786, 32, 32);
		brickPink = new TextureRegion(UIGameElements, 1973, 0, 32, 32);
		brickPurple = new TextureRegion(UIGameElements, 1941, 32, 32, 32);
		brickRed = new TextureRegion(UIGameElements, 1877, 96, 32, 32);
		brickViolet = new TextureRegion(UIGameElements, 1909, 64, 32, 32);
		brickWheat = new TextureRegion(UIGameElements, 1344, 634, 32, 32);
		buttonLevelEmpty = new TextureRegion(UIGameElements, 1024, 210, 128, 128);
		buttonLevel1 = new TextureRegion(UIGameElements, 1217, 58, 128, 128);
		buttonLevel2 = new TextureRegion(UIGameElements, 1024, 338, 128, 128);
		buttonLevel3 = new TextureRegion(UIGameElements, 1152, 210, 128, 128);
		buttonLevel4 = new TextureRegion(UIGameElements, 1365, 0, 128, 128);
		buttonLevel5 = new TextureRegion(UIGameElements, 1024, 466, 128, 128);
		buttonLevel6 = new TextureRegion(UIGameElements, 1280, 186, 128, 128);
		buttonLevel7 = new TextureRegion(UIGameElements, 1152, 338, 128, 128);
		buttonLevel8 = new TextureRegion(UIGameElements, 1493, 0, 128, 128);
		buttonUnavailable = new TextureRegion(UIGameElements, 1600, 256, 64, 64);
		gameOverMessage = new TextureRegion(UIGameElements, 625, 58, 269, 58);
		levelPassedMessage = new TextureRegion(UIGameElements, 1024, 0, 193, 153);
		mainMenuButtonControlSwipe = new TextureRegion(UIGameElements, 1280, 314, 128, 128);
		mainMenuButtonControlTilt = new TextureRegion(UIGameElements, 1408, 128, 128, 128);
		mainMenuButtonControlTouch = new TextureRegion(UIGameElements, 1152, 466, 128, 128);
		mainMenuButtonHelp = new TextureRegion(UIGameElements, 1024, 594, 128, 128);
		mainMenuButtonHome = new TextureRegion(UIGameElements, 1621, 0, 128, 128);
		mainMenuButtonNo = new TextureRegion(UIGameElements, 1408, 256, 128, 128);
		mainMenuButtonPlay = new TextureRegion(UIGameElements, 1536, 128, 128, 128);
		mainMenuButtonReset = new TextureRegion(UIGameElements, 1280, 442, 128, 128);
		mainMenuButtonScore = new TextureRegion(UIGameElements, 1024, 722, 128, 128);
		mainMenuButtonSoundDisabled = new TextureRegion(UIGameElements, 1152, 594, 128, 128);
		mainMenuButtonSoundEnabled = new TextureRegion(UIGameElements, 1749, 0, 128, 128);
		mainMenuButtonYes = new TextureRegion(UIGameElements, 1664, 128, 128, 128);
		mainMenuTextControl = new TextureRegion(UIGameElements, 512, 194, 256, 128);
		mainMenuTextGoon = new TextureRegion(UIGameElements, 512, 322, 256, 128);
		mainMenuTextHelp = new TextureRegion(UIGameElements, 512, 450, 256, 128);
		mainMenuTextHighscores = new TextureRegion(UIGameElements, 768, 116, 256, 128);
		mainMenuTextNo = new TextureRegion(UIGameElements, 768, 244, 256, 128);
		mainMenuTextPlay = new TextureRegion(UIGameElements, 512, 578, 256, 128);
		mainMenuTextQuit = new TextureRegion(UIGameElements, 768, 372, 256, 128);
		mainMenuTextReset = new TextureRegion(UIGameElements, 512, 706, 256, 128);
		mainMenuTextResume = new TextureRegion(UIGameElements, 768, 500, 256, 128);
		mainMenuTextRetry = new TextureRegion(UIGameElements, 512, 834, 256, 128);
		mainMenuTextSound = new TextureRegion(UIGameElements, 768, 628, 256, 128);
		mainMenuTextYes = new TextureRegion(UIGameElements, 768, 756, 256, 128);
		pauseButton = new TextureRegion(UIGameElements, 1152, 722, 64, 64);
		pauseMessage = new TextureRegion(UIGameElements, 1217, 0, 148, 58);
		racquet = new TextureRegion(UIGameElements, 768, 884, 256, 32);
		readyMessage = new TextureRegion(UIGameElements, 1024, 153, 182, 57);
		resetHighscoresMessage = new TextureRegion(UIGameElements, 0, 706, 512, 256);
		selectLevelMessage = new TextureRegion(UIGameElements, 625, 0, 328, 58);

		
		fontBebasneue64x64White = new Font(UIGameElements, 0, 194, 8, 64, 64);
		scoreFont = new ScoreFont(UIGameElements, 128, 128);

		
		gameScreenBackground = new Texture(game, "background_with_transp_gamefield_1.png");
		gameScreenBackgroundRegion = new TextureRegion(gameScreenBackground, 0,
				0, 1080, 1920);
		

		clickSound = game.getAudio().newSound("click.ogg");
		racquetHitSound = game.getAudio().newSound("Hit_Hurt26.wav");
		frameHitSound = game.getAudio().newSound("Hit_Hurt26.wav");
		brickHitSound = game.getAudio().newSound("Hit_Hurt26.wav");
		//levelStartsSound = game.getAudio().newSound("level_starts_sound.ogg");
		//ballRollingAndKnockSound = game.getAudio().newSound("ball_rolling_and_knock_sound.ogg");
	
		//shiftSound = game.getAudio().newSound("shift_8b_22050.ogg");
		//gameOverSound = game.getAudio().newSound("game_over.ogg");
		//gameOverLongSound = game.getAudio().newSound("game_over_long_8b_22050Hz.ogg");
		//levelPassedSound = game.getAudio().newSound("level_passed.ogg");
	}

	public static void reload() {
		//!!!!!    Note: very important - if you add a new texture, don't forget to add it in this method. otherwise you will face 
		// bugs when GL ES loses context!
		mainScreenBackground.reload();
		gameScreenBackground.reload();
		UIGameElements.reload();
		//highscoresScreenBackground.reload();
		//gameFieldGreen.reload();
		//selectLevelScreenElements.reload();
		//userInterfaceElements.reload();

		//gameScreenElements.reload();
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
			
	}

}
