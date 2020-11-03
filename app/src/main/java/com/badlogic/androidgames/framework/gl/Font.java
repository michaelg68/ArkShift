package com.badlogic.androidgames.framework.gl;

import com.mmango.arkshift.Assets;

import android.util.Log;

public class Font {

	public final Texture texture;
	public final int glyphWidth;
	public final int glyphHeight;
	public final TextureRegion[] glyphs = new TextureRegion[96];

	public Font(Texture texture, int offsetX, int offsetY, int glyphsPerRow, int glyphWidth, int glyphHeight) {
		this.texture = texture;
		this.glyphWidth = glyphWidth;
		this.glyphHeight = glyphHeight;
		int x = offsetX;
		int y = offsetY;
		for (int i = 0; i < 96; i++) {
			glyphs[i] = new TextureRegion(texture, x, y, glyphWidth, glyphHeight);
			x += glyphWidth;
			if (x == offsetX + glyphsPerRow * glyphWidth) {
				x = offsetX;
				y += glyphHeight;
			}
		}
	}

	public void drawText(SpriteBatcher batcher, String text, float x, float y) {
		int len = text.length();
		for (int i = 0; i < len; i++) {
			int c = text.charAt(i) - ' ';
			if (c < 0 || c > glyphs.length - 1)
				continue;
			TextureRegion glyph = glyphs[c];
			batcher.drawSprite(x, y, glyphWidth, glyphHeight, glyph);
			x += glyphWidth;
		}
	}
	
	public void drawTextZoomed(SpriteBatcher batcher, String text, float x, float y, float xZoom, float yZoom ) {
		int len = text.length();
		for (int i = 0; i < len; i++) {
			int c = text.charAt(i) - ' ';
			if (c < 0 || c > glyphs.length - 1)
				continue;
			TextureRegion glyph = glyphs[c];
			batcher.drawSprite(x, y, glyphWidth * xZoom, glyphHeight * yZoom, glyph);
			x += glyphWidth;
		}
	}
	
	public void drawTextZoomedWider(SpriteBatcher batcher, String text, float x, float y, float xZoom, float yZoom ) {
		int len = text.length();
		for (int i = 0; i < len; i++) {
			int c = text.charAt(i) - ' ';
			if (c < 0 || c > glyphs.length - 1)
				continue;
			TextureRegion glyph = glyphs[c];
			//Log.d("Font:drawTextZoomedWider", "c = " + c + "; x = " + x);
			batcher.drawSprite(x, y, glyphWidth * xZoom, glyphHeight * yZoom, glyph);
			x += glyphWidth * xZoom;
		}
	}

}
