package com.markuswi.ld27.ui;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.markuswi.gdxessentials.gfx.camera.CameraManager;
import com.markuswi.gdxessentials.gfx.font.FontManager;
import com.markuswi.ld27.GameStateManager;
import com.markuswi.ld27.entity.EntityManager;

public class UiManager {

	private static final UiManager INSTANCE = new UiManager();

	public static UiManager getInstance() {
		return UiManager.INSTANCE;
	}

	private double secondTimer = 10;
	private DecimalFormat decimalFormat = new DecimalFormat("#00.00");

	private UiManager() {
	}

	public void render(SpriteBatch batch) {
		if (!EntityManager.getInstance().getPlayer().isDead()) {
			Vector3 timerVector = CameraManager.getInstance().translateToWorldCoordinates(260, 10);
			FontManager.getInstance().getFonts().get("ps2").setColor(Color.WHITE);
			FontManager.getInstance().getFonts().get("ps2").setScale(2f);
			FontManager.getInstance().getFonts().get("ps2").draw(batch, this.decimalFormat.format(this.secondTimer), timerVector.x, timerVector.y);
			//
			Vector3 levelVector = CameraManager.getInstance().translateToWorldCoordinates(30, 430);
			FontManager.getInstance().getFonts().get("ps2").setColor(Color.WHITE);
			FontManager.getInstance().getFonts().get("ps2").setScale(1f);
			FontManager.getInstance().getFonts().get("ps2")
					.draw(batch, "LEVEL " + GameStateManager.getInstance().getCurrentLevel(), levelVector.x, levelVector.y);
		}

	}

	public void resetTimer() {
		this.secondTimer = 10;
	}

	public void tick() {
		this.secondTimer -= Gdx.graphics.getDeltaTime();
		if (this.secondTimer <= 0) {
			EntityManager.getInstance().getPlayer().onDeath();
		}
	}
}
