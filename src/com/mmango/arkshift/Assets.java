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
/*	
	public static Texture mainScreenUIElements;
	public static TextureRegion mainMenuLogo;
	public static TextureRegion mainMenuButtonPlay;
	public static TextureRegion mainMenuButtonControlSwipe;
	public static TextureRegion mainMenuButtonControlTouch;
	public static TextureRegion mainMenuButtonControlTilt;
	public static TextureRegion mainMenuButtonSoundEnabled;
	public static TextureRegion mainMenuButtonSoundDisabled;
	public static TextureRegion mainMenuButtonScore;
	public static TextureRegion mainMenuButtonHelp;
	public static TextureRegion mainMenuTextPlay;
	public static TextureRegion mainMenuTextControl;
	public static TextureRegion mainMenuTextSound;
	public static TextureRegion mainMenuTextHelp;
	public static TextureRegion mainMenuTextHighScores;

*/	
	public static Texture mainScreenUIElements;
	public static TextureRegion alphaOverGameField1040x1730;
	public static TextureRegion mainMenuLogo;
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
	public static TextureRegion levelSymbol;
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
	public static TextureRegion overwriteScoreMessage;
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
	public static TextureRegion readyMessage;
	public static TextureRegion resetHighscoresMessage;
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
	public static TextureRegion selectLevelMessage;
	public static Font fontBebasneue64x64White;
	public static ScoreFont scoreFont;
	
//	public static Texture highscoresScreenBackground;
//	public static TextureRegion highscoresScreenBackgroundRegion;
	
	public static Texture gameFieldGreen;
	public static TextureRegion gameFieldGreenRegion;
	
	public static Texture userInterfaceElements;
	public static TextureRegion mainScreenMenuRegion;
	public static TextureRegion readyBannerRegion;
	public static TextureRegion resumeQuitMenuRegion;
	public static TextureRegion levelUpRegion;
	public static TextureRegion gameOverBannerRegion;	
	public static TextureRegion buttonPause;
	public static TextureRegion buttonHome;
	public static TextureRegion buttonBack;
	public static TextureRegion buttonForward;
	//public static Font font;

	
	public static Texture gameScreenBackground;
	public static TextureRegion gameScreenBackgroundRegion;

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
	
//	public static Texture selectLevelScreenElements;
//	public static TextureRegion selectLevelScreenBackgroundRegion;
//	public static TextureRegion level1Button;
//	public static TextureRegion level2Button;
//	public static TextureRegion level3Button;
//	public static TextureRegion level4Button;
//	public static TextureRegion level5Button;
//	public static TextureRegion level6Button;
//	public static TextureRegion level7Button;
//	public static TextureRegion level8Button;
//	//public static TextureRegion buttonUnavailable;
//	public static TextureRegion buttonHomeGreen;

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
		mainScreenBackground = new Texture(game, "background_mainscreen.png");
		mainScreenBackgroundRegion = new TextureRegion(mainScreenBackground, 0,	0, 1080, 1920);
		
		mainScreenUIElements = new Texture(game, "atlas_ui_elements.png");
		
		alphaOverGameField1040x1730 = new TextureRegion(mainScreenUIElements, 1536, 1406, 267, 445);
		mainMenuLogo = new TextureRegion(mainScreenUIElements, 0, 0, 1080, 300);
		ballsLeft0 = new TextureRegion(mainScreenUIElements, 676, 828, 128, 128);
		ballsLeft1 = new TextureRegion(mainScreenUIElements, 1592, 286, 128, 128);
		ballsLeft2 = new TextureRegion(mainScreenUIElements, 256, 1740, 128, 128);
		ballsLeft3 = new TextureRegion(mainScreenUIElements, 1720, 286, 128, 128);
		ballsLeft4 = new TextureRegion(mainScreenUIElements, 384, 1740, 128, 128);
		ballsLeft5 = new TextureRegion(mainScreenUIElements, 512, 1792, 128, 128);
		ballsLeft6 = new TextureRegion(mainScreenUIElements, 640, 1792, 128, 128);
		ballsLeft7 = new TextureRegion(mainScreenUIElements, 768, 1792, 128, 128);
		ballsLeft8 = new TextureRegion(mainScreenUIElements, 896, 1792, 128, 128);
		ballsLeft9 = new TextureRegion(mainScreenUIElements, 1024, 1792, 128, 128);
		ballsSymbol = new TextureRegion(mainScreenUIElements, 1152, 1792, 128, 128);
		buttonLevel1 = new TextureRegion(mainScreenUIElements, 1024, 1536, 256, 256);
		buttonLevel2 = new TextureRegion(mainScreenUIElements, 1280, 1536, 256, 256);
		buttonLevel3 = new TextureRegion(mainScreenUIElements, 2046, 0, 256, 256);
		buttonLevel4 = new TextureRegion(mainScreenUIElements, 1960, 256, 256, 256);
		buttonLevel5 = new TextureRegion(mainScreenUIElements, 2302, 0, 256, 256);
		buttonLevel6 = new TextureRegion(mainScreenUIElements, 1831, 512, 256, 256);
		buttonLevel7 = new TextureRegion(mainScreenUIElements, 2216, 256, 256, 256);
		buttonLevel8 = new TextureRegion(mainScreenUIElements, 2558, 0, 256, 256);
		buttonUnavailable = new TextureRegion(mainScreenUIElements, 828, 300, 200, 200);    // buttonUnavailableX
		//buttonUnavailable = new TextureRegion(mainScreenUIElements, 1831, 768, 256, 256); // buttonUnavailableY
		gameOverMessage = new TextureRegion(mainScreenUIElements, 0, 828, 676, 144);
		levelPassedMessage = new TextureRegion(mainScreenUIElements, 1536, 1024, 481, 382);
		levelSymbol = new TextureRegion(mainScreenUIElements, 0, 1740, 256, 128);
		mainMenuButtonControlSwipe = new TextureRegion(mainScreenUIElements, 2087, 512, 256, 256);
		mainMenuButtonControlTilt = new TextureRegion(mainScreenUIElements, 2472, 256, 256, 256);
		mainMenuButtonControlTouch = new TextureRegion(mainScreenUIElements, 2814, 0, 256, 256);
		mainMenuButtonHelp = new TextureRegion(mainScreenUIElements, 2087, 768, 256, 256);
		mainMenuButtonHome = new TextureRegion(mainScreenUIElements, 2343, 512, 256, 256);
		mainMenuButtonNo = new TextureRegion(mainScreenUIElements, 2728, 256, 256, 256);
		mainMenuButtonPlay = new TextureRegion(mainScreenUIElements, 2017, 1024, 256, 256);
		mainMenuButtonReset = new TextureRegion(mainScreenUIElements, 3070, 0, 256, 256);
		mainMenuButtonScore = new TextureRegion(mainScreenUIElements, 2343, 768, 256, 256);
		mainMenuButtonSoundDisabled = new TextureRegion(mainScreenUIElements, 2599, 512, 256, 256);
		mainMenuButtonSoundEnabled = new TextureRegion(mainScreenUIElements, 1803, 1406, 256, 256);
		mainMenuButtonYes = new TextureRegion(mainScreenUIElements, 2984, 256, 256, 256);
		mainMenuTextControl = new TextureRegion(mainScreenUIElements, 807, 512, 512, 256);
		mainMenuTextGoon = new TextureRegion(mainScreenUIElements, 807, 768, 512, 256);
		mainMenuTextHelp = new TextureRegion(mainScreenUIElements, 1319, 512, 512, 256);
		mainMenuTextHighscores = new TextureRegion(mainScreenUIElements, 1319, 768, 512, 256);
		mainMenuTextNo = new TextureRegion(mainScreenUIElements, 0, 972, 512, 256);
		mainMenuTextPlay = new TextureRegion(mainScreenUIElements, 0, 1228, 512, 256);
		mainMenuTextQuit = new TextureRegion(mainScreenUIElements, 0, 1484, 512, 256);
		mainMenuTextReset = new TextureRegion(mainScreenUIElements, 512, 1024, 512, 256);
		mainMenuTextResume = new TextureRegion(mainScreenUIElements, 512, 1280, 512, 256);
		mainMenuTextRetry = new TextureRegion(mainScreenUIElements, 512, 1536, 512, 256);
		mainMenuTextSound = new TextureRegion(mainScreenUIElements, 1024, 1024, 512, 256);
		mainMenuTextYes = new TextureRegion(mainScreenUIElements, 1024, 1280, 512, 256);
		pauseButton = new TextureRegion(mainScreenUIElements, 1280, 1792, 128, 128);
		pauseMessage = new TextureRegion(mainScreenUIElements, 1592, 142, 368, 144);
		readyMessage = new TextureRegion(mainScreenUIElements, 1592, 0, 454, 142);
		resetHighscoresMessage = new TextureRegion(mainScreenUIElements, 0, 444, 807, 384);
		/* the score charactes are used in ScoreFont.java
		score0 = new TextureRegion(mainScreenUIElements, 1408, 1792, 128, 128);
		score1 = new TextureRegion(mainScreenUIElements, 2273, 1024, 128, 128);
		score2 = new TextureRegion(mainScreenUIElements, 3326, 0, 128, 128);
		score3 = new TextureRegion(mainScreenUIElements, 2855, 512, 128, 128);
		score4 = new TextureRegion(mainScreenUIElements, 2599, 768, 128, 128);
		score5 = new TextureRegion(mainScreenUIElements, 2273, 1152, 128, 128);
		score6 = new TextureRegion(mainScreenUIElements, 2401, 1024, 128, 128);
		score7 = new TextureRegion(mainScreenUIElements, 3326, 128, 128, 128);
		score8 = new TextureRegion(mainScreenUIElements, 3454, 0, 128, 128);
		score9 = new TextureRegion(mainScreenUIElements, 1803, 1662, 128, 128);
		scoreDot = new TextureRegion(mainScreenUIElements, 2059, 1280, 128, 128);
		scoreMinus = new TextureRegion(mainScreenUIElements, 2059, 1408, 128, 128);
		scoreNone = new TextureRegion(mainScreenUIElements, 2187, 1280, 128, 128);
		*/
		selectLevelMessage = new TextureRegion(mainScreenUIElements, 0, 300, 828, 144);
		fontBebasneue64x64White = new Font(mainScreenUIElements, 1080, 0, 8, 64, 64);
		scoreFont = new ScoreFont(mainScreenUIElements, 128, 128);

		

		
		
		
		
		/*highscoresScreenBackground = new Texture(game,
				"background_highscores2.png");
		highscoresScreenBackgroundRegion = new TextureRegion(highscoresScreenBackground, 0,
				0, 1080, 1920);*/
	
		userInterfaceElements = new Texture(game, "atlas_ui_elements.png");
		//mainScreenMenuRegion = new TextureRegion(userInterfaceElements, 0, 0, 700, 1000);
		readyBannerRegion = new TextureRegion(userInterfaceElements, 0, 1500, 700, 250);
		resumeQuitMenuRegion = new TextureRegion(userInterfaceElements, 0, 1000, 700, 500);
		levelUpRegion = new TextureRegion(userInterfaceElements, 1400,0, 700, 500);
		gameOverBannerRegion = new TextureRegion(userInterfaceElements, 700, 0, 700, 500);
		buttonPause = new TextureRegion(userInterfaceElements, 228, 1878, 128, 128);
		//buttonHome = new TextureRegion(userInterfaceElements, 100, 1750, 128, 128);
		//buttonBack = new TextureRegion(userInterfaceElements, 228, 1750, 128, 128);
		buttonForward = new TextureRegion(userInterfaceElements, 100, 1878, 128, 128);
		//font = new Font(userInterfaceElements, 700, 500, 8, 64, 64);

		
		gameScreenBackground = new Texture(game, "background_with_transp_gamefield_1.png");
		gameScreenBackgroundRegion = new TextureRegion(gameScreenBackground, 0,
				0, 1080, 1920);
		
		//gameFieldGreen = new Texture(game, "gamefield_green_1040x1730.png");
		//gameFieldGreenRegion = new TextureRegion(gameFieldGreen, 0, 0, 1040, 1730);
		
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
		
/*		selectLevelScreenElements = new Texture(game, "atlas_select_level_elements.png");
		selectLevelScreenBackgroundRegion = new TextureRegion(selectLevelScreenElements, 0, 0, 1080, 1920);
		level1Button = new TextureRegion(selectLevelScreenElements, 1080, 0, 200, 200);
		level2Button = new TextureRegion(selectLevelScreenElements, 1280, 0, 200, 200);
		level3Button = new TextureRegion(selectLevelScreenElements, 1480, 0, 200, 200);
		level4Button = new TextureRegion(selectLevelScreenElements, 1680, 0, 200, 200);
		level5Button = new TextureRegion(selectLevelScreenElements, 1880, 0, 200, 200);
		level6Button = new TextureRegion(selectLevelScreenElements, 1080, 200, 200, 200);
		level7Button = new TextureRegion(selectLevelScreenElements, 1280, 200, 200, 200);
		level8Button = new TextureRegion(selectLevelScreenElements, 1480, 200, 200, 200);
		//buttonUnavailable = new TextureRegion(selectLevelScreenElements, 1680, 200, 200, 200);
		buttonHomeGreen = new TextureRegion(selectLevelScreenElements, 1880, 200, 200, 200);*/

		
		


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
		mainScreenUIElements.reload();
		//highscoresScreenBackground.reload();
		//gameFieldGreen.reload();
		//selectLevelScreenElements.reload();
		userInterfaceElements.reload();
		gameScreenBackground.reload();
		gameScreenElements.reload();
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
			
	}

}
