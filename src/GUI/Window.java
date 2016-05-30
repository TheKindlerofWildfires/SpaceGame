package GUI;

//import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import gameEngine.EntityManager;
import gameEngine.TickManager;
import graphicEngine.TextureManager;
import gameEngine.Tick;

public class Window implements Runnable {
	private Thread thread;
	public boolean running = true;
	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback cursorCallback;

	public Long window;

	EntityManager entityManager;
	TickManager tickManager;

	public static void main(String args[]) {

		Window game = new Window();

		game.run();

	}

	public void start() {

		running = true;
		thread = new Thread(this, "SpaceGame");
		thread.start();

	}

	public void init() {
		if (glfwInit() != GL_TRUE) {
			System.err.println("GLFW init fail");
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		window = glfwCreateWindow(1920, 1080, "SpaceGame", NULL, NULL);

		if (window == NULL) {
			System.err.println("Could not create window");
		}

		glfwSetKeyCallback(window, keyCallback = new KeyboardInput());
		glfwSetCursorPosCallback(window, cursorCallback = (GLFWCursorPosCallback) new MouseInput());

		//GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, 0, 20);

		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();

		glClearColor(0.2f, 0.258f, 0.425f, 1.0f);

		glEnable(GL_DEPTH_TEST);

		entityManager = new EntityManager();
		tickManager = new TickManager();

		//System.out.println(glGetString(GL_VERSION));
	}

	public void update() {
		glfwPollEvents();

		entityManager.update();
		tickManager.update();

		/*
		if (KeyboardInput.keys[GLFW_KEY_A]) {
			System.out.println("A");
		}else if(KeyboardInput.keys[GLFW_KEY_W]) {
			System.out.println("W");
		}else if(KeyboardInput.keys[GLFW_KEY_S]) {
			System.out.println("S");
		}else if(KeyboardInput.keys[GLFW_KEY_D]) {
			System.out.println("D");
		}
		*/
	}

	public void render() {
		glfwSwapBuffers(window);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		entityManager.render();
		tickManager.render();
	}

	@Override
	public void run() {
		TextureManager.loadImages();
		init();
		TextureManager.glTheFools();
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		//int updates = 0;
		//int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				Tick.updateTick();
				update();
				//updates++;
				delta--;
			}
			render();
			//frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println(updates + " UPS, " + frames + " FPS");
				//frames = 0;
				//updates = 0;
			}

			if (glfwWindowShouldClose(window) == GL_TRUE) {
				running = false;
			}
		}
		keyCallback.release();
		glfwDestroyWindow(window);
	}
}
