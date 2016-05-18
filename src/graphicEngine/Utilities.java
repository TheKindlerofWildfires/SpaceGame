package graphicEngine;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

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

	public static int loadShader(String filepath, int type) {
		StringBuilder result = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer);
				result.append("\n");
			}
			System.out.println(result);
			reader.close();

		}catch (IOException e){
			System.err.println(e);
		} 
		finally{
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
		}
}
