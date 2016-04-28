package com.mmango.arkshift;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class SelectLevelScreen extends GLScreen {
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle backBounds;
	Vector2 touchPoint;
	String[] highScores;
	Rectangle level1ButtonBounds;
	Rectangle level2ButtonBounds;
	Rectangle level3ButtonBounds;
	Rectangle level4ButtonBounds;
	Rectangle level5ButtonBounds;
	Rectangle level6ButtonBounds;
	Rectangle level7ButtonBounds;
	Rectangle level8ButtonBounds;
	// Rectangle buttonUnavailable;
	Rectangle buttonHomeGreenBounds;

	public SelectLevelScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
		level1ButtonBounds = new Rectangle(270 - 100, 1290 - 100, 200, 200);
		level2ButtonBounds = new Rectangle(810 - 100, 1290 - 100, 200, 200);
		level3ButtonBounds = new Rectangle(270 - 100, 990 - 100, 200, 200);
		level4ButtonBounds = new Rectangle(810 - 100, 990 - 100, 200, 200);
		level5ButtonBounds = new Rectangle(270 - 100, 690 - 100, 200, 200);
		level6ButtonBounds = new Rectangle(810 - 100, 690 - 100, 200, 200);
		level7ButtonBounds = new Rectangle(270 - 100, 390 - 100, 200, 200);
		level8ButtonBounds = new Rectangle(810 - 100, 390 - 100, 200, 200);
		// buttonUnavailable = new Rectangle(0, 0, RESOLUTION_X, RESOLUTION_Y);
		buttonHomeGreenBounds = new Rectangle(540 - 100, 150 - 100, 200, 200);

		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 20);

	}

	@Override
	public void update(float deltaTime) {
		int level = 0;
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);

			if (event.type == TouchEvent.TOUCH_UP) {
				if (OverlapTester.pointInRectangle(level1ButtonBounds,
						touchPoint)) {
					level = 1;
				} else if (OverlapTester.pointInRectangle(level2ButtonBounds,
						touchPoint)) {
					level = 2;
				} else if (OverlapTester.pointInRectangle(level3ButtonBounds,
						touchPoint)) {
					level = 3;
				} else if (OverlapTester.pointInRectangle(level4ButtonBounds,
						touchPoint)) {
					level = 4;
				} else if (OverlapTester.pointInRectangle(level5ButtonBounds,
						touchPoint)) {
					level = 5;
				} else if (OverlapTester.pointInRectangle(level6ButtonBounds,
						touchPoint)) {
					level = 6;
				} else if (OverlapTester.pointInRectangle(level7ButtonBounds,
						touchPoint)) {
					level = 7;
				} else if (OverlapTester.pointInRectangle(level8ButtonBounds,
						touchPoint)) {
					level = 8;
				} else if (OverlapTester.pointInRectangle(
						buttonHomeGreenBounds, touchPoint)) {
					level = 0;
				}

				if (level == 0) {
					game.setScreen(new MainMenuScreen(game));
					return;
				} else {
					game.setScreen(new GameScreen(game, level));
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

		batcher.beginBatch(Assets.selectLevelScreenElements);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, RESOLUTION_X,
				RESOLUTION_Y, Assets.selectLevelScreenBackgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		batcher.beginBatch(Assets.selectLevelScreenElements);
		batcher.drawSprite(270, 1290, 200, 200, Assets.level1Button);
		batcher.drawSprite(810, 1290, 200, 200, Assets.level2Button);
		batcher.drawSprite(270, 990, 200, 200, Assets.level3Button);
		batcher.drawSprite(810, 990, 200, 200, Assets.level4Button);
		batcher.drawSprite(270, 690, 200, 200, Assets.level5Button);
		batcher.drawSprite(810, 690, 200, 200, Assets.level6Button);
		batcher.drawSprite(270, 390, 200, 200, Assets.level7Button);
		batcher.drawSprite(810, 390, 200, 200, Assets.level8Button);
		batcher.drawSprite(810, 390, 200, 200, Assets.buttonUnavailable);
		batcher.drawSprite(540, 150, 200, 200, Assets.buttonHomeGreen);
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
