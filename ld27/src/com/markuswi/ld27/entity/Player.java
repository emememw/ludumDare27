package com.markuswi.ld27.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.markuswi.gdxessentials.gfx.camera.CameraManager;
import com.markuswi.ld27.Direction;
import com.markuswi.ld27.GameStateManager;
import com.markuswi.ld27.Globals;
import com.markuswi.ld27.effects.EffectManager;
import com.markuswi.ld27.effects.PlayerDeathEffect;
import com.markuswi.ld27.map.MapManager;
import com.markuswi.ld27.map.Tile;

public class Player extends Entity {

	private boolean jumpButtonPressed;
	private float fireButtonPressedTime;
	private float hitTime = 0;
	private float recoveryTime = 0;

	public Player(int startGridX, int startGridY) {
		super(startGridX, startGridY);
		CameraManager.getInstance().getCamera().position.y = CameraManager.getInstance().getCamera().viewportHeight / 2;
	}

	private void levelComplete() {
		GameStateManager.getInstance().nextLevel();

	}

	@Override
	public void onDeath() {
		if (!this.dead) {
			System.out.println("dead " + this);
			this.dead = true;
			EffectManager.getInstance().getEffects().add(new PlayerDeathEffect());
		}
	}

	@Override
	public void onHit() {

	}

	public void playerHit(Entity sourceEntity) {
		if (this.hitTime <= 0 && this.recoveryTime <= 0) {
			this.hitTime = 0.1f;
			this.recoveryTime = 1f;
			this.currentDirection = sourceEntity.getCurrentDirection();
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		if (!this.dead && (this.recoveryTime <= 0 || this.recoveryTime % 0.1f > 0.05f)) {
			super.render(batch);
		}
	}

	private void test() {
		if (this.isStanding()) {
			CameraManager.getInstance().getCamera().position.y = this.getY() + CameraManager.getInstance().getCamera().viewportHeight / 2 - Globals.tilesize;
			CameraManager.getInstance().getCamera().update();
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.updateCamera();
	}

	@Override
	protected void tickLogic() {

		if (this.recoveryTime > 0) {
			this.recoveryTime -= Gdx.graphics.getDeltaTime();
		}

		if (this.hitTime <= 0) {
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
			if (Gdx.input.isKeyPressed(Keys.X)) {
				// EntityManager.getInstance().getShots().add(new
				// Shot(this.getX(),
				// this.getY(), 1000f, this));
				this.fireButtonPressedTime += Gdx.graphics.getDeltaTime();
			} else if (!Gdx.input.isKeyPressed(Keys.X) && this.fireButtonPressedTime > 0) {
				/*
				 * EntityManager.getInstance().getShots() .add(new
				 * Shot(this.getX(), this.getY() + this.getHeight() / 2,
				 * this.currentDirection == Direction.RIGHT ? 1000f : -1000f,
				 * this));
				 */
				this.fireButtonPressedTime = 0;
			}

			if (MapManager.getInstance().getCurrentGameMap().getTiles()[Double.valueOf(this.getX() / Globals.tilesize).intValue()][Double.valueOf(
					this.getY() / Globals.tilesize).intValue()] == Tile.DOOR) {
				this.levelComplete();
				this.dead = true;
			}
		} else {
			this.hitTime -= Gdx.graphics.getDeltaTime();
			if (this.currentDirection == Direction.RIGHT) {
				this.moveHorizontal(this.speed * 2);
			} else {
				this.moveHorizontal(-this.speed * 2);
			}
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

	}
}
