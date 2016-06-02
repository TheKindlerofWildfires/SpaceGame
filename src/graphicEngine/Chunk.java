package graphicEngine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

import gameEngine.Map;

public class Chunk {

	public static final int CHUNKSIZE = 16;

	int xIndex;
	int yIndex;

	int[][] tileAttribs = new int[16][16];

	public Chunk(int[][] land, int startIndexX, int startIndexY) {
		if (startIndexX % CHUNKSIZE != 0 || startIndexY % CHUNKSIZE != 0) {
			throw new IllegalArgumentException("indeces must be a multiple of chunk size");
		}
		xIndex=startIndexX;
		yIndex=startIndexY;
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				tileAttribs[x][y] = land[x + startIndexX][y + startIndexY];
			}
		}
	}

	public void setShaderUniforms(){
		ShaderManager.chunkShader.start();
		ShaderManager.chunkShader.setUniform1i("chunkX", xIndex);
		ShaderManager.chunkShader.setUniform1i("chunkY", yIndex);
		ShaderManager.chunkShader.stop();
	}
	
	public void render(){
		ShaderManager.chunkShader.start();
		glBindVertexArray(Map.vaoID);
		glEnableVertexAttribArray(0);
		glDrawArraysInstanced(GL_TRIANGLE_FAN, 0, 6, CHUNKSIZE * CHUNKSIZE);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}
	
}
