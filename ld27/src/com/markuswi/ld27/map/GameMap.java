package com.markuswi.ld27.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.markuswi.ld27.Globals;

public class GameMap {

	private Tile[][] tiles;

	public GameMap(int width, int height) {

		this.tiles = new Tile[width][height];

	}

	public Tile[][] getTiles() {
		return this.tiles;
	}

	public void render(SpriteBatch batch) {
		for (int x = 0; x < this.tiles.length; x++) {
			for (int y = 0; y < this.tiles[x].length; y++) {
				batch.draw(Tile.NONE.getTextureRegion(), x * Globals.tilesize, y * Globals.tilesize, Globals.tilesize, Globals.tilesize);
				if (this.tiles[x][y] != null) {
					batch.draw(this.tiles[x][y].getTextureRegion(), x * Globals.tilesize, y * Globals.tilesize, Globals.tilesize, Globals.tilesize);
				}
			}
		}
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

}
