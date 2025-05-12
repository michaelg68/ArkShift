package com.mmango.arkshift;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.androidgames.framework.gl.TextureRegion;

public class BallTest {
    private Ball ball;
    
    @Mock
    private TextureRegion mockTextureRegion;
    
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the static texture regions
        AssetsGame.ballWhite = mockTextureRegion;
        AssetsGame.ballYellow = mockTextureRegion;
        AssetsGame.ballRed = mockTextureRegion;
        
        // Create a ball instance for testing
        ball = new Ball(10f, 20f, Ball.BALL_COLOR_WHITE);
    }
    
    @Test
    public void testBallInitialization() {
        assertEquals(10f, ball.position.x, 0.001f);
        assertEquals(20f, ball.position.y, 0.001f);
        assertEquals(Ball.BALL_STATE_STILL, ball.state);
        assertEquals(0f, ball.stateTime, 0.001f);
        assertEquals(Ball.BALL_NORMAL_ACCELL, ball.ballAccel, 0.001f);
        assertEquals(Ball.BALL_COLOR_WHITE, ball.color);
    }
    
    @Test
    public void testBallMovement() {
        // Test initial velocity
        assertEquals(2f, ball.velocity.x, 0.001f);
        assertEquals(10f, ball.velocity.y, 0.001f);
        
        // Update ball position
        float deltaTime = 0.016f; // Simulate ~60 FPS
        ball.update(deltaTime);
        
        // Check if ball moved correctly
        // New position = old position + (velocity * acceleration * deltaTime)
        float expectedX = 10f + (2f * Ball.BALL_NORMAL_ACCELL * deltaTime);
        float expectedY = 20f + (10f * Ball.BALL_NORMAL_ACCELL * deltaTime);
        
        assertEquals(expectedX, ball.position.x, 0.001f);
        assertEquals(expectedY, ball.position.y, 0.001f);
        assertEquals(Ball.BALL_STATE_MOVING, ball.state);
    }
    
    @Test
    public void testBallColorChange() {
        // Test white ball
        ball.setBallColor(Ball.BALL_COLOR_WHITE);
        assertEquals(Ball.BALL_COLOR_WHITE, ball.color);
        assertEquals(AssetsGame.ballWhite, ball.ballTextureRegion);
        
        // Test yellow ball
        ball.setBallColor(Ball.BALL_COLOR_YELLOW);
        assertEquals(Ball.BALL_COLOR_YELLOW, ball.color);
        assertEquals(AssetsGame.ballYellow, ball.ballTextureRegion);
        
        // Test red ball
        ball.setBallColor(Ball.BALL_COLOR_RED);
        assertEquals(Ball.BALL_COLOR_RED, ball.color);
        assertEquals(AssetsGame.ballRed, ball.ballTextureRegion);
    }
} 