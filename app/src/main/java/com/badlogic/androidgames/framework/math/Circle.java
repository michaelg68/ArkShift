package com.badlogic.androidgames.framework.math;

public class Circle {
	public final Vector2 center = new Vector2();
	public float radius;

	public Circle(float x, float y, float radius) {
		this.center.x = x;
		this.center.y = y;
		this.radius = radius;
	}

}
