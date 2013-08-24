package com.markuswi.ld27.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.Direction;
import com.markuswi.ld27.Globals;
import com.markuswi.ld27.map.MapManager;

public class BasicEnemy extends Entity {

	private Random random = new Random();

	private float pauseTime = 0;
	private boolean actionFlag;

	public BasicEnemy(int startGridX, int startGridY) {
		super(startGridX, startGridY);
		this.speed = 300;

		this.textureRegion1 = TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[2][0];
		this.textureRegion2 = TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[2][0];
		this.setRegion(this.textureRegion1);
	}

	protected void enemyTickLogic() {
		this.fallWhenPlayerIsNear();
	}

	protected void fallWhenPlayerIsNear() {
		this.maxFallVelocity = 650f;
		int absY = (int) (EntityManager.getInstance().getPlayer().getY() / Globals.tilesize - this.getY() / Globals.tilesize);
		int absX = (int) Math.abs(EntityManager.getInstance().getPlayer().getX() - this.getX()) / Globals.tilesize;
		if (absY >= -3 && absY <= 0 && absX <= 0) {
			this.actionFlag = true;
		} else if (!this.actionFlag) {
			this.setY(this.startY);

		}
	}

	protected void moveTowardsPlayer(boolean jump) {
		int abs = (int) Math.abs(EntityManager.getInstance().getPlayer().getY() - this.getY()) / Globals.tilesize;
		if (abs <= 2) {
			if (abs > 0 && this.random.nextInt(1000) < 100) {
				this.jump();
			}
			if (EntityManager.getInstance().getPlayer().getX() > this.getX()) {
				this.moveHorizontal(this.speed);
			} else {
				this.moveHorizontal(-this.speed);
			}
		}
	}

	@Override
	public void onDeath() {
		EntityManager.getInstance().getEntites().removeValue(this, true);
		this.dead = true;
	}

	@Override
	public void onHit() {
		this.addToCurrentHp(-1);
	}

	protected void patrol() {
		if (this.currentDirection == Direction.RIGHT) {
			if (this.blocked
					|| MapManager.getInstance().getCurrentGameMap().getTiles()[(int) this.getX() / Globals.tilesize + 1][(int) (this.getY()) / Globals.tilesize
							- 1] == null
					|| MapManager.getInstance().getCurrentGameMap().getTiles()[(int) this.getX() / Globals.tilesize + 1][(int) (this.getY()) / Globals.tilesize
							- 1].isAccessible()) {
				this.currentDirection = Direction.LEFT;
				this.moveHorizontal(-this.speed);
				this.pauseTime = 1f;
			} else {
				this.moveHorizontal(this.speed);
			}

		} else {
			if (this.blocked
					|| MapManager.getInstance().getCurrentGameMap().getTiles()[(int) this.getX() / Globals.tilesize][(int) this.getY() / Globals.tilesize - 1] == null
					|| MapManager.getInstance().getCurrentGameMap().getTiles()[(int) this.getX() / Globals.tilesize][(int) this.getY() / Globals.tilesize - 1]
							.isAccessible()) {
				this.currentDirection = Direction.RIGHT;
				this.moveHorizontal(this.speed);
				this.pauseTime = 1f;
			} else {
				this.moveHorizontal(-this.speed);
			}
		}
	}

	@Override
	protected void tickLogic() {
		if (this.pauseTime <= 0) {
			this.enemyTickLogic();
		} else {
			this.pauseTime -= Gdx.graphics.getDeltaTime();
		}
	}
}
