package com.markuswi.ld27;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.CrtMonitor;
import com.bitfire.postprocessing.effects.Vignette;
import com.bitfire.utils.ShaderLoader;
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
		FontManager.getInstance().addFont("ps2", "ps2");
		this.batch = new SpriteBatch();
		GameStateManager.getInstance().startGame();

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
		MapManager.getInstance().getCurrentGameMap().render(this.batch);
		EntityManager.getInstance().render(this.batch);
		EffectManager.getInstance().render(this.batch);
		UiManager.getInstance().render(this.batch);

		this.batch.end();

		this.postProcessor.render();

		EntityManager.getInstance().tick();
		UiManager.getInstance().tick();
		EffectManager.getInstance().tick();
		CameraManager.getInstance().getCamera().update();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}
