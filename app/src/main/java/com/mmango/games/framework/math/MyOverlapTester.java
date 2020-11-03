package com.mmango.games.framework.math;

//import java.util.ArrayList;
//import java.util.List;

import com.badlogic.androidgames.framework.math.Circle;
import com.badlogic.androidgames.framework.math.Rectangle;
//import com.badlogic.androidgames.framework.math.Vector2;
//import com.mmango.arkshift.Brick;
//
//import android.util.Log;

public class MyOverlapTester {

	public final static int TOP_BORDER = 1;
	public final static int BOTTOM_BORDER = 2;
	public final static int LEFT_BORDER = 3;
	public final static int RIGHT_BORDER = 4;
	public final static int NO_COLLISION = 0;
	public final static int COLLISION_WITH_X = 1;
	public final static int COLLISION_WITH_Y = 2;
	public final static int COLLISION_WITH_CORNER = 5;

/*	public static int overlapCircleRectangleAdv(Circle c, Rectangle r) {
		float closestX = c.center.x;
		float closestY = c.center.y;
		int collisionStatus = NO_COLLISION;
		int closestVerticalSide = 0; // left or right side
		int closestHorisontalSide = 0; // top or bottom

		if ((c.center.x >= r.lowerLeft.x)
				&& (c.center.x <= r.lowerLeft.x + r.width)) {
			if (c.center.y < r.lowerLeft.y) {
				collisionStatus = BOTTOM_BORDER;
			} else {
				collisionStatus = TOP_BORDER;
			}
		} else if ((c.center.y >= r.lowerLeft.y)
				&& (c.center.y <= r.lowerLeft.y + r.height)) {
			if (c.center.x < r.lowerLeft.x) {
				collisionStatus = LEFT_BORDER;
			} else {
				collisionStatus = RIGHT_BORDER;
			}
		} else {
			collisionStatus = COLLISION_WITH_CORNER;
		}
		// Log.d("OverlapTester:overlapCircleRectangleAdv", "collisionStatus = "
		// + collisionStatus);
		return collisionStatus;
	}*/

	/*
	 * public static int overlapCircleRectangleHorisontal(Circle c, Rectangle
	 * r0, Rectangle r1) { //List<Float> touchResults = new ArrayList<Float>();
	 * if ((c.center.x <= r0.lowerLeft.x + Brick.BRICK_WIDTH)) { return 0; }
	 * else { return 1; } }
	 */

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

		// Log.d("OverlapTester:circleCompletelyInsideRectangle", "border = " +
		// border);

		return border;
	}

	/*
	 * public static int overlapCircleRectangleVertical(Circle c, Rectangle r0,
	 * Rectangle r1) { // TODO Auto-generated method stub return 0; }
	 */

/*	public static Circle overlapCircleInCorner(Circle c, Rectangle r0, Rectangle r1, boolean isCeiling) {
		// find the object in the higher row
		
		if (isCeiling) {
			c.center.y = Math.max(r0.lowerLeft.y, r1.lowerLeft.y) - c.radius;
		}

//		if (r0.lowerLeft.y < r1.lowerLeft.y) {
//			if (r0.lowerLeft.x < r1.lowerLeft.x) {
//				if (isCeiling) {
//					// case A:
//					c.center.x = r0.lowerLeft.x + r0.width + c.radius;
//					c.center.x = r0.lowerLeft.x + r0.width + c.radius;
//				}
//			}
//
//		}
		return c;
	}*/

	// public static int overlapCircleRectangleSide(Circle c, Rectangle r) {
	// // return the brick's side (bottom, top, left, side) which overlaps the
	// ball
	//
	// // //
	// int border = 0;
	// float toLeftSide = Math.abs(r.lowerLeft.x - (c.center.x + c.radius));
	// float toRightSide = Math.abs((c.center.x + c.radius) - (r.lowerLeft.x +
	// r.width));
	// float toBottom = Math.abs(r.lowerLeft.y - (c.center.y + c.radius));
	//
	// // {
	// // border = LEFT_BORDER;
	// // } else if (c.center.x + c.radius > r.lowerLeft.x + r.width) {
	// // border = RIGHT_BORDER;
	// // } else if (c.center.y - c.radius < r.lowerLeft.y) {
	// // border = BOTTOM_BORDER;
	// // } else if (c.center.y + c.radius > r.lowerLeft.y + r.height) {
	// // border = TOP_BORDER;
	// // }
	//
	// //Log.d("OverlapTester:circleCompletelyInsideRectangle", "border = " +
	// border);
	//
	// return border;
	// }

}
