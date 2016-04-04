package com.mmango.arkshift;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Ball extends DynamicGameObject {
	public static final int BALL_STATE_STILL = 0;
	public static final int BALL_STATE_MOVING = 1;
	public static final int BALL_STATE_HIT_RACQUET = 2;
	public static final int BALL_STATE_HIT_CEILING_BRICK = 3;
	public static final int BALL_STATE_HIT_FLOOR_BRICK = 4;
	public static final int BALL_STATE_HIT_FRAME = 5;
	public static final float BALL_MOVE_VELOCITY = 0;
	public static final float BALL_WIDTH = 40f;
	public static final float BALL_HEIGHT = 5f;
	public static final int BALL_COLOR_WHITE = 0;
	public static final int BALL_COLOR_YELLOW = 1;
	public static final int BALL_COLOR_RED = 2;
	
	public int color;
    int state;
    float stateTime;  
	
    public Ball(float x, float y, int color) {
        super(x, y, BALL_WIDTH, BALL_WIDTH);
        state = BALL_STATE_STILL;
        stateTime = 0;
        this.color = color;
    }
    
    public void update(float deltaTime) {
		//velocity.add(0 * deltaTime, 0 * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		if (velocity.x != 0 || velocity.y != 0) {
			state = BALL_STATE_MOVING;
			stateTime = 0;
		}
		
		if (position.x < 0)
			position.x = 0;
		if (position.x > World.GAME_FIELD_WIDTH)
			position.x = World.GAME_FIELD_WIDTH;

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

}
