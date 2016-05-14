package com.mmango.arkshift;

import android.util.Log;

import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;

public class ScoreFont {

	public final Texture texture;
	public final int glyphWidth;
	public final int glyphHeight;
	public final TextureRegion[] glyphs = new TextureRegion[13];

	public ScoreFont(Texture texture, int glyphWidth, int glyphHeight) {
		this.texture = texture;
		this.glyphWidth = glyphWidth;  //128
		this.glyphHeight = glyphHeight; //128

		glyphs[0] =  new TextureRegion(texture, 1024, 850, 64, 64); // 0
		glyphs[1] =  new TextureRegion(texture, 960, 916, 64, 64);
		glyphs[2] =  new TextureRegion(texture, 1877, 0, 64, 64);
		glyphs[3] =  new TextureRegion(texture, 1280, 634, 64, 64);
		glyphs[4] =  new TextureRegion(texture, 1344, 570, 64, 64);
		glyphs[5] =  new TextureRegion(texture, 1408, 512, 64, 64);
		glyphs[6] =  new TextureRegion(texture, 1664, 256, 64, 64);
		glyphs[7] =  new TextureRegion(texture, 1600, 320, 64, 64);
		glyphs[8] =  new TextureRegion(texture, 1536, 384, 64, 64);
		glyphs[9] =  new TextureRegion(texture, 1472, 448, 64, 64); // 9
		glyphs[10] = new TextureRegion(texture, 1792, 128, 64, 64); // scoreDot
		glyphs[11] = new TextureRegion(texture, 1088, 850, 64, 64); // scoreMinus
		glyphs[12] = new TextureRegion(texture, 1024, 914, 64, 64); // scoreNone

	}

	public void drawScoreZoomed(SpriteBatcher batcher, String text, float x, float y, float xZoom, float yZoom) {
		boolean printable = true;
		TextureRegion glyph = glyphs[0];
		int len = text.length();
		for (int i = 0; i < len; i++) {
			char c = text.charAt(i);
			//Log.d("ScoreFont:drawText", "Read character = \"" + c + "\"");

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
			case ' ':
				glyph = glyphs[12];
				break;
			default:
				printable = false; // if the character is space " " , then do not print it
				break;
			}

			if (printable) {
				//Log.d("ScoreFont:drawText", "Printing character = \"" + c + "\"");
				batcher.drawSprite(x, y, glyphWidth * xZoom, glyphHeight * yZoom, glyph);
			}
			x = x + glyphWidth * xZoom ; // if the character is space " " , then do not print it
			printable = true;
		}
	}

}
