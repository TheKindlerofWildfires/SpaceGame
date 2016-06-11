package graphicEngine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import maths.Matrix4f;
import maths.Utilities;
import maths.Vector3f;

public class Chunk {

	//@formatter:off
	private static final float sqrt3 = (float)Math.sqrt(3);
	
	private static final float[] vertices = {
			//TOP
			2/sqrt3,   0, 1,   1,0,0,  0,0,1,//right 0
			1/sqrt3,  -1, 1,   0,0,1,  0,0,1,// lower right 1
			-1/sqrt3, -1, 1,   1,0,0,  0,0,1,//lower left 2
			-2/sqrt3,  0, 1,   0,0,1,  0,0,1,//left 3
			-1/sqrt3,  1, 1,   1,0,0,  0,0,1,//upper left 4
			1/sqrt3,   1, 1,   0,0,1,  0,0,1,//upper right 5
			//BOTTOM
			2/sqrt3,   0, 0,   0,1,1,  0,0,-1,//right 0
			1/sqrt3,  -1, 0,   1,0,1,  0,0,-1,// lower right 1
			-1/sqrt3, -1, 0,   1,1,0,  0,0,-1,//lower left 2
			-2/sqrt3,  0, 0,   1,0,1,  0,0,-1,//left 3
			-1/sqrt3,  1, 0,   0,1,1,  0,0,-1,//upper left 4
			1/sqrt3,   1, 0,   1,0,1,  0,0,-1,//upper right 5
			//FRONT
			-1/sqrt3,  1, 1,   1,0,1,  0,1,0,//upper left 4
			1/sqrt3,   1, 1,   1,0,1,  0,1,0,//upper right 5
			1/sqrt3,   1, 0,   1,0,1,  0,1,0,//upper right 5
			-1/sqrt3,  1, 0,   1,0,1,  0,1,0,//upper left 4
			//FRONT RIGHT
			1/sqrt3,   1, 1,   1,1,0,  1/2,sqrt3/2,0,//upper right 5
			2/sqrt3,   0, 1,   1,1,0,  1/2,sqrt3/2,0,//right 0
			2/sqrt3,   0, 0,   1,1,0,  1/2,sqrt3/2,0,//right 0
			1/sqrt3,   1, 0,   1,1,0,  1/2,sqrt3/2,0,//upper right 5
			//BACK RIGHT
			2/sqrt3,   0, 1,   1,0,0,  1/2,-sqrt3/2,0,//right 0
			1/sqrt3,  -1, 1,   1,0,0,  1/2,-sqrt3/2,0,// lower right 1
			1/sqrt3,  -1, 0,   1,0,0,  1/2,-sqrt3/2,0,// lower right 1
			2/sqrt3,   0, 0,   1,0,0,  1/2,-sqrt3/2,0,//right 0
			//BACK
			1/sqrt3,  -1, 1,   0,1,0,  0,-1,0,// lower right 1
			-1/sqrt3, -1, 1,   0,1,0,  0,-1,0,//lower left 2
			-1/sqrt3, -1, 0,   0,1,0,  0,-1,0,//lower left 2
			1/sqrt3,  -1, 0,   0,1,0,  0,-1,0,// lower right 1
			//BACK LEFT
			-1/sqrt3, -1, 1,   0,0,1,  -1/2,-sqrt3/2,0,//lower left 2
			-2/sqrt3,  0, 1,   0,0,1,  -1/2,-sqrt3/2,0,//left 3
			-2/sqrt3,  0, 0,   0,0,1,  -1/2,-sqrt3/2,0,//left 3
			-1/sqrt3, -1, 0,   0,0,1,  -1/2,-sqrt3/2,0,//lower left 2
			//FRONT LEFT
			-2/sqrt3,  0, 1,   0,1,1,  -1/2,sqrt3/2,0,//left 3
			-1/sqrt3, +1, 1,   0,1,1,  -1/2,sqrt3/2,0,//lower left 2
			-1/sqrt3, +1, 0,   0,1,1,  -1/2,sqrt3/2,0,//lower left 2
			-2/sqrt3,  0, 0,   0,1,1,  -1/2,sqrt3/2,0//left 3
	};
	
	private static final byte[] indices = new byte[] {
			0,1,2,0,2,3,0,3,4,0,4,5, 
			6,7,8,6,8,9,6,9,10,6,10,11, 
			12,13,14,12,14,15,
			16,17,18,16,18,19,
			20,21,22,20,22,23,
			24,25,26,24,26,27,
			28,29,30,28,30,31,
			32,33,34,32,34,35 
	};
	
	private final float[] top = { //NORMAL 0,0,1
			2/sqrt3,   0, 1,  1,1,1,//right 0
			1/sqrt3,  -1, 1,  1,1,1,// lower right 1
			-1/sqrt3, -1, 1,  1,1,1,//lower left 2
			
			2/sqrt3,   0, 1,  1,1,1,//right 0
			-1/sqrt3, -1, 1,  1,1,1,//lower left 2
			-2/sqrt3,  0, 1,  1,1,1,//left 3

			2/sqrt3,   0, 1,  1,1,1,//right 0
			-2/sqrt3,  0, 1,  1,1,1,//left 3
			-1/sqrt3,  1, 1,  1,1,1,//upper left 4

			2/sqrt3,   0, 1,  1,1,1,//right 0
			-1/sqrt3,  1, 1,  1,1,1,//upper left 4
			1/sqrt3,   1, 1,  1,1,1,//upper right 5

			
			
			2/sqrt3,   0, 3,  1,1,1,//right 0
			1/sqrt3,  -1, 3,  1,1,1,// lower right 1
			-1/sqrt3, -1, 3,  1,1,1,//lower left 2
			
			2/sqrt3,   0, 3,  1,1,1,//right 0
			-1/sqrt3, -1, 3,  1,1,1,//lower left 2
			-2/sqrt3,  0, 3,  1,1,1,//left 3

			2/sqrt3,   0, 3,  1,1,1,//right 0
			-2/sqrt3,  0, 3,  1,1,1,//left 3
			-1/sqrt3,  1, 3,  1,1,1,//upper left 4

			2/sqrt3,   0, 3,  1,1,1,//right 0
			-1/sqrt3,  1, 3,  1,1,1,//upper left 4
			1/sqrt3,   1, 3,  1,1,1//upper right 5			
	};
	
	//@formatter:on

	private final VertexArrayObject hexagon = new VertexArrayObject(top, 2);

	public static final int CHUNKSIZE = 16;
	public static final int CHUNKHEIGHT = 16;

	private int x;
	private int y;

	private float[][][] properties = new float[CHUNKSIZE][CHUNKSIZE][CHUNKHEIGHT];

	private Matrix4f model;
	private Matrix4f normal;

	public Chunk(float[][][] properties, int x, int y) {
		model = Matrix4f.translate(6 * 1.2f * x / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * y - 10, 0).multiply(Matrix4f.scale(1, 1, .5f));
		
		normal = Matrix4f.inverse(model).transpose();
		this.properties = properties;
		this.x = x;
		this.y = y;
	}

	public static void initShader() {
		ShaderManager.chunkShader.start();

		ShaderManager.chunkShader.setUniform3f("lightColor", new Vector3f(1f, 1, 1));
		///	ShaderManager.chunkShader.setUniform3f("objectColor", new Vector3f(1f, .5f, .31f));
		ShaderManager.chunkShader.setUniform1f("ambientStrength", 1f);
		ShaderManager.chunkShader.setUniform1f("specularStrength", .4f);
		ShaderManager.chunkShader.setUniform1f("shininess", 256);

		ShaderManager.chunkShader.setUniform1f("apothem", (float) Math.sqrt(3) / 2);
		ShaderManager.chunkShader.setUniform1f("side", 1f);
		ShaderManager.chunkShader.setUniform1i("chunkSize", CHUNKSIZE);

		ShaderManager.chunkShader.setUniformBlockf("Properties", Utilities.createUniformBuffer(
				Utilities.createUniformFloatBuffer(new float[CHUNKSIZE * CHUNKSIZE * CHUNKHEIGHT])), 0);

		ShaderManager.chunkShader.stop();
	}

	public void render() {
		ShaderManager.chunkShader.start();

		//	mat3(transpose(inverse(model)))
		ShaderManager.chunkShader.setUniformMatrix4f("model", model);
		ShaderManager.chunkShader.setUniformMatrix4f("norm", normal);
		
		ShaderManager.chunkShader.setUniform3f("normal", new Vector3f(0,0,1));

		glBindVertexArray(hexagon.getVaoID());
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_TRIANGLES, 0, indices.length);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		ShaderManager.chunkShader.stop();

	}
}
