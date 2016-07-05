package gameEngine;

import static org.lwjgl.glfw.GLFW.*;
import graphicEngine.Camera;
import graphicEngine.Chunk;
import graphicEngine.ShaderManager;
import maths.Vector3f;
import GUI.KeyboardInput;
import GUI.MouseInput;
import GUI.Window;

public class EntityManager {

	// Player player;
	public static Map map;
	// public static Player player;
	// public static Monster monster;
	public WorldType world;

	public static final Vector3f startingCameraPos = new Vector3f(0, 0, 30);
	public static final Vector3f startingCameraTarget = new Vector3f(0, 1, 29);
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
		world = new WorldType();
		map = new Map();
		
		// player = new Player(map, "Neo");
		// monster = new MonsterV1(map);
		// player.position.y = -0.01f;
		// player.position.x = 0.0f;
		// zoom(10f);
	}

	public void update() {
		//camera.rotateCamera(Window.);
		double[] mousePos = MouseInput.pos();
		camera.rotateCamera(mousePos);
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_D)) {
			camera.moveCamera("LEFT");
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_A)) {
			camera.moveCamera("RIGHT");
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_W)) {
			camera.moveCamera("FORWARD");
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_S)) {
			camera.moveCamera("BACK");
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_SPACE)) {
			camera.moveCamera("UP");
		}
		if (KeyboardInput.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
			camera.moveCamera("DOWN");
		}
	}

	public void render() {
		// player.render();
		map.render();
	}
}
