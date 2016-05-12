package testing;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class Test {
	static long id;

	public static void main(String[] args) {
		initWindow();
		drawScene();
		//drawBackgroundThreeColors();
		/* Delete our opengl context, destroy our window, and shutdown SDL */
		glfwTerminate();
	}

	public static void drawScene() {
		int vao, vbo1, vbo2;

		float[] diamond = { 0.0f, 1.0f, /* Top point */
				1.0f, 0.0f, /* Right point */
				0.0f, -1.0f, /* Bottom point */
				-1.0f, 0.0f }; /* Left point */

		/*float[] colors = { 1.0f, 0.0f, 0.0f, // Red 
				0.0f, 1.0f, 0.0f, // Green 
				0.0f, 0.0f, 1.0f, // Blue 
				1.0f, 1.0f, 1.0f }; // White*/

		String vertexSource, fragmentSource;

		int vertexShader, fragmentShader;

		int shaderProgram;

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vbo1 = glGenBuffers();
		//vbo2 = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vbo1);
		glBufferData(GL_ARRAY_BUFFER, FloatBuffer.wrap(diamond), GL_STATIC_DRAW);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

	/*	glBindBuffer(GL_ARRAY_BUFFER, vbo2);
		glBufferData(GL_ARRAY_BUFFER, FloatBuffer.wrap(colors), GL_STATIC_DRAW);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);*/

		vertexSource = fileToString("src/testing/vertex.shader");
		fragmentSource = fileToString("src/testing/fragment.shader");

		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexSource);
		glCompileShader(vertexShader);

		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentSource);
		glCompileShader(fragmentShader);

		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);

		glBindAttribLocation(shaderProgram, 0, "in_Position");
	//	glBindAttribLocation(shaderProgram, 1, "in_Color");

		glLinkProgram(shaderProgram);

		glUseProgram(shaderProgram);

		glClearColor(0, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);

		glDrawArrays(GL_LINE_LOOP, 0, 4);

		glfwSwapBuffers(id);

		/* Sleep for 2 seconds */
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		glUseProgram(0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDetachShader(shaderProgram, vertexShader);
		glDetachShader(shaderProgram, fragmentShader);
		glDeleteProgram(shaderProgram);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteBuffers(vbo1);
		//glDeleteBuffers(vbo2);
		glDeleteVertexArrays(vao);
	}

	public static void drawBackgroundThreeColors() {
		glClearColor(1, 0, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		glfwSwapBuffers(id);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		glClearColor(0, 1, 0, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		glfwSwapBuffers(id);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		glClearColor(0, 1, 1, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		glfwSwapBuffers(id);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void initWindow() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

		if (glfwInit() != GLFW_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		String title = "MyTitle";

		int width = 1024;
		int height = 768;

		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

		id = glfwCreateWindow(width, height, title, NULL, NULL);
		if (id == NULL)
			throw new RuntimeException("Failed to create window");

		glfwMakeContextCurrent(id);
		GL.createCapabilities();

		glfwSwapInterval(1); // How many draws to swap the buffer
		glfwShowWindow(id); // Shows the window
	}

	public static String fileToString(String filepath) {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filepath));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer);
				result.append("\n");
			}
			//	System.out.println(result);
			reader.close();

		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}
}