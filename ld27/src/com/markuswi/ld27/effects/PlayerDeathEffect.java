package com.markuswi.ld27.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.markuswi.gdxessentials.audio.AudioManager;
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
			FontManager.getInstance().getFonts().get("ps2").draw(batch, "Game Over :(", vector.x + 120, vector.y);
			FontManager.getInstance().getFonts().get("ps2").draw(batch, "Your journey ends", vector.x + 30, vector.y - 90);
			FontManager.getInstance().getFonts().get("ps2")
					.draw(batch, "on Level " + GameStateManager.getInstance().getCurrentLevel(), vector.x + 150, vector.y - 150);
			FontManager.getInstance().getFonts().get("ps2").draw(batch, "[SPACE] to try again", vector.x - 25, vector.y - 250);
		}
	}

	@Override
	void tick() {
		AudioManager.getInstance().getSongs().get("song").stop();
		this.animationTime += Gdx.graphics.getDeltaTime();
		if (this.animationTime > 0.07f && this.state < 5) {
			if (this.state == 1) {
				AudioManager.getInstance().getSounds().get("death").play();
			}
			this.animationTime = 0;
			this.state++;
		} else if (this.animationTime > 0.5f) {
			this.showGameOver = true;
		}

		if (this.showGameOver && Gdx.input.isKeyPressed(Keys.SPACE)) {

			GameStateManager.getInstance().startGame();
		}

	}
}
