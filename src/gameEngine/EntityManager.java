package gameEngine;

import entity.MonsterV1;
import entity.Player;
import graphicEngine.ShaderManager;

public class EntityManager {

	ShaderManager shaderManager;
	//Player player;
	public Map map;
	public static Player player;
	public static MonsterV1 monster;

	public EntityManager() {
		ShaderManager.loadAll();
		//shaderManager = new ShaderManager();
		//ShaderManager.loadAll();
		map = new Map();
		player = new Player();
<<<<<<< HEAD
		//	player = new Player();
=======
		monster = new MonsterV1();
>>>>>>> mitchell-bitches-about-gameplay
		//	player.position.y = -0.01f;
		//	player.position.x = 0.0f;
	}

	public void update() {
		player.update();
		monster.update();
		//map.update();
	}

<<<<<<< HEAD
	public void render() {
		map.render();
		player.render();
=======
	public void draw() {
		player.draw();
		monster.draw();
		map.draw();
>>>>>>> mitchell-bitches-about-gameplay

	}
}
