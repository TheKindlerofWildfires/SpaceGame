package gameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import GUI.KeyboardInput;
import entity.Monster;
import entity.Player;
import graphicEngine.ShaderManager;

public class EntityManager {

	//Player player;
	public static Map map;
	public static Player player;
	public static Monster monster;
	public WorldType world;

	public static final float APOTHEM = 0.005f; //0.005

	public static final float sqrt3 = 1.7320508075688772f;
	public static final float aspectScaler = 16 / 9f;
	public static final float side = APOTHEM * 2 / sqrt3;

	public static float[] vertices = { 2/sqrt3, 0, 0, //right 0
			1/sqrt3, -1 * aspectScaler, 0, // lower right 1
			-1/sqrt3, -1 * aspectScaler, 0, //lower left 2
			-2/sqrt3, 0, 0, //left 3
			-1/sqrt3, 1 * aspectScaler, 0, //upper left 4
			1/sqrt3, 1 * aspectScaler, 0, //upper right 5
	};
	
	public static byte[] indices = new byte[] { 0, 1, 2, 3, 4, 5, 0 };

	
	public EntityManager() {
		ShaderManager.loadAll();
		//shaderManager = new ShaderManager();
		//ShaderManager.loadAll();
		
		map = new Map();
		world = new WorldType();
		player = new Player(map, "Neo");
		//monster = new MonsterV1(map);
		//	player.position.y = -0.01f;
		//	player.position.x = 0.0f;
		zoom(1f);
	}

	public void update() {
		if (KeyboardInput.isKeyDown(GLFW_KEY_RIGHT)) {
			//	System.out.println("right");
			offset(-.01f, 0);
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_LEFT)) {
			//	System.out.println("left");
			offset(+.01f, 0);
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_UP)) {
			//	System.out.println("up");
			offset(0, -0.01f);
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_DOWN)) {
			//	System.out.println("down");
			offset(0, +0.01f);
		}
		player.update();
	}

	public void render() {
		player.render();
		map.render();
	}
	
	public void offset(float x,float y){
		map.offset(x, y);
		player.offset(x,y);
	}
	
	public void zoom(float zoom){
		map.zoom(zoom);
		player.zoom(zoom);
	}
}
