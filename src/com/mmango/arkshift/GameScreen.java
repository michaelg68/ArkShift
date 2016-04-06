package com.mmango.arkshift;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.mmango.arkshift.Assets;
import com.mmango.arkshift.MainMenuScreen;
import com.mmango.arkshift.Settings;
import com.mmango.arkshift.World;
import com.mmango.arkshift.WorldRenderer;
import com.mmango.arkshift.World.WorldListener;

public class GameScreen extends GLScreen {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle moveRacquetLeftTouchZone;
	Rectangle moveRacquetRightTouchZone;
	int lastScore;
	String scoreString;

	public GameScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 1080, 1920);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 300);
		worldListener = new WorldListener() {

			public void hitAtRacquet() {
				Assets.playSound(Assets.knockSound);
			}

			public void hitAtBrick() {
				Assets.playSound(Assets.knockSound);
			}

			public void hitAtBrickFloor() {
				Assets.playSound(Assets.knockSound);
			}

			public void hitAtFrame() {
				Assets.playSound(Assets.knockSound);
			}

			public void shiftBrick() {
				Assets.playSound(Assets.shiftSound);
			}
			
			public void levelPassed() {
				Assets.playSound(Assets.levelPassedSound);
			}
			
			public void gameOver() {
				Assets.playSound(Assets.gameOverLongSound);
			}
		};

		world = new World(worldListener);
		renderer = new WorldRenderer(glGraphics, batcher, world);
		//remember - in Rectange x and y coordinates point to the lowerLeft corner of the rectangle! Counting from the lower left corner of the screen!
		pauseBounds = new Rectangle(1080 - 123, 1920 - 122, 100, 100);
		
		resumeBounds = new Rectangle(1080 / 2 - 350, 1920 / 2, 700, 250);
		quitBounds = new Rectangle(1080 / 2 - 350, 1920 / 2 - 250, 700, 250);
		moveRacquetLeftTouchZone = new Rectangle(0, 0, 540, 1500);
		moveRacquetRightTouchZone = new Rectangle(540, 0, 540, 1500);
		lastScore = 0;
		scoreString = "score: 0";
	}

	@Override
	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			//Log.d("GameScreen", "case GAME_READY");
			updateReady();
			break;
		case GAME_RUNNING:
			//Log.d("GameScreen", "case GAME_RUNNING");
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			//Log.d("GameScreen", "case GAME_PAUSED");
			updatePaused();
			break;
		case GAME_LEVEL_END:
			//Log.d("GameScreen", "case GAME_LEVEL_END");
			updateLevelEnd();
			break;
		case GAME_OVER:
			//Log.d("GameScreen", "case GAME_OVER");
			updateGameOver();
			break;
		}
	}

	private void updateReady() {
		if (game.getInput().getTouchEvents().size() > 0)
			state = GAME_RUNNING;
	}

	private void updateRunning(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			//Log.d("GameScreen:", "event.x = " + Float.toString(event.x));
			//Log.d("GameScreen:", "event.y = " + Float.toString(event.y));			
			guiCam.touchToWorld(touchPoint);

			if (OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
				Log.d("GameScreen:", "touched in pauseBounds");
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}

		}

/*		world.update(deltaTime, game.getInput().getAccelX());
		if (world.score != lastScore)

		{
			lastScore = world.score;
			scoreString = "" + lastScore;
		}
		if (world.state == World.WORLD_STATE_NEXT_LEVEL)

		{
			state = GAME_LEVEL_END;
		}
		if (world.state == World.WORLD_STATE_GAME_OVER)

		{
			state = GAME_OVER;
			if (lastScore >= Settings.highscores[4])
				scoreString = "new highscore: " + lastScore;
			else
				scoreString = "score: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save(game.getFileIO());
		}*/

	}

	private void updatePaused() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;

			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}

			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}

		}
	}

	private void updateLevelEnd() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			world = new World(worldListener);
			renderer = new WorldRenderer(glGraphics, batcher, world);
			world.score = lastScore;
			state = GAME_READY;
		}
	}

	private void updateGameOver() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		renderer.render();

		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.userInterfaceElements);
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	private void presentReady() {
		batcher.drawSprite(1080 / 2, 1920 / 2, 700, 250, Assets.readyBannerRegion);
	}

	private void presentRunning() {
		//1080 - 123, 1920 - 22, 100, 100
		batcher.drawSprite(1080 - 73 , 1920 - 72, 100, 100, Assets.buttonPause);
		//Assets.font.drawTextZoomed(batcher, scoreString, 1080 / 2 , 1920/2, 12, 12);
		//Assets.font.drawText(batcher, "Score", 1080 / 2 - 200 , 1920/2);
		//Assets.font.drawTextZoomed(batcher, "score", 1080 / 2 - 200 , 1920/2, 2, 2);
	}

	private void presentPaused() {
		batcher.drawSprite(1080 / 2, 1920 / 2, 700, 500, Assets.resumeQuitMenuRegion);
		//Assets.font.drawText(batcher, scoreString, 16, 480 - 20);
	}

	private void presentLevelEnd() {
		String levelEndText = "Moving to the next level!";
		float levelEndTextWidth = Assets.font.glyphWidth * levelEndText.length();
		Assets.font.drawText(batcher, levelEndText, 540 - levelEndTextWidth / 2, 1080 - 500);
	}

	private void presentGameOver() {
		batcher.drawSprite(160, 240, 160, 96, Assets.gameOverBannerRegion);
		float scoreWidth = Assets.font.glyphWidth * scoreString.length();
		Assets.font.drawText(batcher, scoreString, 540 - scoreWidth / 2, 1080 - 200);
	}

	@Override
	public void resume() {
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
