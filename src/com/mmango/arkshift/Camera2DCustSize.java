package com.mmango.arkshift;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.framework.math.Vector2;

public class Camera2DCustSize {
	public final Vector2 position;
	public float zoom;
	public final float frustumWidth;
	public final float frustumHeight;
	final GLGraphics glGraphics;
//	public int border;
//	public int notificationAreaAndBorder;
//	public int widthN;
//	public int heightN;

	public Camera2DCustSize(GLGraphics glGraphics, float frustumWidth,
			float frustumHeight) {
		this.glGraphics = glGraphics;
		this.frustumWidth = frustumWidth;
		this.frustumHeight = frustumHeight;
		this.position = new Vector2(frustumWidth / 2, frustumHeight / 2);
		this.zoom = 1.0f;
	}

	public void setViewportAndMatrices() {
		GL10 gl = glGraphics.getGL();

		// Log.d("Camera2DCustSize", "glGraphics.getWidth() = " +
		// glGraphics.getWidth());
		// //////On LG G3: Camera2DCustSize(23085): glGraphics.getWidth() = 1440
		// Log.d("Camera2DCustSize", "glGraphics.getHeight() = " +
		// glGraphics.getHeight());
		// //////On LG G3: Camera2DCustSize(23085): glGraphics.getHeight() =
		// 2560
		
		World.widthCoefficient = glGraphics.getWidth() / (frustumWidth * 10f);
		World.heightCoefficient = glGraphics.getHeight()/ (frustumHeight * 10f);

		// Log.d("Camera2DCustSize", "widthCoefficient = " + widthCoefficient +
		// ";" + " heightCoefficient = " + heightCoefficient);

		// gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		// gl.glViewport(100, 100, 1440 - 100 * 2, 2560 - 100 * 2); //it's good

		// in the next line: 20 pixels - the width/height of the frame of
		// ArkShift game field
		// 150 pixels - the height of the notification area just above the game
		// field in ArkShift

		World.border = Math.round(20 * World.widthCoefficient);
		World.notificationAreaAndBorder = Math.round((150 + 20) * World.heightCoefficient);
		World.widthN = glGraphics.getWidth() - World.border * 2;
		World.heightN = glGraphics.getHeight() - World.border - World.notificationAreaAndBorder;

		// Log.d("Camera2DCustSize", "border = " + border);
		// Log.d("Camera2DCustSize", "notificationAreaAndBorder = " +
		// notificationAreaAndBorder);
		// Log.d("Camera2DCustSize", "widthN = " + widthN);
		// Log.d("Camera2DCustSize", "heightN = " + heightN);

		gl.glViewport(World.border, World.border, World.widthN, World.heightN);
		/*
		 * gl.glViewport(Math.round(20 * widthCoefficient), Math.round((150 +
		 * 20) * heightCoefficient), glGraphics.getWidth() - Math.round(20 *
		 * widthCoefficient), glGraphics.getHeight() - Math.round((150 + 20 * 2)
		 * * heightCoefficient));
		 */

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(position.x - frustumWidth * zoom / 2, position.x
				+ frustumWidth * zoom / 2, position.y - frustumHeight * zoom
				/ 2, position.y + frustumHeight * zoom / 2, 1, -1);

		/*
		 * gl.glOrthof(position.x - frustumWidth * zoom / 2 + 2f, position.x +
		 * frustumWidth * zoom/ 2 - 2f, position.y - frustumHeight * zoom / 2 +
		 * 2f , position.y + frustumHeight * zoom/ 2 - 2f, 1, -1);
		 */

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void touchToWorld(Vector2 touch) {
		touch.x = (touch.x / (float) glGraphics.getWidth()) * frustumWidth
				* zoom;
		touch.y = (1 - touch.y / (float) glGraphics.getHeight())
				* frustumHeight * zoom;
		touch.add(position).sub(frustumWidth * zoom / 2,
				frustumHeight * zoom / 2);
	}
}