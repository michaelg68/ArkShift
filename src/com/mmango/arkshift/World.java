package com.mmango.arkshift;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;
import com.mmango.arkshift.Assets;

public class World {
	public interface WorldListener {
		public void hitAtRacquet();

		public void hitAtBrick();

		public void hitAtBrickFloor();

		public void hitAtFrame();

		public void shiftBrick();

		public void gameOver();

		public void levelPassed();
	}

	public static final float WORLD_HEIGHT = 192;
	public static final float WORLD_WIDTH = 108;
	public static final float GAME_FIELD_HEIGHT = 192 - 2 - 2 - 15;
	public static final float GAME_FIELD_WIDTH = 108 - 2 - 2;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;

	public final Racquet racquet;
	public Ball ball;
	public final List<Brick> bricks;

	public final WorldListener listener;
	public final Random rand;
	public int ballsLeft;
	public int columns;
	public int score;
	public int state;

	public World(WorldListener listener) {
		rand = new Random();

		this.racquet = new Racquet(108f / 2, 2 + 10f + 2.7f);
		//randomize the x coordinate of the ball on the racquet: shift it from the center of the racquet in range from -18f to +18f
		float ballXOffset = rand.nextFloat() * 36 - 18;
        Log.d("World", "ballXOffset = " + Float.toString(ballXOffset));

		this.ball = new Ball(108f / 2 + ballXOffset, 2 + 10 + 5.4f + 2.7f, Assets.ballWhite);
		this.bricks = new ArrayList<Brick>();
		this.listener = listener;
		columns = 10;
		generateLevel(columns, 3);

		ballsLeft = 9;

		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel(int columns, int rows) {
		TextureRegion brickRegion;
		for (int y = 0; y < rows; y++) {
	        //Log.d("World", "y = " + Integer.toString(y));
			for (int x = 0; x < columns; x++) {
		        //Log.d("World", "x = " + Integer.toString(x));
				int brick_color = rand.nextInt(10);
		        //Log.d("World", "brick_color = " + Integer.toString(brick_color));
				switch (brick_color) {
				case 0:
					brickRegion = Assets.brickGold;
					break;
				case 1:
					brickRegion = Assets.brickGreen;
					break;
				case 2:
					brickRegion = Assets.brickBlue;
					break;
				case 3:
					brickRegion = Assets.brickOrange;
					break;
				case 4:
					brickRegion = Assets.brickGrey;
					break;
				case 5:
					brickRegion = Assets.brickRed;
					break;
				case 6:
					brickRegion = Assets.brickPink;
					break;
				case 7:
					brickRegion = Assets.brickWheat;
					break;
				case 8:
					brickRegion = Assets.brickViolet;
					break;
				case 9:
					brickRegion = Assets.brickPurple;
					break;
				default:
					brickRegion = Assets.brickGrey;
					break;
				}

				Brick brick = new Brick(2.0f + 5.2f  + 10.4f * x, 192 - 17 - 5.2f - 10.4f * y, brickRegion);
				//2.0f + 5.2f + 10.4f, 192 - 17 - 5.2f
				//Log.d("World", "Adding a brick");
				bricks.add(brick);
			}
		}

	}

	public void update(float deltaTime, float accelX) {
		updateRacquet(deltaTime, accelX);
		updateBall(deltaTime);
		updateBricks(deltaTime);
		checkCollisions();
		checkGameOver();
	}

	private void updateRacquet(float deltaTime, float accelX) {
		
	}

	private void updateBall(float deltaTime) {
	}

	private void updateBricks(float deltaTime) {
	}

	private void checkCollisions() {
		checkBallCollisions();
	}

	private void checkBallCollisions() {
		// TODO Auto-generated method stub

	}

	private void checkGameOver() {
		if (ballsLeft < 1) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
