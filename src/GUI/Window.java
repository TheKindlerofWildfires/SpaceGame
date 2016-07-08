package GUI;

//import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
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
import gameEngine.EntityManager;
import gameEngine.Tick;
import gameEngine.TickManager;
import maths.Matrix4f;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

public class Window implements Runnable {
	private Thread thread;
	public boolean running = true;
	private GLFWKeyCallback keyCallback;
	public static GLFWCursorPosCallback cursorCallback;

	public Long window;
	EntityManager entityManager;
	TickManager tickManager;

	public static void main(String args[]) {

		Matrix4f toast = new Matrix4f(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1);

		System.out.println(Matrix4f.inverse(toast));

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
		glfwSetCursorPosCallback(window,
				cursorCallback = (GLFWCursorPosCallback) new MouseInput());

		// GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, 0, 20); 

		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();

		glClearColor(0.2f, 0.258f, 0.425f, 1.0f);

		glEnable(GL_DEPTH_TEST);
		//new stuff
		/*
		int objectDisplayList = glGenLists(1);
		glNewList(objectDisplayList, GL_COMPILE);
		{
			
			Model m = null;
			try{
				m = OBJLoader.loadmodel(new File("resources/test.obj"));
			}catch(FileNotFoundException e){
				e.printStackTrace();
				System.exit(1);
			}catch(IOException e){
				e.printStackTrace();
				System.exit(1);
			}
			for (Face face : m.faces) {
                Vector3f n1 = m.normals.get((int) face.normal.x - 1);
                glNormal3f(n1.x, n1.y, n1.z);
                Vector3f v1 = m.vertices.get((int) (face.vertex.x - 1));
                glVertex3f(v1.x, v1.y, v1.z);
                Vector3f n2 = m.normals.get((int) (face.normal.y - 1));
                glNormal3f(n2.x, n2.y, n2.z);
                Vector3f v2 = m.vertices.get((int) (face.vertex.y - 1));
                glVertex3f(v2.x, v2.y, v2.z);
                Vector3f n3 = m.normals.get((int) (face.normal.z - 1));
                glNormal3f(n3.x, n3.y, n3.z);
                Vector3f v3 = m.vertices.get((int) (face.vertex.z - 1));
                glVertex3f(v3.x, v3.y, v3.z);
            }
            glEnd();
        }
        glEndList();
        looks like the list function didnt work right
		*/ 
		
		
		//end new stuff
		entityManager = new EntityManager();
		tickManager = new TickManager();

		// System.out.println(glGetString(GL_VERSION));
	}

	public void update() {
		glfwPollEvents();
		entityManager.update();
		tickManager.update();
	}
	public void render() {
		glfwSwapBuffers(window);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		entityManager.render();
		tickManager.render();

	}

	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				Tick.updateTick();
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " UPS, " + frames + " FPS");
				frames = 0;
				updates = 0;
			}

			if (glfwWindowShouldClose(window) == GL_TRUE) {
				running = false;
			}
		}
		keyCallback.release();
		glfwDestroyWindow(window);
	}
}
