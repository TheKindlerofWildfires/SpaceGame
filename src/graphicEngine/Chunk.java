package graphicEngine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;

import maths.Matrix4f;
import maths.Utilities;
import maths.Vector3f;

public class Chunk {

	public static final int CHUNKSIZE = 16;
	public static final int CHUNKHEIGHT = 16;

	public static final float sqrt3 = (float) Math.sqrt(3);
	public static final float APOTHEM = sqrt3 / 2;
	public static final float SIDE = 1f;

	//@formatter:off	
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
		
	private static final float[] top = { //NORMAL 0,0,1
			2/sqrt3,   0, 1,  //right 0
			1/sqrt3,  -1, 1,  // lower right 1
			-1/sqrt3, -1, 1,  //lower left 2
			
			2/sqrt3,   0, 1, //right 0
			-1/sqrt3, -1, 1, //lower left 2
			-2/sqrt3,  0, 1, //left 3

			2/sqrt3,   0, 1, //right 0
			-2/sqrt3,  0, 1, //left 3
			-1/sqrt3,  1, 1, //upper left 4

			2/sqrt3,   0, 1, //right 0
			-1/sqrt3,  1, 1, //upper left 4
			1/sqrt3,   1, 1, //upper right 5
	};
	//@formatter:on

	private VertexArrayObject chunk;

	private Matrix4f model;
	private Matrix4f normal;

	private int numberOfTopPts;

	public Chunk(float[][][] properties, int chunkX, int chunkY) {
		model = Matrix4f
				.translate(6 * 1.2f * chunkX / 4 * CHUNKSIZE - 10,
						2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * chunkY - 10, 0)
				.multiply(Matrix4f.scale(1, 1, .5f));

		normal = Matrix4f.inverse(model).transpose();

		//CREATE TOP VAO

		float[] color = new float[3];
		color[0] = .5f;
		color[1] = .5f;
		color[2] = .31f;

		ArrayList<Float> topPts = new ArrayList<Float>();

		for (int x = 0; x < properties.length; x++) {
			for (int y = 0; y < properties[0].length; y++) {
				for (int z = 0; z < properties[0][0].length; z++) {
					float xOfset = 1.2f * (x * 3 * SIDE / 2);
					float yOfset;
					if (x % 2 == 0) {
						yOfset = 1.2f * (y * 2 * APOTHEM);
					} else {
						yOfset = 1.2f * ((y * 2 * APOTHEM - APOTHEM));
					}
					float zOfset = z * 1.0f;
					System.out.println(xOfset + "," + yOfset + "," + zOfset);

					topPts.add(top[0] + xOfset);
					topPts.add(top[1] + yOfset);
					topPts.add(top[2] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[3] + xOfset);
					topPts.add(top[4] + yOfset);
					topPts.add(top[5] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[6] + xOfset);
					topPts.add(top[7] + yOfset);
					topPts.add(top[8] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[9] + xOfset);
					topPts.add(top[10] + yOfset);
					topPts.add(top[11] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[12] + xOfset);
					topPts.add(top[13] + yOfset);
					topPts.add(top[14] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[15] + xOfset);
					topPts.add(top[16] + yOfset);
					topPts.add(top[17] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[18] + xOfset);
					topPts.add(top[19] + yOfset);
					topPts.add(top[20] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[21] + xOfset);
					topPts.add(top[22] + yOfset);
					topPts.add(top[23] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[24] + xOfset);
					topPts.add(top[25] + yOfset);
					topPts.add(top[26] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[27] + xOfset);
					topPts.add(top[28] + yOfset);
					topPts.add(top[29] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[30] + xOfset);
					topPts.add(top[31] + yOfset);
					topPts.add(top[32] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);

					topPts.add(top[33] + xOfset);
					topPts.add(top[34] + yOfset);
					topPts.add(top[35] + zOfset);

					topPts.add(color[0]);
					topPts.add(color[1]);
					topPts.add(color[2]);
				}
			}
		}
		float[] top = new float[topPts.size()];
		for (int i = 0; i < topPts.size(); i++) {
			top[i] = topPts.get(i);
		}

		chunk = new VertexArrayObject(top, 2);

		numberOfTopPts = top.length / 2;
	}

	public static void initShader() {
		ShaderManager.chunkShader.start();

		ShaderManager.chunkShader.setUniform3f("lightColor", new Vector3f(1f, 1, 1));
		///	ShaderManager.chunkShader.setUniform3f("objectColor", new Vector3f(1f, .5f, .31f));
		ShaderManager.chunkShader.setUniform1f("ambientStrength", .1f);
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

		ShaderManager.chunkShader.setUniform3f("normal", new Vector3f(0, 0, 1));

		glBindVertexArray(chunk.getVaoID());
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_TRIANGLES, 0, numberOfTopPts);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		ShaderManager.chunkShader.stop();

	}
}
