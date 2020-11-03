package com.badlogic.androidgames.framework.math;

public class Rectangle {
	public final Vector2 lowerLeft;
	public float width, height;

	//Remember - in Rectange x and y coordinates point to the lowerLeft corner of the rectangle!
	//Counting from the lower left corner of the screen!
	public Rectangle(float x, float y, float width, float height) {
		this.lowerLeft = new Vector2(x, y);
		this.width = width;
		this.height = height;
	}

}
