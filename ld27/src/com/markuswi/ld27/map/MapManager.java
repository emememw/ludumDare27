package com.markuswi.ld27.map;

import com.markuswi.gdxessentials.gfx.texture.ImageProcessor;

public class MapManager {

	private static final MapManager INSTANCE = new MapManager();

	public static MapManager getInstance() {
		return MapManager.INSTANCE;
	}

	public GameMap currentGameMap;

	private MapManager() {
		this.currentGameMap = this.loadMap("map1.png");
	}

	public GameMap getCurrentGameMap() {
		return this.currentGameMap;
	}

	public GameMap loadMap(String filename) {

		String[][] pixels = ImageProcessor.getPixelsFromImage("maps/" + filename);
		Tile[][] tiles = new Tile[pixels.length][pixels[0].length];
		for (int x = 0; x < pixels.length; x++) {
			for (int y = 0; y < pixels[x].length; y++) {
				if (pixels[x][y].startsWith("000000")) {
					tiles[x][pixels[x].length - 1 - y] = Tile.TEST;
				} else if (pixels[x][y].startsWith("0000ff")) {
					tiles[x][pixels[x].length - 1 - y] = Tile.DOOR;
				}
			}
		}
		GameMap gameMap = new GameMap(tiles.length, tiles[0].length);
		gameMap.setTiles(tiles);
		return gameMap;
	}

	public void setCurrentGameMap(GameMap currentGameMap) {
		this.currentGameMap = currentGameMap;
	}

}
