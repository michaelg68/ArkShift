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

public class HighscoreScreen extends GLScreen {
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle backBounds;
    Vector2 touchPoint;
    String[] highScores;  
    float xOffset = 0;

    public HighscoreScreen(Game game) {
        super(game);
        
        guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
        backBounds = new Rectangle(0, 0, RESOLUTION_X, RESOLUTION_Y);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 200);
        highScores = new String[5];        
        for(int i = 0; i < 5; i++) {
        	Log.d("HighScoreScreen:cunstructor", "Settings.highscores[" + i + "] = " + Settings.highscores[i]);
            highScores[i] = (i + 1) + ". " + Settings.highscores[i];
            xOffset = Math.max(highScores[i].length() * Assets.font.glyphWidth, xOffset);
        }        
        xOffset = RESOLUTION_X / 2 - xOffset / 2;
    }    

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
            
            if(event.type == TouchEvent.TOUCH_UP) {
                if(OverlapTester.pointInRectangle(backBounds, touchPoint)) {
                    game.setScreen(new MainMenuScreen(game));
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
        batcher.drawSprite(RESOLUTION_X / 2 , RESOLUTION_Y / 2, RESOLUTION_X, RESOLUTION_Y, Assets.mainScreenBackgroundRegion);
        batcher.endBatch();
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.userInterfaceElements);
        //batcher.drawSprite(160, 360, 300, 33, Assets.highScoresRegion);
        
        float y = RESOLUTION_Y / 4;
        for(int i = 4; i >= 0; i--) {
            Assets.font.drawTextZoomed(batcher, highScores[i], xOffset, y,  3f, 3f);
            y += Assets.font.glyphHeight * 3f;
        }

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
