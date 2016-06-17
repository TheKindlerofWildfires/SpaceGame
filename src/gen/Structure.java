package gen;

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
		for (int i = 0; i < WorldType.interference.size(); i++) {
			ruins[i] = WorldType.interference.get(i);
			switch (ruins[i]) {
			case "octividRuins1":
				octividRuins1(x, y, z);
				break;
			case "kinikariRuins1":
				kinikariRuins1();
				break;
			case "jeneunGates1":
				jeneunGates1();
				break;
			case "jeneunRuins1":
				jeneunRuins1();
				break;
			}
		}
	}

	private static void jeneunRuins1() {
		// TODO Auto-generated method stub

	}

	private static void jeneunGates1() {
		// TODO Auto-generated method stub

	}

	private static void kinikariRuins1() {
		// TODO Auto-generated method stub

	}

	public static void octividRuins1(int cx, int cy, int cz) {
		// this is just me being really lazy
		// should prolly be in txt file imported from blender or some shit
		sizeX = 9;
		sizeY = 7;
		sizeZ = 7;
		if (cx + sizeX < Map.HEXESACROSS && cy + sizeY < Map.HEXESDOWN
				&& cz + sizeZ < Map.WORLDHEIGHT) {
			for (int z = cz; z < cz + 5; z++) {
				WorldGenerator.data[cx + 5][cy + 1][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 4][cy + 1][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 6][cy + 1][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 3][cy + 2][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 7][cy + 2][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 3][cy + 1][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 7][cy + 1][z] = Block.OCTOWALL;

				WorldGenerator.data[cx + 2][cy + 2][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 8][cy + 2][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 2][cy + 3][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 8][cy + 3][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 2][cy + 4][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 8][cy + 4][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 2][cy + 5][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 8][cy + 5][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 1][cy + 4][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 9][cy + 4][z] = Block.OCTOWALL;

				WorldGenerator.data[cx + 3][cy + 7][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 7][cy + 7][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 5][cy + 7][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 4][cy + 6][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 6][cy + 6][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 3][cy + 6][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 7][cy + 6][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 3][cy + 6][z] = Block.OCTOWALL;
				WorldGenerator.data[cx + 7][cy + 6][z] = Block.OCTOWALL;
			}
			WorldGenerator.data[cx + 6][cy + 5][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 6][cy + 2][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 7][cy + 5][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 7][cy + 4][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 7][cy + 3][cz + 5] = Block.OCTOROOF;

			WorldGenerator.data[cx + 5][cy + 6][cz + 5] = Block.OCTOROOF;

			WorldGenerator.data[cx + 4][cy + 2][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 3][cy + 5][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 3][cy + 4][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 3][cy + 3][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 5][cy + 2][cz + 5] = Block.OCTOROOF;
			WorldGenerator.data[cx + 4][cy + 5][cz + 5] = Block.OCTOROOF;

			WorldGenerator.data[cx + 6][cy + 4][cz + 6] = Block.OCTOROOF;
			WorldGenerator.data[cx + 6][cy + 3][cz + 6] = Block.OCTOROOF;
			WorldGenerator.data[cx + 5][cy + 3][cz + 6] = Block.OCTOROOF;
			WorldGenerator.data[cx + 4][cy + 4][cz + 6] = Block.OCTOROOF;
			WorldGenerator.data[cx + 4][cy + 3][cz + 6] = Block.OCTOROOF;
			WorldGenerator.data[cx + 5][cy + 5][cz + 6] = Block.OCTOROOF;

			WorldGenerator.data[cx + 5][cy + 4][cz + 7] = Block.OCTOROOF;

			for(int q = cx; q<cx+Structure.sizeX;q++){
				for(int w = cy; w<cy+Structure.sizeY;w++){
					for(int e = 0; e<cz;e++){
						WorldGenerator.data[q][w][e] = 1;
					}
				}
			}
		}
	}
}
