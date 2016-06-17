package gameEngine;

import gen.Plant;
import gen.Structure;
import graphicEngine.Chunk;

import java.util.ArrayList;
import java.util.Random;

import maths.Utilities;

public class WorldGenerator {
	public static final double HEIGHT = Chunk.CHUNKHEIGHT/2;
	private static final double ELEVATIONSCALER = 50;
	public static final int WATERLEVEL = Chunk.CHUNKHEIGHT/5;
	private static final int MOISTURESCALER = 20;
	public static String mapType;
	public static String worldType;
	public static int seedCount;
	int[][] seeds;
	int[] seed = new int[3];
	int[][] eTracker = new int[Map.HEXESACROSS][Map.HEXESDOWN];
	int[][] mTracker = new int[Map.HEXESACROSS][Map.HEXESDOWN];
	public static int[][][] data = new int[Map.HEXESACROSS][Map.HEXESDOWN][Map.WORLDHEIGHT];
	Random rng = Map.rng;

	public WorldGenerator() {
		new WorldType();
		Map.noise.setFrequency(1);
		Map.noise.setLacunarity(2);
		Map.noise.setOctaveCount(30);
	}

	public int[][][] generate() {
		for (int y = 0; y < Map.HEXESDOWN; y++) {
			for (int x = 0; x < Map.HEXESACROSS; x++) {
				elevate(x, y);
				fill(x, y);
				moisturize(x, y);
				foliate(x, y);
				block(x, y);

			}
		}
		new Structure();
		return data;
	}

	/*
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
	 * Block.REEDS; } break; } // System.out.println(land[i][j]); } } }
	 */
	private void foliate(int x, int y) {
		if (data[x][y][eTracker[x][y]] == 1) {
			if (rng.nextDouble() > 0.99) {
				Plant.tree(x, y, eTracker[x][y]);
			} else if (rng.nextDouble() > 0.98) {
				Plant.bush(x, y, eTracker[x][y]);
			}
		}

	}

	private void block(int x, int y) {
		for (int z = 0; z < Map.WORLDHEIGHT; z++) {
			if (data[x][y][z] == 1) {
				data[x][y][z] = Block.setBlock(eTracker[x][y], mTracker[x][y]);
			}
		}

	}

	private void moisturize(int x, int y) {
		int moist = (int) (Math.abs(Map.noise.getValue(x / MOISTURESCALER, y
				/ MOISTURESCALER, 0.1)) * HEIGHT);
		mTracker[x][y] = moist;

	}

	private void fill(int x, int y) {
		data[x][y][eTracker[x][y]] = 1;
		for (int z = 0; z < Map.WORLDHEIGHT; z++) {
			if (data[x][y][z] == 0 && z < (eTracker[x][y])) {
				data[x][y][z] = 1;
			}
			if (data[x][y][z] == 1 && z < (eTracker[x][y]-4)) {
				data[x][y][z] = 8;
			}
			if (eTracker[x][y] < WATERLEVEL) {
				if (data[x][y][z] == 0 && z < WATERLEVEL) {
					data[x][y][z] = Block.WATER;
				}
			} else {

			}
		}

	}

	private void elevate(int x, int y) {
		int elev = (int) (Math.abs(Map.noise.getValue(x / ELEVATIONSCALER, y
				/ ELEVATIONSCALER, 0.1)) * HEIGHT);
		// data[x][y][elev] = 1;
		eTracker[x][y] = elev;

	}

	public int[][][] badbadbad() {
		// Map.noise.getValue(double, double , 0.1);
		seedCount = 1;
		seeds = new int[seedCount][3];
		ArrayList<int[]> outerLand = new ArrayList<int[]>();

		seeds[0][0] = Map.HEXESACROSS / 2;
		seeds[0][1] = Map.HEXESDOWN / 2;
		seeds[0][2] = Map.WORLDHEIGHT / 2;

		for (int i = 0; i < seedCount; i++) {
			outerLand.add(seed);
			data[seed[0]][seed[1]][seed[2]] = Block.SEED;
		}
		for (int i = 0; i < seedCount; i++) {
			seed[0] = seeds[i][0];
			seed[1] = seeds[i][1];
			seed[2] = seeds[i][2];
		}

		double p = 1;
		while (outerLand.size() != 0 && p != 0) {
			ArrayList<int[]> newLand = new ArrayList<int[]>();
			for (int i = 0; i < outerLand.size(); i++) {
				int[][] neighbors = Utilities.getNeighborIndices3(
						outerLand.get(i)[0], outerLand.get(i)[1],
						outerLand.get(i)[2]);
				for (int j = 0; j < neighbors.length; j++) {
					if (data[neighbors[j][0]][neighbors[j][1]][0] != Block.LAND
							&& data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] != Block.SEED) {
						if (true) {
							data[neighbors[j][0]][neighbors[j][1]][0] = 1;
							newLand.add(neighbors[j]);
						}
					}
				}
			}
		}
		return data;
	}

}
