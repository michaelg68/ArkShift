package com.mmango.arkshift;

import com.badlogic.androidgames.framework.DynamicGameObject;
import com.mmango.arkshift.World;

public class Racquet extends DynamicGameObject {
	public static final int RACQUET_STATE_STILL = 0;
	public static final int RACQUET_STATE_MOVING_LEFT = 1;
	public static final int RACQUET_STATE_MOVING_RIGHT = 2;
	public static final float RACQUET_MOVE_VELOCITY = 0;
	public static final float RACQUET_WIDTH = 40f;
	public static final float RACQUET_HEIGHT = 5f;
	static float RACQUET_VELOCITY = 5f;


	int state;
	float stateTime;

	public Racquet(float x, float y) {
		super(x, y, RACQUET_WIDTH, RACQUET_HEIGHT);
		state = RACQUET_STATE_STILL;
		stateTime = 0;
	}

	public void update(float deltaTime, float accelX) {
		
		velocity.set(accelX / 10 * RACQUET_VELOCITY, 0);
		position.add(velocity.x * deltaTime, 0);
		
		if (position.x < 0)
			position.x = 0;
		if (position.x > World.GAME_FIELD_WIDTH)
			position.x = World.GAME_FIELD_WIDTH;
		
		if (velocity.x > 0) {
			state = RACQUET_STATE_MOVING_RIGHT;
			stateTime = 0;
		}
		
		if (velocity.x < 0) {
			state = RACQUET_STATE_MOVING_LEFT;
			stateTime = 0;
		}
		stateTime += deltaTime;

		//velocity.add(0 * deltaTime, 0 * deltaTime);
		//velocity.x = veloc;
		
/*		position.add(velocity.x * deltaTime, 0);
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

		if (velocity.x > 0) {
			state = RACQUET_STATE_MOVING_RIGHT;
			stateTime = 0;
		}
		
		if (velocity.x < 0) {
			state = RACQUET_STATE_MOVING_LEFT;
			stateTime = 0;
		}


		if (position.x < 0)
			position.x = 0;
		if (position.x > World.GAME_FIELD_WIDTH)
			position.x = World.GAME_FIELD_WIDTH;

		stateTime += deltaTime;*/
	}

}
