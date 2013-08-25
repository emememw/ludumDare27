package com.markuswi.ld27.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class EffectManager {

	private static final EffectManager INSTANCE = new EffectManager();

	public static EffectManager getInstance() {
		return EffectManager.INSTANCE;
	}

	Array<Effect> effects = new Array<Effect>();

	private EffectManager() {
	}

	public Array<Effect> getEffects() {
		return this.effects;
	}

	public void render(SpriteBatch batch) {
		for (Effect effect : this.effects) {
			effect.render(batch);
		}
	}

	public void resetEffects() {
		this.effects = new Array<Effect>();
	}

	public void setEffects(Array<Effect> effects) {
		this.effects = effects;
	}

	public void tick() {
		for (Effect effect : this.effects) {
			effect.tick();
		}
	}

}
