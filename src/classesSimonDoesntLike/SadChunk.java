package classesSimonDoesntLike;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;

import gameEngine.EntityManager;
import maths.Matrix4f;
import maths.Utilities;
import maths.Vector3f;
import maths.Vector4f;

public class SadChunk {

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
			2/sqrt3,   0, 0,  //right 0
			1/sqrt3,  -1, 0,  // lower right 1
			-1/sqrt3, -1, 0,  //lower left 2
			
			2/sqrt3,   0, 0, //right 0
			-1/sqrt3, -1, 0, //lower left 2
			-2/sqrt3,  0, 0, //left 3

			2/sqrt3,   0, 0, //right 0
			-2/sqrt3,  0, 0, //left 3
			-1/sqrt3,  1, 0, //upper left 4

			2/sqrt3,   0, 0, //right 0
			-1/sqrt3,  1, 0, //upper left 4
			1/sqrt3,   1, 0, //upper right 5
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
	
	private static final float[] front = {
		-1/sqrt3,  1, 1, //upper left 4
		1/sqrt3,   1, 1, //upper right 5
		1/sqrt3,   1, 0, //upper right 5
		
		-1/sqrt3,  1, 1, //upper left 4
		1/sqrt3,   1, 0, //upper right 5
		-1/sqrt3,  1, 0  //upper left 4
	};
	
	private static final float[] back = {
			1/sqrt3,  -1, 1, // lower right 1
			-1/sqrt3, -1, 1, //lower left 2
			-1/sqrt3, -1, 0, //lower left 2
			
			1/sqrt3,  -1, 1, // lower right 1
			-1/sqrt3, -1, 0, //lower left 2
			1/sqrt3,  -1, 0  // lower right 1
	};
	
	private static final float[] frontRight = {
		1/sqrt3,   1, 1, //upper right 5
		2/sqrt3,   0, 1, //right 0
		2/sqrt3,   0, 0, //right 0
		
		1/sqrt3,   1, 1, //upper right 5
		2/sqrt3,   0, 0, //right 0
		1/sqrt3,   1, 0  //upper right 5
	};
	
	private static final float[] frontLeft = {
		-2/sqrt3,  0, 1, //left 3
		-1/sqrt3, +1, 1, //lower left 2
		-1/sqrt3, +1, 0, //lower left 2
		
		-2/sqrt3,  0, 1, //left 3
		-1/sqrt3, +1, 0, //lower left 2
		-2/sqrt3,  0, 0  //left 3
	};

	private static final float[] backLeft = {
		-1/sqrt3, -1, 1, //lower left 2
		-2/sqrt3,  0, 1, //left 3
		-2/sqrt3,  0, 0, //left 3
		
		-1/sqrt3, -1, 1, //lower left 2
		-2/sqrt3,  0, 0, //left 3
		-1/sqrt3, -1, 0  //lower left 2
	};
	
	private static final float[] backRight = {
		2/sqrt3,   0, 1, //right 0
		1/sqrt3,  -1, 1, // lower right 1
		1/sqrt3,  -1, 0, // lower right 1
		
		2/sqrt3,   0, 1, //right 0
		1/sqrt3,  -1, 0, // lower right 1
		2/sqrt3,   0, 0  //right 0
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
	private VertexArrayObject[] VAOS = new VertexArrayObject[8];
	private int[] numberOfPts = new int[8];
	private static final Vector3f[] normals = new Vector3f[8];

	public Vector3f[] boundingBox = new Vector3f[8];

	private Matrix4f model;
	private Matrix4f normal;

	public SadChunk(float[][][] properties, int chunkX, int chunkY) {
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

		float[] color = new float[3];
		color[0] = .5f;
		color[1] = .5f;
		color[2] = .31f;

		ArrayList<Float> topPts = new ArrayList<Float>();
		ArrayList<Float> bottomPts = new ArrayList<Float>();
		ArrayList<Float> frontPts = new ArrayList<Float>();
		ArrayList<Float> frontLeftPts = new ArrayList<Float>();
		ArrayList<Float> frontRightPts = new ArrayList<Float>();
		ArrayList<Float> backPts = new ArrayList<Float>();
		ArrayList<Float> backRightPts = new ArrayList<Float>();
		ArrayList<Float> backLeftPts = new ArrayList<Float>();

		for (int x = 0; x < properties.length; x++) {
			for (int y = 0; y < properties[0].length; y++) {
				for (int z = 0; z < CHUNKHEIGHT; z++) {
					if (properties[x][y][z] != 0) {
						float xOfset = 1.2f * (x * 3 * SIDE / 2);
						float yOfset;
						if (x % 2 == 0) {
							yOfset = 1.2f * (y * 2 * APOTHEM);
						} else {
							yOfset = 1.2f * ((y * 2 * APOTHEM - APOTHEM));
						}
						float zOfset = z * 1.0f;

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

						bottomPts.add(bottom[0] + xOfset);
						bottomPts.add(bottom[1] + yOfset);
						bottomPts.add(bottom[2] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[3] + xOfset);
						bottomPts.add(bottom[4] + yOfset);
						bottomPts.add(bottom[5] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[6] + xOfset);
						bottomPts.add(bottom[7] + yOfset);
						bottomPts.add(bottom[8] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[9] + xOfset);
						bottomPts.add(bottom[10] + yOfset);
						bottomPts.add(bottom[11] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[12] + xOfset);
						bottomPts.add(bottom[13] + yOfset);
						bottomPts.add(bottom[14] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[15] + xOfset);
						bottomPts.add(bottom[16] + yOfset);
						bottomPts.add(bottom[17] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[18] + xOfset);
						bottomPts.add(bottom[19] + yOfset);
						bottomPts.add(bottom[20] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[21] + xOfset);
						bottomPts.add(bottom[22] + yOfset);
						bottomPts.add(bottom[23] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[24] + xOfset);
						bottomPts.add(bottom[25] + yOfset);
						bottomPts.add(bottom[26] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[27] + xOfset);
						bottomPts.add(bottom[28] + yOfset);
						bottomPts.add(bottom[29] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[30] + xOfset);
						bottomPts.add(bottom[31] + yOfset);
						bottomPts.add(bottom[32] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						bottomPts.add(bottom[33] + xOfset);
						bottomPts.add(bottom[34] + yOfset);
						bottomPts.add(bottom[35] + zOfset);

						bottomPts.add(color[0]);
						bottomPts.add(color[1]);
						bottomPts.add(color[2]);

						frontPts.add(front[0] + xOfset);
						frontPts.add(front[1] + yOfset);
						frontPts.add(front[2] + zOfset);

						frontPts.add(color[0]);
						frontPts.add(color[1]);
						frontPts.add(color[2]);

						frontPts.add(front[3] + xOfset);
						frontPts.add(front[4] + yOfset);
						frontPts.add(front[5] + zOfset);

						frontPts.add(color[0]);
						frontPts.add(color[1]);
						frontPts.add(color[2]);

						frontPts.add(front[6] + xOfset);
						frontPts.add(front[7] + yOfset);
						frontPts.add(front[8] + zOfset);

						frontPts.add(color[0]);
						frontPts.add(color[1]);
						frontPts.add(color[2]);

						frontPts.add(front[9] + xOfset);
						frontPts.add(front[10] + yOfset);
						frontPts.add(front[11] + zOfset);

						frontPts.add(color[0]);
						frontPts.add(color[1]);
						frontPts.add(color[2]);

						frontPts.add(front[12] + xOfset);
						frontPts.add(front[13] + yOfset);
						frontPts.add(front[14] + zOfset);

						frontPts.add(color[0]);
						frontPts.add(color[1]);
						frontPts.add(color[2]);

						frontPts.add(front[15] + xOfset);
						frontPts.add(front[16] + yOfset);
						frontPts.add(front[17] + zOfset);

						frontPts.add(color[0]);
						frontPts.add(color[1]);
						frontPts.add(color[2]);

						backPts.add(back[0] + xOfset);
						backPts.add(back[1] + yOfset);
						backPts.add(back[2] + zOfset);

						backPts.add(color[0]);
						backPts.add(color[1]);
						backPts.add(color[2]);

						backPts.add(back[3] + xOfset);
						backPts.add(back[4] + yOfset);
						backPts.add(back[5] + zOfset);

						backPts.add(color[0]);
						backPts.add(color[1]);
						backPts.add(color[2]);

						backPts.add(back[6] + xOfset);
						backPts.add(back[7] + yOfset);
						backPts.add(back[8] + zOfset);

						backPts.add(color[0]);
						backPts.add(color[1]);
						backPts.add(color[2]);

						backPts.add(back[9] + xOfset);
						backPts.add(back[10] + yOfset);
						backPts.add(back[11] + zOfset);

						backPts.add(color[0]);
						backPts.add(color[1]);
						backPts.add(color[2]);

						backPts.add(back[12] + xOfset);
						backPts.add(back[13] + yOfset);
						backPts.add(back[14] + zOfset);

						backPts.add(color[0]);
						backPts.add(color[1]);
						backPts.add(color[2]);

						backPts.add(back[15] + xOfset);
						backPts.add(back[16] + yOfset);
						backPts.add(back[17] + zOfset);

						backPts.add(color[0]);
						backPts.add(color[1]);
						backPts.add(color[2]);

						frontRightPts.add(frontRight[0] + xOfset);
						frontRightPts.add(frontRight[1] + yOfset);
						frontRightPts.add(frontRight[2] + zOfset);

						frontRightPts.add(color[0]);
						frontRightPts.add(color[1]);
						frontRightPts.add(color[2]);

						frontRightPts.add(frontRight[3] + xOfset);
						frontRightPts.add(frontRight[4] + yOfset);
						frontRightPts.add(frontRight[5] + zOfset);

						frontRightPts.add(color[0]);
						frontRightPts.add(color[1]);
						frontRightPts.add(color[2]);

						frontRightPts.add(frontRight[6] + xOfset);
						frontRightPts.add(frontRight[7] + yOfset);
						frontRightPts.add(frontRight[8] + zOfset);

						frontRightPts.add(color[0]);
						frontRightPts.add(color[1]);
						frontRightPts.add(color[2]);

						frontRightPts.add(frontRight[9] + xOfset);
						frontRightPts.add(frontRight[10] + yOfset);
						frontRightPts.add(frontRight[11] + zOfset);

						frontRightPts.add(color[0]);
						frontRightPts.add(color[1]);
						frontRightPts.add(color[2]);

						frontRightPts.add(frontRight[12] + xOfset);
						frontRightPts.add(frontRight[13] + yOfset);
						frontRightPts.add(frontRight[14] + zOfset);

						frontRightPts.add(color[0]);
						frontRightPts.add(color[1]);
						frontRightPts.add(color[2]);

						frontRightPts.add(frontRight[15] + xOfset);
						frontRightPts.add(frontRight[16] + yOfset);
						frontRightPts.add(frontRight[17] + zOfset);

						frontRightPts.add(color[0]);
						frontRightPts.add(color[1]);
						frontRightPts.add(color[2]);

						frontLeftPts.add(frontLeft[0] + xOfset);
						frontLeftPts.add(frontLeft[1] + yOfset);
						frontLeftPts.add(frontLeft[2] + zOfset);

						frontLeftPts.add(color[0]);
						frontLeftPts.add(color[1]);
						frontLeftPts.add(color[2]);

						frontLeftPts.add(frontLeft[3] + xOfset);
						frontLeftPts.add(frontLeft[4] + yOfset);
						frontLeftPts.add(frontLeft[5] + zOfset);

						frontLeftPts.add(color[0]);
						frontLeftPts.add(color[1]);
						frontLeftPts.add(color[2]);

						frontLeftPts.add(frontLeft[6] + xOfset);
						frontLeftPts.add(frontLeft[7] + yOfset);
						frontLeftPts.add(frontLeft[8] + zOfset);

						frontLeftPts.add(color[0]);
						frontLeftPts.add(color[1]);
						frontLeftPts.add(color[2]);

						frontLeftPts.add(frontLeft[9] + xOfset);
						frontLeftPts.add(frontLeft[10] + yOfset);
						frontLeftPts.add(frontLeft[11] + zOfset);

						frontLeftPts.add(color[0]);
						frontLeftPts.add(color[1]);
						frontLeftPts.add(color[2]);

						frontLeftPts.add(frontLeft[12] + xOfset);
						frontLeftPts.add(frontLeft[13] + yOfset);
						frontLeftPts.add(frontLeft[14] + zOfset);

						frontLeftPts.add(color[0]);
						frontLeftPts.add(color[1]);
						frontLeftPts.add(color[2]);

						frontLeftPts.add(frontLeft[15] + xOfset);
						frontLeftPts.add(frontLeft[16] + yOfset);
						frontLeftPts.add(frontLeft[17] + zOfset);

						frontLeftPts.add(color[0]);
						frontLeftPts.add(color[1]);
						frontLeftPts.add(color[2]);

						backLeftPts.add(backLeft[0] + xOfset);
						backLeftPts.add(backLeft[1] + yOfset);
						backLeftPts.add(backLeft[2] + zOfset);

						backLeftPts.add(color[0]);
						backLeftPts.add(color[1]);
						backLeftPts.add(color[2]);

						backLeftPts.add(backLeft[3] + xOfset);
						backLeftPts.add(backLeft[4] + yOfset);
						backLeftPts.add(backLeft[5] + zOfset);

						backLeftPts.add(color[0]);
						backLeftPts.add(color[1]);
						backLeftPts.add(color[2]);

						backLeftPts.add(backLeft[6] + xOfset);
						backLeftPts.add(backLeft[7] + yOfset);
						backLeftPts.add(backLeft[8] + zOfset);

						backLeftPts.add(color[0]);
						backLeftPts.add(color[1]);
						backLeftPts.add(color[2]);

						backLeftPts.add(backLeft[9] + xOfset);
						backLeftPts.add(backLeft[10] + yOfset);
						backLeftPts.add(backLeft[11] + zOfset);

						backLeftPts.add(color[0]);
						backLeftPts.add(color[1]);
						backLeftPts.add(color[2]);

						backLeftPts.add(backLeft[12] + xOfset);
						backLeftPts.add(backLeft[13] + yOfset);
						backLeftPts.add(backLeft[14] + zOfset);

						backLeftPts.add(color[0]);
						backLeftPts.add(color[1]);
						backLeftPts.add(color[2]);

						backLeftPts.add(backLeft[15] + xOfset);
						backLeftPts.add(backLeft[16] + yOfset);
						backLeftPts.add(backLeft[17] + zOfset);

						backLeftPts.add(color[0]);
						backLeftPts.add(color[1]);
						backLeftPts.add(color[2]);

						backRightPts.add(backRight[0] + xOfset);
						backRightPts.add(backRight[1] + yOfset);
						backRightPts.add(backRight[2] + zOfset);

						backRightPts.add(color[0]);
						backRightPts.add(color[1]);
						backRightPts.add(color[2]);

						backRightPts.add(backRight[3] + xOfset);
						backRightPts.add(backRight[4] + yOfset);
						backRightPts.add(backRight[5] + zOfset);

						backRightPts.add(color[0]);
						backRightPts.add(color[1]);
						backRightPts.add(color[2]);

						backRightPts.add(backRight[6] + xOfset);
						backRightPts.add(backRight[7] + yOfset);
						backRightPts.add(backRight[8] + zOfset);

						backRightPts.add(color[0]);
						backRightPts.add(color[1]);
						backRightPts.add(color[2]);

						backRightPts.add(backRight[9] + xOfset);
						backRightPts.add(backRight[10] + yOfset);
						backRightPts.add(backRight[11] + zOfset);

						backRightPts.add(color[0]);
						backRightPts.add(color[1]);
						backRightPts.add(color[2]);

						backRightPts.add(backRight[12] + xOfset);
						backRightPts.add(backRight[13] + yOfset);
						backRightPts.add(backRight[14] + zOfset);

						backRightPts.add(color[0]);
						backRightPts.add(color[1]);
						backRightPts.add(color[2]);

						backRightPts.add(backRight[15] + xOfset);
						backRightPts.add(backRight[16] + yOfset);
						backRightPts.add(backRight[17] + zOfset);

						backRightPts.add(color[0]);
						backRightPts.add(color[1]);
						backRightPts.add(color[2]);
					}
				}
			}
		}
		float[] top = new float[topPts.size()];
		for (int i = 0; i < topPts.size(); i++) {
			top[i] = topPts.get(i);
		}

		VAOS[TOP] = new VertexArrayObject(top, 2);

		numberOfPts[TOP] = top.length / 2;

		float[] bottom = new float[bottomPts.size()];
		for (int i = 0; i < bottomPts.size(); i++) {
			bottom[i] = bottomPts.get(i);
		}

		VAOS[BOTTOM] = new VertexArrayObject(bottom, 2);

		numberOfPts[BOTTOM] = bottom.length / 2;

		float[] front = new float[frontPts.size()];
		for (int i = 0; i < frontPts.size(); i++) {
			front[i] = frontPts.get(i);
		}

		VAOS[FRONT] = new VertexArrayObject(front, 2);

		numberOfPts[FRONT] = front.length / 2;

		float[] back = new float[backPts.size()];
		for (int i = 0; i < backPts.size(); i++) {
			back[i] = backPts.get(i);
		}

		VAOS[BACK] = new VertexArrayObject(back, 2);

		numberOfPts[BACK] = back.length / 2;

		float[] frontRight = new float[frontRightPts.size()];
		for (int i = 0; i < frontRightPts.size(); i++) {
			frontRight[i] = frontRightPts.get(i);
		}

		VAOS[FRONTRIGHT] = new VertexArrayObject(frontRight, 2);

		numberOfPts[FRONTRIGHT] = frontRight.length / 2;

		float[] frontLeft = new float[frontLeftPts.size()];
		for (int i = 0; i < frontLeftPts.size(); i++) {
			frontLeft[i] = frontLeftPts.get(i);
		}

		VAOS[FRONTLEFT] = new VertexArrayObject(frontLeft, 2);

		numberOfPts[FRONTLEFT] = frontLeft.length / 2;

		float[] backLeft = new float[backLeftPts.size()];
		for (int i = 0; i < backLeftPts.size(); i++) {
			backLeft[i] = backLeftPts.get(i);
		}

		VAOS[BACKLEFT] = new VertexArrayObject(backLeft, 2);

		numberOfPts[BACKLEFT] = backLeft.length / 2;

		float[] backRight = new float[backRightPts.size()];
		for (int i = 0; i < backRightPts.size(); i++) {
			backRight[i] = backRightPts.get(i);
		}

		VAOS[BACKRIGHT] = new VertexArrayObject(backRight, 2);

		numberOfPts[BACKRIGHT] = backRight.length / 2;
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

		ShaderManager.chunkShader.setUniformMatrix4f("model", model);
		ShaderManager.chunkShader.setUniformMatrix4f("norm", normal);

		Vector3f cameraDir = EntityManager.camera.getPos().subtract(EntityManager.camera.getTarget());

		for (int i = 0; i < normals.length; i++) {
			if (normals[i].dot(cameraDir) > 0) {
				ShaderManager.chunkShader.setUniform3f("normal", normals[i]);
				glBindVertexArray(VAOS[i].getVaoID());
				glDrawArrays(GL_TRIANGLES, 0, numberOfPts[i]);
			}
		}

		glBindVertexArray(0);
		ShaderManager.chunkShader.stop();
	}
}
