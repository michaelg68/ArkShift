package com.badlogic.androidgames.framework.math;

import android.util.Log;

public class OverlapTester {
    public static boolean overlapCircles(Circle c1, Circle c2) {
        float distance = c1.center.distSquared(c2.center);
        float radiusSum = c1.radius + c2.radius;
        return distance <= radiusSum * radiusSum;
    }
    
    public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
        if(r1.lowerLeft.x < r2.lowerLeft.x + r2.width &&
           r1.lowerLeft.x + r1.width > r2.lowerLeft.x &&
           r1.lowerLeft.y < r2.lowerLeft.y + r2.height &&
           r1.lowerLeft.y + r1.height > r2.lowerLeft.y)
            return true;
        else
            return false;
    }
    
    public static boolean overlapCircleRectangle(Circle c, Rectangle r) {
        float closestX = c.center.x;
        float closestY = c.center.y;
        
        if(c.center.x < r.lowerLeft.x) {
            closestX = r.lowerLeft.x; 
        } 
        else if(c.center.x > r.lowerLeft.x + r.width) {
            closestX = r.lowerLeft.x + r.width;
        }
          
        if(c.center.y < r.lowerLeft.y) {
            closestY = r.lowerLeft.y;
        } 
        else if(c.center.y > r.lowerLeft.y + r.height) {
            closestY = r.lowerLeft.y + r.height;
        }
        
        return c.center.distSquared(closestX, closestY) < c.radius * c.radius;           
    }
    
    
    public static int circleCompletelyInsideRectangle(Circle c, Rectangle r) {
    	//to test if the circle is COMPLETELY inside the Rectangle 
    	int inside = 0;
    	//inside 0 -> no collision; 1 -> collision x, 2 -> collision y//
        
        if(c.center.x - c.radius < r.lowerLeft.x ) {
            inside = 2; 
        } 
        else if(c.center.x + c.radius > r.lowerLeft.x + r.width) {
            inside = 2; 
        }
        else if(c.center.y - c.radius < r.lowerLeft.y) {
            inside = 1; 
        } 
        else if(c.center.y + c.radius > r.lowerLeft.y + r.height) {
            inside = 1; 
        }
        
        //Log.d("OverlapTester:circleCompletelyInsideRectangle", "inside=" + Boolean.toString(inside));

        return inside;           
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
    
    public static boolean pointInCircle(Circle c, Vector2 p) {
        return c.center.distSquared(p) < c.radius * c.radius;
    }
    
    public static boolean pointInCircle(Circle c, float x, float y) {
        return c.center.distSquared(x, y) < c.radius * c.radius;
    }
    
    public static boolean pointInRectangle(Rectangle r, Vector2 p) {
        return r.lowerLeft.x <= p.x && r.lowerLeft.x + r.width >= p.x &&
               r.lowerLeft.y <= p.y && r.lowerLeft.y + r.height >= p.y;
    }
    
    public static boolean pointInRectangle(Rectangle r, float x, float y) {
        return r.lowerLeft.x <= x && r.lowerLeft.x + r.width >= x &&
               r.lowerLeft.y <= y && r.lowerLeft.y + r.height >= y;
    }
    
    public static boolean overlapSpheres(Sphere s1, Sphere s2) {
        float distance = s1.center.distSquared(s2.center);
        float radiusSum = s1.radius + s2.radius;
        return distance <= radiusSum * radiusSum;
    }

    public static boolean pointInSphere(Sphere c, Vector3 p) {
        return c.center.distSquared(p) < c.radius * c.radius;
    }

    public static boolean pointInSphere(Sphere c, float x, float y, float z) {
        return c.center.distSquared(x, y, z) < c.radius * c.radius;
    }       
}
