package com.mmango.arkshift;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.mmango.arkshift.Assets;
import com.mmango.arkshift.World;
import com.mmango.arkshift.Racquet;

public class WorldRenderer {
	
	//static final float FRUSTUM_WIDTH = 10;
    //static final float FRUSTUM_HEIGHT = 15; 
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;    
    
    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        //this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.cam = new Camera2D(glGraphics, World.WORLD_WIDTH, World.WORLD_HEIGHT);

        this.batcher = batcher;        
    }

    
    public void render() {
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();        
    }
    
    public void renderBackground() {
        batcher.beginBatch(Assets.gameScreenBackground);
        batcher.drawSprite(cam.position.x, cam.position.y, World.WORLD_WIDTH, World.WORLD_HEIGHT, 
                           Assets.gameScreenBackgroundRegion);
        batcher.endBatch();
    }
    
    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.gameScreenElements);
        renderRacquet();
        renderBall();
        renderBricks();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }
    
    private void renderRacquet() {
        Racquet racquet = world.racquet;
        //Log.d("WorldRenderer:renderRacquet", "inside method renderRacquet, before drawSprite");
        //batcher.drawSprite(54, 2f + 10.4f + 2.7f , 40f, 5f, Assets.racquet);
        //batcher.drawSprite(racquet.position.x + racquet.RACQUET_WIDTH / 2, racquet.position.y - racquet.RACQUET_HEIGHT / 2, 40f, 5.4f, Assets.racquet);
        batcher.drawSprite(racquet.position.x, racquet.position.y, Racquet.RACQUET_WIDTH, Racquet.RACQUET_HEIGHT, Assets.racquet);

        //Log.d("WorldRenderer:renderRacquet", "inside method renderRacquet, after drawSprite");
       // batcher.drawSprite(racquet.position.x, racquet.position.y, 40f, 5f,	racquetRegion)
    }
    
    private void renderBall() {
        Ball ball = world.ball;
        //Log.d("WorldRenderer:renderBall", "inside method renderBall, before drawSprite");
        //batcher.drawSprite(ball.position.x + ball.BALL_WIDTH / 2, ball.position.y - ball.BALL_HEIGHT / 2, 5.4f, 5.4f, Assets.ballWhite);
        batcher.drawSprite(ball.position.x, ball.position.y, 5.4f, 5.4f, ball.ballTexture);
        //Log.d("WorldRenderer:renderBall", "inside method renderBall, after drawSprite");
    }
    
    private void renderBricks() {
       // Log.d("WorldRenderer", "inside method renderBricks, before world.bricks.size");

        int len = world.bricks.size();
        //Log.d("WorldRenderer", "world.bricks.size(): " + Integer.toString(len));
        
        for(int i = 0; i < len; i++) {
            Brick brick = world.bricks.get(i);
            //Log.d("WorldRenderer", "inside method renderBricks, before drawSprite");
            batcher.drawSprite(brick.position.x, brick.position.y, 10.4f, 10.4f, brick.brickTexture);
        }
    }
}
