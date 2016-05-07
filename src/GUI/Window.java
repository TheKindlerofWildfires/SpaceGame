package GUI;

//99% of this is copied from tutorials by this guy
//https://www.youtube.com/channel/UCwFl9Y49sWChrddQTD9QhRA
//remember to take out comments when we are done
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer; //this is sad

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window implements Runnable {
	private Thread thread;
	public boolean running = true;

	private GLFWKeyCallback keyCallback;

	public Long window;

	public static void main(String args[]) {
		Window game = new Window();
		game.start();
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

		window = glfwCreateWindow(800, 600, "SpaceGame", NULL, NULL);

		if (window == NULL) {
			System.err.println("Could not create window");
		}

		glfwSetKeyCallback(window, keyCallback = new Input());

		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, 100, 100);

		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		glClearColor(0.56f, 0.258f, 0.425f, 1.0f);
		
		glEnable(GL_DEPTH_TEST);
		
		System.out.println(glGetString(GL_VERSION));
	}

	public void update() {
		glfwPollEvents();
		
		if (Input.keys[GLFW_KEY_A]) {
			System.out.println("A");
		}else if(Input.keys[GLFW_KEY_W]) {
			System.out.println("W");
		}else if(Input.keys[GLFW_KEY_S]) {
			System.out.println("S");
		}else if(Input.keys[GLFW_KEY_D]) {
			System.out.println("D");
		}
	}

	public void render() {
		glfwSwapBuffers(window);
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	@Override
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
