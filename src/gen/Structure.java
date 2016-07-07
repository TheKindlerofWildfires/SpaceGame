package gen;

import classesSimonDoesntLike.Chunk;
import gameEngine.Block;
import gameEngine.Map;
import gameEngine.WorldGenerator;
import gameEngine.WorldType;

public class Structure {
	static String[] ruins = new String[WorldType.interference.size()];
	private String worldType = Map.worldType;
	public static int sizeX = 0;
	public static int sizeY = 0;
	public static int sizeZ = 0;

	public static void gen(int x, int y, int z) {
		// this code is bad i think, should be single type per use
		for (int i = 0; i < WorldType.interference.size(); i++) {
			ruins[i] = WorldType.interference.get(i);
			// x = Map.rng.nextInt(Map.HEXESACROSS);
			// y = Map.rng.nextInt(Map.HEXESDOWN);
			// z = Map.rng.nextInt(WorldGenerator.HEIGHT);
			z = getTallest(x, y);
			switch (ruins[i]) {
			case "octividRuinsLeast":
				// octividRuinsLeast(x, y, z);
				jeneunGateLeast(x, y, z);
				break;
			case "kinikariRuinsLeast":
				// kinikariRuinsLeast(x, y, z);
				jeneunGateLeast(x, y, z);
				break;
			case "jeneunGateLeast":
				jeneunGateLeast(x, y, z);
				break;
			case "jeneunRuinsLeast":
				// jeneunRuinsLeast(x, y, z);
				jeneunGateLeast(x, y, z);
				break;
			}

		}
	}

	private static int getTallest(int x, int y) {
		int tall = 0;
		for (int q = x; q < x + sizeX; q++) {
			for (int w = y; w < y + sizeY; w++) {
				if (WorldGenerator.inBounds(q, w, 1)) {
					if (WorldGenerator.eTracker[q][w] > tall) {
						tall = WorldGenerator.eTracker[q][w];
					}
				}
			}
		}
		return tall;
	}

	private static void clear(int cx, int cy, int cz) {
		// this "function" may do literally jack shit
		for (int q = cx; q < cx + sizeX; q++) {
			for (int w = cy; w < cy + sizeY; w++) {
				for (int e = cz; e < Map.WORLDHEIGHT; e++) {
					if (WorldGenerator.inBounds(q, w, e)) {
						WorldGenerator.data[q][w][e] = Block.AIR;
					}
				}
			}
		}
		// works
		for (int q = cx; q < cx + sizeX; q++) {
			for (int w = cy; w < cy + sizeY; w++) {
				for (int e = 0; e < cz; e++) {
					if (WorldGenerator.inBounds(q, w, e)) {
						WorldGenerator.data[q][w][e] = Block.setBlock(e,
								WorldGenerator.mTracker[q][w]);

					}
				}
			}
		}
	}

	private static void kinikariRuinsLeast(int cx, int cy, int cz) {
		sizeX = 11;
		sizeY = 6;
		sizeZ = 10;

		if (cx + sizeX < Map.HEXESACROSS && cy + sizeY < Map.HEXESDOWN
				&& cz + sizeZ < Map.WORLDHEIGHT && cx % 2 == 0) {
			clear(cx, cy, cz);
			for (int z = cz; z < cz + 5; z++) {
				WorldGenerator.data[cx + 5][cy][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 4][cy][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 6][cy][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 3][cy + 1][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 7][cy + 1][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 2][cy + 1][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 8][cy + 1][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 1][cy + 2][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 9][cy + 2][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx][cy + 2][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 10][cy + 2][z] = Block.KINIKARI_WALL;

				WorldGenerator.data[cx + 1][cy + 3][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 9][cy + 3][z] = Block.KINIKARI_WALL;

				WorldGenerator.data[cx + 3][cy + 3][z] = Block.KINIKARI_WALL;
				WorldGenerator.data[cx + 7][cy + 3][z] = Block.KINIKARI_WALL;

				WorldGenerator.data[cx + 5][cy + 3][z] = Block.KINIKARI_WALL;
			}

			WorldGenerator.data[cx + 5][cy + 3][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 5][cy + 2][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 5][cy + 1][cz + 4] = Block.KINIKARI_WALL;

			WorldGenerator.data[cx + 4][cy + 2][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 4][cy + 1][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 3][cy + 3][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 3][cy + 2][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 2][cy + 2][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 1][cy + 3][cz + 4] = Block.KINIKARI_WALL;

			WorldGenerator.data[cx + 6][cy + 2][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 6][cy + 1][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 7][cy + 3][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 7][cy + 2][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 8][cy + 2][cz + 4] = Block.KINIKARI_WALL;
			WorldGenerator.data[cx + 9][cy + 3][cz + 4] = Block.KINIKARI_WALL;

			WorldGenerator.data[cx + 5][cy + 4][cz + 2] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 4][cz + 2] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 4][cz + 2] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 4][cz + 2] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 4][cz + 2] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 5][cz + 3] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 5][cz + 3] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 5][cz + 3] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 5][cz + 3] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 5][cz + 3] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 4][cz + 3] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 4][cz + 3] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 4][cz + 3] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 4][cz + 3] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 4][cz + 3] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 5][cz + 4] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 5][cz + 4] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 5][cz + 4] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 5][cz + 4] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 5][cz + 4] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 5][cz + 5] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 5][cz + 5] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 5][cz + 5] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 5][cz + 5] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 5][cz + 5] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 4][cz + 5] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 4][cz + 5] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 4][cz + 5] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 4][cz + 5] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 4][cz + 5] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 4][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 4][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 4][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 4][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 4][cz + 6] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 3][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 3][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 3][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 3][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 3][cz + 6] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 3][cz + 7] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 3][cz + 7] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 3][cz + 7] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 3][cz + 7] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 3][cz + 7] = Block.KINIKARI_ROOF;

			WorldGenerator.data[cx + 5][cy + 2][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 3][cy + 2][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 7][cy + 2][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 1][cy + 2][cz + 6] = Block.KINIKARI_ROOF;
			WorldGenerator.data[cx + 9][cy + 2][cz + 6] = Block.KINIKARI_ROOF;

		}

	}

	private static void jeneunGateLeast(int cx, int cy, int cz) {
		sizeX = 10;
		sizeY = 10;
		sizeZ = 10;
		if (cx + sizeX < Map.HEXESACROSS && cy + sizeY < Map.HEXESDOWN
				&& cz + sizeZ < Map.WORLDHEIGHT && cx % 2 == 0) {
			clear(cx, cy, cz);
			for (int x = cx; x < cx + 11; x++) {
				for (int y = cy; y < cy + 10; y++) {
					WorldGenerator.data[x][y][cz] = Block.JENEUN_WALL;
				}
			}
		}

	}

	private static void jeneunRuinsLeast(int cx, int cy, int cz) {
		sizeX = 10;
		sizeY = 10;
		sizeZ = 10;

		if (cx + sizeX < Map.HEXESACROSS && cy + sizeY < Map.HEXESDOWN
				&& cz + sizeZ < Map.WORLDHEIGHT && cx % 2 == 0) {
			clear(cx, cy, cz);
			for (int x = cx; x < cx + 11; x++) {
				for (int y = cy; y < cy + 10; y++) {
					WorldGenerator.data[x][y][cz + 5] = Block.JENEUN_WALL;
				}
			}
			for (int z = cz; z < cz + 5; z++) {

				WorldGenerator.data[cx + 2][cy][z] = Block.JENEUN_WALL;
				WorldGenerator.data[cx + 4][cy][z] = Block.JENEUN_WALL;
				WorldGenerator.data[cx + 6][cy][z] = Block.JENEUN_WALL;
				WorldGenerator.data[cx + 8][cy][z] = Block.JENEUN_WALL;

				WorldGenerator.data[cx + 2][cy + 8][z] = Block.JENEUN_WALL;
				WorldGenerator.data[cx + 4][cy + 8][z] = Block.JENEUN_WALL;
				WorldGenerator.data[cx + 6][cy + 8][z] = Block.JENEUN_WALL;
				WorldGenerator.data[cx + 8][cy + 8][z] = Block.JENEUN_WALL;
				for (int y = cy; y < cy + 9;) {
					WorldGenerator.data[cx][y][z] = Block.JENEUN_WALL;
					WorldGenerator.data[cx + 10][y][z] = Block.JENEUN_WALL;
					y += 2;
				}
			}
			// TOP PART
			for (int z = cz + 5; z < cz + 10; z++) {
				WorldGenerator.data[cx + 4][cy + 2][z] = Block.JENEUN_ROOF;
				WorldGenerator.data[cx + 6][cy + 2][z] = Block.JENEUN_ROOF;

				WorldGenerator.data[cx + 4][cy + 6][z] = Block.JENEUN_ROOF;
				WorldGenerator.data[cx + 6][cy + 6][z] = Block.JENEUN_ROOF;
				for (int y = cy + 2; y < cy + 7;) {
					WorldGenerator.data[cx + 2][y][z] = Block.JENEUN_ROOF;
					WorldGenerator.data[cx + 8][y][z] = Block.JENEUN_ROOF;
					y += 2;
				}
			}

			for (int x = cx; x < cx + 7; x++) {
				for (int y = cy; y < cy + 6; y++) {
					WorldGenerator.data[x + 2][y + 2][cz + 10] = Block.JENEUN_ROOF;
				}
			}

		}

	}

	public static void octividRuinsLeast(int cx, int cy, int cz) {
		// this is just me being really lazy
		// should prolly be in txt file imported from blender or some shit
		sizeX = 9;
		sizeY = 7;
		sizeZ = 7;

		if (cx + sizeX < Map.HEXESACROSS && cy + sizeY < Map.HEXESDOWN
				&& cz + sizeZ < Map.WORLDHEIGHT && cx % 2 == 0) {
			clear(cx, cy, cz);
			for (int z = cz; z < cz + 5; z++) {
				WorldGenerator.data[cx + 5][cy + 1][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 4][cy + 1][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 6][cy + 1][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 3][cy + 2][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 7][cy + 2][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 3][cy + 1][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 7][cy + 1][z] = Block.OCTO_WALL;

				WorldGenerator.data[cx + 2][cy + 2][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 8][cy + 2][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 2][cy + 3][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 8][cy + 3][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 2][cy + 4][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 8][cy + 4][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 2][cy + 5][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 8][cy + 5][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 1][cy + 4][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 9][cy + 4][z] = Block.OCTO_WALL;

				WorldGenerator.data[cx + 3][cy + 7][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 7][cy + 7][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 5][cy + 7][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 4][cy + 6][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 6][cy + 6][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 3][cy + 6][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 7][cy + 6][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 3][cy + 6][z] = Block.OCTO_WALL;
				WorldGenerator.data[cx + 7][cy + 6][z] = Block.OCTO_WALL;
			}
			WorldGenerator.data[cx + 6][cy + 5][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 6][cy + 2][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 7][cy + 5][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 7][cy + 4][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 7][cy + 3][cz + 5] = Block.OCTO_ROOF;

			WorldGenerator.data[cx + 5][cy + 6][cz + 5] = Block.OCTO_ROOF;

			WorldGenerator.data[cx + 4][cy + 2][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 3][cy + 5][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 3][cy + 4][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 3][cy + 3][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 5][cy + 2][cz + 5] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 4][cy + 5][cz + 5] = Block.OCTO_ROOF;

			WorldGenerator.data[cx + 6][cy + 4][cz + 6] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 6][cy + 3][cz + 6] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 5][cy + 3][cz + 6] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 4][cy + 4][cz + 6] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 4][cy + 3][cz + 6] = Block.OCTO_ROOF;
			WorldGenerator.data[cx + 5][cy + 5][cz + 6] = Block.OCTO_ROOF;

			WorldGenerator.data[cx + 5][cy + 4][cz + 7] = Block.OCTO_ROOF;

		}
	}
}
