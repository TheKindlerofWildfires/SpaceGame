package gameEngine;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BORDER_COLOR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexParameterfv;
import static org.lwjgl.opengl.GL13.GL_TEXTURE5;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.GL_R32UI;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;
import static org.lwjgl.opengl.GL31.glTexBuffer;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import GUI.Vector3f;
import graphicEngine.ShaderManager;
import graphicEngine.Utilities;
import graphicEngine.VertexArrayObject;

public class Map {
	public static final int HEXESACROSS = 300;
	public static final int HEXESDOWN = 200;

	public static final int MOISTURESCALER = 12;
	public static final int ELEVATIONSCALER = 17;

<<<<<<< HEAD
	public static final float APOTHEM = 0.002f;

	public static final int LAND = 100;
	public static final int WATER = 0;
	public static final int SEED = 50;
=======
	public static final float APOTHEM = 0.02f;
>>>>>>> mitchell-bitches-about-gameplay

	public String mapType;
	public int seedCount;

	public String[] maps = { "fractal", "soft", "disk", "stand", "trig" };
	private ArrayList<ArrayList<Hexagon>> hexes = new ArrayList<ArrayList<Hexagon>>();
	//private Hexagon[] seeds;

	private Random rng = new Random();

	public static final float sqrt3 = 1.7320508075688772f;
	public static final float aspectScaler = 16 / 9f;
	public static final float side = APOTHEM * 2 / sqrt3;

	public static float[] vertices = { side, 0, 0, //right 0
			side / 2, -APOTHEM * aspectScaler, 0, // lower right 1
			-side / 2, -APOTHEM * aspectScaler, 0, //lower left 2
			-side, 0, 0, //left 3
			-side / 2, APOTHEM * aspectScaler, 0, //upper left 4
			side / 2, APOTHEM * aspectScaler, 0, //upper right 5
	};

	public byte[] indices = new byte[] { 0, 1, 2, 3, 4, 5, 0 };
	public VertexArrayObject vao = new VertexArrayObject(vertices, indices);
	public int vaoID = vao.getVaoID();

	public int[][] land = new int[HEXESACROSS][HEXESDOWN];
	public int[][] seeds;

	public Map() {
		long seed = rng.nextLong();
		rng.setSeed(seed);
		System.out.println("Random Seed is " + seed);
		initializeMap();
		initShader();
	}

	private void initShader() {
		ShaderManager.shader1.start();
		ShaderManager.shader1.setUniform1f("side", side);
		ShaderManager.shader1.setUniform1i("hexesAcross", HEXESACROSS);
		ShaderManager.shader1.setUniform1f("apothem", APOTHEM);
		ShaderManager.shader1.setUniform1f("aspect", aspectScaler);
		ShaderManager.shader1.setUniform3f("pos", new Vector3f(-1f, 1f, 0));
		int[] land = new int[HEXESACROSS * HEXESDOWN];
		int counter = 0;
		for (int x = 0; x < HEXESDOWN; x++) {
			for (int y = 0; y < HEXESACROSS; y++) {
				land[counter] = this.land[y][x];
				counter++;
			}
		}
		//land[2025] = SEED;
		int bufferID = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBindBuffer(GL_TEXTURE_BUFFER, bufferID);
		IntBuffer data = Utilities.createIntBuffer(land);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);

		int textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_BUFFER, textureID);
		glTexBuffer(GL_TEXTURE_BUFFER, GL_R32UI, bufferID);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glActiveTexture(GL_TEXTURE5);
		glBindTexture(GL_TEXTURE_BUFFER, textureID);
		float color[] = { 1.0f, 0.0f, 0.0f, 1.0f };
		glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, Utilities.createFloatBuffer(color));
	}

	private Hexagon[] getAllNeighbors(Hexagon hex) {
		if (hex.xIndex > 0 && hex.yIndex > 0 && hex.xIndex < HEXESACROSS - 1 && hex.yIndex < HEXESDOWN - 1) {
			Hexagon[] neighbors = new Hexagon[6];
			neighbors[0] = hexes.get(hex.yIndex + 1).get(hex.xIndex);
			neighbors[1] = hexes.get(hex.yIndex - 1).get(hex.xIndex);
			neighbors[2] = hexes.get(hex.yIndex).get(hex.xIndex + 1);
			neighbors[3] = hexes.get(hex.yIndex).get(hex.xIndex - 1);
			if (hex.xIndex % 2 == 0) {
				neighbors[4] = hexes.get(hex.yIndex - 1).get(hex.xIndex + 1);
				neighbors[5] = hexes.get(hex.yIndex - 1).get(hex.xIndex - 1);
			} else {
				neighbors[4] = hexes.get(hex.yIndex + 1).get(hex.xIndex + 1);
				neighbors[5] = hexes.get(hex.yIndex + 1).get(hex.xIndex - 1);
			}
			return neighbors;
		} else {
			return new Hexagon[0];
		}
	}

	private int[][] getNeighborIndices(int x, int y) {
	//	System.out.println(x + "," + y);
		if (x > 0 && y > 0 && x < HEXESACROSS - 1 && y < HEXESDOWN - 1) {
			int[][] neighbors = new int[6][2];
			neighbors[0][0] = x;
			neighbors[0][1] = y + 1;
			neighbors[1][0] = x;
			neighbors[1][1] = y - 1;
			neighbors[2][0] = x + 1;
			neighbors[2][1] = y;
			neighbors[3][0] = x - 1;
			neighbors[3][1] = y;
			if (x % 2 == 0) {
				neighbors[4][0] = x + 1;
				neighbors[4][1] = y - 1;
				neighbors[5][0] = x + 1;
				neighbors[5][1] = y + 1;
			} else {
				neighbors[4][0] = x - 1;
				neighbors[4][1] = y - 1;
				neighbors[5][0] = x - 1;
				neighbors[5][1] = y + 1;
			}
			return neighbors;
		} else {
			return new int[0][0];
		}
	}

	public void draw() {
		ShaderManager.shader1.start();
		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);
		glDrawArraysInstanced(GL_TRIANGLE_FAN, 0, 6, HEXESACROSS * HEXESDOWN);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		ShaderManager.shader1.stop();
	}

	private void initializeMap() {
		//INIT MAPTYPE ETC
		mapType = "fractal";
		seedCount = 1;
		seeds = new int[seedCount][2];
		///mapType = maps[rng.nextInt(maps.length)];

		/*// INIT TILES
		for (int i = 0; i <= HEXESACROSS; i++) {
			hexes.add(new ArrayList<Hexagon>());
		}
		
		//Perlin noise = new Perlin();
		//noise.setSeed(rng.nextInt(1000000));
		for (int j = 0; j < HEXESDOWN; j++) {
			for (int i = 0; i < HEXESACROSS; i++) {
				hexes.get(j)
						.add(new Hexagon(i, j,
								(float) Math.abs(noise.getValue(i / MOISTURESCALER, j / MOISTURESCALER, .1)),
								(float) Math.abs(noise.getValue(i / ELEVATIONSCALER, j / ELEVATIONSCALER, .3))));
			}
		}*/

		// INIT SEEDS
		System.out.println("Initiailzing Seeds");
		seeds[0][0] = 100;
		seeds[0][1] = 100;

		//INIT LAND ARRAY
		for (int x = 0; x < HEXESACROSS; x++) {
			for (int y = 0; y < HEXESDOWN; y++) {
				land[x][y] = WATER;
			}
		}

		/*seeds = new Hexagon[seedCount];
		for (int i = 0; i < seedCount; i++) {
			seeds[i] = hexes.get(HEXESDOWN / (8 / 3) + rng.nextInt(HEXESDOWN / 3))
					.get(HEXESACROSS / (8 / 3) + rng.nextInt(HEXESACROSS / 3));
			seeds[i].setLand(true);
		}*/

		// GEN LAND
		System.out.println("Genning Land");
		ArrayList<int[]> outerLand = new ArrayList<int[]>();
		for (int i = 0; i < seedCount; i++) {
			int[] seed = { seeds[i][0], seeds[i][1] };
			outerLand.add(seed);
			land[seed[0]][seed[1]] = SEED;
		}

		/*	ArrayList<int[]> newLand = new ArrayList<int[]>();
			for (int i = 0; i < outerLand.size(); i++) {
				int[][] neighbors = getNeighborIndices(seeds[0][1], seeds[0][1]);
				for (int j = 0; j < neighbors.length; j++) {
					land[neighbors[j][0]][neighbors[j][1]] = LAND;
					newLand.add(neighbors[j]);
				}
			}*/
		double p = 1;
		while (outerLand.size() != 0 && p != 0) {
			ArrayList<int[]> newLand = new ArrayList<int[]>();
			for (int i = 0; i < outerLand.size(); i++) {
				int[][] neighbors = getNeighborIndices(outerLand.get(i)[0], outerLand.get(i)[1]);
				for (int j = 0; j < neighbors.length; j++) {
					if (land[neighbors[j][0]][neighbors[j][1]] != LAND && land[neighbors[j][0]][neighbors[j][1]] != SEED ) {
						if (rng.nextDouble() <= p) {
							land[neighbors[j][0]][neighbors[j][1]] = LAND;
							newLand.add(neighbors[j]);
						}
					}
				}
			}
			outerLand.clear();
			outerLand.addAll(newLand);
			newLand.clear();

			switch (mapType) {
			case "fractal":
				p = 0.29 / (Math.log(p + 2));
				break;
			case "soft":
				p = 0.18 / p;
				break;
			case "disk":
				p = Math.pow(2.618, -2.47 * p);
				break;
			case "stand":
				p = 0.988 * p;
				break;
			case "trig":
				p = Math.cos(1.443 * p);
				break;
			default:
				System.err.println("invalid map type");
				System.exit(-1);
				p = 0;
			}
		}

		/*hexes.get(10).get(10).setLand(true);
		hexes.get(11).get(10).setLand(true);
		hexes.get(12).get(10).setLand(true);
		hexes.get(13).get(10).setLand(true);
		hexes.get(14).get(10).setLand(true);
		hexes.get(15).get(10).setLand(true);
		
		hexes.get(13).get(10).setLand(true);
		hexes.get(13).get(11).setLand(true);
		hexes.get(13).get(12).setLand(true);
		hexes.get(13).get(13).setLand(true);
		
		hexes.get(10).get(13).setLand(true);
		hexes.get(11).get(13).setLand(true);
		hexes.get(12).get(13).setLand(true);
		hexes.get(13).get(13).setLand(true);
		hexes.get(14).get(13).setLand(true);
		hexes.get(15).get(13).setLand(true);
		
		hexes.get(10).get(15).setLand(true);
		hexes.get(12).get(15).setLand(true);
		hexes.get(13).get(15).setLand(true);
		hexes.get(14).get(15).setLand(true);
		hexes.get(15).get(15).setLand(true);*/

		System.out.println("Map init complete");
	}
}
