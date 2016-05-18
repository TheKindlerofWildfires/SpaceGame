package gameEngine;

import entity.MonsterV1;
import entity.Player;
import graphicEngine.ShaderManager;

public class EntityManager {

	//Player player;
	public Map map;
	public static Player player;
	public static MonsterV1 monster;

	public static final float APOTHEM = 0.002f;

	public static final float sqrt3 = 1.7320508075688772f;
	public static final float aspectScaler = 16 / 9f;
	public static final float side = APOTHEM * 2 / sqrt3;

	public static float[] vertices = { side, 0, 0, //right 0
			side / 2, -APOTHEM * aspectScaler, 0, // lower right 1
			-side / 2, -APOTHEM * aspectScaler, 0, //lower left 2
			-side, 0, 0, //left 3
			-side / 2, APOTHEM * aspectScaler, 0, //upper left 4
			side / 2, APOTHEM * aspectScaler, 0, //upper right 5
	};
	
	public static byte[] indices = new byte[] { 0, 1, 2, 3, 4, 5, 0 };

	
	public EntityManager() {
		ShaderManager.loadAll();
		//shaderManager = new ShaderManager();
		//ShaderManager.loadAll();
		map = new Map();
		player = new Player(map);
		monster = new MonsterV1();
		//	player.position.y = -0.01f;
		//	player.position.x = 0.0f;
	}

	public void update() {
		player.update();
		monster.update();
		//map.update();
	}

	public void render() {
		monster.render();
		player.render();
		map.render();
	}
}
