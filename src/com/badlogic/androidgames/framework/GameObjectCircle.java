package com.badlogic.androidgames.framework;

import com.badlogic.androidgames.framework.math.Circle;
import com.badlogic.androidgames.framework.math.Vector2;

public class GameObjectCircle {
	public final Vector2 position;
	public final Circle bounds;

	public GameObjectCircle(float x, float y, float radius) {
		this.position = new Vector2(x, y);
		this.bounds = new Circle(x, y, radius);
	}

}
