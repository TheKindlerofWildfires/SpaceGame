package maths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

import static org.lwjgl.opengl.GL31.GL_UNIFORM_BUFFER;
import gameEngine.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import GUI.MouseInput;

public class Utilities {
	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static ByteBuffer createByteBuffer(byte[] data) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	/** pads to the correct shit for ubo */
	public static FloatBuffer createUniformFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length * 4);
		for (int i = 0; i < data.length; i++) {
			buffer.put(data[i]);
			buffer.put(0);
			buffer.put(0);
			buffer.put(0);
		}
		buffer.flip();
		return buffer;
	}

	public static int loadShader(String filepath, int type) {
		StringBuilder result = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filepath));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer);
				result.append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, result.toString());
		glCompileShader(shaderID);
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader");
			System.err.println(-1);
		}
		return shaderID;
	}

	public static int[] convertMouseIndex() {
		double[] mousePos = MouseInput.pos();
		int[] index = new int[2];
		double xC = 1920.0 / Map.HEXESACROSS;
		double yC = 1080.0 / Map.HEXESDOWN;

		mousePos[0] = mousePos[0] / xC;
		mousePos[1] = mousePos[1] / yC;
		index[0] = (int) mousePos[0];
		index[1] = (int) mousePos[1];
		if (index[0] % 2 == 0) {
			index[1] += 1;
		}
		//this function "almost" works, the modulo is sad

		System.out.println(index[0] + " " + index[1] + "g");
		return index;

	}

	/** 
	 * creates uniform buffer object in gpu memory 
	 */
	public static int createUniformBuffer(FloatBuffer data) {
		int ubo = glGenBuffers();
		glBindBuffer(GL_UNIFORM_BUFFER, ubo);
		glBufferData(GL_UNIFORM_BUFFER, data, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_UNIFORM_BUFFER, 0);
		return ubo;
	}

}
