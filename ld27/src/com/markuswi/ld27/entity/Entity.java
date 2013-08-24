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
	private float standingTime = 0;

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
		int gridXRight = (int) (this.getX() + Globals.tilesize - 2) / Globals.tilesize;
		int gridY = (int) (this.getY() - Globals.tilesize / 2) / Globals.tilesize;
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridXLeft][gridY] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridXLeft][gridY]);
		}
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridXRight][gridY] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridXRight][gridY]);
		}
		return tiles;
	}

	protected Array<Tile> getTilesLeft() {
		Array<Tile> tiles = new Array<Tile>();
		int gridYUp = (int) (this.getY()) / Globals.tilesize;
		int gridYDown = (int) (this.getY() + Globals.tilesize / 2) / Globals.tilesize;
		int gridX = (int) (this.getX()) / Globals.tilesize;
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridX][gridYUp] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridX][gridYUp]);
		}
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridX][gridYDown] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridX][gridYDown]);
		}
		return tiles;
	}

	protected Array<Tile> getTilesRight() {
		Array<Tile> tiles = new Array<Tile>();
		int gridYUp = (int) (this.getY()) / Globals.tilesize;
		int gridYDown = (int) (this.getY() + Globals.tilesize / 2) / Globals.tilesize;
		int gridX = (int) (this.getX() + Globals.tilesize) / Globals.tilesize;
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridX][gridYUp] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridX][gridYUp]);
		}
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridX][gridYDown] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridX][gridYDown]);
		}
		return tiles;
	}

	protected Array<Tile> getTilesUp() {
		Array<Tile> tiles = new Array<Tile>();
		int gridXLeft = (int) this.getX() / Globals.tilesize;
		int gridXRight = (int) (this.getX() + Globals.tilesize - 1) / Globals.tilesize;
		int gridY = (int) (this.getY() + Globals.tilesize) / Globals.tilesize;
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridXLeft][gridY] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridXLeft][gridY]);
		}
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridXRight][gridY] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridXRight][gridY]);
		}
		return tiles;
	}

	private boolean isCollidingLeft() {
		boolean collision = false;
		for (Tile tile : this.getTilesLeft()) {
			if (!tile.isAccessible()) {
				collision = true;
				break;
			}
		}
		return collision;
	}

	private boolean isCollidingRight() {
		boolean collision = false;
		for (Tile tile : this.getTilesRight()) {
			if (!tile.isAccessible()) {
				collision = true;
				break;
			}
		}
		return collision;
	}

	private boolean isCollidingUp() {
		boolean collision = false;
		for (Tile tile : this.getTilesUp()) {
			if (!tile.isAccessible()) {
				collision = true;
				break;
			}
		}
		return collision;
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
		this.currentHorizontalVelocity = 0;
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
					this.setY(Double.valueOf(this.getY() / Globals.tilesize).intValue() * Globals.tilesize);
					this.currentVerticalVelocity = 0;
					break;
				}
			}
			if (!this.standing) {
				this.fall();
			}
		}

		this.tickLogic();

		if (this.isCollidingRight()) {
			this.setX(Double.valueOf(this.getX() / Globals.tilesize).intValue() * Globals.tilesize);
		} else if (this.isCollidingLeft()) {
			this.setX(Double.valueOf(this.getX() / Globals.tilesize).intValue() * Globals.tilesize + Globals.tilesize);
		}

		if (this.isCollidingUp()) {
			this.jumping = false;
			this.currentHorizontalVelocity = 0;
			this.setY(Double.valueOf(this.getY() / Globals.tilesize).intValue() * Globals.tilesize);
		}

		if (this.standing) {
			this.standingTime += Gdx.graphics.getDeltaTime();
		} else {
			this.standingTime = 0f;
		}

	}

	protected void tickLogic() {

	}

}
