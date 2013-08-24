package com.markuswi.ld27.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.Globals;
import com.markuswi.ld27.map.MapManager;
import com.markuswi.ld27.map.Tile;

public class Entity extends Sprite {

	private float currentHorizontalVelocity = 0f;
	private float currentVerticalVelocity = 0f;
	private float maxFallVelocity = 400f;
	private float fallVelocityStep = 90f;
	private float maxJumpVelocity = 785f;
	private float jumpVelocityStep = 50f;
	private boolean jumping;
	boolean standing;

	public Entity(int startGridX, int startGridY) {
		this.setRegion(TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[0][0]);
		this.setSize(Globals.tilesize, Globals.tilesize);
		this.setX(startGridX * Globals.tilesize);
		this.setY(startGridY * Globals.tilesize);
	}

	protected void fall() {
		if (this.currentVerticalVelocity - this.fallVelocityStep < -this.maxFallVelocity) {
			this.moveVertical(-this.maxFallVelocity);
		} else {
			this.moveVertical(this.currentVerticalVelocity - this.fallVelocityStep);
		}
	}

	protected Array<Tile> getTilesDown() {
		Array<Tile> tiles = new Array<Tile>();
		int gridXLeft = (int) this.getX() / Globals.tilesize;
		int gridXRight = (int) (this.getX() + Globals.tilesize) / Globals.tilesize;
		int gridY = (int) (this.getY() - Globals.tilesize / 2) / Globals.tilesize;
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridXLeft][gridY] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridXLeft][gridY]);
		}
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridXRight][gridY] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridXRight][gridY]);
		}
		return tiles;
	}

	protected void jump() {
		if (!this.jumping && this.standing) {
			this.jumping = true;
		}
	}

	private void jumpMove() {
		if (this.currentVerticalVelocity - this.jumpVelocityStep <= 0) {
			this.jumping = false;
		} else {
			this.moveVertical(this.currentVerticalVelocity - this.jumpVelocityStep);
		}
	}

	protected void moveHorizontal(float velocity) {
		this.setX(this.getX() + (velocity * Gdx.graphics.getDeltaTime()));
		this.currentHorizontalVelocity = velocity;
	}

	protected void moveVertical(float velocity) {
		this.setY(this.getY() + (velocity * Gdx.graphics.getDeltaTime()));
		this.currentVerticalVelocity = velocity;
	}

	public void tick() {
		Array<Tile> tilesBelow = this.getTilesDown();
		if (this.jumping) {
			if (this.standing) {
				this.currentVerticalVelocity = this.maxJumpVelocity;
			}
			this.jumpMove();
		}

		this.standing = false;
		if (!this.jumping) {
			for (Tile tile : tilesBelow) {
				if (!tile.isAccessible()) {
					this.standing = true;
					this.setY((int) this.getY() / Globals.tilesize + Globals.tilesize - 1);
					this.currentVerticalVelocity = 0;
					break;
				}
			}
			if (!this.standing) {
				this.fall();
			}
		}

		System.out.println(this.currentVerticalVelocity);

		this.tickLogic();
	}

	protected void tickLogic() {

	}

}
