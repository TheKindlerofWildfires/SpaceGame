package gameEngine;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL13.GL_TEXTURE5;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL30.GL_R32UI;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;
import static org.lwjgl.opengl.GL31.glTexBuffer;
import graphicEngine.Chunk;
import graphicEngine.ShaderManager;
import graphicEngine.VertexArrayObject;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import noiseLibrary.module.source.Perlin;
import maths.Distance;
import maths.Utilities;
import maths.Vector3f;

public class Map {

	int numberOfChunks = 10;
	Chunk[] chunk = new Chunk[numberOfChunks];

	public static final int HEXESACROSS = 192;
	public static final int HEXESDOWN = 96;

	public static final int MOISTURESCALER = 3;
	public static final int ELEVATIONSCALER = 1;

	public static final int LAND = 100;
	public static final int WATER = 20;
	public static final int SEED = 50;

	public static String mapType;
	public static String worldType;
	public int seedCount;
	public int landCount;

	Distance distance;
	ShaderManager shaderManager;

	public static Random rng = new Random();

	public Chunk[][] chunks = new Chunk[HEXESACROSS / Chunk.CHUNKSIZE][HEXESDOWN / Chunk.CHUNKSIZE];

	public static VertexArrayObject vao = new VertexArrayObject(EntityManager.vertices, EntityManager.indices);
	public static int vaoID = vao.getVaoID();

	public static final String[] maps = { "fractal","soft","stand","trig","quad","e2","e" };
	public static final String[] splots = { "grit","exp","ln","rng","arm","disk" };// there are more
	public static final String[] worldTypes = { "telilic","sapric","worlic" };
	public static int[][] land = new int[HEXESACROSS][HEXESDOWN];
	public static int[][] elevation = new int[HEXESACROSS + 1][HEXESDOWN + 1];
	public static int[][] moisture = new int[HEXESACROSS + 1][HEXESDOWN + 1];

	public int[][] seeds;
	public int[] seed = new int[2];

	private float offsetX = 0;
	private float offsetY = 0;
	private float zoomFactor;
	Perlin noise = new Perlin();

	float[] data = new float[Chunk.CHUNKSIZE * Chunk.CHUNKSIZE * Chunk.CHUNKHEIGHT];

	public Map() {

		distance = new Distance();
		long seed = rng.nextLong();
		rng.setSeed(seed);
		noise.setSeed((int) seed);
		noise.setFrequency(2);
		noise.setLacunarity(2);
		System.out.println("Random Seed is " + seed);
		initializeMap();
		generateFoliage();

		for (int x = 0; x < chunks.length; x++) {
			for (int y = 0; y < chunks[0].length; y++) {

				for (int i = 0; i < Chunk.CHUNKSIZE * Chunk.CHUNKSIZE * Chunk.CHUNKHEIGHT; i++) {
					data[i] = 0;
				}

				for (int i = 0; i < Chunk.CHUNKSIZE * Chunk.CHUNKSIZE * Chunk.CHUNKHEIGHT; i++) {
					if (i < Chunk.CHUNKSIZE * Chunk.CHUNKSIZE) {
						int a = i / Chunk.CHUNKSIZE + x * Chunk.CHUNKSIZE;
						int b = i % Chunk.CHUNKSIZE + y * Chunk.CHUNKSIZE;

						data[i] = land[a][b];
						if (elevation[a][b] > 0) {
							data[i + Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 1) {
							data[i + 2 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 2) {
							data[i + 3 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 3) {
							data[i + 4 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 4) {
							data[i + 5 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 5) {
							data[i + 6 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 6) {
							data[i + 7 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 7) {
							data[i + 8 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 8) {
							data[i + 9 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 9) {
							data[i + 10 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 10) {
							data[i + 11 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 11) {
							data[i + 12 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 12) {
							data[i + 13 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 13) {
							data[i + 14 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						if (elevation[a][b] > 14) {
							data[i + 15 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						}
						//	if (elevation[a][b] > 15) {
						//	data[i + 16 * Chunk.CHUNKSIZE * Chunk.CHUNKSIZE] = land[a][b];
						//}*/
					} else {
						//data[i] = 0;
					}
				}

				//	chunks[x][y] = new Chunk(data, x, y);
			}
		}

		float[][][] dat = new float[Chunk.CHUNKSIZE][Chunk.CHUNKSIZE][Chunk.CHUNKHEIGHT];

		for (int x = 0; x < dat.length; x++) {
			for (int y = 0; y < dat[0].length; y++) {
				for (int z = 0; z < dat[0][0].length; z++) {
					dat[x][y][z] = 1;
				}
			}
		}
		for (int i = 0; i < chunk.length; i++) {
			chunk[i] = new Chunk(dat, i, 1);
		}

	}

	@Deprecated
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
				land[counter] = Map.land[y][x];
				counter++;
			}
		}
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

	}

	/*
	 * @Deprecated private Hexagon[] getAllNeighbors(Hexagon hex) { if
	 * (hex.xIndex > 0 && hex.yIndex > 0 && hex.xIndex < HEXESACROSS - 1 &&
	 * hex.yIndex < HEXESDOWN - 1) { Hexagon[] neighbors = new Hexagon[6];
	 * neighbors[0] = hexes.get(hex.yIndex + 1).get(hex.xIndex); neighbors[1] =
	 * hexes.get(hex.yIndex - 1).get(hex.xIndex); neighbors[2] =
	 * hexes.get(hex.yIndex).get(hex.xIndex + 1); neighbors[3] =
	 * hexes.get(hex.yIndex).get(hex.xIndex - 1); if (hex.xIndex % 2 == 0) {
	 * neighbors[4] = hexes.get(hex.yIndex - 1).get(hex.xIndex + 1);
	 * neighbors[5] = hexes.get(hex.yIndex - 1).get(hex.xIndex - 1); } else {
	 * neighbors[4] = hexes.get(hex.yIndex + 1).get(hex.xIndex + 1);
	 * neighbors[5] = hexes.get(hex.yIndex + 1).get(hex.xIndex - 1); } return
	 * neighbors; } else { return new Hexagon[0]; } }
	 */

	private int[][] getNeighborIndices(int x, int y) {
		// System.out.println(x + "," + y);
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

	@Deprecated
	public void zoom(float zoomFactor) {
		this.zoomFactor = zoomFactor;
		ShaderManager.chunkShader.start();
		ShaderManager.chunkShader.setUniform1f("side", EntityManager.side * zoomFactor);
		ShaderManager.chunkShader.setUniform1f("apothem", EntityManager.APOTHEM * zoomFactor);
		ShaderManager.chunkShader.setUniform3f("pos", new Vector3f(-zoomFactor + offsetX, zoomFactor + offsetY, 0));
		ShaderManager.chunkShader.stop();
	}

	@Deprecated
	public void offset(float x, float y) {
		if (offsetX + x > -zoomFactor + 1 && offsetX + x < zoomFactor - 1) {
			offsetX += x;
		} else {
			// System.out.println("toofar");
		}
		if (offsetY + y > -zoomFactor + 1 && offsetY + y < zoomFactor - 1) {
			offsetY += y;
		} else {
			// System.out.println("toofar");
		}
		ShaderManager.chunkShader.start();
		ShaderManager.chunkShader.setUniform3f("pos", new Vector3f(-zoomFactor + offsetX, zoomFactor + offsetY, 0));
		ShaderManager.chunkShader.stop();

	}

	public void render() {
		// ShaderManager.landShader.start();
		// glBindVertexArray(vaoID);
		// glEnableVertexAttribArray(0);
		// glDrawArraysInstanced(GL_TRIANGLE_FAN, 0, 6, HEXESACROSS *
		// HEXESDOWN);
		// glDisableVertexAttribArray(0);
		// glBindVertexArray(0);
		// ShaderManager.landShader.stop();

		//for (int x = 0; x < chunks.length; x++) {
		//	for (Chunk chunk : chunks[x]) {
		//				chunk.setShaderUniforms();
		//				chunk.render();
		//			}
		//		}

		/*int x = (int) (EntityManager.cameraPos.x / 32);
		int y = (int) (EntityManager.cameraPos.y / 32);
		chunks[x][y].render();
		if (x > 0 && y > 0) {
			chunks[x - 1][y].render();
			chunks[x + 1][y].render();
			chunks[x][y - 1].render();
			chunks[x - 1][y - 1].render();
			chunks[x + 1][y - 1].render();
			chunks[x][y + 1].render();
			chunks[x - 1][y + 1].render();
			chunks[x + 1][y + 1].render();
		}*/

		//	chunks[4][3].render();
		//	chunks[5][3].render();
		//	chunks[3][4].render();
		//	chunks[4][4].render();
		//	chunks[5][4].render();
		//	chunks[3][5].render();
		//	chunks[4][5].render();
		//	chunks[5][5].render();
		//

		//int x = (int)(EntityManager.cameraPos.x/32/1.2);
		//int y = (int)(EntityManager.cameraPos.y/32/1.2);
		//System.out.println(x);
		//chunks[x][y].render();

		//		for (int x = 0; x < HEXESACROSS / Chunk.CHUNKSIZE; x++) {
		//			for (int y = 0; y < HEXESDOWN / Chunk.CHUNKSIZE; y++) {
		//				chunks[x][y].render();
		//			}
		//		}

		for(int i=0;i<numberOfChunks;i++){
			chunk[i].render();
		}
	}

	public void update() {

	}

	private double getP(String genType, double p, int iter) {
		// pro tip, these are not all to gen land, some could gen other effects
		switch (genType) {
		case "fractal":
			p = .3 / (Math.log(p + 2));
			break;
		case "soft": //
			p = 0.21 / p;
			break;
		case "stand":
			p = (p + 1) / (p + 3.4);
			break;
		case "disk":
			p = 0.97 * p;
			break;
		case "trig":
			p = Math.cos(1.443 * p);
			break;
		case "quad":
			if (p >= 1) {
				p = 0.15;
			} else {
				p = Math.pow(p, 2) + 2 * p;
			}
			break;
		case "it":
			p -= 0.02;
			break;
		case "lin":
			p -= p * 0.02;
			break;
		case "atic":
			p -= Math.pow(p, 2) * 0.05;
			break;
		case "grit":
			p -= Math.sin(p / 4) / 9;
			break;
		case "exp":
			p -= Math.pow(Math.E, p / 50) - Math.pow(Math.E, 1 / 49);
			break;
		case "ln":
			p -= Math.log(p + 2) / 75;
			break;
		case "root":
			p -= Math.sqrt(p) - 0.6;
			break;
		case "rng":
			p -= (rng.nextDouble() + 1) / 100;
			break;
		case "arm":
			p -= (p / (p + 50));
			break;
		case "invE":
			p = (100 / (Math.pow(Math.E, iter / 400)) * (rng.nextDouble() + 1) / 2);
			break;
		case "inv":
			p = ((5000 / iter) - +(Math.abs(rng.nextDouble())));
			break;
		case "invL":
			p = ((10 / Math.log(iter)) - (Math.abs(rng.nextDouble())));
			break;
		case "invT":
			System.err.println("invalid map type");
			System.exit(-1);
			p = ((Math.pow(Math.cos(iter) * (1.9), 2)) - (1 * Math.abs(rng.nextDouble())) + 0.1);
			break;
		case "e":
			p = Math.pow(Math.E, iter / -(HEXESACROSS * HEXESDOWN / 2));
			break;
		case "e2":
			p = 0.35 * Math.pow(Math.E, iter / (HEXESACROSS * HEXESDOWN / 2));
			break;
		default:
			System.err.println("invalid map type");
			System.exit(-1);
			p = 0;
		}

		return p;
	}

	private void scatter(String type, String genType, int[] seed) {

		ArrayList<int[]> outerLand = new ArrayList<int[]>();

		for (int i = 0; i < seedCount; i++) {
			outerLand.add(seed);
			land[seed[0]][seed[1]] = SEED;
		}
		double p = 1;
		int iter = 0;
		while (outerLand.size() != 0 && p != 0) {
			ArrayList<int[]> newLand = new ArrayList<int[]>();
			for (int i = 0; i < outerLand.size(); i++) {
				int[][] neighbors = getNeighborIndices(outerLand.get(i)[0], outerLand.get(i)[1]);
				for (int j = 0; j < neighbors.length; j++) {

					if (land[neighbors[j][0]][neighbors[j][1]] != LAND
							&& land[neighbors[j][0]][neighbors[j][1]] != SEED) {
						if (rng.nextDouble() <= p) {
							land[neighbors[j][0]][neighbors[j][1]] = LAND;
							// moisture[neighbors[j][0]][neighbors[j][1]] =
							// (int) (p*(20 + 5*rng.nextDouble()));
							// elevation[neighbors[j][0]][neighbors[j][1]] =
							// (int) (p*(20+ 5*rng.nextDouble()));
							iter++;
							newLand.add(neighbors[j]);
						}
					}
				}
			}
			outerLand.clear();
			outerLand.addAll(newLand);
			newLand.clear();
			p = getP(genType, p, iter);

			// System.out.println(p*100);
		}
	}

	private void initializeMap() {
		// INIT MAPTYPE ETC
		//mapType = "e2";
		mapType = maps[rng.nextInt(maps.length)];
		//worldType = "sapric"; //"telilic", "sapric", "worlic"
		worldType = worldTypes[rng.nextInt(worldTypes.length)];
		seedCount = 1;
		seeds = new int[seedCount][2];

		// INIT SEEDS
		System.out.println("Initiailzing Seeds");
		seeds[0][0] = HEXESACROSS / 2;
		seeds[0][1] = HEXESDOWN / 2;

		// INIT LAND ARRAY
		for (int x = 0; x < HEXESACROSS; x++) {
			for (int y = 0; y < HEXESDOWN; y++) {
				land[x][y] = WATER;
				elevation[x][y] = 0;
				moisture[x][y] = 0;
			}
		}

		// GEN LAND
		for (int i = 0; i < seedCount; i++) {
			seed[0] = seeds[i][0];
			seed[1] = seeds[i][1];
		}
		System.out.println("Genning Land");
		scatter("land", mapType, seed);
		// VALUE FOR TROUBLESHOOTING--DONT DELETE
		/*
		 * int[][] neighbors = getNeighborIndices(seeds[0][0], seeds[0][1]); for
		 * (int j = 0; j < neighbors.length; j++) { if
		 * (land[neighbors[j][0]][neighbors[j][1]] != LAND &&
		 * land[neighbors[j][0]][neighbors[j][1]] != SEED) {
		 * land[neighbors[j][0]][neighbors[j][1]] = LAND; } }
		 */

		System.out.println("Map init complete");
		System.out.println(mapType);
		for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				int[][] neighbors = getNeighborIndices(i, j);
				/*if (land[i][j] == SEED) {
					elevation[i][j] = 4;
					moisture[i][j] = 4;
					for (int k = 0; k < neighbors.length; k++) {
						elevation[neighbors[k][0]][neighbors[k][1]] = 20;
						moisture[neighbors[k][0]][neighbors[k][1]] = 20;
					}
				}*/
				if (land[i][j] == LAND) {

					int elev = (int) (Math.abs(noise.getValue(i / ELEVATIONSCALER, j / ELEVATIONSCALER, .1)) * 16);
					int moist = (int) (Math.abs(noise.getValue(i / MOISTURESCALER, j / MOISTURESCALER, .1)) * 16);
					for (int k = 0; k < neighbors.length; k++) {
						elev += elevation[neighbors[k][0]][neighbors[k][1]];
						moist += moisture[neighbors[k][0]][neighbors[k][1]];
					}
					elev = elev / 3 - rng.nextInt(2);
					moist = moist / 3 - rng.nextInt(3);
					//elev = (elev / 5 + (int) (4+4*rng.nextDouble()));//one day this will all be perlin
					//moist = (elev / 5 + (int) (4+4*rng.nextDouble()));
					moisture[i][j] = moist;

					elevation[i][j] = elev;
					land[i][j] = Block.getBlock(elevation[i][j], worldType, moisture[i][j]);
					landCount += 1;
				}
			}
		}

		//does this do anything?
		/*for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				if (land[i][j] == LAND) {
					land[i][j] = Block.getBlock(elevation[i][j], worldType,
							moisture[i][j]);
				}
			}
		}*/
		if (landCount < (HEXESDOWN / 2) * (HEXESACROSS / 2) || landCount > (HEXESDOWN - 5) * (HEXESACROSS - 5)) {
			System.out.println(landCount);
			System.out.println((HEXESDOWN / 2) * (HEXESACROSS / 2));
			System.out.println((HEXESDOWN - 5) * (HEXESACROSS - 5));
			System.out.println("Bad map gen");
			//System.exit(-1);
		}
	}

	public void generateFoliage() {
		for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				if (Block.destinationTraversable(i, j)) {
					switch (worldType) {
					case "telilic":
						if (rng.nextDouble() > 0.99) {
							elevation[i][j] += 30;
							land[i][j] = Block.JG_TREE;
						} else if (rng.nextDouble() > 0.98) {
							land[i][j] = Block.FRUIT_BUSH;
						}
						break;
					case "sapric":
						if (rng.nextDouble() > 0.99) {
							elevation[i][j] += 30;
							land[i][j] = Block.ASH_TREE;
						} else if (rng.nextDouble() > 0.98) {
							land[i][j] = Block.THORN_BUSH;
						}
						break;
					case "worlic":
						if (rng.nextDouble() > 0.99) {
							elevation[i][j] += 30;
							land[i][j] = Block.PALM_TREE;
						} else if (rng.nextDouble() > 0.98) {
							land[i][j] = Block.REEDS;
						}
						break;
					}
					//System.out.println(land[i][j]);
				}
			}
		}
	}
}
