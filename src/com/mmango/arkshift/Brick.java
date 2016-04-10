package com.mmango.arkshift;

import android.util.Log;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;


	//public class Brick extends DynamicGameObject {
	public class Brick {

	public static final int BRICK_STATE_STILL = 0;
	public static final int BRICK_STATE_MOVING_DOWN = 1;
	public static final int BRICK_STATE_MOVING_UP = 2;

	public static final float BRICK_WIDTH = 10.4f;
	public static final float BRICK_HEIGHT = 10.4f;
	
	public final Vector2 position;
	public final Rectangle bounds;
	public final Vector2 velocity;
	public final Vector2 accel;
	
/*	public static final int BRICK_COLOR_GOLD = 0;
	public static final int BRICK_COLOR_GREEN = 1;
	public static final int BRICK_COLOR_BLUE = 2;
	public static final int BRICK_COLOR_ORANGE = 3;
	public static final int BRICK_COLOR_GREY = 4;
	public static final int BRICK_COLOR_RED = 5;
	public static final int BRICK_COLOR_PINK = 6;
	public static final int BRICK_COLOR_WHEAT = 7;
	public static final int BRICK_COLOR_VIOLET = 8;
	public static final int BRICK_COLOR_PURPLE = 9;*/
	
	public TextureRegion brickTexture;
	public int color;
    int state;
    float stateTime;
    float x;
    float y;
    public int row;
    public int column;
    
    public Brick(int column, int row, TextureRegion brickTexture) {
    	this.column = column;
    	this.row = row;

    	x = World.FRAME_WIDTH + BRICK_WIDTH / 2 + BRICK_WIDTH * (float)column;
    	y = World.WORLD_HEIGHT - World.NOTIFICATION_AREA_HEIGHT - World.FRAME_WIDTH - BRICK_WIDTH / 2 - BRICK_WIDTH * (float)row;
        //super(x, y, BRICK_WIDTH, BRICK_WIDTH);
    	velocity = new Vector2();
		accel = new Vector2();
    	this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x - BRICK_WIDTH / 2, y - BRICK_WIDTH / 2, BRICK_WIDTH, BRICK_WIDTH);
	    this.brickTexture = brickTexture;
        state = BRICK_STATE_STILL;
        stateTime = 0;
    }
	
   /* public Brick(float x, float y, TextureRegion brickTexture) {
        super(x, y, BRICK_WIDTH, BRICK_WIDTH);
        this.brickTexture = brickTexture;
        state = BRICK_STATE_STILL;
        stateTime = 0;
    }*/
    
    public void update(float deltaTime) {
		//velocity.add(0 * deltaTime, 0 * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

/*		if (velocity.y != 0) {
			state = BRICK_STATE_MOVING;
			stateTime = 0;
		}*/
		
		if (position.y < 0)
			position.y = 0;
		if (position.y > World.GAME_FIELD_HEIGHT)
			position.y = World.GAME_FIELD_HEIGHT;

		stateTime += deltaTime;
	}
    
    public void moveDown() {
    	//state=Brick.BRICK_STATE_MOVING_DOWN;
    	Log.d("Brick:moveDown", "moving the brick down");
    }

    public void moveUp() {
    	//state=Brick.BRICK_STATE_MOVING_UP;
    	Log.d("Brick:moveUp", "moving the brick up");
    }


}
