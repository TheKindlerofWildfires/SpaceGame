package gameEngine;

import java.util.ArrayList;

import maths.Utilities;
import graphicEngine.Chunk;

public class Plant {

	public static void tree(int x, int y, int z) {
		switch (Map.worldType) {
		case "telilic":
			jungleTree(x, y, z);
			break;
		case "sapric":
			ashTree(x, y, z);
			break;
		case "worlic":
			palmTree(x, y, z);
			break;
		}
	}

	public static void bush(int x, int y, int z) {
		switch (Map.worldType) {
		case "telilic":
			fruitBush(x, y, z);
			break;
		case "sapric":
			thornBush(x, y, z);
			break;
		case "worlic":
			reed(x, y, z);
			break;
		}

	}

	private static void reed(int x, int y, int z) {
		if (z > WorldGenerator.WATERLEVEL) {
			WorldGenerator.data[x][y][z + 1] = Block.REEDS;
			WorldGenerator.data[x][y][z + 2] = Block.REEDS;
		}
	}

	private static void thornBush(int x, int y, int z) {
		if (z > WorldGenerator.WATERLEVEL) {
			WorldGenerator.data[x][y][z + 1] = Block.THORN_BUSH;
			WorldGenerator.data[x][y][z + 2] = Block.THORN_BUSH;
		}

	}

	private static void fruitBush(int x, int y, int z) {
		if (z > WorldGenerator.WATERLEVEL) {
			WorldGenerator.data[x][y][z + 1] = Block.FRUIT_BUSH;
			WorldGenerator.data[x][y][z + 2] = Block.FRUIT_BUSH;
		}

	}

	private static void jungleTree(int x, int y, int z) {
		
		
		ArrayList<int[]> branchCore = new ArrayList<int[]>();
		
		
		ArrayList<int[][]> leaves1 = new ArrayList<int[][]>();
		ArrayList<int[][]> leaves2 = new ArrayList<int[][]>();
		int[][] neighbors = Utilities.getNeighborIndices2(x, y, 1);
		int[][] neighbors2 = null;

		// TOP LEVEL
		for (int q = 0; q < neighbors.length; q++) {
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][15] = Block.JG_LEAF;
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][14] = Block.JG_LEAF;
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][13] = Block.JG_LEAF;
			leaves1.add(neighbors);
			if(Map.rng.nextDouble()>0.5){
				WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][13] = Block.JG_TREE;
				int [] branch = new int[2];
				branch[0] = neighbors[q][0];
				branch[1] = neighbors[q][1];
				branchCore.add(branch);
			}

		}
		//THIS IS HARD TO SEE
		for(int l = 0; l <branchCore.size(); l++){
			int[][] b = Utilities.getNeighborIndices2(branchCore.get(l)[0], branchCore.get(l)[1], 1);
			for(int q = 0; q<b.length; q++){
				if(Map.rng.nextDouble()>0.5){
					WorldGenerator.data[b[q][0]][b[q][1]][13] = Block.JG_TREE;
					leaves1.add(b);
				}
			}
		}
		// SECOND LEVEL
		for (int l = 0; l < leaves1.size(); l++) {
			for (int q = 0; q < neighbors.length; q++) {
				neighbors2 = Utilities.getNeighborIndices2(
						leaves1.get(l)[q][0], leaves1.get(l)[q][1], 1);
				for (int e = 0; e < neighbors2.length; e++) {
					WorldGenerator.data[neighbors2[e][0]][neighbors2[e][1]][14] = Block.JG_LEAF;
					WorldGenerator.data[neighbors2[e][0]][neighbors2[e][1]][13] = Block.JG_LEAF;
					leaves2.add(neighbors2);
				}
			}

		}
		// THIRD LEVEL
		for (int l = 0; l < leaves2.size(); l++) {
			for (int q = 0; q < neighbors2.length; q++) {
				int[][] neighbors3 = Utilities.getNeighborIndices2(
						leaves2.get(l)[q][0], leaves2.get(l)[q][1], 1);
				for (int e = 0; e < neighbors3.length; e++) {
					WorldGenerator.data[neighbors3[e][0]][neighbors3[e][1]][13] = Block.JG_LEAF;
				}
			}

		}

		// OPTIMIZE ME PLS
		for (int h = z; h < Chunk.CHUNKHEIGHT; h++) {
			WorldGenerator.data[x][y][h] = Block.JG_TREE;

		}
	}

	private static void palmTree(int x, int y, int z) {
		for (int h = z; h < Chunk.CHUNKHEIGHT; h++) {
			WorldGenerator.data[x][y][h] = Block.PALM_TREE;
			ArrayList<int[][]> leaves1 = new ArrayList<int[][]>();
			int[][] neighbors = Utilities.getNeighborIndices2(x, y, 1);

			// TOP LEVEL

			for (int q = 0; q < neighbors.length; q++) {
				WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][15] = Block.PALM_LEAF;
				WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][14] = Block.PALM_LEAF;
				leaves1.add(neighbors);

			}
			// SECOND LEVEL
			for (int l = 0; l < leaves1.size(); l++) {
				for (int q = 0; q < neighbors.length; q++) {
					int[][] neighbors2 = Utilities.getNeighborIndices2(
							leaves1.get(l)[q][0], leaves1.get(l)[q][1], 1);
					for (int e = 0; e < neighbors2.length; e++) {
						WorldGenerator.data[neighbors2[e][0]][neighbors2[e][1]][14] = Block.PALM_LEAF;
					}
				}

			}

		}

	}

	private static void ashTree(int x, int y, int z) {
		// OPTIMIZE ME PLS
		ArrayList<int[][]> leaves1 = new ArrayList<int[][]>();
		ArrayList<int[][]> leaves2 = new ArrayList<int[][]>();
		int[][] neighbors = Utilities.getNeighborIndices2(x, y, 1);
		int[][] neighbors2 = null;

		// TOP LEVEL

		for (int q = 0; q < neighbors.length; q++) {
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][15] = Block.ASH_LEAF;
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][14] = Block.ASH_LEAF;
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][13] = Block.ASH_LEAF;
			leaves1.add(neighbors);

		}
		// SECOND LEVEL
		for (int l = 0; l < leaves1.size(); l++) {
			for (int q = 0; q < neighbors.length; q++) {
				neighbors2 = Utilities.getNeighborIndices2(
						leaves1.get(l)[q][0], leaves1.get(l)[q][1], 1);
				for (int e = 0; e < neighbors2.length; e++) {
					WorldGenerator.data[neighbors2[e][0]][neighbors2[e][1]][14] = Block.ASH_LEAF;
					WorldGenerator.data[neighbors2[e][0]][neighbors2[e][1]][13] = Block.ASH_LEAF;
					leaves2.add(neighbors2);
				}
			}

		}
		// THIRD LEVEL
		for (int l = 0; l < leaves2.size(); l++) {
			for (int q = 0; q < neighbors2.length; q++) {
				int[][] neighbors3 = Utilities.getNeighborIndices2(
						leaves2.get(l)[q][0], leaves2.get(l)[q][1], 1);
				for (int e = 0; e < neighbors3.length; e++) {
					WorldGenerator.data[neighbors3[e][0]][neighbors3[e][1]][14] = Block.JG_LEAF;
					WorldGenerator.data[neighbors3[e][0]][neighbors3[e][1]][13] = Block.JG_LEAF;
				}
			}

		}
		for (int h = z; h < Chunk.CHUNKHEIGHT; h++) {
			WorldGenerator.data[x][y][h] = Block.ASH_TREE;

		}

	}
}
