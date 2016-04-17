package com.mmango.games.framework.gl;

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

    public Camera2DCustSize(GLGraphics glGraphics, float frustumWidth, float frustumHeight) {
        this.glGraphics = glGraphics;
        this.frustumWidth = frustumWidth;
        this.frustumHeight = frustumHeight;
        this.position = new Vector2(frustumWidth / 2, frustumHeight / 2);
        this.zoom = 1.0f;
    }

    public void setViewportAndMatrices() {
        GL10 gl = glGraphics.getGL();
       
        Log.d("Camera2DCustSize", "glGraphics.getWidth() = " + glGraphics.getWidth());
        Log.d("Camera2DCustSize", "glGraphics.getHeight() = " + glGraphics.getHeight());
        //gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        //gl.glViewport(100, 100, 2560 - 100 * 2, 1440 - 100 * 2);  //it's good
        gl.glViewport(20, 150 + 20, glGraphics.getWidth(), glGraphics.getHeight() - 150 - 20 * 2);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(position.x - frustumWidth * zoom / 2, 
                    position.x + frustumWidth * zoom/ 2, 
                    position.y - frustumHeight * zoom / 2 ,  
                    position.y + frustumHeight * zoom/ 2, 
                    1, -1);
      
        
/*        gl.glOrthof(position.x - frustumWidth * zoom / 2 + 2f, 
                position.x + frustumWidth * zoom/ 2 - 2f, 
                position.y - frustumHeight * zoom / 2 + 2f ,  
                position.y + frustumHeight * zoom/ 2 - 2f, 
                1, -1);*/
  
        
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void touchToWorld(Vector2 touch) {
        touch.x = (touch.x / (float) glGraphics.getWidth()) * frustumWidth * zoom;
        touch.y = (1 - touch.y / (float) glGraphics.getHeight()) * frustumHeight * zoom;
        touch.add(position).sub(frustumWidth * zoom / 2, frustumHeight * zoom / 2);
    }
}
