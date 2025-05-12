package com.mmango.arkshift;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RacquetTest {
    private Racquet racquet;
    private static final float DELTA = 0.001f;
    
    @Before
    public void setUp() {
        // Create a racquet instance for testing with normal width
        racquet = new Racquet(50f, 10f, Racquet.RACQUET_WIDTH_NORMAL);
    }
    
    @Test
    public void testRacquetInitialization() {
        assertEquals(50f, racquet.position.x, DELTA);
        assertEquals(10f, racquet.position.y, DELTA);
        assertEquals(Racquet.RACQUET_WIDTH_NORMAL, racquet.racquetWidth, DELTA);
        assertEquals(Racquet.RACQUET_STATE_STILL, racquet.state);
        assertEquals(0f, racquet.stateTime, DELTA);
    }
    
    @Test
    public void testRacquetMovement() {
        float deltaTime = 0.016f; // Simulate ~60 FPS
        float accelX = 1.0f; // Simulate right movement
        
        racquet.update(deltaTime, accelX);
        
        // Expected position = initial position + (velocity * deltaTime)
        // where velocity = accelX * RACQUET_VELOCITY
        float expectedX = 50f + (accelX * Racquet.RACQUET_VELOCITY * deltaTime);
        assertEquals(expectedX, racquet.position.x, DELTA);
        assertEquals(10f, racquet.position.y, DELTA); // Y position should not change
    }
    
    @Test
    public void testRacquetLeftBoundary() {
        // Move racquet to left boundary
        racquet.position.x = 2f + Racquet.RACQUET_WIDTH_NORMAL / 2;
        float deltaTime = 0.016f;
        float accelX = -1.0f; // Try to move left
        
        racquet.update(deltaTime, accelX);
        
        // Should not move past left boundary
        assertEquals(2f + Racquet.RACQUET_WIDTH_NORMAL / 2, racquet.position.x, DELTA);
    }
    
    @Test
    public void testRacquetRightBoundary() {
        // Move racquet to right boundary
        racquet.position.x = World.GAME_FIELD_WIDTH + 2f - Racquet.RACQUET_WIDTH_NORMAL / 2;
        float deltaTime = 0.016f;
        float accelX = 1.0f; // Try to move right
        
        racquet.update(deltaTime, accelX);
        
        // Should not move past right boundary
        assertEquals(World.GAME_FIELD_WIDTH + 2f - Racquet.RACQUET_WIDTH_NORMAL / 2, 
                    racquet.position.x, DELTA);
    }
    
    @Test
    public void testRacquetBoundsUpdate() {
        float deltaTime = 0.016f;
        float accelX = 1.0f;
        
        racquet.update(deltaTime, accelX);
        
        // Check if bounds are correctly updated
        assertEquals(racquet.position.x - racquet.racquetWidth / 2, 
                    racquet.bounds.lowerLeft.x, DELTA);
        assertEquals(racquet.position.y - Racquet.RACQUET_HEIGHT / 2, 
                    racquet.bounds.lowerLeft.y, DELTA);
        assertEquals(Racquet.RACQUET_WIDTH_NORMAL, racquet.bounds.width, DELTA);
    }
    
    @Test
    public void testNarrowRacquet() {
        Racquet narrowRacquet = new Racquet(50f, 10f, Racquet.RACQUET_WIDTH_NARROW);
        assertEquals(Racquet.RACQUET_WIDTH_NARROW, narrowRacquet.racquetWidth, DELTA);
        
        // Test movement with narrow racquet
        float deltaTime = 0.016f;
        float accelX = 1.0f;
        narrowRacquet.update(deltaTime, accelX);
        
        // Check bounds are correctly set for narrow racquet
        assertEquals(narrowRacquet.position.x - Racquet.RACQUET_WIDTH_NARROW / 2,
                    narrowRacquet.bounds.lowerLeft.x, DELTA);
        assertEquals(Racquet.RACQUET_WIDTH_NARROW, narrowRacquet.bounds.width, DELTA);
    }
} 