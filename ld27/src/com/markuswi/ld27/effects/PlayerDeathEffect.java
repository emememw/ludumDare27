package com.markuswi.ld27.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.markuswi.gdxessentials.gfx.camera.CameraManager;
import com.markuswi.gdxessentials.gfx.font.FontManager;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.GameStateManager;
import com.markuswi.ld27.Globals;
import com.markuswi.ld27.entity.EntityManager;

public class PlayerDeathEffect extends Effect {

	private float animationTime;
	private int state = 1;
	private boolean showGameOver = false;

	@Override
	void render(SpriteBatch batch) {

		batch.draw(TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[this.state + 2][0], EntityManager.getInstance()
				.getPlayer().getX(), EntityManager.getInstance().getPlayer().getY(), Globals.tilesize, Globals.tilesize);
		if (this.showGameOver) {
			Vector3 vector = CameraManager.getInstance().translateToWorldCoordinates(100, 100);
			FontManager.getInstance().getFonts().get("ps2").setScale(1f);
			FontManager.getInstance().getFonts().get("ps2").draw(batch, "Game Over", vector.x + 170, vector.y);
			FontManager.getInstance().getFonts().get("ps2").draw(batch, "[ Enter To Restart ]", vector.x, vector.y - 50);
		}
	}

	@Override
	void tick() {

		this.animationTime += Gdx.graphics.getDeltaTime();
		if (this.animationTime > 0.07f && this.state < 5) {
			this.animationTime = 0;
			this.state++;
		} else if (this.animationTime > 0.5f) {
			this.showGameOver = true;
		}

		if (this.showGameOver && Gdx.input.isKeyPressed(Keys.ENTER)) {

			GameStateManager.getInstance().startGame();
		}

	}
}
