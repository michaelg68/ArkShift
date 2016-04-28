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

public class MainMenuScreen extends GLScreen {
	static final int RESOLUTION_X = 1080;
	static final int RESOLUTION_Y = 1920;
    Camera2D guiCam;
    SpriteBatcher batcher;
    
    Rectangle playBounds;
    Rectangle settingsBounds;
    Rectangle highscoresBounds;
    Rectangle helpBounds;
    Vector2 touchPoint;

    public MainMenuScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, RESOLUTION_X, RESOLUTION_Y);
        batcher = new SpriteBatcher(glGraphics, 30);
        //note that parameters in Rectangle are: lower_left_x, lower_left_y, wide, height  
        playBounds = new Rectangle(RESOLUTION_X / 2 - 350, RESOLUTION_Y / 2, 700, 250);
        settingsBounds = new Rectangle(RESOLUTION_X / 2 - 350, RESOLUTION_Y / 2 - 250 , 700, 250);
        highscoresBounds = new Rectangle(RESOLUTION_X / 2 - 350, RESOLUTION_Y / 2 - 500, 700, 250);
        helpBounds = new Rectangle(RESOLUTION_X / 2 - 350, RESOLUTION_Y / 2  - 1000, 700, 250);
        touchPoint = new Vector2();               
    }       

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);                        
            if(event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                //Log.d("GameScreen:", "event.x = " + Float.toString(event.x));
    			//Log.d("GameScreen:", "event.y = " + Float.toString(event.y));                
                guiCam.touchToWorld(touchPoint);
                
                if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    game.setScreen(new SelectLevelScreen(game));
                    return;
                }
                if(OverlapTester.pointInRectangle(settingsBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    //game.setScreen(new SettingsScreen(game));
                    return;
                }
                if(OverlapTester.pointInRectangle(highscoresBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    Log.d("MainMenuScreen:update", "highscoresBounds is touched. Opening HighscoreScreen");
                    game.setScreen(new HighscoreScreen(game));
                    return;
                }
                if(OverlapTester.pointInRectangle(helpBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    //game.setScreen(new HelpScreen(game));
                    return;
                }
/*                if(OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled) 
                        Assets.music.play();
                    else
                        Assets.music.pause();
                }*/
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
        batcher.drawSprite(RESOLUTION_X / 2, RESOLUTION_Y / 2 - 250, 700, 1000, Assets.mainScreenMenuRegion);
        
        batcher.endBatch();
        
        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {        
        //Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {        
    }       

    @Override
    public void dispose() {        
    }
}
