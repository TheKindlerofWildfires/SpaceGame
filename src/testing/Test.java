package testing;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL;

public class Test {
	public static void main(String[] args) {
		glfwInit();

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

		long window = glfwCreateWindow(800, 600, "OpenGL", NULL, NULL); // Windowed
		//long window = glfwCreateWindow(800, 600, "OpenGL", glfwGetPrimaryMonitor(), NULL); // Fullscreen
		glfwMakeContextCurrent(window);

		GL.createCapabilities();

		//CREATE VAO
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);

		//CREATE VBO
		int vbo = glGenBuffers();
		//PUT DATA IN VBO
		double vertices[] = { 0.0, 0.5, // Vertex 1 (X, Y)
				0.5, -0.5, // Vertex 2 (X, Y)
				-0.5, -0.5 // Vertex 3 (X, Y)
		};
		DoubleBuffer data = DoubleBuffer.wrap(vertices);

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(vbo, data, GL_STATIC_DRAW);

		//CREATE AND COMPILE VERTEX SHADER
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, loadShader("src/testing/vertex.shader"));
		glCompileShader(vertexShader);

		//CREATE AND COMPILE FRAGMENT SHADER
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, loadShader("src/testing/fragment.shader"));
		glCompileShader(fragmentShader);

		//LINK VERTEX AND FRAGMENT SHADERS INTO PROGRAM
		int shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		glBindFragDataLocation(shaderProgram, 0, "outColor");
		glLinkProgram(shaderProgram);
		glUseProgram(shaderProgram);

		//SPECIFY LAYOUT OF VERTEX DATA
		int posAttrib = glGetAttribLocation(shaderProgram, "position");
		glEnableVertexAttribArray(posAttrib);
		glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false, 0, 0);

		boolean running = true;
		while (running) {
			if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) { //terminate on ESC key
				glfwSetWindowShouldClose(window, GL_TRUE);
			}

			//CLEAR SCREEN TO BLACK
			glClearColor(0, 0, 0, 1);
			glClear(GL_COLOR_BUFFER_BIT);

			//DRAW TRIANGLE
			glDrawArrays(GL_TRIANGLES, 0, 3);

			glfwSwapBuffers(window);
			glfwPollEvents();
		}

		glDeleteProgram(shaderProgram);
		glDeleteShader(fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteBuffers(vbo);
		glDeleteVertexArrays(vbo);
		
		glfwTerminate();
	}

	public static void render() {
		//glDrawArrays(GL_TRIANGLES, 0, 3);
	}

	public static String loadShader(String filepath) {
		StringBuilder result = new StringBuilder();
		System.out.println(filepath);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filepath));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer);
				result.append("\n");
			}

		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(result);
		}
		return result.toString();
	}
}