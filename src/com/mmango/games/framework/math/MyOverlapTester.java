package com.mmango.games.framework.math;

import com.badlogic.androidgames.framework.math.Circle;
import com.badlogic.androidgames.framework.math.Rectangle;

import android.util.Log;

public class MyOverlapTester {
    
    
    public static int circleCompletelyInsideRectangle(Circle c, Rectangle r) {
    	//to test if the circle is COMPLETELY inside the Rectangle 
    	int border = 0;
    	final int TOP_BORDER = 1;
    	final int BOTTOM_BORDER = 2;
    	final int LEFT_BORDER = 3;
    	final int RIGHT_BORDER = 4;
    	
    	
    	////
        
        if(c.center.x - c.radius < r.lowerLeft.x ) {
        	border = LEFT_BORDER; 
        } 
        else if(c.center.x + c.radius > r.lowerLeft.x + r.width) {
        	border = RIGHT_BORDER; 
        }
        else if(c.center.y - c.radius < r.lowerLeft.y) {
        	border = BOTTOM_BORDER; 
        } 
        else if(c.center.y + c.radius > r.lowerLeft.y + r.height) {
        	border = TOP_BORDER; 
        }
        
        //Log.d("OverlapTester:circleCompletelyInsideRectangle", "border=" + Boolean.toString(border));

        return border;           
    }
    
/*    public static boolean circleCompletelyInsideRectangle(Circle c, Rectangle r) {
    	//to test if the circle is COMPLETELY inside the Rectangle 
        boolean inside = true;
        if(c.center.x - c.radius < r.lowerLeft.x ) {
            inside = false; 
        } 
        else if(c.center.x + c.radius > r.lowerLeft.x + r.width) {
            inside = false; 
        }
        else if(c.center.y - c.radius < r.lowerLeft.y) {
            inside = false; 
        } 
        else if(c.center.y + c.radius > r.lowerLeft.y + r.height) {
            inside = false; 
        }
        
        //Log.d("OverlapTester:circleCompletelyInsideRectangle", "inside=" + Boolean.toString(inside));

        return inside;           
    }*/
    
/*    public static boolean circleCompletelyInsideRectangle(Circle c, Rectangle r) {
    	//to test if the circle is COMPLETELY inside the Rectangle 
        float closestX = c.center.x;
        float closestY = c.center.y;
        
        if(c.center.x < r.lowerLeft.x + r.width / 2) {
            closestX = r.lowerLeft.x; 
        } 
        else if(c.center.x > r.lowerLeft.x + r.width / 2) {
            closestX = r.lowerLeft.x + r.width;
        }
          
        if(c.center.y < r.lowerLeft.y + r.height / 2) {
            closestY = r.lowerLeft.y;
        } 
        else if(c.center.y > r.lowerLeft.y + r.height / 2) {
            closestY = r.lowerLeft.y + r.height;
        }
        
        Log.d("OverlapTester:circleCompletelyInsideRectangle", "c.center.x=" + Float.toString(c.center.x));
        Log.d("OverlapTester:circleCompletelyInsideRectangle", "c.center.y=" + Float.toString(c.center.y));
        Log.d("OverlapTester:circleCompletelyInsideRectangle", "closestX=" + Float.toString(closestX));
        Log.d("OverlapTester:circleCompletelyInsideRectangle", "closestY=" + Float.toString(closestY));
        float dist = c.center.dist(closestX, closestY);
        float distSquared = c.center.distSquared(closestX, closestY);
        float radiusSquared = c.radius * c.radius;
        Log.d("OverlapTester:circleCompletelyInsideRectangle", "dist=" + Float.toString(dist));
        Log.d("OverlapTester:circleCompletelyInsideRectangle", "distSquared=" + Float.toString(distSquared));
        Log.d("OverlapTester:circleCompletelyInsideRectangle", "radiusSquared=" + Float.toString(radiusSquared));
        
        boolean inside = distSquared > radiusSquared;
        Log.d("OverlapTester:circleCompletelyInsideRectangle", "inside=" + Boolean.toString(inside));

        return inside;           
    }*/
    
      
}
