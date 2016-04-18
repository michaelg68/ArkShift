package com.mmango.arkshift;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

import com.mmango.games.framework.math.MyOverlapTester;
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
	public static final int NO_OBJECT_ID = 99999; // an empty cell

	// public final static int NO_COLLISION = 0;
	// public final static int COLLISION_WITH_X = 1;
	// public final static int COLLISION_WITH_Y = 2;

	public static Rectangle gameField;

	public Racquet racquet;
	public Ball ball;
	public final List<Brick> bricks;
	public int[][] ceilingBricksId;
	// public final List<Brick> floorBricks;
	public int[][] floorBricksId;

	public final WorldListener listener;
	public final Random rand;
	public int ballsLeft;
	public int score;
	public int state;
	public int level;

	public World(WorldListener listener) {
		level = 6;

		gameField = new Rectangle(FRAME_WIDTH, FRAME_WIDTH, GAME_FIELD_WIDTH,
				GAME_FIELD_HEIGHT);
		rand = new Random();

		// this.racquet = new Racquet(WORLD_WIDTH / 2, FRAME_WIDTH +
		// Brick.BRICK_HEIGHT * (level) + Racquet.RACQUET_HEIGHT / 2 + 0.5f);
		this.racquet = new Racquet(WORLD_WIDTH / 2, FRAME_WIDTH
				+ Racquet.RACQUET_HEIGHT / 2 + 0.5f);

		// randomize the x coordinate of the ball on the racquet: shift it from
		// the center of the racquet in range from -18f to +18f
		float ballXOffset = rand.nextFloat() * (Racquet.RACQUET_WIDTH * 0.8f)
				- (Racquet.RACQUET_WIDTH * 0.8f) / 2;
		// Log.d("World", "ballXOffset = " + Float.toString(ballXOffset));

		this.ball = new Ball(WORLD_WIDTH / 2 + ballXOffset, racquet.position.y
				+ Racquet.RACQUET_HEIGHT / 2 + Ball.BALL_DIAMETER / 2,
				Assets.ballWhite);
		this.bricks = new ArrayList<Brick>();
		// this.floorBricks = new ArrayList<Brick>();
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
				bricks.add(brick);
				ceilingBricksId[x][y] = brickId;
				brickId++;
				// populate the floorBricksId array with 99999 which stands for
				// "there is no brick"
				floorBricksId[x][y] = NO_OBJECT_ID;
			}
		}

	}

	public void update(float deltaTime, float accelX) {
		updateRacquet(deltaTime, accelX);
		updateBall(deltaTime);
		checkBallCollisions();
		updateBricks(deltaTime);

		// checkGameOver();
	}

	private void updateRacquet(float deltaTime, float accelX) {
		// calculate racquet Y coordinate
		int bricksInTheHighestFloorColumn = 0;
		for (int y = 0; y < level; y++) {
			for (int x = 0; x < COLUMNS; x++) {
				if (floorBricksId[x][y] != NO_OBJECT_ID) {
					bricksInTheHighestFloorColumn = y + 1;
					break;
				}
			}
		}
		racquet.position.y = FRAME_WIDTH + Brick.BRICK_HEIGHT
				* bricksInTheHighestFloorColumn + Racquet.RACQUET_HEIGHT / 2
				+ 0.5f;
		racquet.update(deltaTime, accelX);
	}

	private void updateBall(float deltaTime) {
		ball.update(deltaTime);
	}

	private void updateBricks(float deltaTime) {
		int len = bricks.size();
		// int collisionStatus = NO_COLLISION;
		for (int i = 0; i < len; i++) {
			Brick brick = bricks.get(i);
			if(brick.state != Brick.BRICK_STATE_STILL)
					brick.update(deltaTime);
		}
	}

	private void checkBallCollisions() {
		checkBallCollisionsWithFrame();
		checkBallCollisionsWithRacquet();
		checkBallCollisionsWithBricks();
		// checkBallCollisionsWithFloorBricks();

	}

	private void checkBallCollisionsWithFrame() {
		int breaktrhough = MyOverlapTester.circleCompletelyInsideRectangle(
				ball.bounds, gameField);
		if (breaktrhough == FRAME_TOP_BORDER_ID) {
			// X collision
			ball.position.y = FRAME_WIDTH + GAME_FIELD_HEIGHT
					- Ball.BALL_RADIUS;
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == FRAME_BOTTOM_BORDER_ID) {
			ball.position.y = FRAME_WIDTH + Ball.BALL_RADIUS;
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == FRAME_LEFT_BORDER_ID) {
			// Y collision
			ball.position.x = FRAME_WIDTH + Ball.BALL_RADIUS;
			ball.velocity.x = ball.velocity.x * (-1);
		} else if (breaktrhough == FRAME_RIGHT_BORDER_ID) {
			// Y collision
			ball.position.x = FRAME_WIDTH + GAME_FIELD_WIDTH - Ball.BALL_RADIUS;
			ball.velocity.x = ball.velocity.x * (-1);
		}
	}

	private void checkBallCollisionsWithRacquet() {
		boolean racquetContact = OverlapTester.overlapCircleRectangle(
				ball.bounds, racquet.bounds);
		// Log.d("World:checkBallCollisionsWithRacquet", "racquetContact = " +
		// racquetContact );
		if (racquetContact) {
//			Log.d("World:checkBallCollisionsWithRacquet",
//					"there was a contact!");
//
//			Log.d("World:checkBallCollisionsWithRacquet", "oldAngle = "
//					+ ball.velocity.angle());
//			Log.d("World:checkBallCollisionsWithRacquet", "racquetAngle = "
//					+ racquet.velocity.angle());
//
//			Log.d("World:checkBallCollisionsWithRacquet",
//					"before changing ball.velocity.y = " + ball.velocity.y);

			// ball.velocity.y = ball.velocity.y * (-1);

//			Log.d("World:checkBallCollisionsWithRacquet",
//					"after changing ball.velocity.y = " + ball.velocity.y);
			if (ball.velocity.y < 0) { // only if the ball moves downward!
				Log.d("World:checkBallCollisionsWithRacquet",
						"Contact with the racket TOP!");
				ball.velocity.y = ball.velocity.y * (-1);
				ball.position.y = racquet.position.y + Racquet.RACQUET_HEIGHT
						/ 2 + Ball.BALL_RADIUS;

				float angleTmp = ball.velocity.angle();
//				Log.d("World:checkBallCollisionsWithRacquet", "angleTmp = "
//						+ angleTmp);

				// Create a copy of ball.velocity
				Vector2 ballVelocityCopy = ball.velocity.cpy();

				// get sum of ballVelocityCopy and racquet.velocity
				ballVelocityCopy.add(racquet.velocity);

				// get the angle between the temp ballVelocityCopy and X
				float angle = ballVelocityCopy.angle();
//				Log.d("World:checkBallCollisionsWithRacquet", "angle = "
//						+ angle);

				// avoid too flat angles, if the angle is less that 45 degrees
				// than make it equal 45 + a random float between 1f to 5f
				if ((angle > 90f) && (angle > 135f)) {
					float randangle = rand.nextFloat() * (5 - 1) + 1;
//					Log.d("World:checkBallCollisionsWithRacquet",
//							"randangle = " + randangle);
					angle = 135f - randangle;
				}
				if ((angle < 90f) && (angle < 45f)) {
					float randangle = rand.nextFloat() * (5 - 1) + 1;
//					Log.d("World:checkBallCollisionsWithRacquet",
//							"randangle = " + randangle);
					angle = 45 + randangle;
				}
				float newAngle = angle - angleTmp;
				// rotate ball.velocity on that angle
				ball.velocity.rotate(newAngle);
//				Log.d("World:checkBallCollisionsWithRacquet", "newAngle = "
//						+ newAngle);
			} else {
				Log.d("World:checkBallCollisionsWithRacquet",
						"Contact with the racket BOTTOM!");
				ball.position.y = racquet.position.y - Racquet.RACQUET_HEIGHT
						/ 2 - Ball.BALL_RADIUS;
				ball.velocity.y = ball.velocity.y * (-1);
			}

		}
	}

	private void checkBallCollisionsWithBricks() {
		int len = bricks.size();
		// int collisionStatus = NO_COLLISION;
		for (int i = 0; i < len; i++) {
			Brick brick = bricks.get(i);
			// Log.d("World:checkBallCollisionsWithBricks", "brick id = " + i);
			// Log.d("World:checkBallCollisionsWithBricks",
			// "brick.bounds.lowerLeft.x = " + brick.bounds.lowerLeft.x);
			// Log.d("World:checkBallCollisionsWithBricks",
			// "brick.bounds.lowerLeft.y = " + brick.bounds.lowerLeft.y);
			if (OverlapTester.overlapCircleRectangle(ball.bounds, brick.bounds)) {
				ball.velocity.y = ball.velocity.y * (-1);

				listener.hitAtBrick();
				int column = brick.column;
				// int row = brick.row;

				// We do not care which brick in the column was hit,
				// we always shift the whole column either up or down

				if (brick.atCeiling) {

					Log.d("World:checkBallCollisionsWithBricks",
							"A collision with a ceiling brick just happened!");

					// the upper bricks on the floor will shift up
					for (int y = level - 1; y > 0; y--) {
						floorBricksId[column][y] = floorBricksId[column][y - 1];
						if (floorBricksId[column][y] != NO_OBJECT_ID){
							bricks.get(floorBricksId[column][y]).setCell(column, y);
							bricks.get(floorBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_UP;
						}
					}

					// the uppermost brick [row=0] goes to the floor [row=0]:
					int topBrick = ceilingBricksId[column][0];
					floorBricksId[column][0] = topBrick;
					bricks.get(topBrick).atCeiling = false;
					bricks.get(topBrick).setCell(column, 0);
					bricks.get(topBrick).state = Brick.BRICK_STATE_SHIFTING_UP_TO_FLOOR;

					// other ceiling bricks in this column will shift one cell
					// up:
					for (int y = 0; y < level - 1; y++) {
						ceilingBricksId[column][y] = ceilingBricksId[column][y + 1];
						if (ceilingBricksId[column][y] != NO_OBJECT_ID)
							bricks.get(ceilingBricksId[column][y]).setCell(column, y);
							bricks.get(ceilingBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_UP;
					}
					// always put "empty" at the cell in last row of the ceiling
					// when the ceiling got hit
					ceilingBricksId[column][level - 1] = NO_OBJECT_ID;

					/*
					 * for (int r = 0; r < level; r++) { for (int c = 0; c <
					 * COLUMNS; c++) {
					 * Log.d("World:checkBallCollisionsWithCeilingBricks",
					 * "c = " + c);
					 * Log.d("World:checkBallCollisionsWithCeilingBricks",
					 * "r = " + r);
					 * Log.d("World:checkBallCollisionsWithCeilingBricks",
					 * "ceilingBricksId[][] = " + ceilingBricksId[c][r]);
					 * Log.d("World:checkBallCollisionsWithCeilingBricks",
					 * "floorBricksId[][] = " + floorBricksId[c][r]); }
					 * 
					 * }
					 */
					break;
				} else { // the collisions happened with a bottom brick
					Log.d("World:checkBallCollisionsWithBricks",
							"A collision with a ceiling brick just happened!");
					// the bricks on the floor will shift down
					for (int y = level - 1; y > 0; y--) {
						ceilingBricksId[column][y] = ceilingBricksId[column][y - 1];
						if (ceilingBricksId[column][y] != NO_OBJECT_ID)
							bricks.get(ceilingBricksId[column][y]).setCell(
									column, y);
					}

					// the bottommost brick [row=0] goes to the ceiling [row=0]:
					int bottomBrick = floorBricksId[column][0];
					ceilingBricksId[column][0] = bottomBrick;
					bricks.get(bottomBrick).atCeiling = true;
					bricks.get(bottomBrick).setCell(column, 0);

					// other floor bricks in this column will shift one cell
					// down:
					for (int y = 0; y < level - 1; y++) {
						floorBricksId[column][y] = floorBricksId[column][y + 1];
						if (floorBricksId[column][y] != NO_OBJECT_ID)
							bricks.get(floorBricksId[column][y]).setCell(
									column, y);
					}

					// always put "empty" at the cell in last row of the floor
					// when the floor got hit
					floorBricksId[column][level - 1] = NO_OBJECT_ID;
					break;
				}

			}

		}

	}

	// private void checkBallCollisionsWithFloorBricks(){
	// int len = floorBricks.size();
	// for (int i = 0; i < len; i++) {
	// Brick brick = floorBricks.get(i);
	// if(OverlapTester.overlapCircleRectangle(ball.bounds, brick.bounds)) {
	// ball.velocity.y = ball.velocity.y * (-1);
	// listener.hitAtBrick();
	// int column = brick.column;
	// int row = brick.row;
	// floorBricks.remove(i);
	// floorBricksId[column][row] = NO_OBJECT_ID;
	// brick.atCeiling = true;
	// int nextId=ceilingBricks.size();
	// ceilingBricksId[column][row] = nextId;
	// ceilingBricks.add(brick);
	// brick.move();
	// i--;
	// len--;
	// break;
	// }
	// }
	// }

	private void checkGameOver() {
		if (ballsLeft < 1) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
