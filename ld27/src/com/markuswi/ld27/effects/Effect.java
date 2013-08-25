package com.markuswi.ld27.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Effect {

	abstract void render(SpriteBatch batch);

	abstract void tick();

}
