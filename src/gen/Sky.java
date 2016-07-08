package gen;

import java.util.ArrayList;

import maths.Utilities;
import gameEngine.Block;
import gameEngine.Map;

public class Sky {

	public static void cloud(int x, int y) {
		//doesnt spread but works--check spread function
		
		ArrayList<int[]> outerCloud = new ArrayList<int[]>();
		int z = (int) (Map.WORLDHEIGHT*0.75);
		int[] seed = {x,y,z};
		outerCloud.add(seed);
		WorldGenerator.data[x][y][z] = Block.CLOUD;
		int p = 1;
		int iter = 0;
		while (outerCloud.size() > 0 && p > 0) {
			System.out.println(iter);
			ArrayList<int[]> newCloud = new ArrayList<int[]>();	
			for (int i = 0; i < outerCloud.size(); i++) {
				
				int[][] neighbors = Utilities.getNeighborIndices3(outerCloud.get(i)[0], outerCloud.get(i)[1], outerCloud.get(i)[2]);
				for (int j = 0; j < neighbors.length; j++) {
					if (Map.rng.nextDouble() <= p) {
						WorldGenerator.data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] = Block.CLOUD;
						newCloud.add(neighbors[j]);
					}
				}
			}
			outerCloud.clear();
			outerCloud.addAll(newCloud);
			newCloud.clear();
			p-=0.25;
			iter++;
		}
	}

}
