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

	/*
	 * public static final int BRICK_COLOR_GOLD = 0; public static final int
	 * BRICK_COLOR_GREEN = 1; public static final int BRICK_COLOR_BLUE = 2;
	 * public static final int BRICK_COLOR_ORANGE = 3; public static final int
	 * BRICK_COLOR_GREY = 4; public static final int BRICK_COLOR_RED = 5; public
	 * static final int BRICK_COLOR_PINK = 6; public static final int
	 * BRICK_COLOR_WHEAT = 7; public static final int BRICK_COLOR_VIOLET = 8;
	 * public static final int BRICK_COLOR_PURPLE = 9;
	 */

	public TextureRegion brickTexture;
	public int color;
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

	public Brick(int column, int row, TextureRegion brickTexture) {
		this.column = column;
		this.row = row;

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

		this.brickTexture = brickTexture;
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
			if (position.y > World.WORLD_HEIGHT - World.NOTIFICATION_AREA_HEIGHT - World.FRAME_WIDTH + BRICK_HEIGHT / 2) {
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
				position.y = World.WORLD_HEIGHT - World.NOTIFICATION_AREA_HEIGHT - World.FRAME_WIDTH + BRICK_HEIGHT / 2;
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
		Log.d("Brick", "position.y" + position.y);
	}

	public void setCell(int column, int row) {
		this.column = column;
		this.row = row;
		if (atCeiling) {
			yDestination = World.WORLD_HEIGHT - World.NOTIFICATION_AREA_HEIGHT - World.FRAME_WIDTH - 
					BRICK_WIDTH	/ 2 - BRICK_WIDTH * (float) row;
		} else {
			yDestination = World.FRAME_WIDTH + BRICK_WIDTH / 2 + BRICK_HEIGHT
					* (float) row;
		}
		Log.d("Brick", "yDestination = " + yDestination);

	}

}
