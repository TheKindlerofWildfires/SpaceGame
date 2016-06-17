package gameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import graphicEngine.Camera;
import graphicEngine.Chunk;
import graphicEngine.ShaderManager;
import maths.Vector3f;
import GUI.KeyboardInput;

public class EntityManager {

	// Player player;
	public static Map map;
	// public static Player player;
	// public static Monster monster;
	public WorldType world;

	public static final Vector3f startingCameraPos = new Vector3f(0, 0, 50);
	public static final Vector3f startingCameraTarget = new Vector3f(0, 20, 0);
	public static final Vector3f startingUp = new Vector3f(0, 0, 1);
	public static final Vector3f startingLampPos = new Vector3f(150, 150, 100);

	public static Camera camera;

	public EntityManager() {
		ShaderManager.loadAll();
		camera = new Camera(startingCameraPos, startingCameraTarget,
				startingUp, 100f, 16 / 9f, .1f, 300f);
		ShaderManager.setLight(startingLampPos);

		Chunk.initShader();
		// shaderManager = new ShaderManager();
		// ShaderManager.loadAll();

		map = new Map();
		world = new WorldType();
		// player = new Player(map, "Neo");
		// monster = new MonsterV1(map);
		// player.position.y = -0.01f;
		// player.position.x = 0.0f;
		// zoom(10f);
	}

	public void update() {
		if (KeyboardInput.isKeyDown(GLFW_KEY_RIGHT)) {
			// System.out.println("right");
			camera.moveCamera(new Vector3f(1, 0, 0));
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_LEFT)) {
			// System.out.println("left");
			camera.moveCamera(new Vector3f(-1, 0, 0));
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_UP)) {
			// System.out.println("up");
			camera.moveCamera(new Vector3f(0, 1, 0));
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_DOWN)) {
			// System.out.println("down");
			camera.moveCamera(new Vector3f(0, -1, 0));
		}
	}

	public void render() {
		// player.render();
		map.render();
	}
}
