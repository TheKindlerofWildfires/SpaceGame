package gen;

import java.util.ArrayList;

import maths.Utilities;
import gameEngine.Block;
import gameEngine.Map;

public class Sky {
	static String type = "soft";
	public static void cloud(int x, int y) {
		//doesnt spread but works--check spread function
		
		ArrayList<int[]> outerCloud = new ArrayList<int[]>();
		int z = (int) (Map.WORLDHEIGHT-5);
		int[] seed = {x,y,z};
		outerCloud.add(seed);
		WorldGenerator.data[x][y][z] = Block.CLOUD;
		double p = 1;
		int iter = 0;
		while (outerCloud.size() > 0 && p > 0) {
			
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
			p=getP(iter, p);
			iter++;
		}
	}

	private static double getP(int iter, double p) {
			switch (type) {
			case "fractal":
				p = .09 / (Math.log(p + 2));
				break;
			case "soft": //
				p = .015 / p;
				break;
			case "stand":
				p = (p + 1) / (p + 9);
				break;
			case "disk":
				p = 0.3 * p;
				break;
			case "trig":
				p = Math.cos(1.5555* p);
				break;
			case "quad":
				if (p >= 1) {
					p = 0.8;
				} else {
					p = Math.pow(p, 2);
				}
				break;
			case "it":
				p -= 0.18;
				break;
			case "lin":
				p -= p * 0.25;
				break;
			case "atic":
				p -= Math.pow(p, 2) * 0.5;
				break;
			case "grit":
				p -= Math.sin(p / 4);
				break;

			default:
				System.err.println("invalid type__SKY");
				System.exit(-1);
				p = 0;
			}

			return p;
		}

}
