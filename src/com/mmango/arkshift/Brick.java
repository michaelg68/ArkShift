package com.mmango.arkshift;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Brick extends DynamicGameObject {
	public static final int BRICK_STATE_STILL = 0;
	public static final int BRICK_STATE_MOVING = 1;
	public static final float BRICK_WIDTH = 40f;
	public static final float BRICK_HEIGHT = 5f;
	
	public static final int BRICK_COLOR_GOLD = 0;
	public static final int BRICK_COLOR_GREEN = 1;
	public static final int BRICK_COLOR_BLUE = 2;
	public static final int BRICK_COLOR_ORANGE = 3;
	public static final int BRICK_COLOR_GREY = 4;
	public static final int BRICK_COLOR_RED = 5;
	public static final int BRICK_COLOR_PINK = 6;
	public static final int BRICK_COLOR_WHEAT = 7;
	public static final int BRICK_COLOR_VIOLET = 8;
	public static final int BRICK_COLOR_PURPLE = 9;
	
	public int color;
    int state;
    float stateTime;  
	
    public Brick(float x, float y, int color) {
        super(x, y, BRICK_WIDTH, BRICK_WIDTH);
        state = BRICK_STATE_STILL;
        stateTime = 0;
        this.color = color;
    }
    
    public void update(float deltaTime) {
		//velocity.add(0 * deltaTime, 0 * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		if (velocity.y != 0 || velocity.y != 0) {
			state = BRICK_STATE_MOVING;
			stateTime = 0;
		}
		
		if (position.y < 0)
			position.y = 0;
		if (position.y > World.GAME_FIELD_HEIGHT)
			position.y = World.GAME_FIELD_HEIGHT;

		stateTime += deltaTime;
	}


}
