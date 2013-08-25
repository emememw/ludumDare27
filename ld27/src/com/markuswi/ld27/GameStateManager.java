package com.markuswi.ld27;

import com.markuswi.gdxessentials.audio.AudioManager;
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
	private boolean showStartScreen = true;
	private boolean showEndScreen;

	private GameStateManager() {
	}

	public int getCurrentLevel() {
		return this.currentLevel;
	}

	public boolean isShowEndScreen() {
		return this.showEndScreen;
	}

	public boolean isShowStartScreen() {
		return this.showStartScreen;
	}

	public void nextLevel() {
		AudioManager.getInstance().getSounds().get("success").play();
		this.currentLevel++;
		EffectManager.getInstance().resetEffects();
		UiManager.getInstance().resetTimer();
		MapManager.getInstance().loadNextMap();
		EntityManager.getInstance().getPlayer().updateCamera();
	}

	public void setShowEndScreen(boolean showEndScreen) {
		this.showEndScreen = showEndScreen;
	}

	public void setShowStartScreen(boolean showStartScreen) {
		this.showStartScreen = showStartScreen;
	}

	public void startGame() {
		AudioManager.getInstance().getSounds().get("start").play();
		this.currentLevel = 1;
		EffectManager.getInstance().resetEffects();
		MapManager.getInstance().init();
		UiManager.getInstance().resetTimer();
		MapManager.getInstance().loadNextMap();
		EntityManager.getInstance().getPlayer().updateCamera();
		AudioManager.getInstance().getSongs().get("song").stop();
		AudioManager.getInstance().getSongs().get("song").setVolume(0.35f);
		AudioManager.getInstance().getSongs().get("song").setLooping(true);
		AudioManager.getInstance().getSongs().get("song").play();
	}

}
