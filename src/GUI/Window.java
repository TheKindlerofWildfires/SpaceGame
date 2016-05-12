package GUI;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
<<<<<<< HEAD
=======
import static org.lwjgl.system.MemoryUtil.*;
import gameEngine.*;

import java.awt.Graphics;
>>>>>>> parent of 78dce87... This commit works on Simon's computer

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

<<<<<<< HEAD
import gameEngine.EntityManager;

public class Window {
=======
public class Window implements Runnable {
	private Thread thread;
>>>>>>> parent of 78dce87... This commit works on Simon's computer
	public boolean running = true;

	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback cursorCallback;

	public Long window;

<<<<<<< HEAD
	EntityManager entityManager;

	public static void main(String args[]) {
		Window game = new Window();
		game.run();
=======
	
	EntityManager entityManager; 
	public static void main(String args[]) {
		Window game = new Window();
		game.start();
	}

	public void start() {
		running = true;
		thread = new Thread(this, "SpaceGame");
		thread.start();

>>>>>>> parent of 78dce87... This commit works on Simon's computer
	}

	public void init() {
		if (glfwInit() != GL_TRUE) {
			System.err.println("GLFW init fail");
			System.exit(-1);
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
<<<<<<< HEAD
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
=======
>>>>>>> parent of 78dce87... This commit works on Simon's computer

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
