package com.mmango.arkshift;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;

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
        this.racquet = new Racquet(54 - 20, 2 + 10 + 5.4f);  
        this.ball = new Ball(54 - 5.4f, 2 + 10 + 5.4f , Ball.BALL_COLOR_WHITE);
        this.bricks = new ArrayList<Brick>();
        this.listener = listener;
        rand = new Random();
        generateLevel(1);
        
        ballsLeft = 9;
       	columns = 10;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }

    private void generateLevel(int rows) {
     	for (int y = 0; y < rows; y++) {
    		for (int x = 0; x < columns; x++ ) {
    			int brick_color = rand.nextInt(10); 
    			Brick brick = new Brick(2 + 10 * x, 17 + 10 * x, brick_color);
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
        if (ballsLeft  < 1) {
            state = WORLD_STATE_GAME_OVER;
        }
    }
}
