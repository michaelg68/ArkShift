package com.mmango.arkshift;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
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
		//backBounds = new Rectangle(0, 0, RESOLUTION_X, RESOLUTION_Y);
		//homeBounds = new Rectangle(RESOLUTION_X / 2 - 128, 150 - 128, 200, 200);
		homeBounds = new Rectangle(RESOLUTION_X / 2 - 256 - 128, 150 - 128, 768, 256);
		resetBounds = new Rectangle(RESOLUTION_X / 2 - 256 - 128, 150 + 128, 768, 256);
		yesBounds = new Rectangle(RESOLUTION_X / 2  - 256 - 128, RESOLUTION_Y - 128, 768, 256);
		noBounds = new Rectangle(RESOLUTION_X / 2  - 256 - 128, RESOLUTION_Y - 128 - 256, 768, 256);
		
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 200);
		highScores = new String[5];
		for (int i = 0; i < 5; i++) {
			Log.d("HighScoreScreen:cunstructor", "Settings.highscores[" + i	+ "] = " + Settings.highscores[i]);
			//highScores[i] = (i + 1) + ".." + "" + Settings.highscores[i];
			int numbOfDots = 6 - (Integer.toString(Settings.highscores[i])).length();
			//Log.d("HighScoreScreen:cunstructor", "numbOfDots = " + numbOfDots);

			highScores[i] = Integer.toString(i + 1);
			for (int j = 0; j < numbOfDots; j++) {
				//Log.d("HighScoreScreen:cunstructor", "adding dot symbol");
				highScores[i] = highScores[i].concat(".");
			}
			highScores[i] = highScores[i].concat(Integer.toString(Settings.highscores[i]));
			//xOffset = Math.max(highScores[i].length() * Assets.scoreFont.glyphWidth, xOffset);
			xOffset = Assets.scoreFont.glyphWidth * 7;
			
			Log.d("HighScoreScreen:cunstructor", "string highScores[" + i + "] = " + highScores[i]);

		}
		//xOffset = RESOLUTION_X / 2 - xOffset / 2;
		xOffset = RESOLUTION_X / 2 - xOffset / 2 + Assets.scoreFont.glyphWidth /2;
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
				} else if (OverlapTester.pointInRectangle(resetBounds, touchPoint)) {
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
					Log.d("HighscoreScreen:updateAsking","YES pressed");
					state = SHOWING_HIGSHSCORE;
					return;
				} else if (OverlapTester.pointInRectangle(noBounds, touchPoint)) {
					Log.d("HighscoreScreen:updateAsking","NO pressed");
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

		batcher.beginBatch(Assets.mainScreenBackground);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, RESOLUTION_X,
				RESOLUTION_Y, Assets.mainScreenBackgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.mainScreenUIElements);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y - 190, 1080, 300,
				Assets.mainMenuLogo);
//		batcher.endBatch();
//
//		batcher.beginBatch(Assets.mainScreenUIElements);
		// batcher.drawSprite(160, 360, 300, 33, Assets.highScoresRegion);

		float y = RESOLUTION_Y / 2 - 200;


		for (int i = 4; i >= 0; i--) {
			//Log.d("HighscoreScreen:present","highScores[" + i + "] = " + i);
			Assets.scoreFont.drawScoreZoomed(batcher, highScores[i], xOffset, y, 1f, 1f);
			y += Assets.scoreFont.glyphHeight + 20;
		}
		
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 150 + 256, 256, 256, Assets.mainMenuButtonReset);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 150 + 256, 512, 256, Assets.mainMenuTextReset);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 150, 256, 256, Assets.mainMenuButtonHome);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 150, 512, 256, Assets.mainMenuTextQuit);


		batcher.endBatch();

		gl.glDisable(GL10.GL_BLEND);
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
}
