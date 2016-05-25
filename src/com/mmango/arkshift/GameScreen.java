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
	int balls;
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
	static final int GAME_PREPARING = 0;
	static final int GAME_READY = 1;
	static final int GAME_RUNNING = 2;
	static final int GAME_PAUSED = 3;
	static final int GAME_LEVEL_END = 4;
	static final int GAME_OVER = 5;
	static final int SPRITES_NUMBER = 250;
	static final int BUTTON_PAUSE_SIDE = 128;
	static final int NOTIFICATION_AREA_HEIGHT = 150;
	static final int NOTIFICATION_AREA_WIDTH = RESOLUTION_X;
	static final int FRAME_WIDTH = 20;

	static int state;
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
		switch (level) {
		case 1:
			balls = 2;
			break;
		case 2:
			balls = 2;
			break;
		case 3:
			balls = 3;
			break;
		case 4:
			balls = 3;
			break;
		case 5:
			balls = 4;
			break;
		case 6:
			balls = 4;
			break;
		case 7:
			balls = 5;
			break;
		case 8:
			balls = 5;
			break;
		}
		guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, SPRITES_NUMBER);
		// ballsLeftRegion = AssetsGame.ballsLeft1;
		worldListener = new WorldListener() {

			public void hitAtRacquet() {
				AssetsGame.playSound(AssetsGame.racquetHitSound);
			}

			public void hitAtBrick() {
				AssetsGame.playSound(AssetsGame.brickHitSound);
			}

			// public void hitAtBrickFloor() {
			// //AssetsGame.playSound(AssetsGame.knockSound);
			// }

			public void hitAtFrame() {
				AssetsGame.playSound(AssetsGame.frameHitSound);
			}

			public void shiftBrick() {
				// AssetsGame.playSound(AssetsGame.shiftSound);
			}

			public void levelBegins() {
				AssetsGame.playSound(AssetsGame.levelStartsSound);
			}

			public void levelPassed() {
				AssetsGame.playSound(AssetsGame.levelPassedSound);
			}

			public void gameOver() {
				AssetsGame.playSound(AssetsGame.gameOverSound);
			}
		};

		world = new World(worldListener, level, balls);
		renderer = new WorldRenderer(glGraphics, batcher, world);
		// remember - in Rectange x and y coordinates point to the lowerLeft
		// corner of the rectangle! Counting from the lower left corner of the
		// screen!
		pauseBounds = new Rectangle(RESOLUTION_X - 23 - 128,
				RESOLUTION_Y - 75 - 64, BUTTON_PAUSE_SIDE, BUTTON_PAUSE_SIDE);
		resumeBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256, 1400 - 128,
				768, 256);
		controlBounds = new Rectangle(RESOLUTION_X / 2 - 128 - 256,
				1400 - 128 - 256, 768, 256);
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
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch (state) {
		case GAME_PREPARING:
			// Log.d("GameScreen:update", "case GAME_PREPARING.");
			updatePreparing(deltaTime);
			break;
		case GAME_READY:
			// Log.d("GameScreen:update", "case GAME_READY.");
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

	private void updatePreparing(float deltaTime) {
		world.preparing(deltaTime);
		if (world.state == World.WORLD_STATE_READY) {
			state = GAME_READY;
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
				//Log.d("GameScreen:", "touched in pauseBounds");
				// Log.d("GameScreen:", "pauseBounds.lowerLeft.x = " +
				// pauseBounds.lowerLeft.x);
				// Log.d("GameScreen:", "pauseBounds.lowerLeft.y = " +
				// pauseBounds.lowerLeft.y);
				// Log.d("GameScreen:", "pauseBounds.width = " +
				// pauseBounds.width);
				// Log.d("GameScreen:", "pauseBounds.height = " +
				// pauseBounds.height);
				AssetsGame.playSound(AssetsGame.clickSound);
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
			if (world.level < 8) { // there is no 9th level! The app will crash
									// if I try to set Settings.levelEnabled[8]!
				Settings.levelEnabled[world.level] = true; // the current level
															// is passed. Enable
															// the next one.
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
				AssetsGame.playSound(AssetsGame.clickSound);
				state = GAME_RUNNING;
				return;
			}

			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
				AssetsGame.playSound(AssetsGame.clickSound);
				state = GAME_RUNNING;
				return;
			}

			if (OverlapTester.pointInRectangle(controlBounds, touchPoint)) {
				AssetsGame.playSound(AssetsGame.clickSound);
				Log.d("GameScreen:updatePaused",
						"controlBounds is touched. Changing the controlType");
				Settings.controlType++;
				if (Settings.controlType > 2)
					Settings.controlType = 0;
				Settings.savePrefs(glGame);
				return;
			}

			if (OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
				AssetsGame.playSound(AssetsGame.clickSound);
				Log.d("GameScreen:updatePaused",
						"soundBounds is touched. Enabling/Disabling sound");
				Settings.soundEnabled = (Settings.soundEnabled) ? false : true;
				Settings.savePrefs(glGame);
				Log.d("MainMenuScreen:update", "AssetsGame.soundEnabled = "
						+ Boolean.toString(Settings.soundEnabled));
				return;
			}

			if (OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
				AssetsGame.playSound(AssetsGame.clickSound);
				// Settings.save(game.getFileIO());
				Log.d("GameScreen:updatePaused",
						"Running Settings.savePrefs(glGame)");
				Settings.savePrefs(glGame);
				// Log.d("GameScreen:updatePaused",
				// "Running Settings.readPrefs(glGame)");
				// Settings.readPrefs(glGame);
				// game.setScreen(new MainMenuScreen(game));

				// finish GameActivity
				state = GAME_PREPARING;
				glGame.finish();
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
				AssetsGame.playSound(AssetsGame.clickSound);
				// game.setScreen(new SelectLevelScreen(game));
				// finish GameActivity
				state = GAME_PREPARING;
				glGame.finish();
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
				AssetsGame.playSound(AssetsGame.clickSound);
				//Log.d("GameScreen:updateGameOver", "Exiting from GameScreen after GameOver");
				// AssetsGame.playSound(AssetsGame.clickSound);
				world.score = lastScore;
				Settings.addScore(lastScore);
				Settings.savePrefs(glGame);
				// game.setScreen(new MainMenuScreen(game));
				// finish GameActivity
				state = GAME_PREPARING;
				glGame.finish();
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
		batcher.beginBatch(AssetsGame.atlasGameElements);

		presentNotificationArea();

		switch (state) {
		case GAME_PREPARING:
			presentPreparing();
			break;
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

	private void presentNotificationArea() {
		batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128,
				AssetsGame.ballsSymbol);
		// batcher.drawSprite(5 + 64, RESOLUTION_Y - 75f, 128, 128,
		// ballsLeftRegion);
		String ballsLeftString = Integer.toString(world.ballsLeft - 1);
		float xCoordB = 0;
		switch (ballsLeftString.length()) {
		case 1:
			xCoordB = 5 + 64;
			break;
		case 2:
			xCoordB = 5 + 48;
			break;
		default:
			break;
		}
		AssetsGame.scoreBallsFont.drawScoreBallsZoomed(batcher,
				ballsLeftString, xCoordB, RESOLUTION_Y - 75f, 1f, 1f);

		AssetsGame.fontBebasneue64x64White.drawTextZoomed(batcher, "L"
				+ Integer.toString(world.level), 5 + 64 + 64 + 64,
				RESOLUTION_Y - 75f + 3, 1.8f, 2.45f);

		float scoreStringHalfLength = scoreString.length() * 128 / 2f;
		AssetsGame.scoreGameFont.drawScoreZoomed(batcher, scoreString,
				RESOLUTION_X / 2f - scoreStringHalfLength + 64,
				RESOLUTION_Y - 75f, 1f, 1f);
	}

	private void presentPreparing() {
		batcher.drawSprite(RESOLUTION_X - 5 - 128 / 2, RESOLUTION_Y - 75, 128,
				128, AssetsGame.pauseButton);
		//Log.d("GameScreen:presentPreparing", "in GameScreen:presentPreparing");
	}

	private void presentReady() {

		// AssetsGame.font.drawTextZoomed(batcher,
		// Integer.toString(world.level),
		// 100, RESOLUTION_Y - 75f, 0.5f, 0.5f);

		batcher.drawSprite(RESOLUTION_X / 2, 1730 / 2 + 20, 1040, 1730,
				AssetsGame.alphaOverGameField_60Opacity);
		// batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, 454, 142,
		// AssetsGame.readyMessage);
		String readyString = "READY?";
		float xZoom = 2f;
		float yZoom = 4f;
		float halfLen = readyString.length()
				* AssetsGame.fontBebasneue64x64White.glyphWidth / 2;
		// Log.d("GameScreen:presentReady", "halfLen = " + halfLen);
		float halfGlyph = AssetsGame.fontBebasneue64x64White.glyphWidth / 2;
		// Log.d("GameScreen:presentReady", "halfGlyph = " + halfGlyph);

		float xCoord = RESOLUTION_X / 2 - halfLen + halfGlyph;
		// Log.d("GameScreen:presentReady", "xCoord = " + xCoord);
		AssetsGame.fontBebasneue64x64White.drawTextZoomed(batcher, readyString,
				xCoord + halfGlyph, RESOLUTION_Y / 2, xZoom, yZoom);
	}

	private void presentRunning() {
		batcher.drawSprite(RESOLUTION_X - 5 - 128 / 2, RESOLUTION_Y - 75, 128,
				128, AssetsGame.pauseButton);
		// Log.d("GameScreen:presentRunning", "scoreString.length() = " +
		// scoreString.length());
	}

	private void presentPaused() {
		batcher.drawSprite(RESOLUTION_X / 2, 1730 / 2 + 20, 1040, 1730,
				AssetsGame.alphaOverGameField_60Opacity);

		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400, 512, 256,
				AssetsGame.mainMenuTextResume);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400, 256, 256,
				AssetsGame.mainMenuButtonPlay);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 256, 512, 256,
				AssetsGame.mainMenuTextControl);
		switch (Settings.controlType) {
		case Settings.CONTROL_BY_TOUCH:
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 256, 256, 256,
					AssetsGame.mainMenuButtonControlTouch);
			break;
		case Settings.CONTROL_BY_SWIPE:
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 256, 256, 256,
					AssetsGame.mainMenuButtonControlSwipe);
			break;
		case Settings.CONTROL_BY_TILT:
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 256, 256, 256,
					AssetsGame.mainMenuButtonControlTilt);
			break;
		}
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 512, 512, 256,
				AssetsGame.mainMenuTextSound);
		if (Settings.soundEnabled) {
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 512, 256, 256,
					AssetsGame.mainMenuButtonSoundEnabled);
		} else {
			batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 512, 256, 256,
					AssetsGame.mainMenuButtonSoundDisabled);
		}

		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 768, 512, 256,
				AssetsGame.mainMenuTextQuit);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 768, 256, 256,
				AssetsGame.mainMenuButtonHome);

	}

	private void presentLevelEnd() {
		batcher.drawSprite(RESOLUTION_X / 2, 1730 / 2 + 20, 1040, 1730,
				AssetsGame.alphaOverGameField_60Opacity);

		// batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2 + 200, 481,
		// 382, AssetsGame.levelPassedMessage);
		String readyString = "LEVEL PASSED!";
		float xZoom = 2f;
		float yZoom = 4f;
		float halfLen = readyString.length()
				* AssetsGame.fontBebasneue64x64White.glyphWidth / 2;
		// Log.d("GameScreen:presentReady", "halfLen = " + halfLen);
		float halfGlyph = AssetsGame.fontBebasneue64x64White.glyphWidth / 2;
		// Log.d("GameScreen:presentReady", "halfGlyph = " + halfGlyph);
		float xCoord = RESOLUTION_X / 2 - halfLen + halfGlyph;
		AssetsGame.fontBebasneue64x64White.drawTextZoomed(batcher, readyString,
				xCoord + halfGlyph, RESOLUTION_Y / 2 + 200, xZoom, yZoom);

		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 768, 512, 256,
				AssetsGame.mainMenuTextQuit);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 768, 256, 256,
				AssetsGame.mainMenuButtonHome);
	}

	private void presentGameOver() {
		batcher.drawSprite(RESOLUTION_X / 2, 1730 / 2 + 20, 1040, 1730,
				AssetsGame.alphaOverGameField_60Opacity);

		// batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2 + 200, 676,
		// 144, AssetsGame.gameOverMessage);
		String readyString = "GAME OVER";
		float xZoom = 1.9f;
		float yZoom = 3.8f;
		float halfLen = readyString.length()
				* AssetsGame.fontBebasneue64x64White.glyphWidth / 2;
		// Log.d("GameScreen:presentReady", "halfLen = " + halfLen);
		float halfGlyph = AssetsGame.fontBebasneue64x64White.glyphWidth / 2;
		// Log.d("GameScreen:presentReady", "halfGlyph = " + halfGlyph);
		float xCoord = RESOLUTION_X / 2 - halfLen + halfGlyph;
		AssetsGame.fontBebasneue64x64White.drawTextZoomed(batcher, readyString,
				xCoord + halfGlyph, RESOLUTION_Y / 2 + 200, xZoom, yZoom);

		batcher.drawSprite(RESOLUTION_X / 2 + 128, 1400 - 768, 512, 256,
				AssetsGame.mainMenuTextQuit);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 1400 - 768, 256, 256,
				AssetsGame.mainMenuButtonHome);

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
		// world.bricks.clear();
		// world.bricksCeiling.clear();
	}

}
