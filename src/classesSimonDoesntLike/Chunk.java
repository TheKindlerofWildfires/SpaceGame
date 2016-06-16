package classesSimonDoesntLike;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL13.GL_TEXTURE5;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.GL_R32UI;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL31.glTexBuffer;
import graphicEngine.ShaderManager;

import java.nio.IntBuffer;

import maths.Utilities;
import maths.Vector3f;

public class Chunk {

	public static final int CHUNKSIZE = 16;

	int xIndex;
	int yIndex;

	int[][] tileAttribs = new int[16][16];

	private static int bufferID;
	private static int textureID;

	public Chunk(int[][] land, int startIndexX, int startIndexY) {
		if (startIndexX % CHUNKSIZE != 0 || startIndexY % CHUNKSIZE != 0) {
			throw new IllegalArgumentException(
					"indeces must be a multiple of chunk size");
		}
		xIndex = startIndexX;
		yIndex = startIndexY;
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				tileAttribs[x][y] = land[x + startIndexX][y + startIndexY];
			}
		}
	}

	public static void initChunkShader() {
		ShaderManager.chunkShader.start();
		// ShaderManager.chunkShader.setUniform1f("side", EntityManager.side);
		// ShaderManager.chunkShader.setUniform1i("chunkSize", Chunk.CHUNKSIZE);
		// ShaderManager.chunkShader.setUniform1f("apothem",
		// EntityManager.APOTHEM);
		// ShaderManager.chunkShader.setUniform1f("aspect",
		// EntityManager.aspectScaler);
		ShaderManager.chunkShader.setUniform3f("pos", new Vector3f(-1f, 1f, 0));
		int[] land = new int[Chunk.CHUNKSIZE * Chunk.CHUNKSIZE];
		for (int i = 0; i < 16 * 16; i++) {
			land[i] = 0;
		}
		bufferID = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBindBuffer(GL_TEXTURE_BUFFER, bufferID);
		IntBuffer data = Utilities.createIntBuffer(land);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);

		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_BUFFER, textureID);
		glTexBuffer(GL_TEXTURE_BUFFER, GL_R32UI, bufferID);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glActiveTexture(GL_TEXTURE5);
		glBindTexture(GL_TEXTURE_BUFFER, textureID);
		ShaderManager.chunkShader.stop();
	}

	public void setShaderUniforms() {
		ShaderManager.chunkShader.start();
		ShaderManager.chunkShader.setUniform1i("chunkX", xIndex);
		ShaderManager.chunkShader.setUniform1i("chunkY", yIndex);

		int[] land = new int[CHUNKSIZE * CHUNKSIZE];
		int counter = 0;
		for (int y = 0; y < CHUNKSIZE; y++) {
			for (int x = 0; x < CHUNKSIZE; x++) {
				land[counter] = tileAttribs[x][y];
				counter++;
			}
		}
		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBindBuffer(GL_TEXTURE_BUFFER, bufferID);
		IntBuffer data = Utilities.createIntBuffer(land);

		glBufferSubData(GL_ARRAY_BUFFER, 0, data);
		glBindTexture(GL_TEXTURE_BUFFER, textureID);
		glTexBuffer(GL_TEXTURE_BUFFER, GL_R32UI, bufferID);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glActiveTexture(GL_TEXTURE5);
		glBindTexture(GL_TEXTURE_BUFFER, textureID);
		ShaderManager.chunkShader.stop();
	}

	public void render() {
		ShaderManager.chunkShader.start();
		// glBindVertexArray(Map.vaoID);
		glEnableVertexAttribArray(0);
		glDrawArraysInstanced(GL_TRIANGLE_FAN, 0, 6, CHUNKSIZE * CHUNKSIZE);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		ShaderManager.chunkShader.stop();

	}

}
