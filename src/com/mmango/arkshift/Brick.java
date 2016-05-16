package com.mmango.arkshift;

import android.util.Log;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

//public class Brick extends DynamicGameObject {
//we will not inherit from DynamicGameObject here anymore
public class Brick {

	public static final int BRICK_STATE_STILL = 0;
	public static final int BRICK_STATE_SHIFTING_UP = 1;
	public static final int BRICK_STATE_SHIFTING_DOWN = 2;
	public static final int BRICK_STATE_SHIFTING_UP_TO_FLOOR = 3;
	public static final int BRICK_STATE_SHIFTING_DOWN_TO_CEILING = 4;

	public static final float BRICK_WIDTH = 10.4f;
	public static final float BRICK_HEIGHT = 10.4f;

	public static final float BRICK_VELOCITY = 20f;

	public final Vector2 position;
	public final Rectangle bounds;
	public final Vector2 velocity;
	public final Vector2 accel;
	public boolean atCeiling;

	public static final int BRICK_COLOR_GOLD = 0; // 7  points
	public static final int BRICK_COLOR_GREEN = 1;  //5 points, reset the normal acceleration and changes the ball color to white
	public static final int BRICK_COLOR_BLUE = 2;   // 3 points
	public static final int BRICK_COLOR_ORANGE = 3; // 6 points
	public static final int BRICK_COLOR_GREY = 4; //5 points set the narrow/wide racquet
	public static final int BRICK_COLOR_RED = 5;  //5 points. set the double acceleration and changes the ball color to red until a green brick is hit  
	public static final int BRICK_COLOR_PINK = 6; // 6 points
	public static final int BRICK_COLOR_BLUESKY = 7;  // 4 points
	public static final int BRICK_COLOR_VIOLET = 8; // 1 point
	public static final int BRICK_COLOR_PURPLE = 9; // 2 point
	
	public int color;
	public TextureRegion brickTextureRegion;
	int state;
	float stateTime;
	float x;
	float y;
	public int row;
	public int column;
	// float xNew;
	float yDestination;
	boolean jumpedToFloor;
	boolean jumpedToCeiling;

	public Brick(int column, int row, int color) {
		this.column = column;
		this.row = row;
		this.color = color;
		
		
		switch (this.color) {
		case BRICK_COLOR_GOLD:
			brickTextureRegion = Assets.brickGold;
			break;
		case BRICK_COLOR_GREEN:
			brickTextureRegion = Assets.brickGreen;
			break;
		case BRICK_COLOR_BLUE:
			brickTextureRegion = Assets.brickBlue;
			break;
		case BRICK_COLOR_ORANGE:
			brickTextureRegion = Assets.brickOrange;
			break;
		case BRICK_COLOR_GREY:
			brickTextureRegion = Assets.brickGrey;
			break;
		case BRICK_COLOR_RED:
			brickTextureRegion = Assets.brickRed;
			break;
		case BRICK_COLOR_PINK:
			brickTextureRegion = Assets.brickPink;
			break;
		case BRICK_COLOR_BLUESKY:
			brickTextureRegion = Assets.brickBluesky;
			break;
		case BRICK_COLOR_VIOLET:
			brickTextureRegion = Assets.brickViolet;
			break;
		case BRICK_COLOR_PURPLE:
			brickTextureRegion = Assets.brickPurple;
			break;
		default:
			brickTextureRegion = Assets.brickGrey;
			break;
		}


		x = World.FRAME_WIDTH + BRICK_WIDTH / 2 + BRICK_WIDTH * (float) column;
		y = World.WORLD_HEIGHT - World.NOTIFICATION_AREA_HEIGHT
				- World.FRAME_WIDTH - BRICK_WIDTH / 2 - BRICK_WIDTH
				* (float) row;

		/*
		 * x = World.FRAME_WIDTH + BRICK_WIDTH / 2 + BRICK_WIDTH * (float)
		 * column; y = World.WORLD_HEIGHT - World.FRAME_WIDTH - BRICK_WIDTH / 2
		 * - BRICK_WIDTH (float) row;
		 */
		// super(x, y, BRICK_WIDTH, BRICK_WIDTH);
		velocity = new Vector2();
		accel = new Vector2();
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(position.x - BRICK_WIDTH / 2, position.y
				- BRICK_HEIGHT / 2, BRICK_WIDTH, BRICK_HEIGHT);
		// all objects will be created at ceiling.
		atCeiling = true;
		state = BRICK_STATE_STILL;
		stateTime = 0;
		// bounds.lowerLeft.set(position).sub(BRICK_WIDTH / 2, BRICK_WIDTH / 2);
		jumpedToFloor = false;
	}

	public void update(float deltaTime) {
		// position.x = xNew;
		velocity.set(0, BRICK_VELOCITY);

		if (state == BRICK_STATE_SHIFTING_UP_TO_FLOOR) {
			position.add(0, velocity.y * deltaTime * 2);
			if (position.y > World.WORLD_HEIGHT
					- World.NOTIFICATION_AREA_HEIGHT - World.FRAME_WIDTH
					+ BRICK_HEIGHT / 2) {
				position.y = World.FRAME_WIDTH - BRICK_HEIGHT / 2;
				jumpedToFloor = true;
			}
			if ((position.y > yDestination) && jumpedToFloor) {
				position.y = yDestination;
				jumpedToFloor = false;
				state = BRICK_STATE_STILL;
			}
		}

		if (state == BRICK_STATE_SHIFTING_UP) {
			position.add(0, velocity.y * deltaTime * 2);
			if (position.y > yDestination) {
				position.y = yDestination;
				state = BRICK_STATE_STILL;
			}
		}

		if (state == BRICK_STATE_SHIFTING_DOWN_TO_CEILING) {
			position.add(0, -velocity.y * deltaTime * 2);
			if (position.y < World.FRAME_WIDTH - BRICK_HEIGHT / 2) {
				position.y = World.WORLD_HEIGHT
						- World.NOTIFICATION_AREA_HEIGHT - World.FRAME_WIDTH
						+ BRICK_HEIGHT / 2;
				jumpedToCeiling = true;
			}
			if ((position.y < yDestination) && jumpedToCeiling) {
				position.y = yDestination;
				jumpedToCeiling = false;
				state = BRICK_STATE_STILL;
			}
		}

		if (state == BRICK_STATE_SHIFTING_DOWN) {
			position.add(0, -velocity.y * deltaTime * 2);
			if (position.y < yDestination) {
				position.y = yDestination;
				state = BRICK_STATE_STILL;
			}
		}

		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		stateTime += deltaTime;
		// Log.d("Brick", "position.x" + position.x);
		// Log.d("Brick", "position.y" + position.y);
	}

	public void setCell(int column, int row) {
		this.column = column;
		this.row = row;
		if (atCeiling) {
			yDestination = World.WORLD_HEIGHT - World.NOTIFICATION_AREA_HEIGHT
					- World.FRAME_WIDTH - BRICK_WIDTH / 2 - BRICK_WIDTH
					* (float) row;
		} else {
			yDestination = World.FRAME_WIDTH + BRICK_WIDTH / 2 + BRICK_HEIGHT
					* (float) row;
		}
		// Log.d("Brick", "yDestination = " + yDestination);

	}

}
