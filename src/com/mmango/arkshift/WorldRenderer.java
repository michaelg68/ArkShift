package com.mmango.arkshift;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.mmango.arkshift.Assets;
import com.mmango.arkshift.World;

public class WorldRenderer {
	
	static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;    
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;    
    
    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;        
    }

    
    public void render() {
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();        
    }
    
    public void renderBackground() {
        batcher.beginBatch(Assets.gameScreenBackground);
        batcher.drawSprite(cam.position.x, cam.position.y,
                           FRUSTUM_WIDTH, FRUSTUM_HEIGHT, 
                           Assets.gameScreenBackgroundRegion);
        batcher.endBatch();
    }
    
    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.gameScreenElements);
        renderRacquet();
        //renderBall();
        //renderBricks();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }
    
    private void renderRacquet() {
        Racquet racquet = world.racquet;
        batcher.drawSprite(racquet.position.x, racquet.position.y, 2, 2, Assets.racquet);
    }
}
