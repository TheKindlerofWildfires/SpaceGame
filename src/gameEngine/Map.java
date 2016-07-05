package gameEngine;

import graphicEngine.Chunk;
import graphicEngine.ShaderManager;

import java.util.ArrayList;
import java.util.Random;

import maths.Distance;
import maths.Vector3f;
import noiseLibrary.module.source.Perlin;

public class Map {

	int numberOfChunks = HEXESACROSS * HEXESDOWN / Chunk.CHUNKSIZE
			/ Chunk.CHUNKSIZE;
	Chunk[] chunk = new Chunk[numberOfChunks];

	public static final int HEXESACROSS = 128;
	public static final int HEXESDOWN = 128;
	public static final int CHUNKSACROSS = HEXESACROSS / Chunk.CHUNKSIZE;
	public static final int CHUNKSDOWN = HEXESDOWN / Chunk.CHUNKSIZE;
	public static final int WORLDHEIGHT = 64;


	public static String mapType;
	public static String worldType;
	public int seedCount;
	public int landCount;
	public static Map gameMap;

	Distance distance;
	ShaderManager shaderManager;

	public static Random rng = new Random();

	public Chunk[][] chunks = new Chunk[HEXESACROSS / Chunk.CHUNKSIZE][HEXESDOWN
			/ Chunk.CHUNKSIZE];

	public static final String[] maps = { "fractal", "soft", "stand", "trig",
			"quad", "e2", "e" };
	public static final String[] splots = { "grit", "exp", "ln", "rng", "arm",
			"disk" };// there are
						// more
	public static final String[] worldTypes = { "telilic", "sapric", "worlic" };
	public static int[][][] land = new int[HEXESACROSS][HEXESDOWN][WORLDHEIGHT];
	// public static int[][] elevation = new int[HEXESACROSS + 1][HEXESDOWN +
	// 1];

	public static int[][] moisture = new int[HEXESACROSS + 1][HEXESDOWN + 1];

	public int[][] seeds;
	public int[] seed = new int[2];

	private float offsetX = 0;
	private float offsetY = 0;
	private float zoomFactor;
	public static Perlin noise = new Perlin();

	float[] data = new float[Chunk.CHUNKSIZE * Chunk.CHUNKSIZE * WORLDHEIGHT];

	public Map() {
		
		worldType = WorldType.worldType; 
		
		distance = new Distance();
		long seed = rng.nextLong();
		rng.setSeed(seed);
		noise.setSeed((int) seed);

		System.out.println("Random Seed is " + seed);

		land = new WorldGenerator().generate();


		for (int i = 0; i < CHUNKSACROSS; i++) {
			for (int j = 0; j < CHUNKSDOWN; j++) {
				chunk[i * CHUNKSDOWN + j] = new Chunk(convertLand(land, i, j),
						i, j);
			}
		}
	}

	public int[][][] convertLand(int[][][] input, int cx, int cy) {
		int[][][] output = new int[Chunk.CHUNKSIZE][Chunk.CHUNKSIZE][Chunk.CHUNKHEIGHT];//flag
		for (int x = cx * Chunk.CHUNKSIZE; x < (cx + 1) * Chunk.CHUNKSIZE; x++) {
			for (int y = cy * Chunk.CHUNKSIZE; y < (cy + 1) * Chunk.CHUNKSIZE; y++) {
				for (int z = 0; z < Chunk.CHUNKHEIGHT; z++) {//flag
					// System.out.println(x);
					output[x - cx * Chunk.CHUNKSIZE][y - cy * Chunk.CHUNKSIZE][z] = input[x][y][z];
				}
			}
		}
		return output;
	}





	@Deprecated
	public void zoom(float zoomFactor) {
		this.zoomFactor = zoomFactor;
		ShaderManager.chunkShader.start();
		// ShaderManager.chunkShader.setUniform1f("side", EntityManager.side
		// * zoomFactor);
		// ShaderManager.chunkShader.setUniform1f("apothem",
		// EntityManager.APOTHEM
		// * zoomFactor);
		ShaderManager.chunkShader.setUniform3f("pos", new Vector3f(-zoomFactor
				+ offsetX, zoomFactor + offsetY, 0));
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
		ShaderManager.chunkShader.setUniform3f("pos", new Vector3f(-zoomFactor
				+ offsetX, zoomFactor + offsetY, 0));
		ShaderManager.chunkShader.stop();
	}

	public void render() {
		int counter = 0;
		for (int i = 0; i < numberOfChunks; i++) {
			if (EntityManager.camera.getFrustum().boxIsInside(
					chunk[i].boundingBox)) {
				chunk[i].render();
				counter++;
			}
		}
	}

	public void update() {

	}

	@Deprecated
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
			p = ((Math.pow(Math.cos(iter) * (1.9), 2))
					- (1 * Math.abs(rng.nextDouble())) + 0.1);
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

	@Deprecated
	private void scatter(String type, String genType, int[] seed) {

		ArrayList<int[]> outerLand = new ArrayList<int[]>();

		for (int i = 0; i < seedCount; i++) {
			outerLand.add(seed);
			land[seed[0]][seed[1]][0] = Block.SEED;
		}
		double p = 1;
		int iter = 0;
		while (outerLand.size() != 0 && p != 0) {
			ArrayList<int[]> newLand = new ArrayList<int[]>();
			for (int i = 0; i < outerLand.size(); i++) {
				int[][] neighbors = getNeighborIndices(outerLand.get(i)[0],
						outerLand.get(i)[1]);
				for (int j = 0; j < neighbors.length; j++) {

					if (land[neighbors[j][0]][neighbors[j][1]][0] != Block.LAND
							&& land[neighbors[j][0]][neighbors[j][1]][0] != Block.SEED) {
						if (rng.nextDouble() <= p) {
							land[neighbors[j][0]][neighbors[j][1]][0] = Block.LAND;
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

	@Deprecated
	private int[][] getNeighborIndices(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	private void initializeMap() {
		// INIT MAPTYPE ETC
		// mapType = "e2";
		mapType = maps[rng.nextInt(maps.length)];
		seedCount = 1;
		seeds = new int[seedCount][2];
		// INIT SEEDS
		System.out.println("Initiailzing Seeds");
		seeds[0][0] = HEXESACROSS / 2;
		seeds[0][1] = HEXESDOWN / 2;
		// INIT LAND ARRAY
		for (int x = 0; x < HEXESACROSS; x++) {
			for (int y = 0; y < HEXESDOWN; y++) {
				land[x][y][0] = Block.WATER;

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
				/*
				 * if (land[i][j] == SEED) { elevation[i][j] = 4; moisture[i][j]
				 * = 4; for (int k = 0; k < neighbors.length; k++) {
				 * elevation[neighbors[k][0]][neighbors[k][1]] = 20;
				 * moisture[neighbors[k][0]][neighbors[k][1]] = 20; } }
				 */
				if (land[i][j][0] == Block.LAND) {
					for (int k = 0; k < neighbors.length; k++) {

					// land[i][j] = Block.getBlock(elevation[i][j], worldType,
					// moisture[i][j]);
					landCount += 1;
				}
			}
		}

		// does this do anything?
		/*
		 * for (int i = 0; i < HEXESACROSS; i++) { for (int j = 0; j <
		 * HEXESDOWN; j++) { if (land[i][j] == LAND) { land[i][j] =
		 * Block.getBlock(elevation[i][j], worldType, moisture[i][j]); } } }
		 */
		if (landCount < (HEXESDOWN / 2) * (HEXESACROSS / 2)
				|| landCount > (HEXESDOWN - 5) * (HEXESACROSS - 5)) {
			System.out.println(landCount);
			System.out.println((HEXESDOWN / 2) * (HEXESACROSS / 2));
			System.out.println((HEXESDOWN - 5) * (HEXESACROSS - 5));
			System.out.println("Bad map gen");
			// System.exit(-1);
		}
	}

	/*
	 * private void initializeMap() { // INIT MAPTYPE ETC // mapType = "e2";
	 * mapType = maps[rng.nextInt(maps.length)]; // worldType = "sapric";
	 * //"telilic", "sapric", "worlic" worldType =
	 * worldTypes[rng.nextInt(worldTypes.length)]; seedCount = 1; seeds = new
	 * int[seedCount][2];
	 * 
	 * // INIT SEEDS System.out.println("Initiailzing Seeds"); seeds[0][0] =
	 * HEXESACROSS / 2; seeds[0][1] = HEXESDOWN / 2;
	 * 
	 * // INIT LAND ARRAY for (int x = 0; x < HEXESACROSS; x++) { for (int y =
	 * 0; y < HEXESDOWN; y++) { land[x][y] = WATER; elevation[x][y] = 0;
	 * moisture[x][y] = 0; } }
	 * 
	 * // GEN LAND for (int i = 0; i < seedCount; i++) { seed[0] = seeds[i][0];
	 * seed[1] = seeds[i][1]; } System.out.println("Genning Land");
	 * scatter("land", mapType, seed); // VALUE FOR TROUBLESHOOTING--DONT DELETE
	 * /* int[][] neighbors = getNeighborIndices(seeds[0][0], seeds[0][1]); for
	 * (int j = 0; j < neighbors.length; j++) { if
	 * (land[neighbors[j][0]][neighbors[j][1]] != LAND &&
	 * land[neighbors[j][0]][neighbors[j][1]] != SEED) {
	 * land[neighbors[j][0]][neighbors[j][1]] = LAND; } }
	 * 
	 * 
	 * System.out.println("Map init complete"); System.out.println(mapType); for
	 * (int i = 0; i < HEXESACROSS; i++) { for (int j = 0; j < HEXESDOWN; j++) {
	 * int[][] neighbors = getNeighborIndices(i, j); /* if (land[i][j] == SEED)
	 * { elevation[i][j] = 4; moisture[i][j] = 4; for (int k = 0; k <
	 * neighbors.length; k++) { elevation[neighbors[k][0]][neighbors[k][1]] =
	 * 20; moisture[neighbors[k][0]][neighbors[k][1]] = 20; } }
	 * 
	 * if (land[i][j] == LAND) {
	 * 
	 * int elev = (int) (Math.abs( noise.getValue(i / ELEVATIONSCALER, j /
	 * ELEVATIONSCALER, .1)) * 16); int moist = (int) (Math
	 * .abs(noise.getValue(i / MOISTURESCALER, j / MOISTURESCALER, .1)) * 16);
	 * for (int k = 0; k < neighbors.length; k++) { elev +=
	 * elevation[neighbors[k][0]][neighbors[k][1]]; moist +=
	 * moisture[neighbors[k][0]][neighbors[k][1]]; } elev = elev / 3 -
	 * rng.nextInt(2); moist = moist / 3 - rng.nextInt(3); // elev = (elev / 5 +
	 * (int) (4+4*rng.nextDouble()));//one day this will all be // perlin //
	 * moist = (elev / 5 + (int) (4+4*rng.nextDouble())); moisture[i][j] =
	 * moist;
	 * 
	 * elevation[i][j] = elev; land[i][j] = Block.getBlock(elevation[i][j],
	 * worldType, moisture[i][j]); landCount += 1; } } }
	 * 
	 * // does this do anything? /* for (int i = 0; i < HEXESACROSS; i++) { for
	 * (int j = 0; j < HEXESDOWN; j++) { if (land[i][j] == LAND) { land[i][j] =
	 * Block.getBlock(elevation[i][j], worldType, moisture[i][j]); } } }
	 * 
	 * if (landCount < (HEXESDOWN / 2) * (HEXESACROSS / 2) || landCount >
	 * (HEXESDOWN - 5) * (HEXESACROSS - 5)) { System.out.println(landCount);
	 * System.out.println((HEXESDOWN / 2) * (HEXESACROSS / 2));
	 * System.out.println((HEXESDOWN - 5) * (HEXESACROSS - 5));
	 * System.out.println("Bad map gen"); // System.exit(-1); } }
	 * 
	 * public void generateFoliage() { for (int i = 0; i < HEXESACROSS; i++) {
	 * for (int j = 0; j < HEXESDOWN; j++) { if (Block.destinationTraversable(i,
	 * j)) { switch (worldType) { case "telilic": if (rng.nextDouble() > 0.99) {
	 * elevation[i][j] += 30; land[i][j] = Block.JG_TREE; } else if
	 * (rng.nextDouble() > 0.98) { land[i][j] = Block.FRUIT_BUSH; } break; case
	 * "sapric": if (rng.nextDouble() > 0.99) { elevation[i][j] += 30;
	 * land[i][j] = Block.ASH_TREE; } else if (rng.nextDouble() > 0.98) {
	 * land[i][j] = Block.THORN_BUSH; } break; case "worlic": if
	 * (rng.nextDouble() > 0.99) { elevation[i][j] += 30; land[i][j] =
	 * Block.PALM_TREE; } else if (rng.nextDouble() > 0.98) { land[i][j] =
	 * Block.REEDS; } break; } // System.out.println(land[i][j]); } } } }
	 */
}
}