package com.mmango.arkshift;

import android.util.Log;

import com.badlogic.androidgames.framework.DynamicGameObjectCircle;
import com.badlogic.androidgames.framework.gl.TextureRegion;

public class Ball extends DynamicGameObjectCircle {
	public static final int BALL_STATE_STILL = 0;
	public static final int BALL_STATE_MOVING = 1;
	public static final int BALL_STATE_HIT_RACQUET = 2;
	public static final int BALL_STATE_HIT_CEILING_BRICK = 3;
	public static final int BALL_STATE_HIT_FLOOR_BRICK = 4;
	public static final int BALL_STATE_HIT_FRAME = 5;
	public static final float BALL_MOVE_VELOCITY = 0;
	public static final float BALL_DIAMETER = 5.4f;
//	public static final float BALL_HEIGHT = 5.4f;
//	public static final int BALL_COLOR_WHITE = 0;
//	public static final int BALL_COLOR_YELLOW = 1;
//	public static final int BALL_COLOR_RED = 2;
	public TextureRegion ballTexture;

	
	//public int color;
    int state;
    float stateTime;  
	
    public Ball(float x, float y, TextureRegion ballTexture) {
        super(x, y, BALL_DIAMETER / 2);
        state = BALL_STATE_STILL;
        stateTime = 0;
        this.ballTexture = ballTexture;
        //velocity.x = 20;
        //velocity.y = 100;
        velocity.set(20, 100);
    }
    
    public void update(float deltaTime) {
		//velocity.add(0 * deltaTime, 0 * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.center.set(position);
		//Log.d("Ball:update", "position.x = " + position.x);
		//Log.d("Ball:update", "position.y = " + position.y);
		//Log.d("Ball:update", "velocity.x = " + velocity.x);
		//Log.d("Ball:update", "velocity.y = " + velocity.y);


		if (velocity.x != 0 || velocity.y != 0) {
			state = BALL_STATE_MOVING;
			stateTime = 0;
		}


		stateTime += deltaTime;
	}
    
    public void hitCielingBrick () {
        velocity.set(0,0);
        state = BALL_STATE_HIT_CEILING_BRICK;        
        stateTime = 0;
    }
    
    public void hitFloorBrick() {
        velocity.set(0,0);
        state = BALL_STATE_HIT_FLOOR_BRICK;        
        stateTime = 0;
    }

    public void hitRacquet() {
        velocity.set(0,0);
        state = BALL_STATE_HIT_RACQUET;        
        stateTime = 0;
    }
    
    public void hitFrame() {
        velocity.set(0,0);
        state = BALL_STATE_HIT_FRAME;        
        stateTime = 0;
    }

	public void setBallTexture(TextureRegion ballTexture) {
		this.ballTexture = ballTexture;
	}

}
