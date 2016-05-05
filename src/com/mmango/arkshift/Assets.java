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
	public static TextureRegion fontBebasneue64x64White;
	public static TextureRegion buttonLevel1;
	public static TextureRegion buttonLevel2;
	public static TextureRegion buttonLevel3;
	public static TextureRegion buttonLevel4;
	public static TextureRegion buttonLevel5;
	public static TextureRegion buttonLevel6;
	public static TextureRegion buttonLevel7;
	public static TextureRegion buttonLevel8;
	public static TextureRegion buttonUnavailableX;
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
	public static Font font;

	
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
	
	public static Texture selectLevelScreenElements;
	public static TextureRegion selectLevelScreenBackgroundRegion;
	public static TextureRegion level1Button;
	public static TextureRegion level2Button;
	public static TextureRegion level3Button;
	public static TextureRegion level4Button;
	public static TextureRegion level5Button;
	public static TextureRegion level6Button;
	public static TextureRegion level7Button;
	public static TextureRegion level8Button;
	public static TextureRegion buttonUnavailable;
	public static TextureRegion buttonHomeGreen;

	//public static Music music;
	public static Sound clickSound;
	public static Sound racquetHitSound;
	public static Sound frameHitSound;
	public static Sound brickHitSound;
	public static Sound levelStartsSound;
	public static Sound ballRollingAndKnockSound;
	public static Sound shiftSound;
	public static Sound gameOverSound;
	public static Sound gameOverLongSound;
	public static Sound levelPassedSound;


	public static void load(GLGame game) {
		mainScreenBackground = new Texture(game, "background_marble_light_flattened.jpg");
		mainScreenBackgroundRegion = new TextureRegion(mainScreenBackground, 0,	0, 1080, 1920);
		
		mainScreenUIElements = new Texture(game, "atlas_ui_elements.png");
		
		alphaOverGameField1040x1730 = new TextureRegion(mainScreenUIElements, 1080, 0, 1040, 1730);
		mainMenuLogo = new TextureRegion(mainScreenUIElements, 0, 0, 1080, 300);
		ballsLeft0 = new TextureRegion(mainScreenUIElements, 828, 684, 128, 128);
		ballsLeft1 = new TextureRegion(mainScreenUIElements, 454, 1740, 128, 128);
		ballsLeft2 = new TextureRegion(mainScreenUIElements, 582, 1740, 128, 128);
		ballsLeft3 = new TextureRegion(mainScreenUIElements, 710, 1740, 128, 128);
		ballsLeft4 = new TextureRegion(mainScreenUIElements, 838, 1740, 128, 128);
		ballsLeft5 = new TextureRegion(mainScreenUIElements, 966, 1740, 128, 128);
		ballsLeft6 = new TextureRegion(mainScreenUIElements, 1094, 1730, 128, 128);
		ballsLeft7 = new TextureRegion(mainScreenUIElements, 1222, 1730, 128, 128);
		ballsLeft8 = new TextureRegion(mainScreenUIElements, 1350, 1730, 128, 128);
		ballsLeft9 = new TextureRegion(mainScreenUIElements, 1478, 1730, 128, 128);
		ballsSymbol = new TextureRegion(mainScreenUIElements, 1606, 1730, 128, 128);
		fontBebasneue64x64White = new TextureRegion(mainScreenUIElements, 0, 972, 512, 512);
		buttonLevel1 = new TextureRegion(mainScreenUIElements, 3144, 256, 256, 256);
		buttonLevel2 = new TextureRegion(mainScreenUIElements, 2120, 1280, 256, 256);
		buttonLevel3 = new TextureRegion(mainScreenUIElements, 2632, 894, 256, 256);
		buttonLevel4 = new TextureRegion(mainScreenUIElements, 3113, 512, 256, 256);
		buttonLevel5 = new TextureRegion(mainScreenUIElements, 2376, 1280, 256, 256);
		buttonLevel6 = new TextureRegion(mainScreenUIElements, 2120, 1536, 256, 256);
		buttonLevel7 = new TextureRegion(mainScreenUIElements, 3400, 256, 256, 256);
		buttonLevel8 = new TextureRegion(mainScreenUIElements, 3656, 0, 256, 256);
		buttonUnavailableX = new TextureRegion(mainScreenUIElements, 2888, 1406, 200, 200);
		gameOverMessage = new TextureRegion(mainScreenUIElements, 0, 828, 676, 144);
		levelPassedMessage = new TextureRegion(mainScreenUIElements, 2632, 512, 481, 382);
		mainMenuButtonControlSwipe = new TextureRegion(mainScreenUIElements, 2632, 1150, 256, 256);
		mainMenuButtonControlTilt = new TextureRegion(mainScreenUIElements, 2888, 894, 256, 256);
		mainMenuButtonControlTouch = new TextureRegion(mainScreenUIElements, 3369, 512, 256, 256);
		mainMenuButtonHelp = new TextureRegion(mainScreenUIElements, 3656, 256, 256, 256);
		mainMenuButtonHome = new TextureRegion(mainScreenUIElements, 2376, 1536, 256, 256);
		mainMenuButtonNo = new TextureRegion(mainScreenUIElements, 3144, 768, 256, 256);
		mainMenuButtonPlay = new TextureRegion(mainScreenUIElements, 2888, 1150, 256, 256);
		mainMenuButtonReset = new TextureRegion(mainScreenUIElements, 2632, 1406, 256, 256);
		mainMenuButtonScore = new TextureRegion(mainScreenUIElements, 3625, 512, 256, 256);
		mainMenuButtonSoundDisabled = new TextureRegion(mainScreenUIElements, 3144, 1024, 256, 256);
		mainMenuButtonSoundEnabled = new TextureRegion(mainScreenUIElements, 3400, 768, 256, 256);
		mainMenuButtonYes = new TextureRegion(mainScreenUIElements, 2632, 1662, 256, 256);
		mainMenuTextControl = new TextureRegion(mainScreenUIElements, 0, 1484, 512, 256);
		mainMenuTextGoon = new TextureRegion(mainScreenUIElements, 512, 972, 512, 256);
		mainMenuTextHelp = new TextureRegion(mainScreenUIElements, 512, 1228, 512, 256);
		mainMenuTextHighscores = new TextureRegion(mainScreenUIElements, 512, 1484, 512, 256);
		mainMenuTextNo = new TextureRegion(mainScreenUIElements, 2120, 0, 512, 256);
		mainMenuTextPlay = new TextureRegion(mainScreenUIElements, 2120, 256, 512, 256);
		mainMenuTextQuit = new TextureRegion(mainScreenUIElements, 2120, 512, 512, 256);
		mainMenuTextReset = new TextureRegion(mainScreenUIElements, 2632, 0, 512, 256);
		mainMenuTextResume = new TextureRegion(mainScreenUIElements, 2632, 256, 512, 256);
		mainMenuTextRetry = new TextureRegion(mainScreenUIElements, 2120, 768, 512, 256);
		mainMenuTextSound = new TextureRegion(mainScreenUIElements, 2120, 1024, 512, 256);
		mainMenuTextYes = new TextureRegion(mainScreenUIElements, 3144, 0, 512, 256);
		overwriteScoreMessage = new TextureRegion(mainScreenUIElements, 0, 300, 997, 384);
		pauseButton = new TextureRegion(mainScreenUIElements, 1734, 1730, 128, 128);
		pauseMessage = new TextureRegion(mainScreenUIElements, 676, 828, 368, 144);
		readyMessage = new TextureRegion(mainScreenUIElements, 0, 1740, 454, 142);
		/*
		score0 = new TextureRegion(mainScreenUIElements, 1862, 1730, 128, 128);
		score1 = new TextureRegion(mainScreenUIElements, 1990, 1730, 128, 128);
		score2 = new TextureRegion(mainScreenUIElements, 3912, 0, 128, 128);
		score3 = new TextureRegion(mainScreenUIElements, 2118, 1792, 128, 128);
		score4 = new TextureRegion(mainScreenUIElements, 2246, 1792, 128, 128);
		score5 = new TextureRegion(mainScreenUIElements, 3912, 128, 128, 128);
		score6 = new TextureRegion(mainScreenUIElements, 2374, 1792, 128, 128);
		score7 = new TextureRegion(mainScreenUIElements, 3912, 256, 128, 128);
		score8 = new TextureRegion(mainScreenUIElements, 2502, 1792, 128, 128);
		score9 = new TextureRegion(mainScreenUIElements, 3912, 384, 128, 128);
		scoreDot = new TextureRegion(mainScreenUIElements, 3881, 512, 128, 128);
		scoreMinus = new TextureRegion(mainScreenUIElements, 3400, 1024, 128, 128);
		scoreNone = new TextureRegion(mainScreenUIElements, 3656, 768, 128, 128);
		*/
		selectLevelMessage = new TextureRegion(mainScreenUIElements, 0, 684, 828, 144);
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
		font = new Font(userInterfaceElements, 700, 500, 8, 64, 64);

		
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
		
		selectLevelScreenElements = new Texture(game, "atlas_select_level_elements.png");
		selectLevelScreenBackgroundRegion = new TextureRegion(selectLevelScreenElements, 0, 0, 1080, 1920);
		level1Button = new TextureRegion(selectLevelScreenElements, 1080, 0, 200, 200);
		level2Button = new TextureRegion(selectLevelScreenElements, 1280, 0, 200, 200);
		level3Button = new TextureRegion(selectLevelScreenElements, 1480, 0, 200, 200);
		level4Button = new TextureRegion(selectLevelScreenElements, 1680, 0, 200, 200);
		level5Button = new TextureRegion(selectLevelScreenElements, 1880, 0, 200, 200);
		level6Button = new TextureRegion(selectLevelScreenElements, 1080, 200, 200, 200);
		level7Button = new TextureRegion(selectLevelScreenElements, 1280, 200, 200, 200);
		level8Button = new TextureRegion(selectLevelScreenElements, 1480, 200, 200, 200);
		buttonUnavailable = new TextureRegion(selectLevelScreenElements, 1680, 200, 200, 200);
		buttonHomeGreen = new TextureRegion(selectLevelScreenElements, 1880, 200, 200, 200);

		
		


		clickSound = game.getAudio().newSound("click.ogg");
		racquetHitSound = game.getAudio().newSound("racquet_hit_sound.ogg");
		frameHitSound = game.getAudio().newSound("frame_hit_sound.ogg");
		brickHitSound = game.getAudio().newSound("brick_hit_sound.ogg");
		levelStartsSound = game.getAudio().newSound("level_starts_sound.ogg");
		ballRollingAndKnockSound = game.getAudio().newSound("ball_rolling_and_knock_sound.ogg");
	
		shiftSound = game.getAudio().newSound("shift_8b_22050.ogg");
		gameOverSound = game.getAudio().newSound("game_over.ogg");
		gameOverLongSound = game.getAudio().newSound(
				"game_over_long_8b_22050Hz.ogg");
		levelPassedSound = game.getAudio().newSound("level_passed.ogg");
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
