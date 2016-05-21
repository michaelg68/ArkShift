package com.mmango.arkshift;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
//import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class HighscoreScreen extends GLScreen {
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle homeBounds;
	Rectangle resetBounds;
	Rectangle yesBounds;
	Rectangle noBounds;
	Vector2 touchPoint;
	String[] highScores;
	float xOffset = 0;
	static final int SHOWING_HIGSHSCORE = 0;
	static final int ASKING_TO_RESET = 1;
	static int state = SHOWING_HIGSHSCORE;

	public HighscoreScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
		// backBounds = new Rectangle(0, 0, RESOLUTION_X, RESOLUTION_Y);
		// homeBounds = new Rectangle(RESOLUTION_X / 2 - 128, 150 - 128, 200,
		// 200);
		homeBounds = new Rectangle(RESOLUTION_X / 2 - 256 - 128, 150 - 128,
				768, 256);
		resetBounds = new Rectangle(RESOLUTION_X / 2 - 256 - 128, 150 + 128,
				768, 256);
		noBounds = new Rectangle(RESOLUTION_X / 2 - 256 - 128, 150 - 128, 768,
				256);
		yesBounds = new Rectangle(RESOLUTION_X / 2 - 256 - 128, 150 + 128, 768,
				256);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 200);
		highScores = new String[5];
		readHighScore();
		xOffset = Assets.scoreFont.glyphWidth * 7;
		// xOffset = RESOLUTION_X / 2 - xOffset / 2;
		xOffset = RESOLUTION_X / 2 - xOffset / 2 + Assets.scoreFont.glyphWidth / 2;
	}

	@Override
	public void update(float deltaTime) {
		switch (state) {
		case SHOWING_HIGSHSCORE:
			updateShowing(deltaTime);
			break;
		case ASKING_TO_RESET:
			updateAsking(deltaTime);
			break;
		}
	}

	public void updateShowing(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (event.type == TouchEvent.TOUCH_UP) {
				if (OverlapTester.pointInRectangle(homeBounds, touchPoint)) {
					game.setScreen(new MainMenuScreen(game));
					return;
				} else if (OverlapTester.pointInRectangle(resetBounds,
						touchPoint)) {
					state = ASKING_TO_RESET;
					return;
				} else {
					continue;
				}
			}
		}
	}

	public void updateAsking(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (event.type == TouchEvent.TOUCH_UP) {
				if (OverlapTester.pointInRectangle(yesBounds, touchPoint)) {
					//Log.d("HighscoreScreen:updateAsking", "YES pressed");
					Settings.resetHighscores(glGame);
					readHighScore();
					state = SHOWING_HIGSHSCORE;
					return;
				} else if (OverlapTester.pointInRectangle(noBounds, touchPoint)) {
					//Log.d("HighscoreScreen:updateAsking", "NO pressed");
					state = SHOWING_HIGSHSCORE;
					return;
				} else {
					continue;
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

		switch (state) {
		case SHOWING_HIGSHSCORE:
			presentShowingHighscore(deltaTime);
			break;
		case ASKING_TO_RESET:
			presentAskReset(deltaTime);
			break;
		}
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentShowingHighscore(float deltaTime) {
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y - 190, 1080, 300,
				Assets.mainMenuLogo);

		float y = RESOLUTION_Y / 2 - 200;

		for (int i = 4; i >= 0; i--) {
			// Log.d("HighscoreScreen:present","highScores[" + i + "] = " + i);
			Assets.scoreFont.drawScoreZoomed(batcher, highScores[i], xOffset,
					y, 1f, 1f);
			y += Assets.scoreFont.glyphHeight + 20;
		}
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 150 + 256, 256, 256,
				Assets.mainMenuButtonReset);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 150 + 256, 512, 256,
				Assets.mainMenuTextReset);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 150, 256, 256,
				Assets.mainMenuButtonHome);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 150, 512, 256,
				Assets.mainMenuTextQuit);
	}

	private void presentAskReset(float deltaTime) {
		//batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, 1080, 1920,
				//Assets.alphaOverGameField_60Opacity);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2 + 200, 1200, 600,
				Assets.resetHighscoresMessage);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 150 + 256, 256, 256,
				Assets.mainMenuButtonYes);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 150 + 256, 512, 256,
				Assets.mainMenuTextYes);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 150, 256, 256,
				Assets.mainMenuButtonNo);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 150, 512, 256,
				Assets.mainMenuTextNo);
	}

	@Override
	public void resume() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
	}

	public void readHighScore() {
		for (int i = 0; i < 5; i++) {
			//Log.d("HighScoreScreen:readHighScore", "Settings.highscores[" + i + "] = " + Settings.highscores[i]);
			// highScores[i] = (i + 1) + ".." + "" + Settings.highscores[i];
			int numbOfDots = 6 - (Integer.toString(Settings.highscores[i]))
					.length();
			// Log.d("HighScoreScreen:readHighScore", "numbOfDots = " +
			// numbOfDots);

			highScores[i] = Integer.toString(i + 1);
			for (int j = 0; j < numbOfDots; j++) {
				highScores[i] = highScores[i].concat(".");
			}
			highScores[i] = highScores[i].concat(Integer.toString(Settings.highscores[i]));
		}
	}

}
