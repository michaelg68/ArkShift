package com.mmango.arkshift;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.FPSCounter;
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
import com.badlogic.androidgames.framework.gl.FPSCounter;

public class GameScreen extends GLScreen {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
	static final int SPRITES_NUMBER = 200;
	static final int BUTTON_PAUSE_SIDE = 100;
	static final int NOTIFICATION_AREA_HEIGHT = 150;
	static final int NOTIFICATION_AREA_WIDTH = 1080;
	static final int FRAME_WIDTH = 20;

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
	FPSCounter fpsCounter;

	public GameScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, SPRITES_NUMBER);
		worldListener = new WorldListener() {

			public void hitAtRacquet() {
				Assets.playSound(Assets.racquetHitSound);
			}

			public void hitAtBrick() {
				Assets.playSound(Assets.brickHitSound);
			}

//			public void hitAtBrickFloor() {
//				//Assets.playSound(Assets.knockSound);
//			}

			public void hitAtFrame() {
				Assets.playSound(Assets.frameHitSound);
			}

			public void shiftBrick() {
				//Assets.playSound(Assets.shiftSound);
			}

			public void levelBegins() {
				Assets.playSound(Assets.levelStartsSound);
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
		// remember - in Rectange x and y coordinates point to the lowerLeft
		// corner of the rectangle! Counting from the lower left corner of the
		// screen!
		pauseBounds = new Rectangle(RESOLUTION_X - 123, RESOLUTION_Y - 122,
				BUTTON_PAUSE_SIDE, BUTTON_PAUSE_SIDE);

		resumeBounds = new Rectangle(RESOLUTION_X / 2 - 350, RESOLUTION_Y / 2,
				700, 250);
		quitBounds = new Rectangle(RESOLUTION_X / 2 - 350,
				RESOLUTION_Y / 2 - 250, 700, 250);
		moveRacquetLeftTouchZone = new Rectangle(0, 0, RESOLUTION_X / 2,
				RESOLUTION_Y - NOTIFICATION_AREA_HEIGHT - FRAME_WIDTH);
		moveRacquetRightTouchZone = new Rectangle(RESOLUTION_X / 2, 0,
				RESOLUTION_X, RESOLUTION_Y - NOTIFICATION_AREA_HEIGHT
						- FRAME_WIDTH);
		lastScore = 0;
		scoreString = Integer.toString(lastScore);
		fpsCounter = new FPSCounter();
	}

	@Override
	public void update(float deltaTime) {
		// if (deltaTime > 0.1f)
		// deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			// Log.d("GameScreen", "case GAME_READY");
			updateReady();
			break;
		case GAME_RUNNING:
			// Log.d("GameScreen", "case GAME_RUNNING");
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			Log.d("GameScreen", "case GAME_PAUSED");
			Settings.addScore(lastScore);
			updatePaused();
			break;
		case GAME_LEVEL_END:
			// Log.d("GameScreen", "case GAME_LEVEL_END");
			Settings.addScore(lastScore);
			updateLevelEnd();
			break;
		case GAME_OVER:
			// Log.d("GameScreen", "case GAME_OVER");
			Settings.addScore(lastScore);
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
			// Log.d("GameScreen:", "event.x = " + Float.toString(event.x));
			// Log.d("GameScreen:", "event.y = " + Float.toString(event.y));
			guiCam.touchToWorld(touchPoint);

			if (OverlapTester.pointInRectangle(pauseBounds, touchPoint)) {
				Log.d("GameScreen:", "touched in pauseBounds");
				// Log.d("GameScreen:", "pauseBounds.lowerLeft.x = " +
				// pauseBounds.lowerLeft.x);
				// Log.d("GameScreen:", "pauseBounds.lowerLeft.y = " +
				// pauseBounds.lowerLeft.y);
				// Log.d("GameScreen:", "pauseBounds.width = " +
				// pauseBounds.width);
				// Log.d("GameScreen:", "pauseBounds.height = " +
				// pauseBounds.height);
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
		}
		world.update(deltaTime, calculateInputAcceleration());
		if (world.score != lastScore) {
			lastScore = world.score;
			scoreString = "" + lastScore;
		}
		//Log.d("GameScreen:updateRunning", "lastScore = " + lastScore);
		if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
			state = GAME_LEVEL_END;
		}

		
	}

	private float calculateInputAcceleration() {
		// Log.d("GameScreen:", "inside method calculateInputAcceleration");

		float accelX = 0;
		if (Settings.touchEnabled) {
			// Log.d("GameScreen:", "Reading the touch data");

			for (int i = 0; i < 2; i++) {
				// Log.d("GameScreen:calculateInputAcceleration",
				// "i = " + Integer.toString(i));
  
				if (game.getInput().isTouchDown(i)) {
					guiCam.touchToWorld(touchPoint.set(game.getInput()
							.getTouchX(i), game.getInput().getTouchY(i)));
					// Log.d("GameScreen:calculateInputAcceleration",
					// "touchPoint.x = " + Float.toString(touchPoint.x));
					// Log.d("GameScreen:calculateInputAcceleration",
					// "touchPoint.y = " + Float.toString(touchPoint.y));
					if (OverlapTester.pointInRectangle(
							moveRacquetLeftTouchZone, touchPoint)) {
						// Log.d("GameScreen:",
						// "touched in moveRacquetLeftTouchZone");
						accelX = -world.racquet.RACQUET_VELOCITY;
						// Log.d("GameScreen: TouchLeft", "accelX = " +
						// Float.toString(accelX));

					}
					if (OverlapTester.pointInRectangle(
							moveRacquetRightTouchZone, touchPoint)) {
						// Log.d("GameScreen:",
						// "touched in moveRacquetLeftTouchZone");
						accelX = world.racquet.RACQUET_VELOCITY;
						// Log.d("GameScreen: TouchRight",
						// "accelX = " + Float.toString(accelX));

					}
				}
			}
		} else {
			// Log.d("GameScreen:", "Reading the Accelerometer data");
			accelX = -game.getInput().getAccelX() * 1.5f;
			// Log.d("GameScreen: Accelerometer",
			// "accelX = " + Float.toString(accelX));
		}
		return accelX;
		
		
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
				Settings.save(game.getFileIO());
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
		fpsCounter.logFrame();

	}

	private void presentReady() {
		float scoreStringHalfLength = scoreString.length() * 64 / 2f;
		Assets.font.drawTextScore(batcher, scoreString, RESOLUTION_X / 2f
				- scoreStringHalfLength + 64, RESOLUTION_Y - 75f, 2f, 2f);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, 700, 250,
				Assets.readyBannerRegion);
	}

	private void presentRunning() {
		// 1080 - 123, 1920 - 22, 100, 100
		batcher.drawSprite(RESOLUTION_X - 23 - BUTTON_PAUSE_SIDE / 2,
				RESOLUTION_Y - 22 - BUTTON_PAUSE_SIDE / 2, BUTTON_PAUSE_SIDE,
				BUTTON_PAUSE_SIDE, Assets.buttonPause);
		// Log.d("GameScreen:presentRunning", "scoreString.length() = " +
		// scoreString.length());
		float scoreStringHalfLength = scoreString.length() * 64 / 2f;
		// Log.d("GameScreen:presentRunning", "scoreStringHalfLength = " +
		// scoreStringHalfLength);
		Assets.font.drawTextScore(batcher, scoreString, RESOLUTION_X / 2f
				- scoreStringHalfLength + 64, RESOLUTION_Y - 75f, 2f, 2f);

	}

	private void presentPaused() {
		float scoreStringHalfLength = scoreString.length() * 64 / 2f;
		Assets.font.drawTextScore(batcher, scoreString, RESOLUTION_X / 2f
				- scoreStringHalfLength + 64, RESOLUTION_Y - 75f, 2f, 2f);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, 700, 500,
				Assets.resumeQuitMenuRegion);
		// Assets.font.drawText(batcher, scoreString, 16, 480 - 20);
	}

	private void presentLevelEnd() {
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, 700, 500,
				Assets.levelUpRegion);
	}

	private void presentGameOver() {
		batcher.drawSprite(160, 240, 160, 96, Assets.gameOverBannerRegion);
		float scoreWidth = Assets.font.glyphWidth * scoreString.length();
		Assets.font.drawText(batcher, scoreString, RESOLUTION_X / 2
				- scoreWidth / 2, RESOLUTION_Y - 200);
	}

	@Override
	public void resume() {
		// if (state == GAME_RUNNING)
		// state = GAME_PAUSED;
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
		world.bricks.clear();
		world.bricksCeiling.clear();

	}

}
