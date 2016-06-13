package graphicEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import gameEngine.EntityManager;
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
	
	private static final float[] bottom = {
			2/sqrt3,   0, 0,   0,1,1,  0,0,-1,//right 0
			1/sqrt3,  -1, 0,   1,0,1,  0,0,-1,// lower right 1
			-1/sqrt3, -1, 0,   1,1,0,  0,0,-1,//lower left 2
			-2/sqrt3,  0, 0,   1,0,1,  0,0,-1,//left 3
			-1/sqrt3,  1, 0,   0,1,1,  0,0,-1,//upper left 4
			1/sqrt3,   1, 0,   1,0,1,  0,0,-1,//upper right 5
	};
		
	private static final float[] top = { //NORMAL 0,0,1
			2/sqrt3,   0, 1,   1,0,0,  0,0,1,//right 0
			1/sqrt3,  -1, 1,   0,0,1,  0,0,1,// lower right 1
			-1/sqrt3, -1, 1,   1,0,0,  0,0,1,//lower left 2
			-2/sqrt3,  0, 1,   0,0,1,  0,0,1,//left 3
			-1/sqrt3,  1, 1,   1,0,0,  0,0,1,//upper left 4
			1/sqrt3,   1, 1,   0,0,1,  0,0,1,//upper right 5
	};
	
	private static final float[] front = {
			-1/sqrt3,  1, 1,   1,0,1,  0,1,0,//upper left 4
			1/sqrt3,   1, 1,   1,0,1,  0,1,0,//upper right 5
			1/sqrt3,   1, 0,   1,0,1,  0,1,0,//upper right 5
			-1/sqrt3,  1, 0,   1,0,1,  0,1,0,//upper left 4
	};
	
	private static final float[] back = {
			1/sqrt3,  -1, 1,   0,1,0,  0,-1,0,// lower right 1
			-1/sqrt3, -1, 1,   0,1,0,  0,-1,0,//lower left 2
			-1/sqrt3, -1, 0,   0,1,0,  0,-1,0,//lower left 2
			1/sqrt3,  -1, 0,   0,1,0,  0,-1,0,// lower right 1
	};
	
	private static final float[] frontRight = {
			1/sqrt3,   1, 1,   1,1,0,  1/2,sqrt3/2,0,//upper right 5
			2/sqrt3,   0, 1,   1,1,0,  1/2,sqrt3/2,0,//right 0
			2/sqrt3,   0, 0,   1,1,0,  1/2,sqrt3/2,0,//right 0
			1/sqrt3,   1, 0,   1,1,0,  1/2,sqrt3/2,0,//upper right 5
	};
	
	private static final float[] frontLeft = {
			-2/sqrt3,  0, 1,   0,1,1,  -1/2,sqrt3/2,0,//left 3
			-1/sqrt3, +1, 1,   0,1,1,  -1/2,sqrt3/2,0,//lower left 2
			-1/sqrt3, +1, 0,   0,1,1,  -1/2,sqrt3/2,0,//lower left 2
			-2/sqrt3,  0, 0,   0,1,1,  -1/2,sqrt3/2,0//left 3
	};

	private static final float[] backLeft = {
			-1/sqrt3, -1, 1,   0,0,1,  -1/2,-sqrt3/2,0,//lower left 2
			-2/sqrt3,  0, 1,   0,0,1,  -1/2,-sqrt3/2,0,//left 3
			-2/sqrt3,  0, 0,   0,0,1,  -1/2,-sqrt3/2,0,//left 3
			-1/sqrt3, -1, 0,   0,0,1,  -1/2,-sqrt3/2,0,//lower left 2
	};
	
	private static final float[] backRight = {
			2/sqrt3,   0, 1,   1,0,0,  1/2,-sqrt3/2,0,//right 0
			1/sqrt3,  -1, 1,   1,0,0,  1/2,-sqrt3/2,0,// lower right 1
			1/sqrt3,  -1, 0,   1,0,0,  1/2,-sqrt3/2,0,// lower right 1
			2/sqrt3,   0, 0,   1,0,0,  1/2,-sqrt3/2,0,//right 0
	};
	
	public static byte[] indices = new byte[] {
			0,1,2,0,2,3,0,3,4,0,4,5, 
			6,7,8,6,8,9,6,9,10,6,10,11, 
			12,13,14,12,14,15,
			16,17,18,16,18,19,
			20,21,22,20,22,23,
			24,25,26,24,26,27,
			28,29,30,28,30,31,
			32,33,34,32,34,35 
	};
	
	public static byte[] hexesIndices = {
			0,1,2,0,2,3,0,3,4,0,4,5, 
	};

	public static byte[] sidesIndices = {
			0,1,2,0,2,3
	};
	//@formatter:on

	public static final int FRONT = 0;
	public static final int BACK = 1;
	public static final int TOP = 2;
	public static final int BOTTOM = 3;
	public static final int FRONTLEFT = 4;
	public static final int FRONTRIGHT = 5;
	public static final int BACKRIGHT = 6;
	public static final int BACKLEFT = 7;

	//PER FACE VAOS, VAO LENGTHS, AND NORMALS
	private static final VertexArrayObject[] VAOS = new VertexArrayObject[8];
	private static final VertexArrayObject fullVAO = new VertexArrayObject(vertices, indices);
	private static final int fullNumberOfPts = vertices.length;
	private static final int[] numberOfPts = new int[8];
	private static final Vector3f[] normals = new Vector3f[8];

	public Vector3f[] boundingBox = new Vector3f[8];

	private Matrix4f model;
	private Matrix4f normal;

	private int UBO;

	public Chunk(float[][][] properties, int chunkX, int chunkY) {
		//NORMALS FOR LIGHT AND FOR FACE CULLING
		normals[FRONT] = new Vector3f(0, 1, 0);
		normals[BACK] = new Vector3f(0, -1, 0);
		normals[TOP] = new Vector3f(0, 0, 1);
		normals[BOTTOM] = new Vector3f(0, 0, -1);
		normals[FRONTLEFT] = new Vector3f(-1 / 2, sqrt3 / 2, 0);
		normals[FRONTRIGHT] = new Vector3f(1 / 2, sqrt3 / 2, 0);
		normals[BACKRIGHT] = new Vector3f(1 / 2, -sqrt3 / 2, 0);
		normals[BACKLEFT] = new Vector3f(-1 / 2, -sqrt3 / 2, 0);

		//MATRICES FOR RENDERING
		model = Matrix4f
				.translate(6 * 1.2f * chunkX / 4 * CHUNKSIZE - 10,
						2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * chunkY - 10, 0)
				.multiply(Matrix4f.scale(1, 1, .5f));

		normal = Matrix4f.inverse(model).transpose();

		//BOUNDING BOX FOR FRUSTUM CULLING
		boundingBox[0] = new Vector3f(6 * 1.2f * chunkX / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * chunkY - 10, 0);

		boundingBox[1] = new Vector3f(6 * 1.2f * (chunkX + 1) / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * chunkY - 10, 0);

		boundingBox[2] = new Vector3f(6 * 1.2f * chunkX / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * (chunkY + 1) - 10, 0);

		boundingBox[3] = new Vector3f(6 * 1.2f * (chunkX + 1) / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * (chunkY + 1) - 10, 0);

		boundingBox[4] = new Vector3f(6 * 1.2f * chunkX / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * chunkY - 10, 16);

		boundingBox[5] = new Vector3f(6 * 1.2f * (chunkX + 1) / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * chunkY - 10, 16);

		boundingBox[6] = new Vector3f(6 * 1.2f * chunkX / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * (chunkY + 1) - 10, 16);

		boundingBox[7] = new Vector3f(6 * 1.2f * (chunkX + 1) / 4 * CHUNKSIZE - 10,
				2 * CHUNKSIZE * (float) Math.sqrt(3) / 2 * 1.2f * (chunkY + 1) - 10, 16);

		//CREATE VAOS (Dont question it)
		VAOS[TOP] = new VertexArrayObject(top, hexesIndices);
		VAOS[BOTTOM] = new VertexArrayObject(bottom, hexesIndices);
		VAOS[FRONT] = new VertexArrayObject(front, sidesIndices);
		VAOS[BACK] = new VertexArrayObject(back, sidesIndices);
		VAOS[FRONTRIGHT] = new VertexArrayObject(frontRight, sidesIndices);
		VAOS[FRONTLEFT] = new VertexArrayObject(frontLeft, sidesIndices);
		VAOS[BACKRIGHT] = new VertexArrayObject(backRight, sidesIndices);
		VAOS[BACKLEFT] = new VertexArrayObject(backLeft, sidesIndices);

		//HOW BIG ARE MY ARRAYS??? NO ONE KNOWS (Except this part of the code. it knows)
		numberOfPts[TOP] = top.length;
		numberOfPts[BOTTOM] = bottom.length;
		numberOfPts[FRONT] = front.length;
		numberOfPts[BACK] = back.length;
		numberOfPts[FRONTLEFT] = frontLeft.length;
		numberOfPts[FRONTRIGHT] = frontRight.length;
		numberOfPts[BACKLEFT] = backLeft.length;
		numberOfPts[BACKRIGHT] = backRight.length;

		UBO = Utilities.createUniformBuffer(Utilities.createUniformFloatBuffer(Utilities.flatten(properties)));
	}

	public static void initShader() {
		ShaderManager.chunkShader.start();

		ShaderManager.chunkShader.setUniform3f("lightColor", new Vector3f(1f, 1, 1));
		///	ShaderManager.chunkShader.setUniform3f("objectColor", new Vector3f(1f, .5f, .31f));
		ShaderManager.chunkShader.setUniform1f("ambientStrength", .1f);
		ShaderManager.chunkShader.setUniform1f("specularStrength", .4f);
<<<<<<< HEAD
		ShaderManager.chunkShader.setUniform1f("shininess", 40);
=======
		ShaderManager.chunkShader.setUniform1f("shininess", 256);
>>>>>>> iWillBeOptimizedPls

		ShaderManager.chunkShader.setUniform1f("apothem", (float) Math.sqrt(3) / 2);
		ShaderManager.chunkShader.setUniform1f("side", 1f);
		ShaderManager.chunkShader.setUniform1i("chunkSize", CHUNKSIZE);

		ShaderManager.chunkShader.setUniformBlockf("Properties", Utilities.createUniformBuffer(
				Utilities.createUniformFloatBuffer(new float[CHUNKSIZE * CHUNKSIZE * CHUNKHEIGHT])), 0);

		ShaderManager.chunkShader.stop();
	}

	public void render() {
		ShaderManager.chunkShader.start();

		ShaderManager.chunkShader.setUniformMatrix4f("model", model);
		ShaderManager.chunkShader.setUniformMatrix4f("norm", normal);

		ShaderManager.chunkShader.setUniformBlockf("Properties", UBO, 0);

		Vector3f cameraDir = EntityManager.camera.getPos().subtract(EntityManager.camera.getTarget());

		for (int i = 0; i < normals.length; i++) {
			if (normals[i].dot(cameraDir) > .1) { //MAKES IT FASTER, BUT NEEDS TWEAKING
				ShaderManager.chunkShader.setUniform3f("normal", normals[i]);
				glBindVertexArray(VAOS[i].getVaoID());
				glDrawElementsInstanced(GL_TRIANGLES,
						(i == BOTTOM || i == TOP) ? hexesIndices.length : sidesIndices.length, GL_UNSIGNED_BYTE, 0,
						CHUNKSIZE * CHUNKSIZE * CHUNKHEIGHT);
			}
		}
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		ShaderManager.chunkShader.stop();
	}
}
