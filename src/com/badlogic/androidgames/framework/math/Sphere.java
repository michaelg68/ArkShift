package com.badlogic.androidgames.framework.math;

public class Sphere {
	public final Vector3 center = new Vector3();
	public float radius;

	public Sphere(float x, float y, float z, float radius) {
		this.center.x = x;
		this.center.y = y;
		this.center.y = z;
		this.radius = radius;
	}

}
