package com.mmango.arkshift;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.math.Circle;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.mmango.games.framework.math.MyOverlapTester;

public class World {
	public interface WorldListener {
		public void hitAtRacquet();

		public void hitAtBrick();

		// public void hitAtBrickFloor();

		public void hitAtFrame();

		public void shiftBrick();

		public void gameOver();

		public void levelPassed();

		public void levelBegins();
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
	public boolean ballReady;
	public int score;
	public int state;
	public int level = 1;
	public int bricksArraySize;

	public World(WorldListener listener, int level, int balls) {
		this.level = level;

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

		ballsLeft = balls;
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
				int brick_color = rand.nextInt(9); // generate colors from 0 to
													// 8. not including 9 which
													// is GOLD (an additional
													// ball)
				if (rand.nextFloat() > 0.96f) { // Approximately 2 brick of 100
												// will be gold
					// hitting a gold brick will bring an extra ball
					brick_color = Brick.BRICK_COLOR_GOLD;
				}

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

				// save the list of all bricks in the bricksCeiling array list
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
		if (bricksInTheHighestFloorColumn == 0) { // if no bricks on the floor
			if (ball.position.y < racquet.position.y) {
				if ((ball.position.x >= racquet.bounds.lowerLeft.x)
						&& (ball.position.x <= racquet.bounds.lowerLeft.x
								+ racquet.bounds.width)) { // and the ball is
															// just under the
															// racquet then
															// donot
					racquet.position.y = FRAME_WIDTH + Brick.BRICK_HEIGHT
							+ Racquet.RACQUET_HEIGHT / 2 + 0.5f;
				}
			}
		}
		racquet.update(deltaTime, accelX);
	}

	private void updateBall(float deltaTime) {
		ball.update(deltaTime);
		if (ballsLeft < 1) // if no more balls then game is over
			state = WORLD_STATE_GAME_OVER;
	}

	private void updateBricks(float deltaTime) {

		// int collisionStatus = NO_COLLISION;
		for (int i = 0; i < bricksArraySize; i++) {
			Brick brick = bricks.get(i);
			if (brick.state != Brick.BRICK_STATE_STILL)
				brick.update(deltaTime);
		}
		if (bricksCeiling.isEmpty()) {
			if (bricks.get(lastCeilingBrickId).state == Brick.BRICK_STATE_STILL) {
				state = WORLD_STATE_NEXT_LEVEL;
			}
		}

	}

	private void checkBallCollisions() {
		checkBallCollisionsWithFrame();
		checkBallCollisionsWithRacquet();
		checkBallCollisionsWithBricks();

	}

	private void checkBallCollisionsWithFrame() {
		int breaktrhough = MyOverlapTester.circleCompletelyInsideRectangle(
				ball.bounds, gameField);

		if (breaktrhough == FRAME_TOP_BORDER_ID) {
			listener.hitAtFrame();
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);
			// X collision
			ball.position.y = FRAME_WIDTH + GAME_FIELD_HEIGHT
					- Ball.BALL_RADIUS;
			ball.velocity.y = ball.velocity.y * (-1);
			ballReady = true; // if the frame top border is hit then consider
								// the ball ready

		} else if (breaktrhough == FRAME_BOTTOM_BORDER_ID) {
			listener.hitAtFrame();
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);

			ball.position.y = FRAME_WIDTH + Ball.BALL_RADIUS;
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == FRAME_LEFT_BORDER_ID) {
			listener.hitAtFrame();
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);

			// Y collision
			ball.position.x = FRAME_WIDTH + Ball.BALL_RADIUS;
			ball.velocity.x = ball.velocity.x * (-1);
		} else if (breaktrhough == FRAME_RIGHT_BORDER_ID) {
			listener.hitAtFrame();
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

			listener.hitAtRacquet();
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
				Log.d("World:checkBallCollisionsWithRacquet",
				"Contact with the racket TOP!");
				ball.velocity.y = ball.velocity.y * (-1);
				ball.position.y = racquet.position.y + Racquet.RACQUET_HEIGHT
						/ 2 + Ball.BALL_RADIUS;

				float angleTmp = ball.velocity.angle();
				Log.d("World:checkBallCollisionsWithRacquet", "angleTmp = "
				+ angleTmp);

				// Create a copy of ball.velocity
				Vector2 ballVelocityCopy = ball.velocity.cpy();

				
				Log.d("World:checkBallCollisionsWithRacquet", "ballVelocityCopy.len = " + ballVelocityCopy.len() + "; ballVelocityCopy.angle = " + ballVelocityCopy.angle());
				Log.d("World:checkBallCollisionsWithRacquet", "racquet.velocity.len = " + racquet.velocity.len() + "; racquet.velocity.angle = " + racquet.velocity.angle());
				
				// get sum of ballVelocityCopy and racquet.velocity
				
				
				ballVelocityCopy.add(racquet.velocity);
				
				
				Log.d("World:checkBallCollisionsWithRacquet", "After adding the racquet velocity: ballVelocityCopy.len = " + ballVelocityCopy.len() + "; ballVelocityCopy.angle = " + ballVelocityCopy.angle());

				// get the angle between the temp ballVelocityCopy and X
				float angle = ballVelocityCopy.angle();
				Log.d("World:checkBallCollisionsWithRacquet", "angle = "
				 + angle);

				// avoid too flat angles, if the angle is less that 45 degrees
				// than make it equal 45 + a random float between 5f to 10f
				if ((angle > 90f) && (angle > 135f)) {
					float randangle = rand.nextFloat() * (10 - 5) + 1;
					Log.d("World:checkBallCollisionsWithRacquet",
					"(angle > 90f) && (angle > 135f). randangle = " + randangle);
					angle = 135f - randangle;
				}
				if ((angle < 90f) && (angle < 45f)) {
					float randangle = rand.nextFloat() * (10 - 5) + 1;
					Log.d("World:checkBallCollisionsWithRacquet",
					"(angle < 90f) && (angle < 45f). randangle = " + randangle);
					angle = 45 + randangle;
				}
				float newAngle = angle - angleTmp;
				// rotate ball.velocity on that angle
				ball.velocity.rotate(newAngle);
				Log.d("World:checkBallCollisionsWithRacquet", "newAngle = "
				+ newAngle);
			} else {
				//Log.d("World:checkBallCollisionsWithRacquet",
				//"Contact with the racket BOTTOM!");
				ball.position.y = racquet.position.y - Racquet.RACQUET_HEIGHT
						/ 2 - Ball.BALL_RADIUS;
				ball.velocity.y = ball.velocity.y * (-1);
			}

		}
	}

	private void checkBallCollisionsWithBricks() {

		List<Integer> bricksTouched = new ArrayList<Integer>();
		List<Integer> bricksAffected = new ArrayList<Integer>();
		boolean isCeiling = false;

		// int b = 0;
		int length = 0;
		// Log.d("World:checkBallCollisionsWithBricks", "----");

		// first I must check if the collision happened with more than one
		// brick!
		// Hopefully this will help to solve the problem of ball swallowing by
		// the bricks

		for (int i = 0; i < bricksArraySize; i++) { // find all bricks which
													// would overlap with the
													// ball in the next move
			Brick brick = bricks.get(i);
			if ((OverlapTester
					.overlapCircleRectangle(ball.bounds, brick.bounds) && (brick.state == Brick.BRICK_STATE_STILL))) {
				bricksTouched.add(i);

				// temporary, to test the level passed situation:
				// state = WORLD_STATE_NEXT_LEVEL;
			}
		}

		length = bricksTouched.size();
		if (length == 2) { // I expect it be not more than 2
			// Log.d("World:checkBallCollisionsWithBricks",
			// "More that one Bricks are touched! length = " + length);
			// for (int k = 0; k < length; k++) {
			// Log.d("World:checkBallCollisionsWithBricks",
			// "brickID in bricks = " + bricksTouched.get(k));
			// Log.d("World:checkBallCollisionsWithBricks", "brick color = "
			// + bricks.get(bricksTouched.get(k)).color);
			// Log.d("World:checkBallCollisionsWithBricks", "brick column = "
			// + bricks.get(bricksTouched.get(k)).column
			// + " ;brick row = "
			// + bricks.get(bricksTouched.get(k)).row);
			// Log.d("World:checkBallCollisionsWithBricks", "brick x = "
			// + bricks.get(bricksTouched.get(k)).x + " ;brick y = "
			// + bricks.get(bricksTouched.get(k)).y);
			// }

			// two bricks in the same row:
			if (bricks.get(bricksTouched.get(0)).row == bricks
					.get(bricksTouched.get(1)).row) {
				// Log.d("World:checkBallCollisionsWithBricks",
				// "Two bricks are in the same row. row = "
				// + bricks.get(bricksTouched.get(1)).row);

				// find the brick whose center.x is closer to the ball's
				// center.x
				// int brickID =
				// MyOverlapTester.overlapCircleRectangleHorisontal(ball.bounds,
				// bricks.get(bricksTouched.get(0)).bounds,
				// bricks.get(bricksTouched.get(1)).bounds);
				if ((ball.bounds.center.x <= bricks.get(bricksTouched.get(0)).x
						+ Brick.BRICK_WIDTH / 2)) {
					bricksAffected.add(bricksTouched.get(0)); // in this array
																// list I store
																// the ID of the
																// actually hit
																// brick
				} else {
					bricksAffected.add(bricksTouched.get(1)); // in this array
																// list I store
																// the ID of the
																// actually hit
																// brick
				}

				if (bricks.get(bricksAffected.get(0)).atCeiling) {
					ball.position.y = bricks.get(bricksAffected.get(0)).bounds.lowerLeft.y
							- Ball.BALL_RADIUS;
				} else {
					ball.position.y = bricks.get(bricksAffected.get(0)).bounds.lowerLeft.y
							+ Brick.BRICK_HEIGHT + Ball.BALL_RADIUS;
				}
				ball.velocity.y = ball.velocity.y * (-1);
			}

			// two bricks in the same column:
			if (bricks.get(bricksTouched.get(0)).column == bricks
					.get(bricksTouched.get(1)).column) {
				// Log.d("World:checkBallCollisionsWithBricks",
				// "Two bricks are in the same column. column = "
				// + bricks.get(bricksTouched.get(1)).column);

				// find the brick whose center.y is closer to the ball's
				// center.y
				// int brickID =
				// MyOverlapTester.overlapCircleRectangleVertical(ball.bounds,
				// bricks.get(bricksTouched.get(0)).bounds,
				// bricks.get(bricksTouched.get(1)).bounds);
				// the brick with lesser ID will be always on top of the brick
				// with bigger ID. either on ceiling or floor
				if ((ball.bounds.center.y >= bricks.get(bricksTouched.get(0)).y
						- Brick.BRICK_HEIGHT / 2)) {
					bricksAffected.add(bricksTouched.get(0)); // in this array
																// list I store
																// the ID of the
																// actually hit
																// brick
				} else {
					bricksAffected.add(bricksTouched.get(1)); // in this array
																// list I store
																// the ID of the
																// actually hit
																// brick
				}
				// now check which side of the brick is hit - left or right
				if (ball.velocity.x > 0) { // the ball was flying from left to
											// right so the left side is hit
					ball.position.x = bricks.get(bricksAffected.get(0)).bounds.lowerLeft.x
							- Ball.BALL_RADIUS; // the
					// brick
					// was
					// hit
					// from
					// the
					// left
				} else { // the ball was flying from right to left so the rigth
							// side is hit
					ball.position.x = bricks.get(bricksAffected.get(0)).bounds.lowerLeft.x
							+ Brick.BRICK_WIDTH + Ball.BALL_RADIUS; // the brick
																	// was hit
																	// from the
																	// right

				}
				ball.velocity.x = ball.velocity.x * (-1);
			}

			if ((bricks.get(bricksTouched.get(0)).row != bricks
					.get(bricksTouched.get(1)).row)
					&& (bricks.get(bricksTouched.get(0)).column != bricks
							.get(bricksTouched.get(1)).column)) {
				Log.d("World:checkBallCollisionsWithBricks",
						"Two bricks are in the different rows and columns! This is a IN-CORNER collision");
				// we consider that both bricks have been affected
				bricksAffected.add(bricksTouched.get(0));
				bricksAffected.add(bricksTouched.get(1));

				if (bricks.get(bricksTouched.get(0)).atCeiling)
					isCeiling = true;
				// MyOverlapTester.overlapCircleInCorner(ball.bounds,
				// bricks.get(bricksTouched.get(0)).bounds,
				// bricks.get(bricksTouched.get(1)).bounds, isCeling);
				Rectangle r0 = bricks.get(bricksTouched.get(0)).bounds;
				Rectangle r1 = bricks.get(bricksTouched.get(1)).bounds;

				if (isCeiling) { // hitting the ceiling bricks
					ball.position.y = Math.max(r0.lowerLeft.y, r1.lowerLeft.y)
							- ball.bounds.radius;
					if (ball.velocity.x < 0) { // the ball is moving left. Case
												// A.
						ball.position.x = Math.max(r0.lowerLeft.x,
								r1.lowerLeft.x) + ball.bounds.radius;
					} else { // the ball is moving right. Case B.
						ball.position.x = Math.max(r0.lowerLeft.x,
								r1.lowerLeft.x) - ball.bounds.radius;
					}
				} else { // hitting the floor bricks
					ball.position.y = Math.min(r0.lowerLeft.y, r1.lowerLeft.y)
							+ r0.height + ball.bounds.radius;
					if (ball.velocity.x < 0) { // the ball is moving left. Case
												// C.
						ball.position.x = Math.max(r0.lowerLeft.x,
								r1.lowerLeft.x) + ball.bounds.radius;
					} else { // the ball is moving right. Case D.
						ball.position.x = Math.max(r0.lowerLeft.x,
								r1.lowerLeft.x) - ball.bounds.radius;
					}

				}

				ball.velocity.mul(-1);

			}

		} else if (bricksTouched.size() == 3) {
			// Log.d("World:checkBallCollisionsWithBricks",
			// "Three bricks would be overlaped. This is a kind of IN-CORNER collision");
			bricksAffected.add(bricksTouched.get(0));
			bricksAffected.add(bricksTouched.get(1));
			bricksAffected.add(bricksTouched.get(2));
			ball.velocity.mul(-1);

		} else if (bricksTouched.size() == 1) { // only one brick would be
												// overlap
			bricksAffected.add(bricksTouched.get(0)); // in this array list I
														// store the ID of the
														// actually hit brick

			Brick brick = bricks.get(bricksAffected.get(0));
			Circle c = ball.bounds;
			Rectangle r = brick.bounds;
			if (bricks.get(bricksTouched.get(0)).atCeiling)
				isCeiling = true;
			if (isCeiling) {
				if (ball.velocity.x > 0) { // the ball is flying right
					if (ball.velocity.y > 0) { // and up
						if ((c.center.x > r.lowerLeft.x)
								&& (c.center.y < r.lowerLeft.y)) { // case A
							ball.position.y = r.lowerLeft.y - Ball.BALL_RADIUS;
							ball.velocity.y *= -1;
						} else if ((c.center.y > r.lowerLeft.y)
								&& (c.center.x < r.lowerLeft.x)) { // case B
							ball.position.x = r.lowerLeft.x - Ball.BALL_RADIUS;
							ball.velocity.x *= -1;
						} else { // hit at the corner, case C
							ball.position.x = r.lowerLeft.x - Ball.BALL_RADIUS;
							ball.position.y = r.lowerLeft.y - Ball.BALL_RADIUS;
							ball.velocity.mul(-1);
						}
					} else { // and down
						ball.position.x = r.lowerLeft.x - Ball.BALL_RADIUS;
						ball.velocity.x *= -1;
					}
				} else if (ball.velocity.x < 0) { // the ball is flying left
					if (ball.velocity.y > 0) { // and up
						if ((c.center.x < r.lowerLeft.x + r.width)
								&& (c.center.y < r.lowerLeft.y)) { // case A
							ball.position.y = r.lowerLeft.y - Ball.BALL_RADIUS;
							ball.velocity.y *= -1;
						} else if ((c.center.y > r.lowerLeft.y)
								&& (c.center.x > r.lowerLeft.x + r.width)) { // case
																				// B
							ball.position.x = r.lowerLeft.x + r.width
									+ Ball.BALL_RADIUS;
							ball.velocity.x *= -1;
						} else { // hit at the corner, case C
							ball.position.x = r.lowerLeft.x + r.width
									+ Ball.BALL_RADIUS;
							ball.position.y = r.lowerLeft.y - Ball.BALL_RADIUS;
							ball.velocity.mul(-1);
						}
					} else { // and down
						ball.position.x = r.lowerLeft.x + r.width
								+ Ball.BALL_RADIUS;
						ball.velocity.x *= -1;
					}
				} else { // the ball is flying vertically up
					ball.position.y = r.lowerLeft.y - r.width
							- Ball.BALL_RADIUS;
					ball.velocity.y *= -1;
				}

			} else { // hitting a brick on the floor
				if (ball.velocity.x > 0) { // the ball is flying right
					if (ball.velocity.y < 0) { // and down
						if ((c.center.x > r.lowerLeft.x)
								&& (c.center.y > r.lowerLeft.y + r.height)) { // case
																				// A
							ball.position.y = r.lowerLeft.y + r.height
									+ Ball.BALL_RADIUS;
							ball.velocity.y *= -1;
						} else if ((c.center.y < r.lowerLeft.y + r.height)
								&& (c.center.x < r.lowerLeft.x)) { // case B
							ball.position.x = r.lowerLeft.x - Ball.BALL_RADIUS;
							ball.velocity.x *= -1;
						} else { // hit at the corner, case C
							ball.position.x = r.lowerLeft.x - Ball.BALL_RADIUS;
							ball.position.y = r.lowerLeft.y + r.height
									+ Ball.BALL_RADIUS;
							ball.velocity.mul(-1);
						}
					} else { // and up
						ball.position.x = r.lowerLeft.x - Ball.BALL_RADIUS;
						ball.velocity.x *= -1;
					}
				} else if (ball.velocity.x < 0) { // the ball is flying left
					if (ball.velocity.y < 0) { // and down
						if ((c.center.x < r.lowerLeft.x + r.width)
								&& (c.center.y > r.lowerLeft.y + r.height)) { // case
																				// A
							ball.position.y = r.lowerLeft.y + r.height
									+ Ball.BALL_RADIUS;
							ball.velocity.y *= -1;
						} else if ((c.center.y < r.lowerLeft.y + r.height)
								&& (c.center.x > r.lowerLeft.x + r.width)) { // case
																				// B
							ball.position.x = r.lowerLeft.x + r.width
									+ Ball.BALL_RADIUS;
							ball.velocity.x *= -1;
						} else { // hit at the corner, case C
							ball.position.x = r.lowerLeft.x + r.width
									+ Ball.BALL_RADIUS;
							ball.position.y = r.lowerLeft.y + r.height
									+ Ball.BALL_RADIUS;
							ball.velocity.mul(-1);
						}
					} else { // and up
						ball.position.x = r.lowerLeft.x + r.width
								+ Ball.BALL_RADIUS;
						ball.velocity.x *= -1;
					}
				} else { // the ball is falling vertically down
					ball.position.y = r.lowerLeft.y + r.height
							+ Ball.BALL_RADIUS;
					ball.velocity.y *= -1;

				}
			}

		}

		// ############################################################
		// here we analyze what to happen to the bricks: shifting, scoring etc.
		for (int i = 0; i < bricksAffected.size(); i++) {
			Brick brick = bricks.get(bricksAffected.get(i));
			listener.hitAtBrick();
			// if the ceiling brick is hit then we increase the score; else -
			// decrease;
			int scoringSign = (brick.atCeiling) ? 1 : -1;

			int column = brick.column;
			// int row = brick.row;

			// customize gameplay
			boolean hitGold = false;

			switch (brick.color) {
			case Brick.BRICK_COLOR_PURPLE:
				score += 2 * scoringSign;
				break;
			case Brick.BRICK_COLOR_GREEN: // if a green brick hit then
				// restore the NORMAL ball acceleration. Also set the
				// ball color to GREEN
				if (ball.ballAccel == Ball.BALL_DOUBLE_ACCELL) {
					ball.ballAccel = Ball.BALL_NORMAL_ACCELL;
					ball.setBallColor(Ball.BALL_COLOR_WHITE);
				}
				score += 5 * scoringSign;
				break;
			case Brick.BRICK_COLOR_BLUE:
				score += 3 * scoringSign;
				break;
			case Brick.BRICK_COLOR_ORANGE:
				score += 6 * scoringSign;
				break;
			case Brick.BRICK_COLOR_GREY: // if a grey brick was hit then
				// switch the racquet width
				// between normal/narrow
				racquet.racquetWidth = (racquet.racquetWidth == Racquet.RACQUET_WIDTH_NORMAL) ? Racquet.RACQUET_WIDTH_NARROW
						: Racquet.RACQUET_WIDTH_NORMAL;
				score += 2 * scoringSign;
				break;
			case Brick.BRICK_COLOR_RED: // if a red brick hit then
										// DOUBLE the ball acceleration.
										// Also set the
										// ball color to RED
				if (brick.atCeiling) {
					if (ball.ballAccel == Ball.BALL_NORMAL_ACCELL) {
						ball.ballAccel = Ball.BALL_DOUBLE_ACCELL;
						ball.setBallColor(Ball.BALL_COLOR_RED);
					}
				}
				score += 1 * scoringSign;
				break;
			case Brick.BRICK_COLOR_PINK:
				score += 6 * scoringSign;
				break;
			case Brick.BRICK_COLOR_BLUESKY:
				score += 4 * scoringSign;
				break;
			case Brick.BRICK_COLOR_VIOLET:
				score += 1 * scoringSign;
				break;
			case Brick.BRICK_COLOR_GOLD:
				hitGold = true;
				score += 7 * scoringSign;
				// if (brick.atCeiling) {
				// when hitting the gold brick even if on the ceiling add a ball
				// and change the color from gold to random
				ballsLeft += 1; // add an extra ball and color it in yellow
				ball.setBallColor(Ball.BALL_COLOR_YELLOW);
				ball.ballAccel = Ball.BALL_NORMAL_ACCELL; // restore the normal
															// ball's
															// acceleration
				brick.setColor(rand.nextInt(9)); // select a random color
													// instead of Gold
				// }
				break;
			default:
				score += 0 * scoringSign;
				break;
			}

			// We do not care which brick in the column was hit,
			// we always shift the whole column either up or down

			if (brick.atCeiling) {
				ballReady = true;
				// Log.d("World:checkBallCollisionsWithBricks",
				// "A collision with a ceiling brick just happened!");

				// the bricks on the floor will shift up
				for (int y = level - 1; y > 0; y--) {
					floorBricksId[column][y] = floorBricksId[column][y - 1];
					if (floorBricksId[column][y] != NO_OBJECT_ID) {
						bricks.get(floorBricksId[column][y]).setCell(column, y);
						bricks.get(floorBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_UP;
					}
				}

				// the uppermost ceiling brick [row=0] goes to the floor
				// [row=0]:
				int topBrick = ceilingBricksId[column][0];
				// exclude this brick from bricksCeiling array list
				lastCeilingBrickId = topBrick;
				bricksCeiling.remove(bricks.get(topBrick));

				floorBricksId[column][0] = topBrick;
				bricks.get(topBrick).atCeiling = false;
				bricks.get(topBrick).setCell(column, 0);
				bricks.get(topBrick).state = Brick.BRICK_STATE_SHIFTING_UP_TO_FLOOR;
				// Log.d("World:checkBallCollisionsWithBricks",
				// "bricks.get(topBrick).state = "
				// + bricks.get(topBrick).state);
				// other ceiling bricks in this column will shift one cell up:
				for (int y = 0; y < level - 1; y++) {
					ceilingBricksId[column][y] = ceilingBricksId[column][y + 1];
					if (ceilingBricksId[column][y] != NO_OBJECT_ID) {
						bricks.get(ceilingBricksId[column][y]).setCell(column,
								y);
						bricks.get(ceilingBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_UP;
					}
				}
				// always put "empty" at the cell in last row of the ceiling
				// when the ceiling got hit
				if (level > 1)
					ceilingBricksId[column][level - 1] = NO_OBJECT_ID;

				break;
			} else { // the collisions happened with a bottom brick
				if (ballReady) {
					ballsLeft -= 1;
					// when a ball is lost:
					ball.ballAccel = Ball.BALL_NORMAL_ACCELL; // restore the
																// normal ball's
																// acceleration
					if (!hitGold) {
						ball.setBallColor(Ball.BALL_COLOR_WHITE); // restore the
																	// color of
																	// ball
					}
					ballReady = false;
				}

				// Log.d("World:checkBallCollisionsWithBricks",
				// "A collision with a ceiling brick just happened!");
				// the bricks on the floor will shift down
				for (int y = level - 1; y > 0; y--) {
					ceilingBricksId[column][y] = ceilingBricksId[column][y - 1];
					if (ceilingBricksId[column][y] != NO_OBJECT_ID) {
						bricks.get(ceilingBricksId[column][y]).setCell(column,
								y);
						bricks.get(ceilingBricksId[column][y]).state = Brick.BRICK_STATE_SHIFTING_DOWN;
					}
				}

				// the bottommost brick [row=0] goes to the ceiling [row=0]:
				int bottomBrick = floorBricksId[column][0];
				ceilingBricksId[column][0] = bottomBrick;
				// Include this brick back into bricksCeiling array list
				bricksCeiling.add(bricks.get(bottomBrick));
				bricks.get(bottomBrick).atCeiling = true;
				bricks.get(bottomBrick).setCell(column, 0);
				bricks.get(bottomBrick).state = Brick.BRICK_STATE_SHIFTING_DOWN_TO_CEILING;

				// other floor bricks in this column will shift one cell
				// down:
				for (int y = 0; y < level - 1; y++) {
					floorBricksId[column][y] = floorBricksId[column][y + 1];
					if (floorBricksId[column][y] != NO_OBJECT_ID) {
						bricks.get(floorBricksId[column][y]).setCell(column, y);
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

	private void checkGameOver() {
		if (ballsLeft < 1) {
			state = WORLD_STATE_GAME_OVER;
		}
	}
}
