package gameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import GUI.KeyboardInput;
import entity.Monster;
import entity.Player;
import graphicEngine.Chunk;
import graphicEngine.ShaderManager;
import maths.Vector3f;

public class EntityManager {

	//Player player;
	public static Map map;
	public static Player player;
	public static Monster monster;
	public WorldType world;

	@Deprecated
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
	
	Vector3f cameraPos = new Vector3f(150,150,100);
	Vector3f cameraTarget = new Vector3f(100,100,0);
	Vector3f up = new Vector3f(0,0,1);
	Vector3f lampPos = new Vector3f(150,150,100);
	
	public EntityManager() {
		ShaderManager.loadAll();
		ShaderManager.setMatrices(cameraPos, cameraTarget, up, lampPos);
		Chunk.initShader();
		//shaderManager = new ShaderManager();
		//ShaderManager.loadAll();
		
		map = new Map();
		world = new WorldType();
		player = new Player(map, "Neo");
		//monster = new MonsterV1(map);
		//	player.position.y = -0.01f;
		//	player.position.x = 0.0f;
<<<<<<< HEAD
	//	zoom(10f);
=======
		zoom(1f);
>>>>>>> playerRework
	}

	public void update() {
		if (KeyboardInput.isKeyDown(GLFW_KEY_RIGHT)) {
			//	System.out.println("right");
			cameraPos = new Vector3f(cameraPos.x+1,cameraPos.y-0,cameraPos.z);
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_LEFT)) {
			//	System.out.println("left");
			cameraPos = new Vector3f(cameraPos.x-1,cameraPos.y-0,cameraPos.z);
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_UP)) {
			//	System.out.println("up");
			cameraPos = new Vector3f(cameraPos.x+0,cameraPos.y+1,cameraPos.z);
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_DOWN)) {
			//	System.out.println("down");
			cameraPos = new Vector3f(cameraPos.x+0,cameraPos.y-1,cameraPos.z);
		}
		ShaderManager.setMatrices(cameraPos, cameraTarget, up, lampPos);
	}

	public void render() {
		//player.render();
		map.render();
	}
	
	@Deprecated
	public void offset(float x,float y){
		map.offset(x, y);
		player.offset(x,y);
	}
	
	@Deprecated
	public void zoom(float zoom){
		map.zoom(zoom);
		player.zoom(zoom);
	}
}
