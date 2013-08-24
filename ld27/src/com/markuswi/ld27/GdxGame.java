package com.markuswi.ld27;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.markuswi.gdxessentials.gfx.camera.CameraManager;
import com.markuswi.gdxessentials.gfx.font.FontManager;
import com.markuswi.gdxessentials.gfx.texture.TextureManager;
import com.markuswi.ld27.entity.EntityManager;
import com.markuswi.ld27.map.MapManager;

public class GdxGame implements ApplicationListener {

	SpriteBatch batch;

	@Override
	public void create() {

		TextureManager.getInstance().addTextureSheet("tiles", "/tiles.png", 16);
		TextureManager.getInstance().addTextureSheet("sprites", "/sprites.png", 16);
		FontManager.getInstance().addFont("ps2", "ps2");
		this.batch = new SpriteBatch();

		GameStateManager.getInstance().startGame();

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
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		this.batch.setProjectionMatrix(CameraManager.getInstance().getCamera().combined);
		this.batch.begin();
		MapManager.getInstance().getCurrentGameMap().render(this.batch);
		EntityManager.getInstance().render(this.batch);
		UiManager.getInstance().render(this.batch);
		this.batch.end();

		EntityManager.getInstance().tick();
		UiManager.getInstance().tick();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}
}
