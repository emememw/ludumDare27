package com.markuswi.ld27;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.markuswi.gdxessentials.gfx.camera.CameraManager;
import com.markuswi.gdxessentials.gfx.font.FontManager;
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
		Vector3 timerVector = CameraManager.getInstance().translateToWorldCoordinates(100, 100);
		FontManager.getInstance().getFonts().get("ps2").setColor(Color.BLACK);
		FontManager.getInstance().getFonts().get("ps2").setScale(1f);
		FontManager.getInstance().getFonts().get("ps2").draw(batch, this.decimalFormat.format(this.secondTimer), timerVector.x, timerVector.y);
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
