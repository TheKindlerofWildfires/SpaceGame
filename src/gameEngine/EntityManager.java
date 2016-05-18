package gameEngine;

import entity.Player;
import graphicEngine.ShaderManager;

public class EntityManager {

	ShaderManager shaderManager;
	//Player player;
	Map map;
	Player player;

	public EntityManager() {
		ShaderManager.loadAll();
		//shaderManager = new ShaderManager();
	//	ShaderManager.loadAll();
		map = new Map();
		player = new Player();
		//	player = new Player();
		//	player.position.y = -0.01f;
		//	player.position.x = 0.0f;
	}

	public void update() {
		//player.update();
		//map.update();
	
	}

	public void render() {
		map.render();
		player.render();

	}
}
