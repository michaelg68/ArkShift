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
	public static Texture UIGameElements;

	public static TextureRegion alphaOverGameField_60Opacity;
	public static TextureRegion mainMenuLogo;
	//public static TextureRegion mainMenuLogoByMmango;
	public static TextureRegion ballRed;
	public static TextureRegion ballRed64;
	public static TextureRegion ballWhite;
	public static TextureRegion ballWhite_64;
	public static TextureRegion ballYellow;
	public static TextureRegion ballYellow_64;
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
	public static TextureRegion brickBlue64;
	public static TextureRegion brickBluesky;
	public static TextureRegion brickBluesky64;
	public static TextureRegion brickGold;
	public static TextureRegion brickGold64;
	public static TextureRegion brickGreen;
	public static TextureRegion brickGreen64;
	public static TextureRegion brickGrey;
	public static TextureRegion brickGrey64;
	public static TextureRegion brickLightviolet;
	public static TextureRegion brickLightviolet64;
	public static TextureRegion brickOrange;
	public static TextureRegion brickOrange64;
	public static TextureRegion brickPink;
	public static TextureRegion brickPink64;
	public static TextureRegion brickPurple;
	public static TextureRegion brickPurple64;
	public static TextureRegion brickRed;
	public static TextureRegion brickRed64;
	public static TextureRegion brickViolet;
	public static TextureRegion brickViolet64;
	public static TextureRegion brickWheat;
	public static TextureRegion brickWheat64;
	public static TextureRegion buttonLevel1;
	public static TextureRegion buttonLevel2;
	public static TextureRegion buttonLevel3;
	public static TextureRegion buttonLevel4;
	public static TextureRegion buttonLevel5;
	public static TextureRegion buttonLevel6;
	public static TextureRegion buttonLevel7;
	public static TextureRegion buttonLevel8;
	public static TextureRegion buttonUnavailable;
	public static TextureRegion buttonUnavailableY;
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
	public static TextureRegion racquet512x64;
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
	
//	public static Texture highscoresScreenBackground;
//	public static TextureRegion highscoresScreenBackgroundRegion;
	
	public static Texture gameFieldGreen;
	public static TextureRegion gameFieldGreenRegion;
	
	/*
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
	 
	 */

	
	public static Texture gameScreenBackground;
	public static TextureRegion gameScreenBackgroundRegion;
/*
	
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
	
*/	
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
		//mainScreenBackground = new Texture(game, "background_mainscreen.png");
		mainScreenBackground = new Texture(game, "background_ui.png");
		mainScreenBackgroundRegion = new TextureRegion(mainScreenBackground, 0,	0, 1080, 1920);
		
		UIGameElements = new Texture(game, "atlas.png");
		alphaOverGameField_60Opacity = new TextureRegion(UIGameElements, 1024, 0, 267, 445);
		//mainMenuLogo = new TextureRegion(UIGameElements, 0, 0, 670, 186);
		mainMenuLogo = new TextureRegion(UIGameElements, 0, 186, 625, 194);
		ballRed = new TextureRegion(UIGameElements, 625, 186, 32, 32);
		ballRed64 = new TextureRegion(UIGameElements, 939, 58, 64, 64);
		ballWhite = new TextureRegion(UIGameElements, 990, 122, 32, 32);
		ballWhite_64 = new TextureRegion(UIGameElements, 926, 122, 64, 64);
		ballYellow = new TextureRegion(UIGameElements, 990, 154, 32, 32);
		ballYellow_64 = new TextureRegion(UIGameElements, 926, 186, 64, 64);
		ballsLeft0 = new TextureRegion(UIGameElements, 881, 250, 64, 64);
		ballsLeft1 = new TextureRegion(UIGameElements, 881, 314, 64, 64);
		ballsLeft2 = new TextureRegion(UIGameElements, 945, 250, 64, 64);
		ballsLeft3 = new TextureRegion(UIGameElements, 945, 314, 64, 64);
		ballsLeft4 = new TextureRegion(UIGameElements, 1547, 128, 64, 64);
		ballsLeft5 = new TextureRegion(UIGameElements, 1024, 701, 64, 64);
		ballsLeft6 = new TextureRegion(UIGameElements, 842, 892, 64, 64);
		ballsLeft7 = new TextureRegion(UIGameElements, 1547, 192, 64, 64);
		ballsLeft8 = new TextureRegion(UIGameElements, 1611, 128, 64, 64);
		ballsLeft9 = new TextureRegion(UIGameElements, 1024, 765, 64, 64);
		ballsSymbol = new TextureRegion(UIGameElements, 1088, 701, 64, 64);
		brickBlue = new TextureRegion(UIGameElements, 990, 186, 32, 32);
		brickBlue64 = new TextureRegion(UIGameElements, 906, 892, 64, 64);
		brickBluesky = new TextureRegion(UIGameElements, 990, 218, 32, 32);
		brickBluesky64 = new TextureRegion(UIGameElements, 1611, 192, 64, 64);
		brickGold = new TextureRegion(UIGameElements, 320, 956, 32, 32);
		brickGold64 = new TextureRegion(UIGameElements, 1547, 256, 64, 64);
		brickGreen = new TextureRegion(UIGameElements, 352, 956, 32, 32);
		brickGreen64 = new TextureRegion(UIGameElements, 1675, 128, 64, 64);
		brickGrey = new TextureRegion(UIGameElements, 384, 956, 32, 32);
		brickGrey64 = new TextureRegion(UIGameElements, 1803, 0, 64, 64);
		brickLightviolet = new TextureRegion(UIGameElements, 416, 956, 32, 32);
		brickLightviolet64 = new TextureRegion(UIGameElements, 1280, 537, 64, 64);
		brickOrange = new TextureRegion(UIGameElements, 448, 956, 32, 32);
		brickOrange64 = new TextureRegion(UIGameElements, 1088, 765, 64, 64);
		brickPink = new TextureRegion(UIGameElements, 480, 956, 32, 32);
		brickPink64 = new TextureRegion(UIGameElements, 1024, 829, 64, 64);
		brickPurple = new TextureRegion(UIGameElements, 512, 949, 32, 32);
		brickPurple64 = new TextureRegion(UIGameElements, 1152, 701, 64, 64);
		brickRed = new TextureRegion(UIGameElements, 544, 949, 32, 32);
		brickRed64 = new TextureRegion(UIGameElements, 1675, 192, 64, 64);
		brickViolet = new TextureRegion(UIGameElements, 576, 949, 32, 32);
		brickViolet64 = new TextureRegion(UIGameElements, 1867, 0, 64, 64);
		brickWheat = new TextureRegion(UIGameElements, 608, 949, 32, 32);
		brickWheat64 = new TextureRegion(UIGameElements, 1803, 64, 64, 64);
		buttonLevel1 = new TextureRegion(UIGameElements, 1611, 256, 64, 64);
		buttonLevel2 = new TextureRegion(UIGameElements, 1547, 320, 64, 64);
		buttonLevel3 = new TextureRegion(UIGameElements, 1739, 128, 64, 64);
		buttonLevel4 = new TextureRegion(UIGameElements, 1484, 384, 64, 64);
		buttonLevel5 = new TextureRegion(UIGameElements, 1280, 601, 64, 64);
		buttonLevel6 = new TextureRegion(UIGameElements, 1344, 537, 64, 64);
		buttonLevel7 = new TextureRegion(UIGameElements, 970, 893, 64, 64);
		buttonLevel8 = new TextureRegion(UIGameElements, 1216, 701, 64, 64);
		buttonUnavailable = new TextureRegion(UIGameElements, 1152, 765, 64, 64);
		buttonUnavailableY = new TextureRegion(UIGameElements, 1088, 829, 64, 64);
		gameOverMessage = new TextureRegion(UIGameElements, 670, 58, 269, 58);
		levelPassedMessage = new TextureRegion(UIGameElements, 1291, 384, 193, 153);
		levelSymbol = new TextureRegion(UIGameElements, 256, 956, 64, 32);
		mainMenuButtonControlSwipe = new TextureRegion(UIGameElements, 1034, 893, 64, 64);
		mainMenuButtonControlTilt = new TextureRegion(UIGameElements, 1611, 320, 64, 64);
		mainMenuButtonControlTouch = new TextureRegion(UIGameElements, 1803, 128, 64, 64);
		mainMenuButtonHelp = new TextureRegion(UIGameElements, 1739, 192, 64, 64);
		mainMenuButtonHome = new TextureRegion(UIGameElements, 1867, 64, 64, 64);
		mainMenuButtonNo = new TextureRegion(UIGameElements, 1675, 256, 64, 64);
		mainMenuButtonPlay = new TextureRegion(UIGameElements, 1931, 0, 64, 64);
		mainMenuButtonReset = new TextureRegion(UIGameElements, 1484, 448, 64, 64);
		mainMenuButtonScore = new TextureRegion(UIGameElements, 1548, 384, 64, 64);
		mainMenuButtonSoundDisabled = new TextureRegion(UIGameElements, 1408, 537, 64, 64);
		mainMenuButtonSoundEnabled = new TextureRegion(UIGameElements, 1344, 601, 64, 64);
		mainMenuButtonYes = new TextureRegion(UIGameElements, 1280, 665, 64, 64);
		mainMenuTextControl = new TextureRegion(UIGameElements, 670, 116, 256, 128);
		mainMenuTextGoon = new TextureRegion(UIGameElements, 625, 244, 256, 128);
		mainMenuTextHelp = new TextureRegion(UIGameElements, 512, 636, 256, 128);
		mainMenuTextHighscores = new TextureRegion(UIGameElements, 512, 764, 256, 128);
		mainMenuTextNo = new TextureRegion(UIGameElements, 1291, 0, 256, 128);
		mainMenuTextPlay = new TextureRegion(UIGameElements, 768, 636, 256, 128);
		mainMenuTextQuit = new TextureRegion(UIGameElements, 1291, 128, 256, 128);
		mainMenuTextReset = new TextureRegion(UIGameElements, 1024, 445, 256, 128);
		mainMenuTextResume = new TextureRegion(UIGameElements, 768, 764, 256, 128);
		mainMenuTextRetry = new TextureRegion(UIGameElements, 1291, 256, 256, 128);
		mainMenuTextSound = new TextureRegion(UIGameElements, 1547, 0, 256, 128);
		mainMenuTextYes = new TextureRegion(UIGameElements, 1024, 573, 256, 128);
		pauseButton = new TextureRegion(UIGameElements, 1152, 829, 64, 64);
		pauseMessage = new TextureRegion(UIGameElements, 694, 892, 148, 58);
		racquet = new TextureRegion(UIGameElements, 0, 956, 256, 32);
		racquet512x64 = new TextureRegion(UIGameElements, 0, 892, 512, 64);
		readyMessage = new TextureRegion(UIGameElements, 512, 892, 182, 57);
		resetHighscoresMessage = new TextureRegion(UIGameElements, 512, 380, 512, 256);
		selectLevelMessage = new TextureRegion(UIGameElements, 670, 0, 328, 58);		

		/*
		score0 = new TextureRegion(UIGameElements, 1216, 765, 64, 64);
		score1 = new TextureRegion(UIGameElements, 1098, 893, 64, 64);
		score2 = new TextureRegion(UIGameElements, 1675, 320, 64, 64);
		score3 = new TextureRegion(UIGameElements, 1931, 64, 64, 64);
		score4 = new TextureRegion(UIGameElements, 1739, 256, 64, 64);
		score5 = new TextureRegion(UIGameElements, 1867, 128, 64, 64);
		score6 = new TextureRegion(UIGameElements, 1803, 192, 64, 64);
		score7 = new TextureRegion(UIGameElements, 1548, 448, 64, 64);
		score8 = new TextureRegion(UIGameElements, 1612, 384, 64, 64);
		score9 = new TextureRegion(UIGameElements, 1484, 512, 64, 64);
		scoreDot = new TextureRegion(UIGameElements, 1280, 729, 64, 64);
		scoreMinus = new TextureRegion(UIGameElements, 1344, 665, 64, 64);
		scoreNone = new TextureRegion(UIGameElements, 1408, 601, 64, 64);
		*/
		fontBebasneue64x64White = new Font(UIGameElements, 0, 380, 8, 64, 64);
		scoreFont = new ScoreFont(UIGameElements, 128, 128);

		

		
		
		
		
		/*highscoresScreenBackground = new Texture(game,
				"background_highscores2.png");
		highscoresScreenBackgroundRegion = new TextureRegion(highscoresScreenBackground, 0,
				0, 1080, 1920);*/
	   /*
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
*/
		
		gameScreenBackground = new Texture(game, "background_with_transp_gamefield_1.png");
		gameScreenBackgroundRegion = new TextureRegion(gameScreenBackground, 0,
				0, 1080, 1920);
		
		//gameFieldGreen = new Texture(game, "gamefield_green_1040x1730.png");
		//gameFieldGreenRegion = new TextureRegion(gameFieldGreen, 0, 0, 1040, 1730);
		
		/*
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
		*/
		
		
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
