package com.markuswi.ld27.map;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.markuswi.gdxessentials.gfx.texture.ImageProcessor;
import com.markuswi.ld27.entity.BasicEnemy;
import com.markuswi.ld27.entity.EntityManager;
import com.markuswi.ld27.entity.Player;

public class MapManager {

	private static final MapManager INSTANCE = new MapManager();

	public static MapManager getInstance() {
		return MapManager.INSTANCE;
	}

	public GameMap currentGameMap;
	private List<GameMapData> availableMaps = new LinkedList<GameMapData>();
	private GameMapData currentGameMapData;

	private MapManager() {

	}

	public GameMap getCurrentGameMap() {
		return this.currentGameMap;
	}

	public GameMapData getCurrentMapData() {
		return this.currentGameMapData;
	}

	public void init() {
		this.availableMaps = new LinkedList<GameMapData>();
		for (GameMapData gameMapData : GameMapData.values()) {
			this.availableMaps.add(gameMapData);
		}
		Collections.shuffle(this.availableMaps);
	}

	private void loadMap(String filename) {
		EntityManager.getInstance().getEntites().clear();
		EntityManager.getInstance().setPlayer(null);
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
				} else if (pixels[x][y].startsWith("ff00ff")) {
					EntityManager.getInstance().getEntites().add(new BasicEnemy(x, pixels[x].length - 1 - y));
				} else if (pixels[x][y].startsWith("00ff00")) {
					EntityManager.getInstance().setPlayer(new Player(x, pixels[x].length - 1 - y));
				}
			}
		}
		GameMap gameMap = new GameMap(tiles.length, tiles[0].length);
		gameMap.setTiles(tiles);

		this.currentGameMap = gameMap;
	}

	public void loadNextMap() {

		String filename = this.availableMaps.get(0).getFilename();
		this.currentGameMapData = this.availableMaps.get(0);
		this.availableMaps.remove(0);

		System.out.println(this.availableMaps.size());
		System.out.println("loading " + filename);
		this.loadMap(filename);
	}

	public void setCurrentGameMap(GameMap currentGameMap) {
		this.currentGameMap = currentGameMap;
	}

}
