package com.markuswi.ld27.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.Globals;

public class GameMap {

	private Tile[][] tiles;

	private float renderTime = 0;
	private boolean textureSwitched;

	public GameMap(int width, int height) {

		this.tiles = new Tile[width][height];

	}

	public Tile[][] getTiles() {
		return this.tiles;
	}

	public void render(SpriteBatch batch) {

		if (this.renderTime > 0.5f) {
			this.textureSwitched = !this.textureSwitched;
			this.renderTime = 0;
		}

		for (int x = 0; x < this.tiles.length; x++) {
			for (int y = 0; y < this.tiles[x].length; y++) {
				batch.draw(Tile.NONE.getTextureRegion(), x * Globals.tilesize, y * Globals.tilesize, Globals.tilesize, Globals.tilesize);
				if (this.tiles[x][y] != null) {
					if (this.textureSwitched && this.tiles[x][y] == Tile.LAVA) {
						batch.draw(TextureManager.getInstance().getTextureSheets().get("tiles").getTextureRegions()[2][1], x * Globals.tilesize, y
								* Globals.tilesize, Globals.tilesize, Globals.tilesize);
					} else {
						batch.draw(this.tiles[x][y].getTextureRegion(), x * Globals.tilesize, y * Globals.tilesize, Globals.tilesize, Globals.tilesize);
					}

				}
			}
		}
		this.renderTime += Gdx.graphics.getDeltaTime();
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

}
