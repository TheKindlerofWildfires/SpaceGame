package gameEngine;

import java.util.ArrayList;

public class WorldGenerator {
	public static String mapType;
	public static String worldType;
	public static int seedCount;
	int[][] seeds;
	int[] seed = new int[3];
	double p;
	boolean f = true;
	// public int[] seed = new int[2];
	public int[][][] data = new int[Map.HEXESACROSS][Map.HEXESDOWN][Map.WORLDHEIGHT];

	public WorldGenerator() {

	}

	public int[][][] generate() {
		setSeed();
		p = 1;
		for (int z = 0; z < Map.WORLDHEIGHT; z++) {
			for (int y = 0; y < Map.HEXESDOWN; y++) {
				for (int x = 1; x < Map.HEXESACROSS; x++) {
					data[x][y][z] = getID(x, y, z);
				}
			}
		}
		while (f) {
			for (int z = 0; z < Map.WORLDHEIGHT; z++) {
				for (int y = 0; y < Map.HEXESDOWN; y++) {
					for (int x = 1; x < Map.HEXESACROSS; x++) {
						data[x][y][z] = cleanUp(x, y, z);
					}
				}
			}
		}
		// gen();
		return data;
	}

	private void setSeed() {
		seed[0] = Map.HEXESACROSS / 2;
		seed[1] = Map.HEXESDOWN / 2;
		seed[2] = Map.WORLDHEIGHT / 2;

	}

	private int cleanUp(int x, int y, int z) {
		f = false;
		if(z < 8){
			if (data[x][y][8] == 1) {
				return 1;
			}else{
				return data[x][y][z];
			}
		} else{
			return data[x][y][z];
			//smth here kills water
		}/*else if (data[x][y][z - 1] != 1) {
			data[x][y][z - 1] = 1;
			f = true;
			return 0;
		} else {
			return data[x][y][z];
		}*/
	}

	private int getID(int x, int y, int z) {
		if (z < 8) {
			return 20;
		} else {
			double p = (16.0 - z) / 8.0;
			p = 0.5;
			if (Map.rng.nextDouble() < p) {
				return 1;
			} else {
				return 0;
			}
		}

		/*
		 * switch(z){ case 0: return 1; case 1: return 1; case 2: return 1; case
		 * 10: return 1; default: return 0; }
		 */
		/*
		 * int dx = Math.abs(x-seed[0]); int dy = Math.abs(y- seed[1]); int dz =
		 * Math.abs(z-seed[2]); double dt = dx+dy+dz; //number from 0 to smth
		 * like 200 p = c/(p+1) double mx = Map.HEXESACROSS + Map.HEXESDOWN +
		 * Map.WORLDHEIGHT; p = (1-dt/mx); System.out.println(p); mx = mx*10;
		 * 
		 * //p = p-0.000001;
		 * 
		 * return p;
		 */
	}

	private void gen() {
		worldType = "telilic";
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
		int iter = 0;
		while (outerLand.size() != 0 && p != 0) {
			ArrayList<int[]> newLand = new ArrayList<int[]>();
			for (int i = 0; i < outerLand.size(); i++) {
				int[][] neighbors = getNeighborIndices2(outerLand.get(i)[0],
						outerLand.get(i)[1]);
				for (int j = 0; j < neighbors.length; j++) {
					if (data[neighbors[j][0]][neighbors[j][1]][0] != Block.LAND
							&& data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] != Block.SEED) {
						if (true) {
							data[neighbors[j][0]][neighbors[j][1]][0] = 1;
							iter++;
							newLand.add(neighbors[j]);
						}
					}
				}
			}
		}
	}

	private int[][] getNeighborIndices3(int x, int y, int z) {
		// System.out.println(x + "," + y);
		if (x > 0 && y > 0 && x < Map.HEXESACROSS - 1 && y < Map.HEXESDOWN - 1
				&& z > 0 && z > Map.WORLDHEIGHT) {
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

	private int[][] getNeighborIndices2(int x, int y) {
		// System.out.println(x + "," + y);
		if (x > 0 && y > 0 && x < Map.HEXESACROSS - 1 && y < Map.HEXESDOWN - 1) {
			int[][] neighbors = new int[8][3];
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

	private void initMap() {
		worldType = "telilic";
		seedCount = 1;
		seeds = new int[seedCount][3];
		ArrayList<int[]> outerLand = new ArrayList<int[]>();

		seeds[0][0] = Map.HEXESACROSS / 2;
		seeds[0][1] = Map.HEXESDOWN / 2;
		seeds[0][1] = Map.WORLDHEIGHT / 2;

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

	/*
	 * while(outerLand.size() != 0 && p != 0) { ArrayList<int[]> newLand = new
	 * ArrayList<int[]>(); for (int i = 0; i < outerLand.size(); i++) { int[][]
	 * neighbors = getNeighborIndices(outerLand.get(i)[0],
	 * outerLand.get(i)[1],outerLand.get(i)[2]); for (int j = 0; j <
	 * neighbors.length; j++) { if
	 * (data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] != Block.LAND &&
	 * data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] != Block.SEED) {
	 * if (Map.rng.nextDouble() <= p) {
	 * data[neighbors[j][0]][neighbors[j][1]][neighbors[j][2]] = 1; iter++;
	 * newLand.add(neighbors[j]); } } } } outerLand.clear();
	 * outerLand.addAll(newLand); newLand.clear(); p = getP("test", p, iter); }
	 * }
	 */
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
		p = p / 10;
		return p;
	}
}
