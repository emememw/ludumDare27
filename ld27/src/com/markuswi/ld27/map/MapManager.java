package com.markuswi.ld27.map;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.markuswi.gdxessentials.gfx.texture.ImageProcessor;
import com.markuswi.ld27.GameStateManager;
import com.markuswi.ld27.entity.EntityManager;
import com.markuswi.ld27.entity.Player;
import com.markuswi.ld27.entity.enemy.Chaser;
import com.markuswi.ld27.entity.enemy.FallingBlock;
import com.markuswi.ld27.entity.enemy.Patroler;

public class MapManager {

	private static final MapManager INSTANCE = new MapManager();

	public static MapManager getInstance() {
		return MapManager.INSTANCE;
	}

	public GameMap currentGameMap;
	private List<String> availableMaps = new LinkedList<String>();

	private MapManager() {

	}

	public GameMap getCurrentGameMap() {
		return this.currentGameMap;
	}

	public void init() {
		this.availableMaps = new LinkedList<String>();
		for (int i = 0; i < 100; i++) {
			if (Gdx.files.internal("data/maps/map" + i + ".png").exists()) {
				this.availableMaps.add("map" + i + ".png");
			}
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
					tiles[x][pixels[x].length - 1 - y] = Tile.WALL;
				} else if (pixels[x][y].startsWith("0000ff")) {
					tiles[x][pixels[x].length - 1 - y] = Tile.DOOR;
				} else if (pixels[x][y].startsWith("ff0000")) {
					tiles[x][pixels[x].length - 1 - y] = Tile.LAVA;
				} else if (pixels[x][y].startsWith("ff00ff")) {
					EntityManager.getInstance().getEntites().add(new FallingBlock(x, pixels[x].length - 1 - y));
				} else if (pixels[x][y].startsWith("ffde00")) {
					EntityManager.getInstance().getEntites().add(new Chaser(x, pixels[x].length - 1 - y));
				} else if (pixels[x][y].startsWith("00ff96")) {
					EntityManager.getInstance().getEntites().add(new Patroler(x, pixels[x].length - 1 - y));
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

		if (this.availableMaps.isEmpty()) {
			GameStateManager.getInstance().setShowEndScreen(true);
		} else {
			String filename = this.availableMaps.get(0);
			this.availableMaps.remove(0);

			System.out.println(this.availableMaps.size());
			System.out.println("loading " + filename);
			this.loadMap(filename);
		}
	}

	public void setCurrentGameMap(GameMap currentGameMap) {
		this.currentGameMap = currentGameMap;
	}

}
