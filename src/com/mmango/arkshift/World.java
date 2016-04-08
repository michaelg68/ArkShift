package com.mmango.arkshift;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.mmango.arkshift.Assets;
import com.mmango.arkshift.Racquet;

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

	public static final float WORLD_HEIGHT = 192f;
	public static final float WORLD_WIDTH = 108f;
	public static final float NOTIFICATION_AREA_HEIGHT = 15f;
	public static final float NOTIFICATION_AREA_WIDTH = 108f;
	public static final float FRAME_WIDTH = 2f;
	public static final float GAME_FIELD_HEIGHT = WORLD_HEIGHT - FRAME_WIDTH - FRAME_WIDTH - NOTIFICATION_AREA_HEIGHT;
	public static final float GAME_FIELD_WIDTH = WORLD_WIDTH - FRAME_WIDTH - FRAME_WIDTH;
	
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int RACQUET_MOVING_LEFT = 0;
	public static final int RACQUET_MOVING_RIGHT = 1;
	
	public static Rectangle gameField;

	public Racquet racquet;
	public Ball ball;
	public final List<Brick> bricks;
	

	public final WorldListener listener;
	public final Random rand;
	public int ballsLeft;
	public int columns;
	public int score;
	public int state;
	public int level;

	public World(WorldListener listener) {
		gameField = new Rectangle(FRAME_WIDTH, FRAME_WIDTH, GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);
		rand = new Random();
		level = 1;

		this.racquet = new Racquet(WORLD_WIDTH / 2, FRAME_WIDTH + Brick.BRICK_WIDTH * level + Racquet.RACQUET_HEIGHT / 2 + 0.5f);
		//randomize the x coordinate of the ball on the racquet: shift it from the center of the racquet in range from -18f to +18f
		float ballXOffset = rand.nextFloat() * (Racquet.RACQUET_WIDTH * 0.8f) - (Racquet.RACQUET_WIDTH * 0.8f) / 2;
        //Log.d("World", "ballXOffset = " + Float.toString(ballXOffset));

		this.ball = new Ball(WORLD_WIDTH / 2 + ballXOffset, FRAME_WIDTH + Brick.BRICK_WIDTH 
				+ Racquet.RACQUET_HEIGHT + Ball.BALL_DIAMETER / 2, Assets.ballWhite);
		this.bricks = new ArrayList<Brick>();
		this.listener = listener;
		columns = 10;
		generateLevel(columns, level);

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

				Brick brick = new Brick(FRAME_WIDTH + Brick.BRICK_WIDTH / 2  + Brick.BRICK_WIDTH * x, 192 - 17 - 5.2f - 10.4f * y, brickRegion);
				//2.0f + 5.2f + 10.4f, 192 - 17 - 5.2f
				//Log.d("World", "Adding a brick");
				bricks.add(brick);
			}
		}

	}

	public void update(float deltaTime, float accelX) {
		racquet.update(deltaTime, accelX);
		updateBall(deltaTime);
		checkBallCollisions();
		//updateBricks(deltaTime);

		//checkGameOver();
	}



	private void updateBall(float deltaTime) {
		ball.update(deltaTime);
	}

	private void updateBricks(float deltaTime) {
	}



	private void checkBallCollisions() {
		checkBallCollisionsWithFrame();
		checkBallCollisionsWithRacquet();
		
	}
	
	private void checkBallCollisionsWithFrame() {
		int breaktrhough = OverlapTester.circleCompletelyInsideRectangle(ball.bounds, gameField);
		if (breaktrhough == 1) {
			//X collision
			//ball.position.y = 
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == 2){
			//Y collision
			//ball.position.x =
			ball.velocity.x = ball.velocity.x * (-1);
		}
	}
	
	private void checkBallCollisionsWithRacquet() {
		boolean racquetContact = OverlapTester.overlapCircleRectangle(ball.bounds, racquet.bounds);
		//Log.d("World:checkBallCollisionsWithRacquet", "racquetContact = " + racquetContact );
		if (racquetContact) {
			Log.d("World:checkBallCollisionsWithRacquet", "there was a contact!" );
			
			Log.d("World:checkBallCollisionsWithRacquet", "oldAngle = " + ball.velocity.angle());
			Log.d("World:checkBallCollisionsWithRacquet", "racquetAngle = " + racquet.velocity.angle());

			ball.velocity.y = ball.velocity.y * (-1);
			
			float angleTmp = ball.velocity.angle();
			Log.d("World:checkBallCollisionsWithRacquet", "angleTmp = " + angleTmp);
			
			//Create a copy of ball.velocity
			Vector2 ballVelocityCopy = ball.velocity.cpy();
			
			//get sum of ballVelocityCopy and racquet.velocity
			ballVelocityCopy.add(racquet.velocity);
			
			//get the angle between the temp ballVelocityCopy and X 
			float angle = ballVelocityCopy.angle();
			Log.d("World:checkBallCollisionsWithRacquet", "angle = " + angle);

			
			//rotate ball.velocity on that angle
			ball.velocity.rotate(angle - angleTmp );
			Log.d("World:checkBallCollisionsWithRacquet", "newAngle = " + ball.velocity.angle());
			

		}
	}
	
/*	private void checkBallCollisions() {
		if (! OverlapTester.circleCompletelyInsideRectangle(ball.bounds, gameField)) {
		//if (ball.position.y >= GAME_FIELD_HEIGHT) {
			Log.d("World:checkBallCollisions", "ball.position.y = " + Float.toString(ball.position.y));
			//ball.velocity.x = -2;
			//ball.velocity.y = -100;
			//ball.velocity.mul(-1);
			float angleX = ball.velocity.angle();
			float angleB = 90 - (angleX - 180);
			float angleRotate = 180 + 2 * angleB;
			ball.velocity.rotate(angleRotate);

			Log.d("World:checkBallCollisions", "angleX = " + Float.toString(angleX));
			Log.d("World:checkBallCollisions", "angleRotate = " + Float.toString(angleRotate));

		}
	}*/

	private void checkGameOver() {
		if (ballsLeft < 1) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
