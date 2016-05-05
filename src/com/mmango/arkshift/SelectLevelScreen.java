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
	Rectangle buttonHomeBounds;

	public SelectLevelScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
		level1ButtonBounds = new Rectangle(270 - 128, 1400 - 128, 256, 2056);
		level2ButtonBounds = new Rectangle(810 - 128, 1400 - 128, 256, 256);
		level3ButtonBounds = new Rectangle(270 - 128, 1100 - 128, 256, 256);
		level4ButtonBounds = new Rectangle(810 - 128, 1100 - 128, 256, 256);
		level5ButtonBounds = new Rectangle(270 - 128, 800 - 128, 256, 256);
		level6ButtonBounds = new Rectangle(810 - 128, 800 - 128, 256, 256);
		level7ButtonBounds = new Rectangle(270 - 128, 500 - 128, 256, 256);
		level8ButtonBounds = new Rectangle(810 - 128, 500 - 128, 256, 256);
		buttonHomeBounds = new Rectangle(RESOLUTION_X / 2 - 128, 150 - 128, 200, 200);

		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 40);

	}

	@Override
	public void update(float deltaTime) {
		int level = 9999;
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
						buttonHomeBounds, touchPoint)) {
					level = 0;
				}

				if (level == 0) {
					game.setScreen(new MainMenuScreen(game));
					return;
				} else if ((level >= 1 ) && (level <= 8 )) {
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

		batcher.beginBatch(Assets.mainScreenBackground);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, RESOLUTION_X,
				RESOLUTION_Y, Assets.mainScreenBackgroundRegion);
		batcher.endBatch();

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		
		batcher.beginBatch(Assets.mainScreenUIElements);
		batcher.drawSprite(RESOLUTION_X / 2, 1700, 796, 185, Assets.selectLevelMessage);
		
		batcher.drawSprite(270, 1400, 256, 256, Assets.buttonLevel1);
		batcher.drawSprite(810, 1400, 256, 256, Assets.buttonLevel2);
		batcher.drawSprite(270, 1100, 256, 256, Assets.buttonLevel3);
		batcher.drawSprite(810, 1100, 256, 256, Assets.buttonLevel4);
		batcher.drawSprite(270, 800, 256, 256, Assets.buttonLevel5);
		batcher.drawSprite(810, 800, 256, 256, Assets.buttonLevel6);
		batcher.drawSprite(270, 500, 256, 256, Assets.buttonLevel7);
		batcher.drawSprite(810, 500, 256, 256, Assets.buttonLevel8);
		batcher.drawSprite(810, 500, 256, 256, Assets.buttonUnavailable);
		batcher.drawSprite(RESOLUTION_X / 2, 150, 256, 256, Assets.mainMenuButtonHome);
		//batcher.drawSprite(156 + 256 + 256, 150, 512, 256, Assets.mainMenuTextQuit);
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
