package com.mmango.arkshift;

import android.util.Log;

import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;

public class ScoreFont {

	public final Texture texture;
	public final int glyphWidth;
	public final int glyphHeight;
	public final TextureRegion[] glyphs = new TextureRegion[12];

	public ScoreFont(Texture texture, int glyphWidth, int glyphHeight) {
		this.texture = texture;
		this.glyphWidth = glyphWidth;  //128
		this.glyphHeight = glyphHeight; //128
		// int x = offsetX;
		// int y = offsetY;
		/*
		 * for (int i = 0; i < 96; i++) { glyphs[i] = new TextureRegion(texture,
		 * x, y, glyphWidth, glyphHeight); x += glyphWidth; if (x == offsetX +
		 * glyphsPerRow * glyphWidth) { x = offsetX; y += glyphHeight; } }
		 */
		glyphs[0] = new TextureRegion(texture, 2176, 768, 128, 128); // 0
		glyphs[1] = new TextureRegion(texture, 2432, 512, 128, 128);
		glyphs[2] = new TextureRegion(texture, 1152, 1792, 128, 128);
		glyphs[3] = new TextureRegion(texture, 2728, 256, 128, 128);
		glyphs[4] = new TextureRegion(texture, 3070, 0, 128, 128);
		glyphs[5] = new TextureRegion(texture, 3070, 0, 128, 128);
		glyphs[6] = new TextureRegion(texture, 2432, 640, 128, 128);
		glyphs[7] = new TextureRegion(texture, 1280, 1792, 128, 128);
		glyphs[8] = new TextureRegion(texture, 2048, 1024, 128, 128);
		glyphs[9] = new TextureRegion(texture, 2304, 768, 128, 128); // 9
		glyphs[10] = new TextureRegion(texture, 2176, 896, 128, 128); // scoreDot
		glyphs[11] = new TextureRegion(texture, 2728, 384, 128, 128); // scoreMinus

	}

	public void drawScoreZoomed(SpriteBatcher batcher, String text, float x, float y, float xZoom, float yZoom) {
		boolean printable = true;
		TextureRegion glyph = glyphs[0];
		int len = text.length();
		for (int i = 0; i < len; i++) {
			char c = text.charAt(i);
			Log.d("ScoreFont:drawText", "Read character = \"" + c + "\"");

			switch (c) {
			case '0':
				glyph = glyphs[0];
				break;
			case '1':
				glyph = glyphs[1];
				break;
			case '2':
				glyph = glyphs[2];
				break;
			case '3':
				glyph = glyphs[3];
				break;
			case '4':
				glyph = glyphs[4];
				break;
			case '5':
				glyph = glyphs[5];
				break;
			case '6':
				glyph = glyphs[6];
				break;
			case '7':
				glyph = glyphs[7];
				break;
			case '8':
				glyph = glyphs[8];
				;
				break;
			case '9':
				glyph = glyphs[9];
				break;
			case '.':
				glyph = glyphs[10];
				break;
			case '-':
				glyph = glyphs[11];
				break;
			default:
				printable = false; // if the character is space " " , then do not print it
				break;
			}

			if (printable) {
				Log.d("ScoreFont:drawText", "Printing character = \"" + c + "\"");
				batcher.drawSprite(x, y, glyphWidth * xZoom, glyphHeight * yZoom, glyph);
			}
			x = x + glyphWidth * xZoom ; // if the character is space " " , then do not print it
			printable = true;
		}
	}

}
