package com.markuswi.ld27.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.Globals;

public class Shot extends Rectangle {

	private Entity sourceEntity;
	private float horizontalVelocity;

	public Shot(float startX, float startY, float horizontalVelocity, Entity sourceEntity) {
		this.x = startX;
		this.y = startY;
		this.setHeight(Globals.tilesize / 4);
		this.setWidth(Globals.tilesize);
		this.sourceEntity = sourceEntity;
		this.horizontalVelocity = horizontalVelocity;
	}

	public void render(SpriteBatch batch) {
		batch.draw(TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[1][0], this.getX(), this.getY(), this.getWidth(),
				this.getHeight());
	}

	public void tick() {

		this.x += this.horizontalVelocity * Gdx.graphics.getDeltaTime();

		if (this.sourceEntity instanceof Player) {
			for (Entity entity : EntityManager.getInstance().getEntites()) {
				if (entity.getBoundingRectangle().overlaps(this)) {
					entity.onHit();
					EntityManager.getInstance().getShots().removeValue(this, true);
					break;
				}
			}
		} else {
			if (EntityManager.getInstance().getPlayer().getBoundingRectangle().overlaps(this)) {
				EntityManager.getInstance().getPlayer().onHit();
				EntityManager.getInstance().getShots().removeValue(this, true);
			}
		}

	}

}
