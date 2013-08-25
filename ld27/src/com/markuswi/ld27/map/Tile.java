package com.markuswi.ld27.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;

public enum Tile {

	WALL1(false, false, 0, 0), DOOR(true, false, 1, 0), LAVA(true, true, 2, 0), NONE(true, false, 3, 0),

	WALL2(false, false, 0, 1), WALL3(false, false, 0, 2), WALL4(false, false, 0, 3), WALL5(false, false, 0, 4), WALL6(false, false, 0, 5)

	;

	private int textureRegionX;
	private int textureRegionY;
	private boolean accessible;
	private boolean deadly;

	private Tile(boolean accessible, boolean deadly, int textureRegionX, int textureRegionY) {
		this.textureRegionX = textureRegionX;
		this.textureRegionY = textureRegionY;
		this.accessible = accessible;
		this.deadly = deadly;
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

	public boolean isDeadly() {
		return this.deadly;
	}

}
