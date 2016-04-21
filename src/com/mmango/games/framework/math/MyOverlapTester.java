package com.mmango.games.framework.math;

import com.badlogic.androidgames.framework.math.Circle;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

import android.util.Log;

public class MyOverlapTester {
	
	
	public final static int TOP_BORDER = 1;
	public final static int BOTTOM_BORDER = 2;
	public final static int LEFT_BORDER = 3;
	public final static int RIGHT_BORDER = 4;
	public final static int NO_COLLISION = 0;
	public final static int COLLISION_WITH_X = 1;
	public final static int COLLISION_WITH_Y = 2;

	public static int overlapCircleRectangleAdv(Circle c, Rectangle r) {
		float closestX = c.center.x;
		float closestY = c.center.y;
		int collisionStatus = NO_COLLISION;
		int closestVerticalSide = 0;   //left or right side
		int closestHorisontalSide = 0; //top or bottom
		
		
		

		if (c.center.x <= r.lowerLeft.x) {
			closestX = r.lowerLeft.x;
			closestVerticalSide = LEFT_BORDER;

		} else if (c.center.x >= r.lowerLeft.x + r.width) {
			closestX = r.lowerLeft.x + r.width;
			closestVerticalSide = RIGHT_BORDER;
		}

		if (c.center.y <= r.lowerLeft.y) {
			closestY = r.lowerLeft.y;
			closestHorisontalSide = BOTTOM_BORDER;
		} else if (c.center.y > r.lowerLeft.y + r.height) {
			closestY = r.lowerLeft.y + r.height;
			closestHorisontalSide = TOP_BORDER;
		}
		
		if (c.center.distSquared(closestX, closestY) < c.radius * c.radius) {
			//find distance from the center of the circle to the closestX and closestY
			if (Math.abs(closestX - c.center.x) < Math.abs(closestY - c.center.y)) { 
				collisionStatus = closestVerticalSide; 
			} else {
				collisionStatus = closestHorisontalSide;
			}
		}
		
		Log.d("OverlapTester:overlapCircleRectangleAdv", "collisionStatus = " + collisionStatus);
		return collisionStatus;
	}

	public static int circleCompletelyInsideRectangle(Circle c, Rectangle r) {
		// to test if the circle is COMPLETELY inside the Rectangle

		// //
		int border = 0;
		if (c.center.x - c.radius < r.lowerLeft.x) {
			border = LEFT_BORDER;
		} else if (c.center.x + c.radius > r.lowerLeft.x + r.width) {
			border = RIGHT_BORDER;
		} else if (c.center.y - c.radius < r.lowerLeft.y) {
			border = BOTTOM_BORDER;
		} else if (c.center.y + c.radius > r.lowerLeft.y + r.height) {
			border = TOP_BORDER;
		}

		 //Log.d("OverlapTester:circleCompletelyInsideRectangle", "border = " + border);

		return border;
	}
	
//	public static int overlapCircleRectangleSide(Circle c, Rectangle r) {
//		// return the brick's side (bottom, top, left, side) which overlaps the ball
//
//		// //
//		int border = 0;
//		float toLeftSide = Math.abs(r.lowerLeft.x - (c.center.x + c.radius));
//		float toRightSide = Math.abs((c.center.x + c.radius) - (r.lowerLeft.x + r.width));
//		float toBottom = Math.abs(r.lowerLeft.y - (c.center.y + c.radius));
//		
////		{
////			border = LEFT_BORDER;
////		} else if (c.center.x + c.radius > r.lowerLeft.x + r.width) {
////			border = RIGHT_BORDER;
////		} else if (c.center.y - c.radius < r.lowerLeft.y) {
////			border = BOTTOM_BORDER;
////		} else if (c.center.y + c.radius > r.lowerLeft.y + r.height) {
////			border = TOP_BORDER;
////		}
//
//		 //Log.d("OverlapTester:circleCompletelyInsideRectangle", "border = " + border);
//
//		return border;
//	}

}
