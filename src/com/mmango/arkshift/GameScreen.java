package com.mmango.arkshift;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.FPSCounter;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
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
	int level;
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	static final int SPRITES_NUMBER = 200;
	static final int BUTTON_PAUSE_SIDE = 128;
	static final int NOTIFICATION_AREA_HEIGHT = 150;
	static final int NOTIFICATION_AREA_WIDTH = RESOLUTION_X;
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
	Rectangle controlBounds;
	Rectangle soundBounds;
	Rectangle quitBounds;
	Rectangle moveRacquetLeftTouchZone;
	Rectangle moveRacquetRightTouchZone;
	int lastScore;
	String scoreString;
	FPSCounter fpsCounter;
	float lastX = -1;
	float lastY = -1;
	TextureRegion ballsLeftRegion;

	public GameScreen(Game game, int level) {
		super(game);
		this.level = level;
		guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, SPRITES_NUMBER);
		ballsLeftRegion = Assets.ballsLeft1;
		worldListener = new WorldListener() {

			public void hitAtRacquet() {
				Assets.playSound(Assets.racquetHitSound);
			}

			public void hitAtBrick() {
				Assets.playSound(Assets.brickHitSound);
			}

			// public void hitAtBrickFloor() {
			// //Assets.playSound(Assets.knockSound);
			// }

			public void hitAtFrame() {
				Assets.playSound(Assets.frameHitSound);
			}

			public void shiftBrick() {
				// Assets.playSound(Assets.shiftSound);
			}

			public void levelBegins() {
				//Assets.playSound(Assets.levelStartsSound);
			}

			public void levelPassed() {
				//Assets.playSound(Assets.levelPassedSound);
			}

			public void gameOver() {
				//Assets.playSound(Assets.gameOverSound);
			}
		};

		world = new World(worldListener, level);
		renderer = new WorldRenderer(glGraphics, batcher, world);
		// remember - in Rectange x and y coordinates point to the lowerLeft
		// corner of the rectangle! Counting from the lower left corner of the
		// screen!
		pauseBounds = new Rectangle(RESOLUTION_X - 23 - 128,
				RESOLUTION_Y - 75 - 64, BUTTON_PAUSE_SIDE, BUTTON_PAUSE_SIDE);
		resumeBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256, 1400 - 128, 768,
				256);
		controlBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256, 1400 - 128 - 256,
				768, 256);
		soundBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256,
				1400 - 128 - 256 * 2, 768, 256);
		quitBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256,
				1400 - 128 - 256 * 3, 768, 256);

		touchPoint = new Vector2();

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
		
		switch (world.ballsLeft - 1) {
		case 0:
			ballsLeftRegion = Assets.ballsLeft0;
			break;
		case 1:
			ballsLeftRegion = Assets.ballsLeft1;
			break;
		case 2:
			ballsLeftRegion = Assets.ballsLeft2;
			break;
		case 3:
			ballsLeftRegion = Assets.ballsLeft3;
			break;
		case 4:
			ballsLeftRegion = Assets.ballsLeft4;
			break;
		case 5:
			ballsLeftRegion = Assets.ballsLeft5;
			break;
		case 6:
			ballsLeftRegion = Assets.ballsLeft6;
			break;
		case 7:
			ballsLeftRegion = Assets.ballsLeft7;
			break;
		case 8:
			ballsLeftRegion = Assets.ballsLeft8;
			break;
		case 9:
			ballsLeftRegion = Assets.ballsLeft9;
			break;
		default:
			ballsLeftRegion = Assets.ballsLeft0;
			break;
		}

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
			// Log.d("GameScreen", "case GAME_PAUSED");
			updatePaused();
			break;
		case GAME_LEVEL_END:
			// Log.d("GameScreen", "case GAME_LEVEL_END");
			updateLevelEnd();
			break;
		case GAME_OVER:
			// Log.d("GameScreen", "case GAME_OVER");
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
		// Log.d("GameScreen:updateRunning", "lastScore = " + lastScore);
		if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
			state = GAME_LEVEL_END;
			world.score = lastScore;
			Settings.addScore(lastScore);
			if (world.level < 8) { //there is no 9th level! The app will crash if I try to set Settings.levelEnabled[8]!
				Settings.levelEnabled[world.level] = true; //the current level is passed. Enable the next one.
			}
			Settings.savePrefs(glGame);			
		}

		if (world.state == World.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;
		}

	}

	private float calculateInputAcceleration() {
		// Log.d("GameScreen:", "inside method calculateInputAcceleration");

		float accelX = 0;

		switch (Settings.controlType) {
		case Settings.CONTROL_BY_TOUCH:
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
						accelX = -Racquet.RACQUET_VELOCITY * 1.4f;
						// Log.d("GameScreen: TouchLeft", "accelX = " +
						// Float.toString(accelX));

					}
					if (OverlapTester.pointInRectangle(
							moveRacquetRightTouchZone, touchPoint)) {
						// Log.d("GameScreen:",
						// "touched in moveRacquetLeftTouchZone");
						accelX = Racquet.RACQUET_VELOCITY * 1.4f;
						// Log.d("GameScreen: TouchRight",
						// "accelX = " + Float.toString(accelX));

					}
				}
			}
			break;
		case Settings.CONTROL_BY_SWIPE:
			// Log.d("GameScreen:", "Reading the swipe data");
			game.getInput().getTouchEvents();
			float x = game.getInput().getTouchX(0);
			float y = game.getInput().getTouchY(0);
			guiCam.touchToWorld(touchPoint.set(x, y));
			if (game.getInput().isTouchDown(0)) {
				/*
				 * Log.d("GameScreen:calculateInputAcceleration",
				 * "touchPoint.x = " + Float.toString(touchPoint.x));
				 * Log.d("GameScreen:calculateInputAcceleration",
				 * "touchPoint.y = " + Float.toString(touchPoint.y));
				 */
				if (lastX == -1) {
					lastX = x;
					// lastY = y;
				} else {
					accelX = Racquet.RACQUET_VELOCITY * 2
							* Math.signum(x - lastX);
					// accelX = world.racquet.RACQUET_VELOCITY / 8 * (x -
					// lastX);
					lastX = x;
					// lastY = y;
				}
			} else {
				lastX = -1;
				lastY = -1;
			}
			break;
		case Settings.CONTROL_BY_TILT:
			// Settings.CONTROL_BY_TILT
			// Log.d("GameScreen:", "Reading the Accelerometer data");
			accelX = -game.getInput().getAccelX() * 4.6f;
			// Log.d("GameScreen: Accelerometer",
			// "accelX = " + Float.toString(accelX));
			break;
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

			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}

			if (OverlapTester.pointInRectangle(controlBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Log.d("GameScreen:updatePaused",
						"controlBounds is touched. Changing the controlType");
				Settings.controlType++;
				if (Settings.controlType > 2)
					Settings.controlType = 0;
				Settings.savePrefs(glGame);
				return;
			}

			if (OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				Log.d("GameScreen:updatePaused",
						"soundBounds is touched. Enabling/Disabling sound");
				Settings.soundEnabled = (Settings.soundEnabled) ? false : true;
				Settings.savePrefs(glGame);
				Log.d("MainMenuScreen:update", "Assets.soundEnabled = "
						+ Boolean.toString(Settings.soundEnabled));
				return;
			}

			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				// Settings.save(game.getFileIO());
				Log.d("GameScreen:updatePaused",
						"Running Settings.savePrefs(glGame)");
				Settings.savePrefs(glGame);
				// Log.d("GameScreen:updatePaused",
				// "Running Settings.readPrefs(glGame)");
				// Settings.readPrefs(glGame);
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
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new SelectLevelScreen(game));
				return;
			}
			

		}
	}

	private void updateGameOver() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				// Settings.save(game.getFileIO());
				Log.d("GameScreen:updateGameOver",
						"Exiting from GameScreen after GameOver");
				//Assets.playSound(Assets.clickSound);
				world.score = lastScore;
				Settings.addScore(lastScore);
				Settings.savePrefs(glGame);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
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
		batcher.beginBatch(Assets.UIGameElements);
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
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, Assets.ballsSymbol);
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, ballsLeftRegion);
		//batcher.drawSprite(5 + 64 + 64  + 128, RESOLUTION_Y - 75f, 220, 128, Assets.levelSymbol);
		//batcher.drawSprite(5 + 64 + 64 + 5 + 128 + 64, RESOLUTION_Y - 75f, 128, 128, Assets.ballsLeft3);
		Assets.fontBebasneue64x64White.drawTextZoomed(batcher, "L" + Integer.toString(world.level), 5 + 64 + 64 + 64, RESOLUTION_Y - 75f + 3, 1.8f, 2.45f);
		//Assets.font.drawTextZoomed(batcher, Integer.toString(world.level), 100, RESOLUTION_Y - 75f, 0.5f, 0.5f);
		float scoreStringHalfLength = scoreString.length() * 128 / 2f;
		Assets.scoreFont.drawScoreZoomed(batcher, scoreString, RESOLUTION_X
				/ 2f - scoreStringHalfLength + 64, RESOLUTION_Y - 75f, 1f, 1f);
		batcher.drawSprite(RESOLUTION_X / 2, 1730 / 2 + 20, 1040, 1730,
				Assets.alphaOverGameField_60Opacity);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, 454, 142,
				Assets.readyMessage);
	}

	private void presentRunning() {
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, Assets.ballsSymbol);
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, ballsLeftRegion);
		Assets.fontBebasneue64x64White.drawTextZoomed(batcher, "L" + Integer.toString(world.level), 5 + 64 + 64 + 64, RESOLUTION_Y - 75f + 3, 1.8f, 2.45f);
		batcher.drawSprite(RESOLUTION_X - 5 - 128 / 2, RESOLUTION_Y - 75, 128,
				128, Assets.pauseButton);
		// Log.d("GameScreen:presentRunning", "scoreString.length() = " +
		// scoreString.length());
		float scoreStringHalfLength = scoreString.length() * 128 / 2f;
		// Log.d("GameScreen:presentRunning", "scoreStringHalfLength = " +
		// scoreStringHalfLength);
		Assets.scoreFont.drawScoreZoomed(batcher, scoreString, RESOLUTION_X
				/ 2f - scoreStringHalfLength + 64, RESOLUTION_Y - 75f, 1f, 1f);
	}

	private void presentPaused() {
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, Assets.ballsSymbol);
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, ballsLeftRegion);
		Assets.fontBebasneue64x64White.drawTextZoomed(batcher, "L" + Integer.toString(world.level), 5 + 64 + 64 + 64, RESOLUTION_Y - 75f + 3, 1.8f, 2.45f);
		float scoreStringHalfLength = scoreString.length() * 128 / 2f;
		Assets.scoreFont.drawScoreZoomed(batcher, scoreString, RESOLUTION_X
				/ 2f - scoreStringHalfLength + 64, RESOLUTION_Y - 75f, 1f, 1f);
		batcher.drawSprite(RESOLUTION_X / 2, 1730 / 2 + 20, 1040, 1730,
				Assets.alphaOverGameField_60Opacity);

		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400, 512, 256,
				Assets.mainMenuTextResume);
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
				Assets.mainMenuTextQuit);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 768, 256, 256,
				Assets.mainMenuButtonHome);


	}

	private void presentLevelEnd() {
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, Assets.ballsSymbol);
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, ballsLeftRegion);
		Assets.fontBebasneue64x64White.drawTextZoomed(batcher, "L" + Integer.toString(world.level), 5 + 64 + 64 + 64, RESOLUTION_Y - 75f + 3, 1.8f, 2.45f);
		float scoreStringHalfLength = scoreString.length() * 128 / 2f;
		Assets.scoreFont.drawScoreZoomed(batcher, scoreString, RESOLUTION_X
				/ 2f - scoreStringHalfLength + 64, RESOLUTION_Y - 75f, 1f, 1f);
		batcher.drawSprite(RESOLUTION_X / 2, 1730 / 2 + 20, 1040, 1730,
				Assets.alphaOverGameField_60Opacity);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2 + 200, 481, 382,
				Assets.levelPassedMessage);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 768, 512, 256,
				Assets.mainMenuTextQuit);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 768, 256, 256,
				Assets.mainMenuButtonHome);
	}

	private void presentGameOver() {
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, Assets.ballsSymbol);
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128, ballsLeftRegion);
		Assets.fontBebasneue64x64White.drawTextZoomed(batcher, "L" + Integer.toString(world.level), 5 + 64 + 64 + 64, RESOLUTION_Y - 75f + 3, 1.8f, 2.45f);
		float scoreStringHalfLength = scoreString.length() * 128 / 2f;
		Assets.scoreFont.drawScoreZoomed(batcher, scoreString, RESOLUTION_X
				/ 2f - scoreStringHalfLength + 64, RESOLUTION_Y - 75f, 1f, 1f);
		batcher.drawSprite(RESOLUTION_X / 2, 1730 / 2 + 20, 1040, 1730,
				Assets.alphaOverGameField_60Opacity);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2 + 200, 676, 144,
				Assets.gameOverMessage);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 768, 512, 256,
				Assets.mainMenuTextQuit);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 768, 256, 256,
				Assets.mainMenuButtonHome);

	}

	@Override
	public void resume() {
		// if (state == GAME_RUNNING)
		// state = GAME_PAUSED;
	}

	@Override
	public void pause() {
		world.score = lastScore;
		Settings.addScore(lastScore);
		Settings.savePrefs(glGame);
	}

	@Override
	public void dispose() {
		world.bricks.clear();
		world.bricksCeiling.clear();

	}

}
