package gameEngine;

import entity.MonsterV1;
import entity.Player;
import graphicEngine.ShaderManager;

public class EntityManager {

	ShaderManager shaderManager;
	//Player player;
	Map map;
	public static Player player;
	public static MonsterV1 monster;

	public EntityManager() {
		ShaderManager.loadAll();
		//shaderManager = new ShaderManager();
	//	ShaderManager.loadAll();
		map = new Map();
<<<<<<< HEAD
		player = new Player();
		monster = new MonsterV1();
=======
		//player = new Player();
>>>>>>> refs/remotes/origin/tesselation-optimization
		//	player = new Player();
		//	player.position.y = -0.01f;
		//	player.position.x = 0.0f;
	}

	public void update() {
<<<<<<< HEAD
		player.update();
		monster.update();
=======
		//player.update();
>>>>>>> refs/remotes/origin/tesselation-optimization
		//map.update();
	
	}

	public void draw() {
<<<<<<< HEAD
		player.draw();
		monster.draw();
		//first drawn is on top
		
		map.draw();

=======
		map.draw();
		//player.draw();
>>>>>>> refs/remotes/origin/tesselation-optimization

	}
}
