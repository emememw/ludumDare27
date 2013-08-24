package com.markuswi.ld27.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;

public class Tile {

	private boolean accessible;
	private int textureRegionX;
	private int textureRegionY;

	public Tile(boolean accessible, int textureRegionX, int textureRegionY) {
		this.accessible = accessible;
		this.textureRegionX = textureRegionX;
		this.textureRegionY = textureRegionY;
	}

	public TextureRegion getTextureRegion() {
		return TextureManager.getInstance().getTextureSheets().get("tiles").getTextureRegions()[this.textureRegionX][this.textureRegionY];
	}

	public int getTextureRegionX() {
		return this.textureRegionX;
	}

	public int getTextureRegionY() {
		return this.textureRegionY;
	}

	public boolean isAccessible() {
		return this.accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public void setTextureRegionX(int textureRegionX) {
		this.textureRegionX = textureRegionX;
	}

	public void setTextureRegionY(int textureRegionY) {
		this.textureRegionY = textureRegionY;
	}

}
