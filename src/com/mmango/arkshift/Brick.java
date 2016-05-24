package com.mmango.arkshift;

import android.util.Log;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

//public class Brick extends DynamicGameObject {
//we will not inherit from DynamicGameObject here anymore
public class Brick {

	public static final int BRICK_STATE_PREPARING = 0;
	public static final int BRICK_STATE_STILL = 1;
	public static final int BRICK_STATE_SHIFTING_UP = 2;
	public static final int BRICK_STATE_SHIFTING_DOWN = 3;
	public static final int BRICK_STATE_SHIFTING_UP_TO_FLOOR = 4;
	public static final int BRICK_STATE_SHIFTING_DOWN_TO_CEILING = 5;

	public static final float BRICK_WIDTH = 10.4f;
	public static final float BRICK_HEIGHT = 10.4f;

	public static final float BRICK_VELOCITY_X = 60f;
	public static final float BRICK_VELOCITY_Y = 20f;

	public final Vector2 position;
	public final Rectangle bounds;
	public final Vector2 velocity;
	public final Vector2 accel;
	public boolean atCeiling;

	public static final int BRICK_COLOR_PURPLE = 0; // 2 point
	public static final int BRICK_COLOR_GREEN = 1; // 5 points, reset the normal
													// acceleration and changes
													// the ball color to white
	public static final int BRICK_COLOR_BLUE = 2; // 3 points
	public static final int BRICK_COLOR_ORANGE = 3; // 6 points
	public static final int BRICK_COLOR_GREY = 4; // 5 points set the
													// narrow/wide racquet
	public static final int BRICK_COLOR_RED = 5; // 5 points. set the double
													// acceleration and changes
													// the ball color to red
													// until a green brick is
													// hit
	public static final int BRICK_COLOR_PINK = 6; // 6 points
	public static final int BRICK_COLOR_BLUESKY = 7; // 4 points
	public static final int BRICK_COLOR_VIOLET = 8; // 1 point
	public static final int BRICK_COLOR_GOLD = 9; // 7 points. Adds one ball.
													// The ball will be yellow
													// till it is lost.

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
	float xPrepDestination;
	float yPrepDestination;
	boolean jumpedToFloor;
	boolean jumpedToCeiling;

	public Brick(int column, int row, int color) {
		this.column = column;
		this.row = row;
		this.color = color;

		setTextureRegion(this.color);

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
		state = BRICK_STATE_PREPARING;
		stateTime = 0;
		// bounds.lowerLeft.set(position).sub(BRICK_WIDTH / 2, BRICK_WIDTH / 2);
		jumpedToFloor = false;
	}

	public void setTextureRegion(int color) {
		switch (color) {
		case BRICK_COLOR_GOLD:
			brickTextureRegion = AssetsGame.brickGold;
			break;
		case BRICK_COLOR_GREEN:
			brickTextureRegion = AssetsGame.brickGreen;
			break;
		case BRICK_COLOR_BLUE:
			brickTextureRegion = AssetsGame.brickBlue;
			break;
		case BRICK_COLOR_ORANGE:
			brickTextureRegion = AssetsGame.brickOrange;
			break;
		case BRICK_COLOR_GREY:
			brickTextureRegion = AssetsGame.brickGrey;
			break;
		case BRICK_COLOR_RED:
			brickTextureRegion = AssetsGame.brickRed;
			break;
		case BRICK_COLOR_PINK:
			brickTextureRegion = AssetsGame.brickPink;
			break;
		case BRICK_COLOR_BLUESKY:
			brickTextureRegion = AssetsGame.brickBluesky;
			break;
		case BRICK_COLOR_VIOLET:
			brickTextureRegion = AssetsGame.brickViolet;
			break;
		case BRICK_COLOR_PURPLE:
			brickTextureRegion = AssetsGame.brickPurple;
			break;
		default:
			brickTextureRegion = AssetsGame.brickGrey;
			break;
		}
	}

	public void updatePreparing(float deltaTime) {
		// position.x = xNew;
		velocity.set(BRICK_VELOCITY_X, 0);
		position.add(velocity.x * deltaTime, 0);
		//if (row % 2 == 0) { //even row
			if (position.x > xPrepDestination) {
				position.x = xPrepDestination;
				state = BRICK_STATE_STILL;
/*			}
		} else { // odd row
			if (position.x < xPrepDestination) {
				position.x = xPrepDestination;
				state = BRICK_STATE_STILL;
			}*/
		}

		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		stateTime += deltaTime;
		//Log.d("Brick:updatePreparing", "position.x = " + position.x);
		//Log.d("Brick:updatePreparing", "position.y = " + position.y);
	}

	public void update(float deltaTime) {
		// position.x = xNew;
		velocity.set(0, BRICK_VELOCITY_Y);

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

	public void setHomeCell(int column, int row) {
		this.column = column;
		this.row = row;

		xPrepDestination = World.FRAME_WIDTH + BRICK_WIDTH / 2 + BRICK_WIDTH
				* (float) column;
		yPrepDestination = World.WORLD_HEIGHT - World.NOTIFICATION_AREA_HEIGHT
				- World.FRAME_WIDTH - BRICK_WIDTH / 2 - BRICK_WIDTH
				* (float) row;

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

	public void setColor(int color) {
		this.color = color;
		setTextureRegion(this.color);
	}

}
