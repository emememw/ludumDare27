package com.markuswi.ld27.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class EntityManager {

	private static final EntityManager INSTANCE = new EntityManager();

	public static EntityManager getInstance() {
		return EntityManager.INSTANCE;
	}

	private Array<Entity> entites = new Array<Entity>();
	private Player player;

	private EntityManager() {
		this.entites.add(new Entity(2, 1));
		this.player = new Player(1, 2);
	}

	public Array<Entity> getEntites() {
		return this.entites;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void render(SpriteBatch batch) {
		this.player.draw(batch);
		for (Entity entity : this.entites) {
			entity.draw(batch);
		}
	}

	public void setEntites(Array<Entity> entites) {
		this.entites = entites;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void tick() {
		this.player.tick();
		for (Entity entity : this.entites) {
			entity.tick();
		}
	}

}
