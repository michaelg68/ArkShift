package com.mmango.arkshift;

//import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class AssetsHelp {
	
	public static Texture atlasBackgroundUI;
	public static TextureRegion backgroundUIRegion;

	public static Texture atlasHelpScreen;
	public static TextureRegion helpScreen1Region;
	public static TextureRegion helpScreen2Region;
	public static TextureRegion helpScreen3Region;	
	public static TextureRegion helpScreen4Region;	
	public static TextureRegion mainMenuButtonHome;
	public static TextureRegion mainMenuTextQuit;

	
	public static Sound clickSound;

	public static void load(GLGame game) {

		atlasHelpScreen = new Texture(game, "atlas_bg_hs.png");
		backgroundUIRegion = new TextureRegion(atlasHelpScreen, 1080, 1920, 360, 640);
		helpScreen1Region = new TextureRegion(atlasHelpScreen, 0, 0, 1080, 1920);
		helpScreen2Region = new TextureRegion(atlasHelpScreen, 1080, 0, 1080, 1920);
		helpScreen3Region = new TextureRegion(atlasHelpScreen, 2160, 0, 1080, 1920);
		helpScreen4Region = new TextureRegion(atlasHelpScreen, 0, 1920, 1080, 1920);
		mainMenuButtonHome = new TextureRegion(atlasHelpScreen, 1440, 1920, 128, 128);
		mainMenuTextQuit = new TextureRegion(atlasHelpScreen, 3240, 0, 256, 128);



		clickSound = game.getAudio().newSound("click.ogg");
	}

	public static void reload() {
		//!!!!!    Note: very important - if you add a new texture, don't forget to add it in this method. otherwise you will face 
		// bugs when GL ES loses context!
		atlasHelpScreen.reload();
	}

	public static void playSound(Sound sound) {
		if (Settings.soundEnabled)
			sound.play(1);
	}

}
