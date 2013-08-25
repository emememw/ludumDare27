package com.markuswi.ld27;

import com.markuswi.ld27.effects.EffectManager;
import com.markuswi.ld27.entity.EntityManager;
import com.markuswi.ld27.map.MapManager;
import com.markuswi.ld27.ui.UiManager;

public class GameStateManager {

	private static final GameStateManager INSTANCE = new GameStateManager();

	public static GameStateManager getInstance() {
		return GameStateManager.INSTANCE;
	}

	private int currentLevel = 0;

	private GameStateManager() {
	}

	public int getCurrentLevel() {
		return this.currentLevel;
	}

	public void nextLevel() {
		this.currentLevel++;
		EffectManager.getInstance().resetEffects();
		UiManager.getInstance().resetTimer();
		MapManager.getInstance().loadNextMap();
		EntityManager.getInstance().getPlayer().updateCamera();
	}

	public void startGame() {
		this.currentLevel = 1;
		EffectManager.getInstance().resetEffects();
		MapManager.getInstance().init();
		UiManager.getInstance().resetTimer();
		MapManager.getInstance().loadNextMap();
		EntityManager.getInstance().getPlayer().updateCamera();
	}

}
