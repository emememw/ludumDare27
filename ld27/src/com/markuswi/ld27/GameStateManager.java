package com.markuswi.ld27;

import com.markuswi.ld27.entity.EntityManager;
import com.markuswi.ld27.map.MapManager;

public class GameStateManager {

	private static final GameStateManager INSTANCE = new GameStateManager();

	public static GameStateManager getInstance() {
		return GameStateManager.INSTANCE;
	}

	private GameStateManager() {
	}

	public void nextLevel() {
		UiManager.getInstance().resetTimer();
		MapManager.getInstance().loadNextMap();
		EntityManager.getInstance().getPlayer().updateCamera();
	}

	public void startGame() {
		MapManager.getInstance().init();
		UiManager.getInstance().resetTimer();
		MapManager.getInstance().loadNextMap();
		EntityManager.getInstance().getPlayer().updateCamera();
	}

}
