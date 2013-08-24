package com.markuswi.ld27.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.markuswi.gdxessentials.gfx.camera.CameraManager;

public class Player extends Entity {

	private float speed = 350f;

	public Player(int startGridX, int startGridY) {
		super(startGridX, startGridY);
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
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			this.jump();
		}
	}

	private void updateCamera() {
		CameraManager.getInstance().getCamera().position.x = this.getX();
		CameraManager.getInstance().getCamera().position.y = this.getY();
		CameraManager.getInstance().getCamera().update();
	}

}
