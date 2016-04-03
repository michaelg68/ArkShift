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

public class MainMenuScreen extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    
    Rectangle playBounds;
    Rectangle settingsBounds;
    Rectangle highscoresBounds;
    Rectangle helpBounds;
    Vector2 touchPoint;

    public MainMenuScreen(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 1080, 1920);
        batcher = new SpriteBatcher(glGraphics, 100);
        playBounds = new Rectangle(540 - 350, 960 + 500, 700, 250);
        settingsBounds = new Rectangle(540 - 350, 960 + 250, 700, 250);
        highscoresBounds = new Rectangle(540 - 350, 960, 700, 250);
        helpBounds = new Rectangle(540 - 350, 960 - 250, 700, 250);
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
                guiCam.touchToWorld(touchPoint);
                
                if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    //game.setScreen(new GameScreen(game));
                    return;
                }
                if(OverlapTester.pointInRectangle(settingsBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                    //game.setScreen(new SettingsScreen(game));
                    return;
                }
                if(OverlapTester.pointInRectangle(highscoresBounds, touchPoint)) {
                    Assets.playSound(Assets.clickSound);
                   // game.setScreen(new HighscoreScreen(game));
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
        batcher.drawSprite(1080/2 , 1920/2, 1080, 1920, Assets.mainScreenBackgroundRegion);
        batcher.endBatch();
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);               
        
        batcher.beginBatch(Assets.mainScreenMenu);                 
        batcher.drawSprite(1080/2, 1920/2 - 200, 700, 1000, Assets.mainScreenMenuRegion);
        
        batcher.endBatch();
        
        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {        
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {        
    }       

    @Override
    public void dispose() {        
    }
}
