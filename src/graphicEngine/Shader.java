package graphicEngine;

import static graphicEngine.Utilities.loadShader;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import GUI.Vector3f;

public class Shader {
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	public Shader(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);

		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		glLinkProgram(programID);
		glValidateProgram(programID);
	}

	public int getID() {
		return this.programID;
	}

	public int getUniform(String name) {
		int result = glGetUniformLocation(programID, name);
		if (result == -1) {
			System.err.println("Could not find uniform variable " + name);
		}
		return result;
	}

	public void setUniform3f(String name, Vector3f position) {
		glUniform3f(getUniform(name), position.x, position.y, position.z);
	}

	public void setUniform1f(String name, float position) {
		glUniform1f(getUniform(name), position);
	}
	
	public void setUniform1i(String name, int position) {
		glUniform1i(getUniform(name),position);
	}

	public void start() {
		glUseProgram(programID);
	}

	public void stop() {
		glUseProgram(0);
	}
}
