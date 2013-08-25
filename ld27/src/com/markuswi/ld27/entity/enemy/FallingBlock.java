package com.markuswi.ld27.entity.enemy;

import com.markuswi.gdxessentials.gfx.texture.TextureManager;

public class FallingBlock extends BasicEnemy {

	public FallingBlock(int startGridX, int startGridY) {
		super(startGridX, startGridY);
		this.textureRegion1 = TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[2][0];
		this.textureRegion2 = TextureManager.getInstance().getTextureSheets().get("sprites").getTextureRegions()[2][0];
		this.setRegion(this.textureRegion1);
	}

}
