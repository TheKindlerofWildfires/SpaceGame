package gameEngine;

import entity.MonsterV1;
import entity.Player;
import graphicEngine.ShaderManager;

public class EntityManager {

	ShaderManager shaderManager;
	//Player player;
	Map map;
	Player player;
	public static MonsterV1 monster;

	public EntityManager() {
		//shaderManager = new ShaderManager();
	//	ShaderManager.loadAll();
		map = new Map();
		player = new Player();
		monster = new MonsterV1();
		//	player = new Player();
		//	player.position.y = -0.01f;
		//	player.position.x = 0.0f;
	}

	public void update() {
		player.update();
		monster.update();
		//map.update();
	
	}

	public void draw() {
		player.draw();
		monster.draw();
		//first drawn is on top
		
		map.draw();


	}
}
