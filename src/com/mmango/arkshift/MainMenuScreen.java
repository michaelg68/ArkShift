package com.mmango.arkshift;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class MainMenuScreen extends GLScreen {
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
	Camera2D guiCam;
	SpriteBatcher batcher;

	Rectangle playBounds;
	Rectangle controlBounds;
	Rectangle soundBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Vector2 touchPoint;

	public MainMenuScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
		batcher = new SpriteBatcher(glGraphics, 100);
		// note that parameters in Rectangle are: lower_left_x, lower_left_y,
		// wide, height
		playBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256, 1400 - 128, 768, 256);
		controlBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256 , 1400 - 128 - 256,
				768, 256);
		soundBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256,
				1400 - 128 - 256 * 2, 768, 256);
		highscoresBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256,
				1400 - 128 - 256 * 3, 768, 256);
		helpBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256,
				1400 - 128 - 256 * 4, 768, 256);
		touchPoint = new Vector2();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				// Log.d("GameScreen:", "event.x = " + Float.toString(event.x));
				// Log.d("GameScreen:", "event.y = " + Float.toString(event.y));
				guiCam.touchToWorld(touchPoint);

				if (OverlapTester.pointInRectangle(playBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Log.d("MainMenuScreen:update",
							"playBounds is touched. Opening SelectLevelScreen");
					game.setScreen(new SelectLevelScreen(game));
					return;
				}
				if (OverlapTester.pointInRectangle(controlBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Log.d("MainMenuScreen:update",
							"controlBounds is touched. Changing the controlType");
					Settings.controlType++;
					if (Settings.controlType > 2)
						Settings.controlType = 0;
					Settings.savePrefs(glGame);
					return;
				}
				if (OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Log.d("MainMenuScreen:update",
							"soundBounds is touched. Enabling/Disabling sound");
					Settings.soundEnabled = (Settings.soundEnabled) ? false
							: true;
					Settings.savePrefs(glGame);
					Log.d("MainMenuScreen:update", "Assets.soundEnabled = "
							+ Boolean.toString(Settings.soundEnabled));
					return;
				}
				if (OverlapTester
						.pointInRectangle(highscoresBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Log.d("MainMenuScreen:update",
							"highscoresBounds is touched. Opening HighscoreScreen");
					game.setScreen(new HighscoreScreen(game));
					return;
				}
				if (OverlapTester.pointInRectangle(helpBounds, touchPoint)) {
					Assets.playSound(Assets.clickSound);
					Log.d("MainMenuScreen:update",
							"helpBounds is touched. Opening HelpScreen");
					//game.setScreen(new HelpScreen(game));
					Intent it = new Intent(glGame,HelpActivity.class);
					glGame.startActivity(it);
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();

		gl.glEnable(GL10.GL_TEXTURE_2D);

		batcher.beginBatch(Assets.atlasUIElements);

		//batcher.beginBatch(Assets.atlasBackgroundUI);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, RESOLUTION_X,
				RESOLUTION_Y, Assets.backgroundUIRegion);
		//batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y - 190, 1080, 300,
				Assets.mainMenuLogo);

		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400, 512, 256,
				Assets.mainMenuTextPlay);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400, 256, 256,
				Assets.mainMenuButtonPlay);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 256, 512, 256,
				Assets.mainMenuTextControl);
		switch (Settings.controlType) {
		case Settings.CONTROL_BY_TOUCH:
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 256, 256, 256,
					Assets.mainMenuButtonControlTouch);
			break;
		case Settings.CONTROL_BY_SWIPE:
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 256, 256, 256,
					Assets.mainMenuButtonControlSwipe);
			break;
		case Settings.CONTROL_BY_TILT:
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 256, 256, 256,
					Assets.mainMenuButtonControlTilt);
			break;
		}
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 512, 512, 256,
				Assets.mainMenuTextSound);
		if (Settings.soundEnabled) {
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 512, 256, 256,
					Assets.mainMenuButtonSoundEnabled);
		} else {
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 512, 256, 256,
					Assets.mainMenuButtonSoundDisabled);
		}
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 768, 512, 256,
				Assets.mainMenuTextHighscores);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 768, 256, 256,
				Assets.mainMenuButtonScore);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 1024, 512, 256,
				Assets.mainMenuTextHelp);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 1024, 256, 256,
				Assets.mainMenuButtonHelp);

		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
	}

	
	@Override
	public void pause() {
		// Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
