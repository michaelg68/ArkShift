package com.mmango.arkshift;

import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class MyGameObject {
	public final Vector2 position;
	public final Rectangle bounds;
	public final Vector2 velocity;
	public final Vector2 accel;

	public MyGameObject(float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x-width/2, y-height/2, width, height);
		velocity = new Vector2();
		accel = new Vector2();
	}

}
