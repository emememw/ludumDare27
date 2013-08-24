package com.markuswi.ld27.map;

import com.markuswi.gdxessentials.gfx.texture.ImageProcessor;
import com.markuswi.ld27.entity.Entity;
import com.markuswi.ld27.entity.EntityManager;
import com.markuswi.ld27.entity.Player;

public class MapManager {

	private static final MapManager INSTANCE = new MapManager();

	public static MapManager getInstance() {
		return MapManager.INSTANCE;
	}

	public GameMap currentGameMap;

	private MapManager() {
		// this.currentGameMap = this.loadMap("map2.png");
	}

	public GameMap getCurrentGameMap() {
		return this.currentGameMap;
	}

	public void loadMap(String filename) {

		String[][] pixels = ImageProcessor.getPixelsFromImage("maps/" + filename);
		Tile[][] tiles = new Tile[pixels.length][pixels[0].length];
		for (int x = 0; x < pixels.length; x++) {
			for (int y = 0; y < pixels[x].length; y++) {
				if (pixels[x][y].startsWith("000000")) {
					tiles[x][pixels[x].length - 1 - y] = Tile.TEST;
				} else if (pixels[x][y].startsWith("0000ff")) {
					tiles[x][pixels[x].length - 1 - y] = Tile.DOOR;
				} else if (pixels[x][y].startsWith("ff0000")) {
					tiles[x][pixels[x].length - 1 - y] = Tile.LAVA;
				}
			}
		}
		GameMap gameMap = new GameMap(tiles.length, tiles[0].length);
		gameMap.setTiles(tiles);
		EntityManager.getInstance().getEntites().clear();
		EntityManager.getInstance().getEntites().add(new Entity(2, 1));
		EntityManager.getInstance().setPlayer(new Player(1, 2));
		this.currentGameMap = gameMap;
	}

	public void setCurrentGameMap(GameMap currentGameMap) {
		this.currentGameMap = currentGameMap;
	}

}
