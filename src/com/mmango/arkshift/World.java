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
	public static final int FRAME_TOP_BORDER_ID = 1;
	public static final int FRAME_BOTTOM_BORDER_ID = 2;
	public static final int FRAME_LEFT_BORDER_ID = 3;
	public static final int FRAME_RIGHT_BORDER_ID = 4;

	public static final float FRAME_WIDTH = 2f;
	public static final float GAME_FIELD_HEIGHT = WORLD_HEIGHT - FRAME_WIDTH
			- FRAME_WIDTH - NOTIFICATION_AREA_HEIGHT;
	public static final float GAME_FIELD_WIDTH = WORLD_WIDTH - FRAME_WIDTH
			- FRAME_WIDTH;

	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final int RACQUET_MOVING_LEFT = 0;
	public static final int RACQUET_MOVING_RIGHT = 1;
	public static final int COLUMNS = 10;
	public static final int NO_OBJECT_ID = 99999;  //an empty cell

	public static Rectangle gameField;

	public Racquet racquet;
	public Ball ball;
	public final List<Brick> ceilingBricks;
	public int[][] ceilingBricksId;
	public final List<Brick> floorBricks;
	public int[][] floorBricksId;

	public final WorldListener listener;
	public final Random rand;
	public int ballsLeft;
	public int score;
	public int state;
	public int level;

	public World(WorldListener listener) {
		level = 2;

		gameField = new Rectangle(FRAME_WIDTH, FRAME_WIDTH, GAME_FIELD_WIDTH,
				GAME_FIELD_HEIGHT);
		rand = new Random();
		this.racquet = new Racquet(WORLD_WIDTH / 2, FRAME_WIDTH
				+ Brick.BRICK_HEIGHT * (level) + Racquet.RACQUET_HEIGHT / 2 + 0.5f);
		// randomize the x coordinate of the ball on the racquet: shift it from
		// the center of the racquet in range from -18f to +18f
		float ballXOffset = rand.nextFloat() * (Racquet.RACQUET_WIDTH * 0.8f)
				- (Racquet.RACQUET_WIDTH * 0.8f) / 2;
		// Log.d("World", "ballXOffset = " + Float.toString(ballXOffset));

		this.ball = new Ball(WORLD_WIDTH / 2 + ballXOffset, racquet.position.y + Racquet.RACQUET_HEIGHT / 2
				+ Ball.BALL_DIAMETER / 2, Assets.ballWhite);
		this.ceilingBricks = new ArrayList<Brick>();
		this.floorBricks = new ArrayList<Brick>();
		this.listener = listener;
		
		ballsLeft = 9;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
		generateLevel(COLUMNS, level);


	}

	private void generateLevel(int columns, int rows) {
		TextureRegion brickRegion;
		int brickId = 0;
		ceilingBricksId = new int[columns][rows];
		floorBricksId = new int[columns][rows];
		for (int y = 0; y < rows; y++) {
			// Log.d("World", "y = " + Integer.toString(y));
			for (int x = 0; x < columns; x++) {
				// Log.d("World", "x = " + Integer.toString(x));
				int brick_color = rand.nextInt(10);
				// Log.d("World", "brick_color = " +
				// Integer.toString(brick_color));
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

				Brick brick = new Brick(x, y, brickRegion);
				// 2.0f + 5.2f + 10.4f, 192 - 17 - 5.2f
				// Log.d("World", "Adding a brick");
				ceilingBricks.add(brick);
				ceilingBricksId[x][y] = brickId;
				brickId++;
				//populate the floorBricksId array with 99999 which stands for "there is no brick"
				floorBricksId[x][y] = NO_OBJECT_ID;  
			}
		}

	}

	public void update(float deltaTime, float accelX) {
		racquet.update(deltaTime, accelX);
		updateBall(deltaTime);
		checkBallCollisions();
		// updateBricks(deltaTime);

		// checkGameOver();
	}

	private void updateBall(float deltaTime) {
		ball.update(deltaTime);
	}

	private void updateBricks(float deltaTime) {
	}

	private void checkBallCollisions() {
		checkBallCollisionsWithFrame();
		checkBallCollisionsWithRacquet();
		checkBallCollisionsWithCeilingBricks();
		checkBallCollisionsWithFloorBricks();

	}

	private void checkBallCollisionsWithFrame() {
		int breaktrhough = OverlapTester.circleCompletelyInsideRectangle(
				ball.bounds, gameField);
		if (breaktrhough == FRAME_TOP_BORDER_ID) {
			// X collision
			//ball.position.y = FRAME_WIDTH + GAME_FIELD_HEIGHT;
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == FRAME_BOTTOM_BORDER_ID) {
			//ball.position.y = FRAME_WIDTH;
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == FRAME_LEFT_BORDER_ID) {
			// Y collision
			//ball.position.x = FRAME_WIDTH;
			ball.velocity.x = ball.velocity.x * (-1);
		} else if (breaktrhough == FRAME_RIGHT_BORDER_ID) {
			// Y collision
			//ball.position.x = FRAME_WIDTH + GAME_FIELD_WIDTH;
			ball.velocity.x = ball.velocity.x * (-1);
		}
	}

	private void checkBallCollisionsWithRacquet() {
		boolean racquetContact = OverlapTester.overlapCircleRectangle(
				ball.bounds, racquet.bounds);
		// Log.d("World:checkBallCollisionsWithRacquet", "racquetContact = " +
		// racquetContact );
		if (racquetContact) {
			Log.d("World:checkBallCollisionsWithRacquet",
					"there was a contact!");

			Log.d("World:checkBallCollisionsWithRacquet", "oldAngle = "
					+ ball.velocity.angle());
			Log.d("World:checkBallCollisionsWithRacquet", "racquetAngle = "
					+ racquet.velocity.angle());

			ball.velocity.y = ball.velocity.y * (-1);

			float angleTmp = ball.velocity.angle();
			Log.d("World:checkBallCollisionsWithRacquet", "angleTmp = "
					+ angleTmp);

			// Create a copy of ball.velocity
			Vector2 ballVelocityCopy = ball.velocity.cpy();

			// get sum of ballVelocityCopy and racquet.velocity
			ballVelocityCopy.add(racquet.velocity);

			// get the angle between the temp ballVelocityCopy and X
			float angle = ballVelocityCopy.angle();
			Log.d("World:checkBallCollisionsWithRacquet", "angle = " + angle);
			
			//avoid too flat angles, if the angle is less that 45 degrees than make it equal 45 + a random float between 1f to 5f 
			if ((angle > 90f) && (angle > 135f)) {
				float randangle = rand.nextFloat() * (5 - 1 ) + 1;
				Log.d("World:checkBallCollisionsWithRacquet", "randangle = " + randangle);
				angle = 135f - randangle;
			}
			if ((angle < 90f) && (angle < 45f)) {
				float randangle = rand.nextFloat() * (5 - 1 ) + 1;
				Log.d("World:checkBallCollisionsWithRacquet", "randangle = " + randangle);
				angle = 45 + randangle;
			}
			
			float newAngle = angle - angleTmp;
			// rotate ball.velocity on that angle
			ball.velocity.rotate(newAngle);
			Log.d("World:checkBallCollisionsWithRacquet", "newAngle = "	+ newAngle);

		}
	}
	
	private void checkBallCollisionsWithCeilingBricks(){
		int len = ceilingBricks.size();
		for (int i = 0; i < len; i++) {
			Brick brick = ceilingBricks.get(i);
			//Log.d("World:checkBallCollisionsWithCeilingBricks", "brick id = " + i);
			//Log.d("World:checkBallCollisionsWithCeilingBricks", "brick.bounds.lowerLeft.x = " + brick.bounds.lowerLeft.x);
			//Log.d("World:checkBallCollisionsWithCeilingBricks", "brick.bounds.lowerLeft.y = " + brick.bounds.lowerLeft.y);

			if(OverlapTester.overlapCircleRectangle(ball.bounds, brick.bounds)) {
				Log.d("World:checkBallCollisionsWithCeilingBricks", "A collision with a ceiling brick just happened!");
				ball.velocity.y = ball.velocity.y * (-1);
				listener.hitAtBrick();
				int column = brick.column;
				int row = brick.row;
				
				
				
				//the upper bricks on the floor will shift up
				for (int y = level - 1; y > 1; y--) {
					floorBricksId[column][y] = ceilingBricksId[column][y - 1];
					if (floorBricksId[column][y] != NO_OBJECT_ID)
						floorBricks.get(floorBricksId[column][y]).setCell(column, y);
				}
				//Add the most upper ceiling brick into the floor List
				int nextId=floorBricks.size();
				//in case of brick collision the most top brick goes to floor:
				int movingToFloorId = ceilingBricksId[column][0];
				floorBricks.add(ceilingBricks.get(movingToFloorId));
				ceilingBricks.get(movingToFloorId).atCeiling = false;	
				ceilingBricks.remove(movingToFloorId);
				//floorBricks.get(movingToFloorId).setCell(column, 0);
				

				//other bricks in this column will shift one cell up:
				for (int y = 1; y < row; y++) {
					ceilingBricksId[column][y - 1] = ceilingBricksId[column][y];
					if (ceilingBricksId[column][y - 1] != NO_OBJECT_ID)
						ceilingBricks.get(ceilingBricksId[column][y]).setCell(column, y - 1);
				}
				//put "empty" at the cell which was hit
				ceilingBricksId[column][row] = NO_OBJECT_ID;
				
				
//				ceilingBricks.remove(i);
//				ceilingBricksId[column][row] = NO_OBJECT_ID;
//				brick.atCeiling = false;
//				int nextId=floorBricks.size();
//				floorBricksId[column][row] = nextId;
//				floorBricks.add(brick);
				brick.move();
				i--;
				len--;
				
				for (int r = 0; r < level; r++) {
					for (int c = 0; c < COLUMNS; c++) {
						Log.d("World:checkBallCollisionsWithCeilingBricks", "c = " + c);
						Log.d("World:checkBallCollisionsWithCeilingBricks", "r = " + r);
						Log.d("World:checkBallCollisionsWithCeilingBricks", "ceilingBricksId[][] = " + ceilingBricksId[c][r]);

						Log.d("World:checkBallCollisionsWithCeilingBricks", "floorBricksId[][] = " + floorBricksId[c][r]);
					}
					
				}
				
				break;
			}
		}
	}

	private void checkBallCollisionsWithFloorBricks(){
		int len = floorBricks.size();
		for (int i = 0; i < len; i++) {
			Brick brick = floorBricks.get(i);
			if(OverlapTester.overlapCircleRectangle(ball.bounds, brick.bounds)) {
				ball.velocity.y = ball.velocity.y * (-1);
				listener.hitAtBrick();
				int column = brick.column;
				int row = brick.row;
				floorBricks.remove(i);
				floorBricksId[column][row] = NO_OBJECT_ID;
				brick.atCeiling = true;
				int nextId=ceilingBricks.size();
				ceilingBricksId[column][row] = nextId;
				ceilingBricks.add(brick);
				brick.move();
				i--;
				len--;
				break;
			}
		}
	}

	private void checkGameOver() {
		if (ballsLeft < 1) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
