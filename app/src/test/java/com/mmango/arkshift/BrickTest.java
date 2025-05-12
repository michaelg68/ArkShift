package com.mmango.arkshift;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.badlogic.androidgames.framework.gl.TextureRegion;

public class BrickTest {
    private Brick brick;
    private static final float DELTA = 0.001f;
    
    @Mock
    private TextureRegion mockTextureRegion;
    
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock the static texture regions
        AssetsGame.brickPurple = mockTextureRegion;
        AssetsGame.brickGreen = mockTextureRegion;
        AssetsGame.brickBlue = mockTextureRegion;
        AssetsGame.brickOrange = mockTextureRegion;
        AssetsGame.brickGrey = mockTextureRegion;
        AssetsGame.brickRed = mockTextureRegion;
        AssetsGame.brickPink = mockTextureRegion;
        AssetsGame.brickBluesky = mockTextureRegion;
        AssetsGame.brickViolet = mockTextureRegion;
        AssetsGame.brickGold = mockTextureRegion;
        
        // Create a brick instance for testing
        brick = new Brick(1, 2, Brick.BRICK_COLOR_PURPLE);
    }
    
    @Test
    public void testBrickInitialization() {
        assertEquals(1, brick.column);
        assertEquals(2, brick.row);
        assertEquals(Brick.BRICK_COLOR_PURPLE, brick.color);
        assertEquals(Brick.BRICK_STATE_PREPARING, brick.state);
        assertEquals(0f, brick.stateTime, DELTA);
        assertTrue(brick.atCeiling);
        assertFalse(brick.jumpedToFloor);
    }
    
    @Test
    public void testBrickPosition() {
        float expectedX = World.FRAME_WIDTH + Brick.BRICK_WIDTH / 2 + Brick.BRICK_WIDTH * 1;
        float expectedY = World.WORLD_HEIGHT - World.NOTIFICATION_AREA_HEIGHT 
                         - World.FRAME_WIDTH - Brick.BRICK_WIDTH / 2 
                         - Brick.BRICK_WIDTH * 2;
        
        assertEquals(expectedX, brick.position.x, DELTA);
        assertEquals(expectedY, brick.position.y, DELTA);
    }
    
    @Test
    public void testBrickBounds() {
        assertEquals(brick.position.x - Brick.BRICK_WIDTH / 2, 
                    brick.bounds.lowerLeft.x, DELTA);
        assertEquals(brick.position.y - Brick.BRICK_HEIGHT / 2, 
                    brick.bounds.lowerLeft.y, DELTA);
        assertEquals(Brick.BRICK_WIDTH, brick.bounds.width, DELTA);
        assertEquals(Brick.BRICK_HEIGHT, brick.bounds.height, DELTA);
    }
    
    @Test
    public void testBrickPreparingMovement() {
        float deltaTime = 0.016f; // Simulate ~60 FPS
        brick.xPrepDestination = brick.position.x + 50f; // Set destination 50 units to the right
        
        brick.updatePreparing(deltaTime);
        
        // Check if brick moves right at correct velocity
        float expectedX = brick.position.x;
        float expectedY = brick.position.y;
        assertEquals(expectedX, brick.position.x, DELTA);
        assertEquals(expectedY, brick.position.y, DELTA);
        assertEquals(Brick.BRICK_VELOCITY_X, brick.velocity.x, DELTA);
        assertEquals(0f, brick.velocity.y, DELTA);
    }
    
    @Test
    public void testBrickShiftingUpMovement() {
        float deltaTime = 0.016f;
        brick.state = Brick.BRICK_STATE_SHIFTING_UP;
        brick.yDestination = brick.position.y + 50f;
        
        brick.update(deltaTime);
        
        // Check if brick moves up at correct velocity
        assertTrue(brick.position.y > brick.bounds.lowerLeft.y);
        assertEquals(0f, brick.velocity.x, DELTA);
        assertEquals(Brick.BRICK_VELOCITY_Y, brick.velocity.y, DELTA);
    }
    
    @Test
    public void testBrickColorChange() {
        // Test each color and its corresponding texture
        int[] colors = {
            Brick.BRICK_COLOR_PURPLE,
            Brick.BRICK_COLOR_GREEN,
            Brick.BRICK_COLOR_BLUE,
            Brick.BRICK_COLOR_ORANGE,
            Brick.BRICK_COLOR_GREY,
            Brick.BRICK_COLOR_RED,
            Brick.BRICK_COLOR_PINK,
            Brick.BRICK_COLOR_BLUESKY,
            Brick.BRICK_COLOR_VIOLET,
            Brick.BRICK_COLOR_GOLD
        };
        
        for (int color : colors) {
            brick.setColor(color);
            assertEquals(color, brick.color);
            assertNotNull(brick.brickTextureRegion);
        }
    }
    
    @Test
    public void testBrickCellPosition() {
        // Test setting new cell position
        brick.setCell(3, 4);
        assertEquals(3, brick.column);
        assertEquals(4, brick.row);
        
        // Test home cell position
        brick.setHomeCell(5, 6);
        assertEquals(5, brick.column);
        assertEquals(6, brick.row);
    }
} 