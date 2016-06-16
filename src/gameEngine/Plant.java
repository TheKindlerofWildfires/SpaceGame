package gameEngine;

import graphicEngine.Chunk;

public class Plant {

public static void tree(int x,int y, int z){
	switch(Map.worldType){
	case "telilic":
		jungleTree(x,y,z);
		break;
	case "sapric":
		ashTree(x,y,z);
		break;
	case "worlic":
		palmTree(x,y,z);
		break;
	}
}

public static void bush(int x, int y, int z) {
	switch(Map.worldType){
	case "telilic":
		fruitBush(x,y,z);
		break;
	case "sapric":
		thornBush(x,y,z);
		break;
	case "worlic":
		reed(x,y,z);
		break;
	}
	
}
private static void reed(int x, int y, int z) {
	if(z>WorldGenerator.WATERLEVEL){
		WorldGenerator.data[x][y][z+1] = Block.REEDS;
		WorldGenerator.data[x][y][z+2] = Block.REEDS;
	}
}


private static void thornBush(int x, int y, int z) {
	if(z>WorldGenerator.WATERLEVEL){
		WorldGenerator.data[x][y][z+1] = Block.THORN_BUSH;
		WorldGenerator.data[x][y][z+2] = Block.THORN_BUSH;
	}
	
}
private static void fruitBush(int x, int y, int z) {
	if(z>WorldGenerator.WATERLEVEL){
		WorldGenerator.data[x][y][z+1] = Block.FRUIT_BUSH;
		WorldGenerator.data[x][y][z+2] = Block.FRUIT_BUSH;
	}
	
}
private static void jungleTree(int x, int y, int z) {
	for(int h = z;h<Chunk.CHUNKHEIGHT;h++){
		WorldGenerator.data[x][y][h] = Block.JG_TREE;
		int[][] neighbors = WorldGenerator.getNeighborIndices2(x,y);
		for(int q = 0; q<neighbors.length;q++){
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][15] = Block.JG_TREE;
		}
	}
}
private static void palmTree(int x, int y, int z) {
	for(int h = z;h<Chunk.CHUNKHEIGHT;h++){
		WorldGenerator.data[x][y][h] = Block.PALM_TREE;
		int[][] neighbors = WorldGenerator.getNeighborIndices2(x,y);
		for(int q = 0; q<neighbors.length;q++){
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][15] = Block.PALM_TREE;
		}
	}
	
}
private static void ashTree(int x, int y, int z) {
	for(int h = z;h<Chunk.CHUNKHEIGHT;h++){
		WorldGenerator.data[x][y][h] = Block.ASH_TREE;
		int[][] neighbors = WorldGenerator.getNeighborIndices2(x,y);
		for(int q = 0; q<neighbors.length;q++){
			WorldGenerator.data[neighbors[q][0]][neighbors[q][1]][15] = Block.ASH_TREE;
		}
	}
	
}

	
}
