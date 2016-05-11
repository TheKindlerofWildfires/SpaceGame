package GUI;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import gameEngine.EntityManager;

public class Window {
	public boolean running = true;

	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback cursorCallback;

	public Long window;

	EntityManager entityManager;

	public static void main(String args[]) {
		Window game = new Window();
		game.run();
	}

	public void init() {
		if (glfwInit() != GL_TRUE) {
			System.err.println("GLFW init fail");
			System.exit(-1);
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		window = glfwCreateWindow(1920, 1080, "SpaceGame", NULL, NULL);

		if (window == NULL) {
			System.err.println("Could not create window");
			System.exit(-1);
		}

		glfwSetKeyCallback(window, keyCallback = new KeyboardInput(this));
		glfwSetCursorPosCallback(window, cursorCallback = (GLFWCursorPosCallback) new MouseInput());

		glfwSetWindowPos(window, 0, 20);

		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();

		glClearColor(0.2f, 0.258f, 0.425f, 1.0f);

		glEnable(GL_DEPTH_TEST);

		entityManager = new EntityManager();

	}

	public void update() {
		entityManager.update();
		glfwPollEvents();
		/*
		 * if (KeyboardInput.keys[GLFW_KEY_A]) { System.out.println("A"); }else
		 * if(KeyboardInput.keys[GLFW_KEY_W]) { System.out.println("W"); }else
		 * if(KeyboardInput.keys[GLFW_KEY_S]) { System.out.println("S"); }else
		 * if(KeyboardInput.keys[GLFW_KEY_D]) { System.out.println("D"); }
		 */
	}

	public void render() {
		glfwSwapBuffers(window);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		entityManager.draw();
	}

	public void run() {
		init();
		while (running) {
			update();
			render();
			if (glfwWindowShouldClose(window) == GL_TRUE) {
				running = false;
			}
		}
		keyCallback.release();
	}
}
