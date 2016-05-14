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

public class HelpScreen extends GLScreen {
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle homeBounds;
	Vector2 touchPoint;
	String[] highScores;
	
	static final int SHOWING_SCREEN1 = 0;
	static final int SHOWING_SCREEN2 = 1;
	//int SHOWING_SCREEN3 = 3;
	int state;

	public HelpScreen(Game game) {
		super(game);

		guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
		// backBounds = new Rectangle(0, 0, RESOLUTION_X, RESOLUTION_Y);
		// homeBounds = new Rectangle(RESOLUTION_X / 2 - 128, 150 - 128, 200,
		// 200);
		homeBounds = new Rectangle(RESOLUTION_X / 2 - 256 - 128, 150 - 128,
				768, 256);
		//nextScreenBounds = new Rectangle(0, 128, RESOLUTION_X, RESOLUTION_Y - 128);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 30);
		state = SHOWING_SCREEN1;
	}

	
	
	@Override
	public void update(float deltaTime) {
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
				} else  {
					state++;
					if (state > SHOWING_SCREEN2)
						state = SHOWING_SCREEN1;
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

		switch (state) {
		case SHOWING_SCREEN1:
			presentHelpScreen1(deltaTime);
			break;
		case SHOWING_SCREEN2:
			presentHelpScreen2(deltaTime);
			break;
		}

		batcher.beginBatch(Assets.UIGameElements);
		batcher.drawSprite(RESOLUTION_X / 2 - 256, 150, 256, 256,
				Assets.mainMenuButtonHome);
		batcher.drawSprite(RESOLUTION_X / 2 + 128, 150, 512, 256,
				Assets.mainMenuTextQuit);
		
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	public void presentHelpScreen1(float deltaTime) {
		batcher.beginBatch(Assets.helpScreen1);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, RESOLUTION_X, RESOLUTION_Y, Assets.helpScreen1Region);
		batcher.endBatch();
	}
	
	public void presentHelpScreen2(float deltaTime) {
		batcher.beginBatch(Assets.helpScreen2);
		batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2, RESOLUTION_X, RESOLUTION_Y, Assets.helpScreen2Region);
		batcher.endBatch();
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