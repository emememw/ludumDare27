package com.markuswi.ld27.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.Globals;

public class Particle extends Effect {

	private float x;
	private float y;
	private float speed;
	private float angle;
	private float lifetime;

	public Particle(float x, float y) {
		this.x = x;
		this.y = y;
		this.speed = 1f;
		this.angle = 0.5f;
		this.lifetime = 1f;
	}

	@Override
	void render(SpriteBatch batch) {
		batch.draw(TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[0][0], this.x, this.y, (float) Globals.tilesize / 4,
				(float) Globals.tilesize / 4);

	}

	@Override
	void tick() {

		if (this.lifetime > 0) {
			this.x += this.speed * Math.cos(this.angle);
			this.y += this.speed * Math.sin(this.angle);
			this.lifetime -= Gdx.graphics.getDeltaTime();
		} else {
			EffectManager.getInstance().getEffects().removeValue(this, true);
		}

	}

}
