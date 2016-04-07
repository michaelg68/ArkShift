package com.badlogic.androidgames.framework;

import com.badlogic.androidgames.framework.math.Vector2;

public class DynamicGameObjectCircle extends GameObjectCircle {
	public final Vector2 velocity;
	public final Vector2 accel;

	public DynamicGameObjectCircle(float x, float y, float radius) {
		super(x, y, radius);
		velocity = new Vector2();
		accel = new Vector2();
	}

}
