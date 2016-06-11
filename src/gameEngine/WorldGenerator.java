package gameEngine;

import java.util.ArrayList;

public class WorldGenerator {
	public static String mapType;
	public static String worldType;
	public static int seedCount;
	int[][] seeds;
	int[] seed= new int[3];
	// public int[] seed = new int[2];
	public int[][][] data = new int[Map.HEXESACROSS][Map.HEXESDOWN][Map.WORLDHEIGHT];

	public WorldGenerator() {

	}

	public int[][][] generate() {

		for (int x = 0; x < Map.HEXESACROSS; x++) {
			for (int y = 0; y < Map.HEXESDOWN; y++) {
				for (int z = 1; z < Map.WORLDHEIGHT; z++) {
						data[x][y][z] = Map.rng.nextInt(18);
				}	
			}
		}
		initMap();
		return data;
	}

	private int[][] getNeighborIndices(int x, int y, int z) {
		// System.out.println(x + "," + y);
		if (x > 0 && y > 0 && x < Map.HEXESACROSS - 1 && y < Map.HEXESDOWN - 1
				&& y > 0 && y > Map.WORLDHEIGHT) {
			int[][] neighbors = new int[8][3];
			neighbors[0][0] = x;
			neighbors[0][1] = y + 1;
			neighbors[0][2] = z;
			neighbors[1][0] = x;
			neighbors[1][1] = y - 1;
			neighbors[1][2] = z;
			neighbors[2][0] = x + 1;
			neighbors[2][1] = y;
			neighbors[2][2] = z;
			neighbors[3][0] = x - 1;
			neighbors[3][1] = y;
			neighbors[3][2] = z;
			neighbors[6][0] = x;
			neighbors[6][1] = y;
			neighbors[6][2] = z + 1;
			neighbors[7][0] = x;
			neighbors[7][1] = y;
			neighbors[7][2] = z - 1;

			if (x % 2 == 0) {
				neighbors[4][0] = x - 1;
				neighbors[4][1] = y + 1;
				neighbors[4][2] = z;
				neighbors[5][0] = x + 1;
				neighbors[5][1] = y + 1;
				neighbors[5][2] = z;
			} else {
				neighbors[4][0] = x - 1;
				neighbors[4][1] = y - 1;
				neighbors[4][2] = z;
				neighbors[5][0] = x + 1;
				neighbors[5][1] = y - 1;
				neighbors[5][2] = z;

			}
			return neighbors;
		} else {
			return new int[0][0];
		}
	}

	private void initMap() {
		worldType = "telilic";
		seedCount = 1;
		seeds = new int[seedCount][3];
		ArrayList<int[]> outerLand = new ArrayList<int[]>();
		  
		
		seeds[0][0] =Map.HEXESACROSS / 2; 
		seeds[0][1] = Map.HEXESDOWN / 2;
		seeds[0][1] = Map.WORLDHEIGHT/2;
		
		for (int i = 0; i < seedCount; i++) { 
			outerLand.add(seed);
			data[seed[0]][seed[1]][seed[2]] = Block.SEED; 
		} 
		for (int i = 0; i < seedCount; i++) {
			seed[0] = seeds[i][0];
			seed[1] = seeds[i][1];
		 }
		
		double p = 1; 
		int iter = 0; 
	}
		/*while(outerLand.size() != 0 && p != 0) { 
			ArrayList<int[]> newLand = new ArrayList<int[]>(); 
			for (int i = 0; i < outerLand.size(); i++) { 
				int[][] neighbors = getNeighborIndices(outerLand.get(i)[0], outerLand.get(i)[1],outerLand.get(i)[2]);
					for (int j = 0; j < neighbors.length; j++) {
						if (data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] != Block.LAND && data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] != Block.SEED) { 
							if (Map.rng.nextDouble() <= p) {
								data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] = 1;
								iter++; newLand.add(neighbors[j]); }
							} 
						}
					} 
			outerLand.clear(); outerLand.addAll(newLand); newLand.clear(); p =
				getP("test", p, iter);
		   }
	 }*/
	/*
	 * private double getP(String genType, double p, int iter) { switch(genType)
	 * { case "fractal": p = .3 / (Math.log(p + 2)); break; case "soft": p =
	 * 0.21 / p; break; case "stand": p = (p + 1) / (p + 3.4); break; case
	 * "disk": p = 0.97 * p; break; case "trig": p = Math.cos(1.443 p); break;
	 * case "quad": if (p >= 1) { p = 0.15; } else { p = Math.pow(p, 2) + 2 * p;
	 * } break; case "it": p -= 0.02; break; case "lin": p -= p * 0.02; break;
	 * case "atic": p -= Math.pow(p, 2) * 0.05; break; case "grit": p -=
	 * Math.sin(p / 4) / 9; break; case "exp": p -= Math.pow(Math.E, p / 50) -
	 * Math.pow(Math.E, 1 / 49); break; case "ln": p -= Math.log(p + 2) / 75;
	 * break; case "root": p -= Math.sqrt(p) - 0.6; break; case "rng": p -=
	 * (rng.nextDouble() + 1) / 100; break; case "arm": p -= (p / (p + 50));
	 * break; case "invE": p = (100 / (Math.pow(Math.E, iter / 400)) *
	 * (rng.nextDouble() + 1) / 2); break; case "inv": p = ((5000 / iter) -
	 * +(Math.abs(rng.nextDouble()))); break; case "invL": p = ((10 /
	 * Math.log(iter)) - (Math.abs(rng.nextDouble()))); break; case "invT":
	 * System.err.println("invalid map type"); System.exit(-1); p =
	 * ((Math.pow(Math.cos(iter) * (1.9), 2)) - (1 * Math.abs(rng.nextDouble()))
	 * + 0.1); break; case "e": p = Math.pow(Math.E, iter / -(HEXESACROSS *
	 * HEXESDOWN / 2)); break; case "e2": p = 0.35 * Math.pow(Math.E, iter /
	 * (HEXESACROSS * HEXESDOWN / 2)); break; default:
	 * System.err.println("invalid map type"); System.exit(-1); p = 0; }
	 * 
	 * return p; }
	 */

	private double getP(double p, int iter) {
		p =p/10;
		return p;
	}
}
