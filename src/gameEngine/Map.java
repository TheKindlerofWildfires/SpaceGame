package gameEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import graphicEngine.ShaderManager;
import graphicEngine.VertexArrayObject;
import maths.Utilities;
import maths.Vector3f;
//import classesSimonDoesntLike.Hexagon;

public class Map {
	public static final int HEXESACROSS = 320;
	public static final int HEXESDOWN = 250;
	public static final int AVG = ((HEXESACROSS+HEXESDOWN)/2);

	public static final int MOISTURESCALER = 12;
	public static final int ELEVATIONSCALER = 17;

	public static final int LAND = 100;
	public static final int WATER = 0;
	public static final int SEED = 50;

	public String mapType;
	public int seedCount;

	ShaderManager shaderManager;
	//public static ArrayList<ArrayList<Hexagon>> hexes = new ArrayList<ArrayList<Hexagon>>();
	//private Hexagon[] seeds;

	private Random rng = new Random();

	public VertexArrayObject vao = new VertexArrayObject(EntityManager.vertices, EntityManager.indices);
	public int vaoID = vao.getVaoID();

	public static final String[] maps = { "fractal", "disk", "soft", "stand", "trig", "quad"};

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
		ShaderManager.landShader.start();
		ShaderManager.landShader.setUniform1f("side", EntityManager.side);
		ShaderManager.landShader.setUniform1i("hexesAcross", HEXESACROSS);
		ShaderManager.landShader.setUniform1f("apothem", EntityManager.APOTHEM);
		ShaderManager.landShader.setUniform1f("aspect", EntityManager.aspectScaler);
		ShaderManager.landShader.setUniform3f("pos", new Vector3f(-1f, 1f, 0));

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

	/*@Deprecated
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
	}*/

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
				neighbors[4][0] = x - 1;
				neighbors[4][1] = y + 1;
				neighbors[5][0] = x + 1;
				neighbors[5][1] = y + 1;
			} else {
				neighbors[4][0] = x - 1;
				neighbors[4][1] = y - 1;
				neighbors[5][0] = x + 1;
				neighbors[5][1] = y - 1;
			}
			return neighbors;
		} else {
			return new int[0][0];
		}
	}

	public void render() {
		ShaderManager.landShader.start();
		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);
		glDrawArraysInstanced(GL_TRIANGLE_FAN, 0, 6, HEXESACROSS * HEXESDOWN);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		ShaderManager.landShader.stop();
	}

	private void initializeMap() {
		//INIT MAPTYPE ETC
		//mapType = "quad";
		mapType = maps[rng.nextInt(maps.length)];
		seedCount = 1;
		seeds = new int[seedCount][2];

		// INIT SEEDS
		System.out.println("Initiailzing Seeds");
		seeds[0][0] = HEXESACROSS/2;
		seeds[0][1] = HEXESDOWN/2;

		//INIT LAND ARRAY
		for (int x = 0; x < HEXESACROSS; x++) {
			for (int y = 0; y < HEXESDOWN; y++) {
				land[x][y] = WATER;
			}
		}

		// GEN LAND
		System.out.println("Genning Land");
		ArrayList<int[]> outerLand = new ArrayList<int[]>();
		for (int i = 0; i < seedCount; i++) {
			int[] seed = { seeds[i][0], seeds[i][1] };
			outerLand.add(seed);
			land[seed[0]][seed[1]] = SEED;
		}

		//VALUE FOR TROUBLESHOOTING--DONT DELETE
		/*int[][] neighbors = getNeighborIndices(seeds[0][0], seeds[0][1]);
		for (int j = 0; j < neighbors.length; j++) {
			if (land[neighbors[j][0]][neighbors[j][1]] != LAND && land[neighbors[j][0]][neighbors[j][1]] != SEED) {
				land[neighbors[j][0]][neighbors[j][1]] = LAND;
			}
		}*/

		
		double p = 5;
		while (outerLand.size() != 0 && p != 0) {
			ArrayList<int[]> newLand = new ArrayList<int[]>();
			for (int i = 0; i < outerLand.size(); i++) {
				int[][] neighbors = getNeighborIndices(outerLand.get(i)[0], outerLand.get(i)[1]);
				for (int j = 0; j < neighbors.length; j++) {
					if (land[neighbors[j][0]][neighbors[j][1]] != LAND
							&& land[neighbors[j][0]][neighbors[j][1]] != SEED) {
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
				p = .3 / (Math.log(p + 2));
				break;
			case "soft":
				p = 0.8 / p;
				break;
			case "stand":
				p = (p+1)/(p+3.4);
				break;
			case "disk":
				p = 0.96 * p;
				break;
			case "trig":
				p = Math.cos(1.443 * p);
				break;
			case "quad":
				if (p>=1){
					p = 0.15;
				}else{
					p = Math.pow(p,2)+2*p;
				}
				break;
			default:
				System.err.println("invalid map type");
				System.exit(-1);
				p = 0;
			}
		}

		System.out.println("Map init complete");
	}
	/*public int getSeeds(String type){
		if (type == "x"){
			return seeds[0][0];
		}else if (type =="y"){
			return seeds[0][1];
		}
		return 15;
	}*/
}
