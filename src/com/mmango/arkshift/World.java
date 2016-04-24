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

	public final static int NO_COLLISION = 0;
	public final static int COLLISION_WITH_X = 1;
	public final static int COLLISION_WITH_Y = 2;
	// public final static int COLLISION_WITH_CORNER = 3;

	// for calculating the overlapped brick border:
	public final static int TOP_BORDER = 1;
	public final static int BOTTOM_BORDER = 2;
	public final static int LEFT_BORDER = 3;
	public final static int RIGHT_BORDER = 4;
	public final static int COLLISION_WITH_CORNER = 5;

	public static Rectangle gameField;

	public Racquet racquet;
	public Ball ball;
	public final List<Brick> bricks;
	public final List<Brick> bricksCeiling;
	public int[][] ceilingBricksId;
	public int lastCeilingBrickId;
	public int[][] floorBricksId;

	public final WorldListener listener;
	public final Random rand;
	public int ballsLeft;
	public int score;
	public int state;
	public int level;
	public int bricksArraySize;

	public World(WorldListener listener) {
		level = 2;

		gameField = new Rectangle(FRAME_WIDTH, FRAME_WIDTH, GAME_FIELD_WIDTH,
				GAME_FIELD_HEIGHT);

		// Log.d("World", "gameField.lowerLeft.x = " + gameField.lowerLeft.x);
		// Log.d("World", "gameField.lowerLeft.y = " + gameField.lowerLeft.y);
		// Log.d("World", "gameField.width = " + gameField.width);
		// Log.d("World", "gameField.height = " + gameField.height);

		rand = new Random();

		// this.racquet = new Racquet(WORLD_WIDTH / 2, FRAME_WIDTH +
		// Brick.BRICK_HEIGHT * (level) + Racquet.RACQUET_HEIGHT / 2 + 0.5f);
		this.racquet = new Racquet(WORLD_WIDTH / 2, FRAME_WIDTH
				+ Racquet.RACQUET_HEIGHT / 2 + 0.5f,
				Racquet.RACQUET_WIDTH_NORMAL);

		// randomize the x coordinate of the ball on the racquet: shift it from
		// the center of the racquet in range from -18f to +18f
		float ballXOffset = rand.nextFloat() * (racquet.racquetWidth * 0.8f)
				- (racquet.racquetWidth * 0.8f) / 2;
		// Log.d("World", "ballXOffset = " + Float.toString(ballXOffset));

		this.ball = new Ball(WORLD_WIDTH / 2 + ballXOffset, racquet.position.y
				+ Racquet.RACQUET_HEIGHT / 2 + Ball.BALL_DIAMETER / 2,
				Ball.BALL_COLOR_WHITE);
		this.bricks = new ArrayList<Brick>();
		this.bricksCeiling = new ArrayList<Brick>();
		this.lastCeilingBrickId = 9999;
		// this.floorBricks = new ArrayList<Brick>();
		this.listener = listener;

		ballsLeft = 9;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
		generateLevel(COLUMNS, level);
	}

	private void generateLevel(int columns, int rows) {
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

				Brick brick = new Brick(x, y, brick_color);
				// 2.0f + 5.2f + 10.4f, 192 - 17 - 5.2f
				// Log.d("World", "Adding a brick");
				bricks.add(brick);
				ceilingBricksId[x][y] = brickId;
				brickId++;
				// populate the floorBricksId array with 99999 which stands for
				// "there is no brick"
				floorBricksId[x][y] = NO_OBJECT_ID;
				
				//save the list of all bricks in the bricksCeiling array list
				bricksCeiling.add(brick);
			}
		}

		bricksArraySize = bricks.size();
		

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

		// int collisionStatus = NO_COLLISION;
		for (int i = 0; i < bricksArraySize; i++) {
			Brick brick = bricks.get(i);
			if (brick.state != Brick.BRICK_STATE_STILL)
				brick.update(deltaTime);
		}
		if(bricksCeiling.isEmpty()) {
			if (bricks.get(lastCeilingBrickId).state == Brick.BRICK_STATE_STILL) {
				state = WORLD_STATE_NEXT_LEVEL;
			}
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
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);
			// X collision
			ball.position.y = FRAME_WIDTH + GAME_FIELD_HEIGHT
					- Ball.BALL_RADIUS;
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == FRAME_BOTTOM_BORDER_ID) {
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);

			ball.position.y = FRAME_WIDTH + Ball.BALL_RADIUS;
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == FRAME_LEFT_BORDER_ID) {
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);

			// Y collision
			ball.position.x = FRAME_WIDTH + Ball.BALL_RADIUS;
			ball.velocity.x = ball.velocity.x * (-1);
		} else if (breaktrhough == FRAME_RIGHT_BORDER_ID) {
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);

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
			// Log.d("World:checkBallCollisionsWithRacquet:checkBallCollisionsWithRacquet",
			// "there was a contact!");
			// Log.d("World:checkBallCollisionsWithRacquet:checkBallCollisionsWithRacquet",
			// "racquet.racquetWidth = " + racquet.racquetWidth);
			//
			// Log.d("World:checkBallCollisionsWithRacquet", "oldAngle = "
			// + ball.velocity.angle());
			// Log.d("World:checkBallCollisionsWithRacquet", "racquetAngle = "
			// + racquet.velocity.angle());
			//
			// Log.d("World:checkBallCollisionsWithRacquet",
			// "before changing ball.velocity.y = " + ball.velocity.y);

			// ball.velocity.y = ball.velocity.y * (-1);

			// Log.d("World:checkBallCollisionsWithRacquet",
			// "after changing ball.velocity.y = " + ball.velocity.y);
			if (ball.velocity.y < 0) { // only if the ball moves downward!
				// Log.d("World:checkBallCollisionsWithRacquet",
				// "Contact with the racket TOP!");
				ball.velocity.y = ball.velocity.y * (-1);
				ball.position.y = racquet.position.y + Racquet.RACQUET_HEIGHT
						/ 2 + Ball.BALL_RADIUS;

				float angleTmp = ball.velocity.angle();
				// Log.d("World:checkBallCollisionsWithRacquet", "angleTmp = "
				// + angleTmp);

				// Create a copy of ball.velocity
				Vector2 ballVelocityCopy = ball.velocity.cpy();

				// get sum of ballVelocityCopy and racquet.velocity
				ballVelocityCopy.add(racquet.velocity);

				// get the angle between the temp ballVelocityCopy and X
				float angle = ballVelocityCopy.angle();
				// Log.d("World:checkBallCollisionsWithRacquet", "angle = "
				// + angle);

				// avoid too flat angles, if the angle is less that 45 degrees
				// than make it equal 45 + a random float between 1f to 5f
				if ((angle > 90f) && (angle > 135f)) {
					float randangle = rand.nextFloat() * (5 - 1) + 1;
					// Log.d("World:checkBallCollisionsWithRacquet",
					// "randangle = " + randangle);
					angle = 135f - randangle;
				}
				if ((angle < 90f) && (angle < 45f)) {
					float randangle = rand.nextFloat() * (5 - 1) + 1;
					// Log.d("World:checkBallCollisionsWithRacquet",
					// "randangle = " + randangle);
					angle = 45 + randangle;
				}
				float newAngle = angle - angleTmp;
				// rotate ball.velocity on that angle
				ball.velocity.rotate(newAngle);
				// Log.d("World:checkBallCollisionsWithRacquet", "newAngle = "
				// + newAngle);
			} else {
				// Log.d("World:checkBallCollisionsWithRacquet",
				// "Contact with the racket BOTTOM!");
				ball.position.y = racquet.position.y - Racquet.RACQUET_HEIGHT
						/ 2 - Ball.BALL_RADIUS;
				ball.velocity.y = ball.velocity.y * (-1);
			}

		}
	}

	private void checkBallCollisionsWithBricks() {

		Log.d("World:checkBallCollisionsWithBricks",
				"--------------------------");

		// int collisionStatus = NO_COLLISION;
		for (int i = 0; i < bricksArraySize; i++) {
			Brick brick = bricks.get(i);

			if ((OverlapTester
					.overlapCircleRectangle(ball.bounds, brick.bounds) && (brick.state == Brick.BRICK_STATE_STILL))) {
				// Log.d("World:checkBallCollisionsWithBricks",
				// "A collision with a brick just happened!");
				// ball.velocity.y = ball.velocity.y * (-1);

				int scoringSign = (brick.atCeiling) ? 1 : -2; // if the ceiling
																// brick hit
																// then we
																// increase the
																// score; else -
																// decrease;

				int collisionStatus = MyOverlapTester
						.overlapCircleRectangleAdv(ball.bounds, brick.bounds);

				Log.d("World:checkBallCollisionsWithBricks", "brick id = " + i);
				Log.d("World:checkBallCollisionsWithBricks",
						"collisionStatus = " + collisionStatus);
				Log.d("World:checkBallCollisionsWithBricks",
						"brick.bounds.lowerLeft.x = "
								+ brick.bounds.lowerLeft.x);
				Log.d("World:checkBallCollisionsWithBricks",
						"brick.bounds.lowerLeft.y = "
								+ brick.bounds.lowerLeft.y);
				// Log.d("World:checkBallCollisionsWithBricks", "brick.state = "
				// + brick.state);
				Log.d("World:checkBallCollisionsWithBricks",
						"Old ball.position x = " + ball.position.x + "; y = "
								+ ball.position.y);

				switch (collisionStatus) {
				case BOTTOM_BORDER:
					ball.position.y = brick.bounds.lowerLeft.y
							- Ball.BALL_RADIUS;
					Log.d("World:checkBallCollisionsWithBricks",
							"New ball.position x = " + ball.position.x
									+ "; y = " + ball.position.y);
					ball.velocity.y = ball.velocity.y * (-1);
					break;

				case TOP_BORDER:
					ball.position.y = brick.bounds.lowerLeft.y
							+ brick.bounds.height + Ball.BALL_RADIUS;
					Log.d("World:checkBallCollisionsWithBricks",
							"New ball.position x = " + ball.position.x
									+ "; y = " + ball.position.y);
					ball.velocity.y = ball.velocity.y * (-1);
					break;

				case LEFT_BORDER:
					ball.position.x = brick.bounds.lowerLeft.x
							- Ball.BALL_RADIUS;
					Log.d("World:checkBallCollisionsWithBricks",
							"New ball.position x = " + ball.position.x
									+ "; y = " + ball.position.y);
					ball.velocity.x = ball.velocity.x * (-1);
					break;

				case RIGHT_BORDER:
					ball.position.x = brick.bounds.lowerLeft.x
							+ brick.bounds.width + Ball.BALL_RADIUS;
					Log.d("World:checkBallCollisionsWithBricks",
							"New ball.position x = " + ball.position.x
									+ "; y = " + ball.position.y);
					ball.velocity.x = ball.velocity.x * (-1);
					break;

				case COLLISION_WITH_CORNER:
					ball.velocity.mul(-1);
					break;
				default:
					break;
				}

				listener.hitAtBrick();
				int column = brick.column;
				// int row = brick.row;

				// customize gameplay

				switch (brick.color) {
				case Brick.BRICK_COLOR_RED: // if a red brick hit then
											// DOUBLE the ball acceleration.
											// Also set the
											// ball color to RED
					if (ball.ballAccel == Ball.BALL_NORMAL_ACCELL) {
						ball.ballAccel = Ball.BALL_DOUBLE_ACCELL;
						ball.setBallColor(Ball.BALL_COLOR_RED);
					}

					score += 2 * scoringSign;
					break;
				case Brick.BRICK_COLOR_GREEN: // if a green brick hit then
					// restore the NORMAL ball acceleration. Also set the
					// ball color to GREEN
					if (ball.ballAccel == Ball.BALL_DOUBLE_ACCELL) {
						ball.ballAccel = Ball.BALL_NORMAL_ACCELL;
						ball.setBallColor(Ball.BALL_COLOR_WHITE);
					}

					score += 2 * scoringSign;
					break;

				case Brick.BRICK_COLOR_GREY: // if a grey brick was hit then
												// switch the racquet width
												// between normal/narrow
					racquet.racquetWidth = (racquet.racquetWidth == Racquet.RACQUET_WIDTH_NORMAL) ? Racquet.RACQUET_WIDTH_NARROW
							: Racquet.RACQUET_WIDTH_NORMAL;
					score += 2 * scoringSign;
					break;

				default:
					score += 1 * scoringSign;
					break;

				}

				// We do not care which brick in the column was hit,
				// we always shift the whole column either up or down

				if (brick.atCeiling) {

					// Log.d("World:checkBallCollisionsWithBricks",
					// "A collision with a ceiling brick just happened!");

					// the bricks on the floor will shift up
					for (int y = level - 1; y > 0; y--) {
						floorBricksId[column][y] = floorBricksId[column][y - 1];
						if (floorBricksId[column][y] != NO_OBJECT_ID) {
							bricks.get(floorBricksId[column][y]).setCell(
									column, y);
							bricks.get(floorBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_UP;
						}
					}

					// the uppermost ceiling brick [row=0] goes to the floor
					// [row=0]:
					int topBrick = ceilingBricksId[column][0];
					//exclude this brick from bricksCeiling array list
					lastCeilingBrickId = topBrick;
					bricksCeiling.remove(bricks.get(topBrick));
				
					floorBricksId[column][0] = topBrick;
					bricks.get(topBrick).atCeiling = false;
					bricks.get(topBrick).setCell(column, 0);
					bricks.get(topBrick).state = Brick.BRICK_STATE_SHIFTING_UP_TO_FLOOR;
					// Log.d("World:checkBallCollisionsWithBricks",
					// "bricks.get(topBrick).state = " +
					// bricks.get(topBrick).state);
					// other ceiling bricks in this column will shift one cell
					// up:
					for (int y = 0; y < level - 1; y++) {
						ceilingBricksId[column][y] = ceilingBricksId[column][y + 1];
						if (ceilingBricksId[column][y] != NO_OBJECT_ID) {
							bricks.get(ceilingBricksId[column][y]).setCell(
									column, y);
							bricks.get(ceilingBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_UP;
						}
					}
					// always put "empty" at the cell in last row of the ceiling
					// when the ceiling got hit
					if (level > 1)
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
					// Log.d("World:checkBallCollisionsWithBricks",
					// "A collision with a ceiling brick just happened!");
					// the bricks on the floor will shift down
					for (int y = level - 1; y > 0; y--) {
						ceilingBricksId[column][y] = ceilingBricksId[column][y - 1];
						if (ceilingBricksId[column][y] != NO_OBJECT_ID) {
							bricks.get(ceilingBricksId[column][y]).setCell(
									column, y);
							bricks.get(ceilingBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_DOWN;
						}
					}

					// the bottommost brick [row=0] goes to the ceiling [row=0]:
					int bottomBrick = floorBricksId[column][0];
					ceilingBricksId[column][0] = bottomBrick;
					//Include this brick back into bricksCeiling array list
					bricksCeiling.add(bricks.get(bottomBrick));
					bricks.get(bottomBrick).atCeiling = true;
					bricks.get(bottomBrick).setCell(column, 0);
					bricks.get(bottomBrick).state = Brick.BRICK_STATE_SHIFTING_DOWN_TO_CEILING;

					// other floor bricks in this column will shift one cell
					// down:
					for (int y = 0; y < level - 1; y++) {
						floorBricksId[column][y] = floorBricksId[column][y + 1];
						if (floorBricksId[column][y] != NO_OBJECT_ID) {
							bricks.get(floorBricksId[column][y]).setCell(
									column, y);
							bricks.get(floorBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_DOWN;
						}
					}

					// always put "empty" at the cell in last row of the floor
					// when the floor got hit
					if (level > 1)
						floorBricksId[column][level - 1] = NO_OBJECT_ID;
					break;
				}

			}

		}

	}

	private void checkGameOver() {
		if (ballsLeft < 1) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
