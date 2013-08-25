package com.markuswi.ld27;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.CrtMonitor;
import com.bitfire.postprocessing.effects.Vignette;
import com.bitfire.utils.ShaderLoader;
import com.markuswi.gdxessentials.audio.AudioManager;
import com.markuswi.gdxessentials.gfx.camera.CameraManager;
import com.markuswi.gdxessentials.gfx.font.FontManager;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.effects.EffectManager;
import com.markuswi.ld27.entity.EntityManager;
import com.markuswi.ld27.map.MapManager;
import com.markuswi.ld27.ui.UiManager;

public class GdxGame implements ApplicationListener {

	SpriteBatch batch;

	private PostProcessor postProcessor;

	@Override
	public void create() {

		TextureManager.getInstance().addTextureSheet("tiles", "/tiles.png", 16);
		TextureManager.getInstance().addTextureSheet("sprites", "/sprites.png", 16);
		AudioManager.getInstance().addSound("death", "death.wav");
		AudioManager.getInstance().addSound("hurt", "hurt.wav");
		AudioManager.getInstance().addSound("stonebump", "stonebump.wav");
		AudioManager.getInstance().addSound("success", "success.wav");
		AudioManager.getInstance().addSound("start", "start.wav");
		AudioManager.getInstance().addSong("song", "song.ogg");
		FontManager.getInstance().addFont("ps2", "ps2");
		this.batch = new SpriteBatch();
		// GameStateManager.getInstance().startGame();

		// Post processing
		ShaderLoader.BasePath = "data/shaders/";
		this.postProcessor = new PostProcessor(false, false, true);

		Vignette vig = new Vignette(false);
		vig.setIntensity(1f);
		CrtMonitor crt = new CrtMonitor(false, false);
		crt.setColorOffset(1f);
		crt.setTint(1f, 1f, 1f);

		// this.postProcessor.addEffect(bloom);
		this.postProcessor.addEffect(crt);
		// this.postProcessor.addEffect(vig);
	}

	@Override
	public void dispose() {
		this.batch.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		this.postProcessor.capture();

		this.batch.setProjectionMatrix(CameraManager.getInstance().getCamera().combined);
		this.batch.begin();
		if (GameStateManager.getInstance().isShowStartScreen()) {
			for (int x = 0; x <= Gdx.graphics.getWidth() / Globals.tilesize; x++) {
				for (int y = 0; y <= Gdx.graphics.getWidth() / Globals.tilesize; y++) {
					this.batch.draw(TextureManager.getInstance().getTextureSheets().get("tiles").getTextureRegions()[3][0], x * Globals.tilesize, y
							* Globals.tilesize, Globals.tilesize, Globals.tilesize);
				}
			}

			Vector3 vector = CameraManager.getInstance().translateToWorldCoordinates(170, 30);
			FontManager.getInstance().getFonts().get("ps2").setScale(1f);
			FontManager.getInstance().getFonts().get("ps2").draw(this.batch, "Castle of Doom", vector.x, vector.y);
			Vector3 vector2 = CameraManager.getInstance().translateToWorldCoordinates(275, 80);
			FontManager.getInstance().getFonts().get("ps2").setScale(0.5f);
			FontManager.getInstance().getFonts().get("ps2").draw(this.batch, "Ludum Dare 27", vector2.x, vector2.y);
			Vector3 vector3 = CameraManager.getInstance().translateToWorldCoordinates(215, 220);
			FontManager.getInstance().getFonts().get("ps2").setScale(0.5f);
			FontManager.getInstance().getFonts().get("ps2").draw(this.batch, "Press [SPACE] to start", vector3.x, vector3.y);
			Vector3 vector4 = CameraManager.getInstance().translateToWorldCoordinates(305, 450);
			FontManager.getInstance().getFonts().get("ps2").setScale(0.25f);
			FontManager.getInstance().getFonts().get("ps2").draw(this.batch, "August 2013 - emveyh", vector4.x, vector4.y);

			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				GameStateManager.getInstance().setShowStartScreen(false);
				GameStateManager.getInstance().startGame();
			}

		} else if (GameStateManager.getInstance().isShowEndScreen()) {
			CameraManager.getInstance().getCamera().setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			for (int x = 0; x <= Gdx.graphics.getWidth() / Globals.tilesize; x++) {
				for (int y = 0; y <= Gdx.graphics.getWidth() / Globals.tilesize; y++) {
					this.batch.draw(TextureManager.getInstance().getTextureSheets().get("tiles").getTextureRegions()[3][0], x * Globals.tilesize, y
							* Globals.tilesize, Globals.tilesize, Globals.tilesize);
				}
			}
			Vector3 vector = CameraManager.getInstance().translateToWorldCoordinates(150, 50);
			FontManager.getInstance().getFonts().get("ps2").setScale(1f);
			FontManager.getInstance().getFonts().get("ps2").draw(this.batch, "Congratulations!", vector.x, vector.y);
			Vector3 vector3 = CameraManager.getInstance().translateToWorldCoordinates(175, 220);
			FontManager.getInstance().getFonts().get("ps2").setScale(0.5f);
			FontManager.getInstance().getFonts().get("ps2").draw(this.batch, "Press [SPACE] to play again", vector3.x, vector3.y);

			Vector3 vector2 = CameraManager.getInstance().translateToWorldCoordinates(270, 130);
			FontManager.getInstance().getFonts().get("ps2").setScale(1f);
			FontManager.getInstance().getFonts().get("ps2").draw(this.batch, "You won!", vector2.x, vector2.y);

			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				GameStateManager.getInstance().setShowEndScreen(false);
				GameStateManager.getInstance().startGame();
			}

		} else {
			MapManager.getInstance().getCurrentGameMap().render(this.batch);
			EntityManager.getInstance().render(this.batch);
			EffectManager.getInstance().render(this.batch);
			UiManager.getInstance().render(this.batch);
		}

		this.batch.end();

		this.postProcessor.render();

		if (!GameStateManager.getInstance().isShowStartScreen() && !GameStateManager.getInstance().isShowEndScreen()) {
			EntityManager.getInstance().tick();
			UiManager.getInstance().tick();
			EffectManager.getInstance().tick();
			CameraManager.getInstance().getCamera().update();
		}

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}
