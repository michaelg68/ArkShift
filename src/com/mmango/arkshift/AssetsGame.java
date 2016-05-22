package com.mmango.arkshift;

//import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class AssetsGame {
	//public static Texture atlasGameScreenBackground;
	//public static TextureRegion gameScreenBackgroundRegion;
	
	public static Texture atlasGameElements;
	public static TextureRegion alphaOverGameField_60Opacity;
	public static TextureRegion gameScreenBackgroundRegion;
	public static TextureRegion ballRed;
	public static TextureRegion ballWhite;
	public static TextureRegion ballYellow;
	/*
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
	*/
	public static TextureRegion ballsSymbol;
	public static TextureRegion brickBlue;
	public static TextureRegion brickBluesky;
	public static TextureRegion brickGoldPlain;
	public static TextureRegion brickGreen;
	public static TextureRegion brickGrey;
	public static TextureRegion brickGold;
	public static TextureRegion brickLightviolet;
	public static TextureRegion brickOrange;
	public static TextureRegion brickPink;
	public static TextureRegion brickPurple;
	public static TextureRegion brickRed;
	public static TextureRegion brickViolet;
	public static TextureRegion mainMenuButtonControlSwipe;
	public static TextureRegion mainMenuButtonControlTilt;
	public static TextureRegion mainMenuButtonControlTouch;
	public static TextureRegion mainMenuButtonHome;
	public static TextureRegion mainMenuButtonPlay;
	public static TextureRegion mainMenuButtonSoundDisabled;
	public static TextureRegion mainMenuButtonSoundEnabled;
	public static TextureRegion mainMenuTextControl;
	public static TextureRegion mainMenuTextQuit;
	public static TextureRegion mainMenuTextResume;
	public static TextureRegion mainMenuTextSound;
	public static TextureRegion pauseButton;
	public static TextureRegion racquet;
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
	public static ScoreGameFont scoreGameFont;
	public static ScoreBallsFont scoreBallsFont;

	

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
		//atlasBackgroundUI = new Texture(game, "background_ui.jpg");
		
		//atlasGameScreenBackground = new Texture(game, "background_with_transp_gamefield_1.png");
		
		
		atlasGameElements = new Texture(game, "atlas_gamescreen.png");

		alphaOverGameField_60Opacity = new TextureRegion(atlasGameElements, 512, 640, 67, 111);
		gameScreenBackgroundRegion = new TextureRegion(atlasGameElements, 512, 0, 360, 640);
		ballRed = new TextureRegion(atlasGameElements, 256, 960, 32, 32);
		ballWhite = new TextureRegion(atlasGameElements, 288, 960, 32, 32);
		ballYellow = new TextureRegion(atlasGameElements, 320, 960, 32, 32);
		/*
		ballsLeft0 = new TextureRegion(atlasGameElements, 0, 928, 64, 64);
		ballsLeft1 = new TextureRegion(atlasGameElements, 64, 928, 64, 64);
		ballsLeft2 = new TextureRegion(atlasGameElements, 128, 928, 64, 64);
		ballsLeft3 = new TextureRegion(atlasGameElements, 192, 928, 64, 64);
		ballsLeft4 = new TextureRegion(atlasGameElements, 256, 896, 64, 64);
		ballsLeft5 = new TextureRegion(atlasGameElements, 320, 896, 64, 64);
		ballsLeft6 = new TextureRegion(atlasGameElements, 579, 640, 64, 64);
		ballsLeft7 = new TextureRegion(atlasGameElements, 872, 384, 64, 64);
		ballsLeft8 = new TextureRegion(atlasGameElements, 512, 751, 64, 64);
		ballsLeft9 = new TextureRegion(atlasGameElements, 384, 896, 64, 64);
		*/
		ballsSymbol = new TextureRegion(atlasGameElements, 579, 704, 64, 64);
		//fontBebasneue64x64White = new TextureRegion(atlasGameElements, 0, 0, 512, 512);
		brickBlue = new TextureRegion(atlasGameElements, 352, 960, 32, 32);
		brickBluesky = new TextureRegion(atlasGameElements, 384, 960, 32, 32);
		brickGoldPlain = new TextureRegion(atlasGameElements, 416, 960, 32, 32);
		brickGreen = new TextureRegion(atlasGameElements, 448, 960, 32, 32);
		brickGrey = new TextureRegion(atlasGameElements, 771, 640, 32, 32);
		brickGold = new TextureRegion(atlasGameElements, 480, 960, 32, 32);
		brickLightviolet = new TextureRegion(atlasGameElements, 771, 672, 32, 32);
		brickOrange = new TextureRegion(atlasGameElements, 803, 640, 32, 32);
		brickPink = new TextureRegion(atlasGameElements, 872, 576, 32, 32);
		brickPurple = new TextureRegion(atlasGameElements, 936, 512, 32, 32);
		brickRed = new TextureRegion(atlasGameElements, 512, 943, 32, 32);
		brickViolet = new TextureRegion(atlasGameElements, 576, 896, 32, 32);
		mainMenuButtonControlSwipe = new TextureRegion(atlasGameElements, 872, 0, 128, 128);
		mainMenuButtonControlTilt = new TextureRegion(atlasGameElements, 256, 640, 128, 128);
		mainMenuButtonControlTouch = new TextureRegion(atlasGameElements, 872, 128, 128, 128);
		mainMenuButtonHome = new TextureRegion(atlasGameElements, 256, 768, 128, 128);
		mainMenuButtonPlay = new TextureRegion(atlasGameElements, 384, 640, 128, 128);
		mainMenuButtonSoundDisabled = new TextureRegion(atlasGameElements, 872, 256, 128, 128);
		mainMenuButtonSoundEnabled = new TextureRegion(atlasGameElements, 384, 768, 128, 128);
		mainMenuTextControl = new TextureRegion(atlasGameElements, 0, 512, 256, 128);
		mainMenuTextQuit = new TextureRegion(atlasGameElements, 0, 640, 256, 128);
		mainMenuTextResume = new TextureRegion(atlasGameElements, 0, 768, 256, 128);
		mainMenuTextSound = new TextureRegion(atlasGameElements, 256, 512, 256, 128);
		pauseButton = new TextureRegion(atlasGameElements, 643, 640, 64, 64);
		racquet = new TextureRegion(atlasGameElements, 0, 896, 256, 32);
		/*
		score0 = new TextureRegion(atlasGameElements, 872, 448, 64, 64);
		score1 = new TextureRegion(atlasGameElements, 936, 384, 64, 64);
		score2 = new TextureRegion(atlasGameElements, 512, 815, 64, 64);
		score3 = new TextureRegion(atlasGameElements, 448, 896, 64, 64);
		score4 = new TextureRegion(atlasGameElements, 707, 640, 64, 64);
		score5 = new TextureRegion(atlasGameElements, 643, 704, 64, 64);
		score6 = new TextureRegion(atlasGameElements, 576, 768, 64, 64);
		score7 = new TextureRegion(atlasGameElements, 936, 448, 64, 64);
		score8 = new TextureRegion(atlasGameElements, 872, 512, 64, 64);
		score9 = new TextureRegion(atlasGameElements, 512, 879, 64, 64);
		scoreDot = new TextureRegion(atlasGameElements, 640, 768, 64, 64);
		scoreMinus = new TextureRegion(atlasGameElements, 576, 832, 64, 64);
		scoreNone = new TextureRegion(atlasGameElements, 707, 704, 64, 64);
		*/
		
		fontBebasneue64x64White = new Font(atlasGameElements, 0, 0, 8, 64, 64);
		scoreGameFont = new ScoreGameFont(atlasGameElements, 128, 128);
		scoreBallsFont = new ScoreBallsFont(atlasGameElements, 128, 128);


		clickSound = game.getAudio().newSound("click.ogg");
		racquetHitSound = game.getAudio().newSound("racquet_hit_sound.ogg");
		frameHitSound = game.getAudio().newSound("frame_hit_sound.ogg");
		brickHitSound = game.getAudio().newSound("brick_hit_sound.ogg");
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
		//atlasBackgroundUI.reload();
		//atlasGameScreenBackground.reload();
		atlasGameElements.reload();
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}

}
