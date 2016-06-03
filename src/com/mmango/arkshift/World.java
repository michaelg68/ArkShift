package com.mmango.arkshift;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.badlogic.androidgames.framework.math.Circle;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.mmango.games.framework.math.MyOverlapTester;

public class World {
	public interface WorldListener {
		// public void hitAtRacquet();

		public void hitAtBrick();

		// public void hitAtFrame();

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
	public static final float CELL_SIZE = Brick.BRICK_HEIGHT;
	public static final float GAME_FIELD_HEIGHT = WORLD_HEIGHT - FRAME_WIDTH
			- FRAME_WIDTH - NOTIFICATION_AREA_HEIGHT;
	public static final float GAME_FIELD_WIDTH = WORLD_WIDTH - FRAME_WIDTH
			- FRAME_WIDTH;

	public static final int WORLD_STATE_READY = 0;
	public static final int WORLD_STATE_RUNNING = 1;
	public static final int WORLD_STATE_NEXT_LEVEL = 2;
	public static final int WORLD_STATE_GAME_OVER = 3;
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
	boolean prepared;
	float timePassed = 0f;
	int[] cellIds = new int[8]; // for calculation the ball's rectBounds
								// vertices
	int cellsPerRow;
	int cellsPerCol;

	public World(WorldListener listener, int level, int balls) {
		this.level = level;

		gameField = new Rectangle(FRAME_WIDTH, FRAME_WIDTH, GAME_FIELD_WIDTH,
				GAME_FIELD_HEIGHT);

		// Log.d("World", "gameField.lowerLeft.x = " + gameField.lowerLeft.x);
		// Log.d("World", "gameField.lowerLeft.y = " + gameField.lowerLeft.y);
		// Log.d("World", "gameField.width = " + gameField.width);
		// Log.d("World", "gameField.height = " + gameField.height);
		this.cellsPerRow = (int) Math.ceil(GAME_FIELD_WIDTH / CELL_SIZE);
		this.cellsPerCol = (int) Math.ceil(GAME_FIELD_HEIGHT / CELL_SIZE);

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
		prepared = false;
	}

	private void generateLevel(int columns, int rows) {
		int brickId = 0;
		ceilingBricksId = new int[columns][rows];
		floorBricksId = new int[columns][rows];
		int prepColumn = 0;

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

				if (y % 2 == 0) { // in even rows like 0, 2, 4
					prepColumn = -2;
				} else { // in odd rows like 1, 3, 5
					prepColumn = -1;
				}
				// Log.d("World:generateLevel", "y = " + y + "; prepColumn = " +
				// prepColumn);

				Brick brick = new Brick(prepColumn, y, brick_color);
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

	public void preparing(float deltaTime) {
		// Log.d("World:preparing", "Running preparing method");
		if (!prepared) {
			for (int y = 0; y < level; y++) {
				for (int x = 0; x < COLUMNS; x++) {
					// Log.d("World:preparing", "x = " + x);
					bricks.get(ceilingBricksId[x][y]).setHomeCell(x, y);
				}
			}
			listener.hitAtBrick();
			prepared = true;
		}
		// Log.d("World:preparing", "deltaTime = " + deltaTime);
		timePassed += deltaTime;
		if (timePassed >= 0.2f) {
			listener.hitAtBrick();
			timePassed = 0;
		}

		boolean bricksMoving = false;
		for (int i = 0; i < bricksArraySize; i++) {
			Brick brick = bricks.get(i);
			brick.updatePreparing(deltaTime);
			if (brick.state == Brick.BRICK_STATE_PREPARING) {
				bricksMoving = true;
			}
		}
		if (!bricksMoving) {
			// the last brick has arrived to it's place. we are ready to play
			listener.levelPassed();
			state = WORLD_STATE_READY;
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
		racquet.position.y = FRAME_WIDTH + CELL_SIZE
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
					racquet.position.y = FRAME_WIDTH + CELL_SIZE
							+ Racquet.RACQUET_HEIGHT / 2 + 0.5f;
				}
			}
		}
		racquet.update(deltaTime, accelX);
	}

	private void updateBall(float deltaTime) {
		ball.update(deltaTime);
		if (ballsLeft < 1) {// if no more balls then game is over
			state = WORLD_STATE_GAME_OVER;
			listener.gameOver();
		}
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
				listener.levelPassed();
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
			listener.hitAtBrick();
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
			listener.hitAtBrick();
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);

			ball.position.y = FRAME_WIDTH + Ball.BALL_RADIUS;
			ball.velocity.y = ball.velocity.y * (-1);
		} else if (breaktrhough == FRAME_LEFT_BORDER_ID) {
			listener.hitAtBrick();
			// Log.d("World:checkBallCollisionsWithFrame", "breaktrhough = " +
			// breaktrhough);
			// Log.d("World:checkBallCollisionsWithFrame", "ball.position.x = "
			// + ball.position.x + "; ball.position.y = " + ball.position.y);

			// Y collision
			ball.position.x = FRAME_WIDTH + Ball.BALL_RADIUS;
			ball.velocity.x = ball.velocity.x * (-1);
		} else if (breaktrhough == FRAME_RIGHT_BORDER_ID) {
			listener.hitAtBrick();
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

			listener.hitAtBrick();
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

				// Log.d("World:checkBallCollisionsWithRacquet",
				// "ballVelocityCopy.len = " + ballVelocityCopy.len()
				// + "; ballVelocityCopy.angle = "
				// + ballVelocityCopy.angle());
				// Log.d("World:checkBallCollisionsWithRacquet",
				// "racquet.velocity.len = " + racquet.velocity.len()
				// + "; racquet.velocity.angle = "
				// + racquet.velocity.angle());

				// get sum of ballVelocityCopy and racquet.velocity

				ballVelocityCopy.add(racquet.velocity);

				// Log.d("World:checkBallCollisionsWithRacquet",
				// "After adding the racquet velocity: ballVelocityCopy.len = "
				// + ballVelocityCopy.len()
				// + "; ballVelocityCopy.angle = "
				// + ballVelocityCopy.angle());

				// get the angle between the temp ballVelocityCopy and X
				float angle = ballVelocityCopy.angle();
				// Log.d("World:checkBallCollisionsWithRacquet", "angle = "
				// + angle);

				// avoid too flat angles, if the angle is less that 45 degrees
				// than make it equal 45 + a random float between 5f to 10f
				if ((angle > 90f) && (angle > 135f)) {
					float randangle = rand.nextFloat() * (10 - 5) + 1;
					// Log.d("World:checkBallCollisionsWithRacquet",
					// "(angle > 90f) && (angle > 135f). randangle = "
					// + randangle);
					angle = 135f - randangle;
				}
				if ((angle < 90f) && (angle < 45f)) {
					float randangle = rand.nextFloat() * (10 - 5) + 1;
					// Log.d("World:checkBallCollisionsWithRacquet",
					// "(angle < 90f) && (angle < 45f). randangle = "
					// + randangle);
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

	private void setNoCellIds() {
		for (int z = 0; z < 8; z++) {
			cellIds[z] = -1;
		}
	}

	private int[] getCellIds(Ball ball, boolean aboveRacquet) {
		if (aboveRacquet) {

			int y2 = (int) Math.floor((WORLD_HEIGHT
					- ball.rectBounds.lowerLeft.y - NOTIFICATION_AREA_HEIGHT
					- FRAME_WIDTH - ball.rectBounds.height)
					/ CELL_SIZE);
			if (y2 > level - 1) { // if the right up corner of the ball rect is
									// lower than the lowest possible row
				// then no collision is possible
				Log.d("World:getCellIds",
						"right up corner of the ball rect is in cell y2 = "
								+ y2
								+ " which is lower than the lowest possible row");
				setNoCellIds();
				return cellIds;
			}

			int x1 = (int) Math
					.floor((ball.rectBounds.lowerLeft.x - FRAME_WIDTH)
							/ CELL_SIZE);
			int y1 = (int) Math
					.floor((WORLD_HEIGHT - ball.rectBounds.lowerLeft.y
							- NOTIFICATION_AREA_HEIGHT - FRAME_WIDTH)
							/ CELL_SIZE);

			int x2 = (int) Math.floor((ball.rectBounds.lowerLeft.x
					- FRAME_WIDTH + ball.rectBounds.width)
					/ CELL_SIZE);

			if (x1 < 0)
				x1 = 0;
			if (x1 > COLUMNS - 1)
				x1 = COLUMNS - 1;
			if (x2 < 0)
				x2 = 0;
			if (x2 > COLUMNS - 1)
				x2 = COLUMNS - 1;
			if (y1 < 0)
				y1 = 0;
			if (y2 < 0)
				y2 = 0;

			Log.d("World:getCellIds", "The ball is in cells: x1=" + x1 + " y1="
					+ y1 + " x2=" + x2 + " y2=" + y2);

			cellIds[0] = x1;
			cellIds[1] = y1;

			if (x1 == x2 && y1 == y2) {
				Log.d("World:getCellIds", "The ball is completely in one cell");
				cellIds[2] = -1; // the ball is completely in one cell
				cellIds[3] = -1; // so no need to duplicate the cell IDs. One is
									// enough
				cellIds[4] = -1;
				cellIds[5] = -1;
				cellIds[6] = -1;
				cellIds[7] = -1;
			} else if ((x1 == x2) || (y1 == y2)) { 
				Log.d("World:getCellIds", "The ball overlaps only two cells either in same column or same row");

				// the ball is in one column
													// or in one row
				cellIds[2] = x2; // in this case only two cells should be
									// checked
				cellIds[3] = y2;
				cellIds[4] = -1;
				cellIds[5] = -1;
				cellIds[6] = -1;
				cellIds[7] = -1;
			} else { // the ball overlaps 4 cells
				Log.d("World:getCellIds", "The ball overlaps four cells");
				cellIds[2] = x2;
				cellIds[3] = y2;
				int x3 = x2;
				int y3 = y1;
				int x4 = x1;
				int y4 = y2;
				if (x3 < 0)
					x3 = 0;
				if (x3 > COLUMNS - 1)
					x3 = COLUMNS - 1;
				if (x4 < 0)
					x4 = 0;
				if (x4 > COLUMNS - 1)
					x4 = COLUMNS - 1;
				if (y3 < 0)
					y3 = 0;
				if (y4 < 0)
					y4 = 0;

				cellIds[4] = x3;
				cellIds[5] = y3;
				cellIds[6] = x4;
				cellIds[7] = y4;
			}
		// end of aboveRacquet == true 
		} else { // the ball is under racquet's central horisontal line, e.g. aboveRacquet = false
			
			int y1 = (int) Math.floor((ball.rectBounds.lowerLeft.y - FRAME_WIDTH) / CELL_SIZE);

			if (y1 > level - 1) { //if the left bottom corner of the ball rect is higher than the highest possible row  then no collision is possible
				Log.d("World:getCellIds",
						"The left bottom corner of the ball rect is in cell y1 = "
								+ y1
								+ " which is higher than the highest possible row");
				setNoCellIds();
				return cellIds;
			}

			int x1 = (int) Math
					.floor((ball.rectBounds.lowerLeft.x - FRAME_WIDTH)
							/ CELL_SIZE);

			int x2 = (int) Math.floor((ball.rectBounds.lowerLeft.x
					- FRAME_WIDTH + ball.rectBounds.width)
					/ CELL_SIZE);
			
			int y2 = (int) Math.floor((ball.rectBounds.lowerLeft.y - FRAME_WIDTH + ball.rectBounds.height)
					/ CELL_SIZE);

			if (x1 < 0)
				x1 = 0;
			if (x1 > COLUMNS - 1)
				x1 = COLUMNS - 1;
			if (x2 < 0)
				x2 = 0;
			if (x2 > COLUMNS - 1)
				x2 = COLUMNS - 1;
			if (y1 < 0)
				y1 = 0;
			if (y2 < 0)
				y2 = 0;

			Log.d("World:getCellIds", "The ball is in cells: x1=" + x1 + " y1="
					+ y1 + " x2=" + x2 + " y2=" + y2);

			cellIds[0] = x1;
			cellIds[1] = y1;

			if (x1 == x2 && y1 == y2) {
				Log.d("World:getCellIds", "The ball is completely in one cell");
				cellIds[2] = -1; // the ball is completely in one cell
				cellIds[3] = -1; // so no need to duplicate the cell IDs. One is
									// enough
				cellIds[4] = -1;
				cellIds[5] = -1;
				cellIds[6] = -1;
				cellIds[7] = -1;
			} else if ((x1 == x2) || (y1 == y2)) { 
				Log.d("World:getCellIds", "The ball overlaps only two cells either in same column or same row");

				// the ball is in one column
													// or in one row
				cellIds[2] = x2; // in this case only two cells should be
									// checked
				cellIds[3] = y2;
				cellIds[4] = -1;
				cellIds[5] = -1;
				cellIds[6] = -1;
				cellIds[7] = -1;
			} else { // the ball overlaps 4 cells
				Log.d("World:getCellIds", "The ball overlaps four cells");
				cellIds[2] = x2;
				cellIds[3] = y2;
				int x3 = x2;
				int y3 = y1;
				int x4 = x1;
				int y4 = y2;
				if (x3 < 0)
					x3 = 0;
				if (x3 > COLUMNS - 1)
					x3 = COLUMNS - 1;
				if (x4 < 0)
					x4 = 0;
				if (x4 > COLUMNS - 1)
					x4 = COLUMNS - 1;
				if (y3 < 0)
					y3 = 0;
				if (y4 < 0)
					y4 = 0;

				cellIds[4] = x3;
				cellIds[5] = y3;
				cellIds[6] = x4;
				cellIds[7] = y4;
			}
		}
		return cellIds;
	}

	private void checkBallCollisionsWithBricks() {

		// List<Integer> bricksTouched = new ArrayList<Integer>();
		// List<Integer> bricksAffected = new ArrayList<Integer>();
		int[] bricksTouched = new int[4];
		int[] bricksAffected = new int[3];
		int bricksTouchedCounter = 0;
		int bricksAffectedCounter = 0;
		boolean isCeiling = false;
		boolean ballAboveRacquet = false;
		int xCell = -1;
		int yCell = -1;
		int[] bricksToCheck = { -1, -1, -1, -1 };

		// int b = 0;
		// int length = 0;
		// Log.d("World:checkBallCollisionsWithBricks", "----");

		// first I must check if the collision happened with more than one
		// brick!
		// Hopefully this will help to solve the problem of ball swallowing by
		// the bricks

		// calculate cells where the corners of ball.rectBounds are located
		Log.d("World:checkBallCollisionsWithBricks",
				"ball.rectBounds.lowerLeft x=" + ball.rectBounds.lowerLeft.x
						+ " y=" + ball.rectBounds.lowerLeft.y);
		// example: ball.rectBounds.lowerLeft x=85.97813 y=161.30772
		int j = 0;
		if (ball.position.y > racquet.position.y) {
			Log.d("World:checkBallCollisionsWithBricks", "Getting the potential cells from the ceiling");
			getCellIds(ball, true); //get the potential cells from the ceiling
			for (int z = 0; z < 8; z++) {
				int w = z + 1;
				if ((cellIds[z] == -1) || (cellIds[w] > level - 1)) {
					z = w;
					continue;
				}
				Log.d("World:checkBallCollisionsWithBricks", "z=" + z + " w=" +w);
				Log.d("World:checkBallCollisionsWithBricks", "Cell column="
						+ cellIds[z] + " row=" + cellIds[w]);
				if (ceilingBricksId[cellIds[z]][cellIds[w]] != NO_OBJECT_ID) {
					bricksToCheck[j] = ceilingBricksId[cellIds[z]][cellIds[w]];
				}
				j++;
				z = w;
			}
		} else {
			Log.d("World:checkBallCollisionsWithBricks", "Getting the potential cells from the floor");
			getCellIds(ball, false); //get the potential cells from the floor
			for (int z = 0; z < 8; z++) {
				int w = z + 1;
				if ((cellIds[z] == -1) || (cellIds[w] > level - 1)) {
					z = w;
					continue;
				}
				Log.d("World:checkBallCollisionsWithBricks", "z=" + z + " w=" +w);
				Log.d("World:checkBallCollisionsWithBricks", "Cell column="
						+ cellIds[z] + " row=" + cellIds[w]);
				if (floorBricksId[cellIds[z]][cellIds[w]] != NO_OBJECT_ID) {
					bricksToCheck[j] = floorBricksId[cellIds[z]][cellIds[w]];
				}
				j++;
				z = w;
			}
		}
		
		for (int i = 0; i < 4; i++) {
			// find all bricks
			// which
			// would overlap with the
			// ball in the next move
			if (bricksToCheck[i] != -1) {
				Log.d("World:checkBallCollisionsWithBricks", "AGAIN bricksToCheck[" + i
						+ "]=" + bricksToCheck[i]);
				Brick brick = bricks.get(bricksToCheck[i]);
				if (OverlapTester.overlapCircleRectangle(ball.bounds,
						brick.bounds)) {
					bricksTouched[bricksTouchedCounter] = bricksToCheck[i];
					bricksTouchedCounter++;
					Log.d("World:checkBallCollisionsWithBricks", "Overlapping brick " + bricksToCheck[i]);

					// temporary, to test the level passed situation:
					// state = WORLD_STATE_NEXT_LEVEL;
				}
			}
		}
		

		if (bricksTouchedCounter != 0) {
			Log.d("World:checkBallCollisionsWithBricks",
					"bricksTouchedCounter = " + bricksTouchedCounter);
			Log.d("World:checkBallCollisionsWithBricks", "ballAtCeiling = "
					+ ballAboveRacquet);
		}

		if (bricksTouchedCounter == 2) { // I expect it be not more than 2
			Log.d("World:checkBallCollisionsWithBricks",
					"2 bricks are touched!");
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
			if (bricks.get(bricksTouched[0]).row == bricks
					.get(bricksTouched[1]).row) {
				// Log.d("World:checkBallCollisionsWithBricks",
				// "Two bricks are in the same row. row = "
				// + bricks.get(bricksTouched.get(1)).row);

				// find the brick whose center.x is closer to the ball's
				// center.x
				// int brickID =
				// MyOverlapTester.overlapCircleRectangleHorisontal(ball.bounds,
				// bricks.get(bricksTouched.get(0)).bounds,
				// bricks.get(bricksTouched.get(1)).bounds);
				if ((ball.bounds.center.x <= bricks.get(bricksTouched[0]).x
						+ Brick.BRICK_WIDTH / 2)) {
					bricksAffected[0] = bricksTouched[0]; // in this array
															// list I store
															// the ID of the
															// actually hit
															// brick
					bricksAffectedCounter = 1;
				} else {
					bricksAffected[0] = bricksTouched[1]; // in this array
															// list I store
															// the ID of the
															// actually hit
															// brick
					bricksAffectedCounter = 1;
				}

				if (bricks.get(bricksAffected[0]).atCeiling) {
					ball.position.y = bricks.get(bricksAffected[0]).bounds.lowerLeft.y
							- Ball.BALL_RADIUS;
				} else {
					ball.position.y = bricks.get(bricksAffected[0]).bounds.lowerLeft.y
							+ CELL_SIZE + Ball.BALL_RADIUS;
				}
				ball.velocity.y = ball.velocity.y * (-1);
			}

			// two bricks in the same column:
			if (bricks.get(bricksTouched[0]).column == bricks
					.get(bricksTouched[1]).column) {
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
				if ((ball.bounds.center.y >= bricks.get(bricksTouched[0]).y
						- CELL_SIZE / 2)) {
					bricksAffected[0] = (bricksTouched[0]); // in this array
															// list I store
															// the ID of the
															// actually hit
															// brick
					bricksAffectedCounter = 1;
				} else {
					bricksAffected[0] = (bricksTouched[1]); // in this array
															// list I store
															// the ID of the
															// actually hit
															// brick
					bricksAffectedCounter = 1;
				}
				// now check which side of the brick is hit - left or right
				if (ball.velocity.x > 0) { // the ball was flying from left to
											// right so the left side is hit
					ball.position.x = bricks.get(bricksAffected[0]).bounds.lowerLeft.x
							- Ball.BALL_RADIUS; // the
					// brick
					// was
					// hit
					// from
					// the
					// left
				} else { // the ball was flying from right to left so the rigth
							// side is hit
					ball.position.x = bricks.get(bricksAffected[0]).bounds.lowerLeft.x
							+ Brick.BRICK_WIDTH + Ball.BALL_RADIUS; // the brick
																	// was hit
																	// from the
																	// right

				}
				ball.velocity.x = ball.velocity.x * (-1);
			}

			if ((bricks.get(bricksTouched[0]).row != bricks
					.get(bricksTouched[1]).row)
					&& (bricks.get(bricksTouched[0]).column != bricks
							.get(bricksTouched[1]).column)) {
				// Log.d("World:checkBallCollisionsWithBricks",
				// "Two bricks are in the different rows and columns! This is a IN-CORNER collision");
				// we consider that both bricks have been affected
				bricksAffected[0] = bricksTouched[0];
				bricksAffected[1] = bricksTouched[1];
				bricksAffectedCounter = 2;

				if (bricks.get(bricksAffected[0]).atCeiling) {
					isCeiling = true;
				}
				// MyOverlapTester.overlapCircleInCorner(ball.bounds,
				// bricks.get(bricksTouched.get(0)).bounds,
				// bricks.get(bricksTouched.get(1)).bounds, isCeling);
				Rectangle r0 = bricks.get(bricksTouched[0]).bounds;
				Rectangle r1 = bricks.get(bricksTouched[1]).bounds;

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

		} else if (bricksTouchedCounter == 3) {
			Log.d("World:checkBallCollisionsWithBricks",
					"3 bricks are touched! This is a kind of IN-CORNER collision");
			// Log.d("World:checkBallCollisionsWithBricks",

			// Actually it is geometricaly impossible for a circle to touch
			// three rectangles so I will probaly fix it
			// The questions is how costly will be calculation

			// int soleR = 999; // the id of the lower/upper brick
			int maxR = 0; // the id of the lower/upper brick
			int r = 0;

			for (int i = 0; i < 3; i++) {
				// find the lower brick, the one with the biggest row number
				if (bricks.get(bricksTouched[i]).row > maxR) {
					maxR = bricks.get(bricksTouched[i]).row;
					r = i;
				}
			}
			// add to the affected list the lower(ceiling)/upper(bottom) brick:
			bricksAffected[0] = bricksTouched[r];

			// if the ball "overlaps" three bricks at the ceiling than the brick
			// which is just above the lower one should be excluded
			int column = bricks.get(bricksAffected[0]).column;
			for (int i = 0; i < 3; i++) {
				if (i == r) {
					continue;
				} else {
					if (bricks.get(bricksTouched[i]).column != column) {
						bricksAffected[1] = bricksTouched[i];
					}
				}
			}
			bricksAffectedCounter = 2;
			ball.velocity.mul(-1);

		} else if (bricksTouchedCounter == 1) { // only one brick would be
												// overlap
			Log.d("World:checkBallCollisionsWithBricks", "1 brick is touched!");

			bricksAffected[0] = bricksTouched[0]; // in this array I
													// store the ID of the
													// actually hit brick
			bricksAffectedCounter = 1;

			Brick brick = bricks.get(bricksAffected[0]);
			Log.d("World:checkBallCollisionsWithBricks",
					"brick.bounds.lowerLeft x=" + brick.bounds.lowerLeft.x
							+ " y=" + brick.bounds.lowerLeft.y);
			// example brick.bounds.lowerLeft x=85.2 y=164.6

			Circle c = ball.bounds;
			Rectangle r = brick.bounds;
			if (bricks.get(bricksTouched[0]).atCeiling)
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
		for (int i = 0; i < bricksAffectedCounter; i++) {
			Brick brick = bricks.get(bricksAffected[i]);
			if (brick.state != Brick.BRICK_STATE_STILL) {
				continue;
			}
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
}
