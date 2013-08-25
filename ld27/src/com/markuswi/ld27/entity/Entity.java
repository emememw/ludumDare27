package com.markuswi.ld27.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.Direction;
import com.markuswi.ld27.Globals;
import com.markuswi.ld27.map.MapManager;
import com.markuswi.ld27.map.Tile;

public class Entity extends Sprite {

	protected float currentHorizontalVelocity = 0f;
	protected float currentVerticalVelocity = 0f;
	protected float maxFallVelocity = 2000f;
	private float fallVelocityStep = 110f;
	private float maxJumpVelocity = 1550f;
	private float jumpVelocityStep = 80f;
	private boolean jumping;
	boolean standing;
	private float standingTime = 0;
	protected float speed = 500f;
	protected Direction currentDirection = Direction.RIGHT;
	protected boolean blocked;
	protected boolean dead = false;
	protected boolean canFall = true;
	protected float startX;
	protected float startY;

	protected TextureRegion textureRegion1;
	protected TextureRegion textureRegion2;
	private boolean animationFlag;

	private float animationTime = 0;

	private int hp = 1;
	private int currentHp = 1;

	public Entity(int startGridX, int startGridY) {

		this.textureRegion1 = TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[0][0];
		this.textureRegion2 = TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[1][0];

		this.setRegion(this.textureRegion1);

		this.setSize(Globals.tilesize / 2, Globals.tilesize);
		this.setX(startGridX * Globals.tilesize);
		this.startX = this.getX();
		this.setY(startGridY * Globals.tilesize);
		this.startY = this.getY();
	}

	public void addToCurrentHp(int amount) {
		this.currentHp += amount;
		if (this.currentHp <= 0) {
			this.onDeath();
		}
	}

	private void changeTextureRegion() {
		if (this.animationFlag) {
			this.setRegion(this.textureRegion1);
		} else {
			this.setRegion(this.textureRegion2);
		}
		if (this.currentDirection == Direction.LEFT) {
			this.flip(true, false);
		}

		this.animationFlag = !this.animationFlag;
	}

	private void checkEntityCollision() {
		if (this instanceof Player) {
			for (Entity entity : EntityManager.getInstance().getEntites()) {
				if (this.getBoundingRectangle().overlaps(entity.getBoundingRectangle())) {
					System.out.println("colliding with entity");
				}
			}
		} else {
			if (this.getBoundingRectangle().overlaps(EntityManager.getInstance().getPlayer().getBoundingRectangle())) {
				EntityManager.getInstance().getPlayer().playerHit(this);
			}
		}

	}

	protected void fall() {
		if (this.currentVerticalVelocity - this.fallVelocityStep < -this.maxFallVelocity) {
			this.moveVertical(-this.maxFallVelocity);
		} else {
			this.moveVertical(this.currentVerticalVelocity - this.fallVelocityStep);
		}
	}

	public float getAnimationTime() {
		return this.animationTime;
	}

	public Direction getCurrentDirection() {
		return this.currentDirection;
	}

	public float getCurrentHorizontalVelocity() {
		return this.currentHorizontalVelocity;
	}

	public int getCurrentHp() {
		return this.currentHp;
	}

	public float getCurrentVerticalVelocity() {
		return this.currentVerticalVelocity;
	}

	public float getFallVelocityStep() {
		return this.fallVelocityStep;
	}

	public int getHp() {
		return this.hp;
	}

	public float getJumpVelocityStep() {
		return this.jumpVelocityStep;
	}

	public float getMaxFallVelocity() {
		return this.maxFallVelocity;
	}

	public float getMaxJumpVelocity() {
		return this.maxJumpVelocity;
	}

	public float getSpeed() {
		return this.speed;
	}

	public float getStandingTime() {
		return this.standingTime;
	}

	public float getStartX() {
		return this.startX;
	}

	public float getStartY() {
		return this.startY;
	}

	public TextureRegion getTextureRegion1() {
		return this.textureRegion1;
	}

	public TextureRegion getTextureRegion2() {
		return this.textureRegion2;
	}

	protected Array<Tile> getTilesDown() {
		Array<Tile> tiles = new Array<Tile>();
		int gridXLeft = (int) this.getX() / Globals.tilesize;
		int gridXRight = (int) (this.getX() + (int) this.getWidth() - 1) / Globals.tilesize;
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
		int gridX = (int) (this.getX() + (int) this.getWidth()) / Globals.tilesize;
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
		int gridXRight = (int) (this.getX() + (int) this.getWidth() - 1) / Globals.tilesize;
		int gridY = (int) (this.getY() + Globals.tilesize) / Globals.tilesize;
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridXLeft][gridY] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridXLeft][gridY]);
		}
		if (MapManager.getInstance().getCurrentGameMap().getTiles()[gridXRight][gridY] != null) {
			tiles.add(MapManager.getInstance().getCurrentGameMap().getTiles()[gridXRight][gridY]);
		}
		return tiles;
	}

	public boolean isAnimationFlag() {
		return this.animationFlag;
	}

	public boolean isBlocked() {
		return this.blocked;
	}

	public boolean isCanFall() {
		return this.canFall;
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

	public boolean isDead() {
		return this.dead;
	}

	public boolean isJumping() {
		return this.jumping;
	}

	public boolean isStanding() {
		return this.standing;
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
		if (velocity > 0) {
			if (this.currentDirection == Direction.LEFT) {
				this.flip(true, false);
			}
			this.currentDirection = Direction.RIGHT;
		} else {
			if (this.currentDirection == Direction.RIGHT) {
				this.flip(true, false);
			}
			this.currentDirection = Direction.LEFT;
		}
	}

	protected void moveVertical(float velocity) {
		this.setY(this.getY() + (velocity * Gdx.graphics.getDeltaTime()));
		this.currentVerticalVelocity = velocity;
	}

	public void onDeath() {
		System.out.println("dead");
		this.dead = true;
	}

	public void onHit() {
		System.out.println("hit");
	}

	public void render(SpriteBatch batch) {
		batch.draw(this, this.getX() - Globals.tilesize / 4, this.getY(), Globals.tilesize, Globals.tilesize);
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void tick() {

		Array<Tile> tilesBelow = this.getTilesDown();
		if (!this.dead) {
			this.currentHorizontalVelocity = 0;

			for (Tile tile : tilesBelow) {
				if (tile.isDeadly()) {
					outer: for (int x = 0; x < MapManager.getInstance().getCurrentGameMap().getTiles().length; x++) {
						for (int y = 0; y < MapManager.getInstance().getCurrentGameMap().getTiles()[x].length; y++) {
							if (MapManager.getInstance().getCurrentGameMap().getTiles()[x][y] != null
									&& MapManager.getInstance().getCurrentGameMap().getTiles()[x][y].isDeadly())
								if (new Rectangle(x * Globals.tilesize, y * Globals.tilesize, Globals.tilesize, Globals.tilesize - 10).overlaps(this
										.getBoundingRectangle())) {
									this.onDeath();
									break outer;
								}
						}
					}

				}
			}
		}

		if (this.jumping && !this.dead) {
			if (this.standing) {
				this.currentVerticalVelocity = this.maxJumpVelocity;
			}
			this.jumpMove();
		}

		this.standing = false;
		if (!this.jumping && this.canFall || this.dead) {
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
		if (!this.dead) {
			this.tickLogic();
			if (!this.dead) {

				boolean currentlyBlocked = false;
				if (this.isCollidingRight()) {
					this.setX(Double.valueOf(this.getX() / Globals.tilesize).intValue() * Globals.tilesize + this.getWidth());
					currentlyBlocked = true;
				} else if (this.isCollidingLeft()) {
					this.setX(Double.valueOf(this.getX() / Globals.tilesize).intValue() * Globals.tilesize + Globals.tilesize);
					currentlyBlocked = true;
				}

				if (this.isCollidingUp()) {
					this.jumping = false;
					this.currentVerticalVelocity = 0;
					this.setY(Double.valueOf(this.getY() / Globals.tilesize).intValue() * Globals.tilesize);
					currentlyBlocked = true;
				}

				if (this.standing) {
					this.standingTime += Gdx.graphics.getDeltaTime();
				} else {
					this.standingTime = 0f;
				}

				this.checkEntityCollision();
				if (currentlyBlocked) {
					this.blocked = true;
				} else {
					this.blocked = false;
				}
			}
		}
		if (this.currentVerticalVelocity != 0) {
			this.animationFlag = false;
			this.changeTextureRegion();
		} else if (this.currentHorizontalVelocity != 0) {
			this.animationTime += Gdx.graphics.getDeltaTime();
		} else if (this.standing) {
			this.animationFlag = true;
			this.changeTextureRegion();
		}
		if (this.animationTime >= 0.1f) {
			this.changeTextureRegion();
			this.animationTime = 0;
		}

	}

	protected void tickLogic() {

	}

}
