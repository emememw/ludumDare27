package com.markuswi.ld27.map;

public enum TileType {

	TEST(false, 0, 0);

	private int textureRegionX;
	private int textureRegionY;
	private boolean accessible;

	private TileType(boolean accessible, int textureRegionX, int textureRegionY) {
		this.textureRegionX = textureRegionX;
		this.textureRegionY = textureRegionY;
		this.accessible = accessible;
	}

	public int getTextureRegionX() {
		return this.textureRegionX;
	}

	public int getTextureRegionY() {
		return this.textureRegionY;
	}

	public Tile getTile() {
		return new Tile(this.accessible, this.textureRegionX, this.textureRegionY);
	}

	public boolean isAccessible() {
		return this.accessible;
	}

}
