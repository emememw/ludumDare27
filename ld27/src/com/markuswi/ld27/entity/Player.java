package com.markuswi.ld27.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.markuswi.gdxessentials.gfx.camera.CameraManager;
import com.markuswi.ld27.GameStateManager;
import com.markuswi.ld27.Globals;
import com.markuswi.ld27.map.MapManager;
import com.markuswi.ld27.map.Tile;

public class Player extends Entity {

	private boolean jumpButtonPressed;

	public Player(int startGridX, int startGridY) {
		super(startGridX, startGridY);
	}

	private void levelComplete() {
		GameStateManager.getInstance().startGame();
	}

	@Override
	public void onDeath() {
		GameStateManager.getInstance().startGame();
	}

	@Override
	public void tick() {
		super.tick();
		this.updateCamera();
	}

	@Override
	protected void tickLogic() {
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			this.moveHorizontal(this.speed);
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			this.moveHorizontal(-this.speed);
		}
		if (Gdx.input.isKeyPressed(Keys.UP) && !this.jumpButtonPressed) {
			this.jumpButtonPressed = true;
			this.jump();
		} else if (!Gdx.input.isKeyPressed(Keys.UP)) {
			this.jumpButtonPressed = false;
		}

		if (MapManager.getInstance().getCurrentGameMap().getTiles()[Double.valueOf(this.getX() / Globals.tilesize).intValue()][Double.valueOf(
				this.getY() / Globals.tilesize).intValue()] == Tile.DOOR) {
			this.levelComplete();
		}
	}

	public void updateCamera() {
		CameraManager.getInstance().getCamera().position.x = this.getX();
		if (this.getY() < CameraManager.getInstance().getCamera().viewportHeight / 2) {
			CameraManager.getInstance().getCamera().position.y = CameraManager.getInstance().getCamera().viewportHeight / 2;
		} else {
			CameraManager.getInstance().getCamera().position.y = CameraManager.getInstance().getCamera().viewportHeight / 2
					+ (this.getY() - CameraManager.getInstance().getCamera().viewportHeight / 2);
		}
		CameraManager.getInstance().getCamera().update();
	}

}
