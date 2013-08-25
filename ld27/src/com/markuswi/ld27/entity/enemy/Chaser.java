package com.markuswi.ld27.entity.enemy;

import com.markuswi.gdxessentials.gfx.texture.TextureManager;

public class Chaser extends BasicEnemy {

	public Chaser(int startGridX, int startGridY) {
		super(startGridX, startGridY);
		this.textureRegion1 = TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[2][1];
		this.textureRegion2 = TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[3][1];
		this.setRegion(this.textureRegion1);
	}

	@Override
	protected void enemyTickLogic() {
		this.moveTowardsPlayer(true);
	}

}
