package gen;

import java.util.ArrayList;
import java.util.Random;

import GUI.LargeHexTile;

public class WorldGenerator {
	

	public LargeHexTile seed;
	public LargeHexTile seed2;
	public LargeHexTile seed3;
	private ArrayList<LargeHexTile> seeds = new ArrayList<LargeHexTile>();

	private Random rng = new Random();

	public int seedCount;
	public String mapType;
	public String[] maps;
	
	public World world;
	
	public WorldGenerator(World world) {
		this.world = world;
		
		maps = new String[5];
		maps[0] = "sfractal";
		maps[1] = "ssoft";
		maps[2] = "sdisk";
		maps[3] = "stand";
		maps[4] = "trig";
		
		//mapType = "stand";
		seedCount = rng.nextInt(2) + 1;
		// seedCount = 3;
	}

	
	public void initializeMap() {
		/* This code is much better, but I don't really understand
		 * how to get i in generateContinent to behave the same
		 * we can use it once someone helps with that
		for(int seednum = 0; seednum < seedCount; seednum++) {
			seeds.add(
				world.hexes.get(world.HEXESACROSS / 4 + rng.nextInt(world.HEXESACROSS / 2))
					 .get(world.HEXESDOWN / 4 + rng.nextInt(world.HEXESDOWN / 2))
			);
		}
		for(LargeHexTile seed : seeds) {
			generateContinent(seed, mapType);
		}
		*/
		seed = world.hexes.get(world.HEXESACROSS / 4 + rng.nextInt(world.HEXESACROSS / 2))
				.get(world.HEXESDOWN / 4 + rng.nextInt(world.HEXESDOWN / 2));
		seed2 = world.hexes.get(world.HEXESACROSS / 4 + rng.nextInt(world.HEXESACROSS / 2))
				.get(world.HEXESDOWN / 4 + rng.nextInt(world.HEXESDOWN / 2));
		seed3 = world.hexes.get(world.HEXESACROSS / 4 + rng.nextInt(world.HEXESACROSS / 2))
				.get(world.HEXESDOWN / 4 + rng.nextInt(world.HEXESDOWN / 2));
		seed.setLand(true);
		if (seedCount >= 2) {
			seed2.setLand(true);
		}
		if (seedCount >= 3) {
			seed3.setLand(true);
		}

		ArrayList<LargeHexTile> outerLand = new ArrayList<LargeHexTile>();
		for (LargeHexTile i : seed.getNeighbors()) {
			i.setLand(true);
			outerLand.add(i);
		}
		for (LargeHexTile i : seed2.getNeighbors()) {
			if (seedCount >= 2) {
				i.setLand(true);
				outerLand.add(i);
			}

		}
		for (LargeHexTile i : seed3.getNeighbors()) {
			if (seedCount >= 3) {
				i.setLand(true);
				outerLand.add(i);
			}
		}
		double i = 1; // changed
		// int to double and i from 0
		while (outerLand.size() != 0) {
			ArrayList<LargeHexTile> newLand = new ArrayList<LargeHexTile>();
			for (int j = 0; j < outerLand.size(); j++) {

				for (LargeHexTile k : outerLand.get(j).getNeighbors()) {

					if (!k.isLand()) {
						if (rng.nextDouble() <= i) {
							// this is where the magic happens
							k.setLand(true);
							newLand.add(k);

						}
					}
				}
			}
			outerLand.clear();
			outerLand.addAll(newLand);
			newLand.clear();
			if (mapType == "sfractal") {
				i = 0.299 / (Math.log(i + 2) * (0.01 * (seedCount - 1) + 1));
			} else if (mapType == "ssoft") {
				i = 0.17 / (i * (0.01 * (seedCount - 1) + 1));
			} else if (mapType == "sdisk") {
				i = Math.pow(2.618, -2.7 * i) / (0.01 * (seedCount - 1) + 1.01);
			} else if (mapType == "stand") {
				//look a bug
				i = (0.72*i + 0.1) / (0.01 * (seedCount - 1) + 1);
			} else if (mapType == "trig") {
				i = Math.cos(1.444 * i)/(1.4*(seedCount - 1) + 1);
			} else {
				i = 0;
			}
		}

		ArrayList<LargeHexTile> outerOcean = new ArrayList<LargeHexTile>();
		outerOcean.add(world.hexes.get(0).get(0));
		outerOcean.add(world.hexes.get(0).get(world.HEXESACROSS - 1));
		outerOcean.add(world.hexes.get(world.HEXESDOWN - 1).get(world.HEXESACROSS - 1));
		outerOcean.add(world.hexes.get(world.HEXESDOWN - 1).get(0));

		// while(outerOcean.size()!=0){

		// }
		// if len(land)>= 500{ regen}

	}
	public void generateContinent(LargeHexTile seed) {
		generateContinent(seed, maps[rng.nextInt(maps.length)]);
	}
	
	public void generateContinent(LargeHexTile seed, String mapType) {
		seed.setLand(true);
		ArrayList<LargeHexTile> land = new ArrayList<LargeHexTile>();
		land.add(seed);
		double i = 1;
		while(land.size() > 0) {
			LargeHexTile tile = land.get(rng.nextInt(land.size()));
			LargeHexTile[] neighbors = new LargeHexTile[6];
			System.arraycopy(tile.neighbors, 0, neighbors, 0, tile.neighbors.length);
			//Shuffle the array of neighbors
			for(int ix = 0;ix < neighbors.length;ix++) {
				int randix = rng.nextInt(neighbors.length);
				LargeHexTile temp = neighbors[ix];
				neighbors[ix] = neighbors[randix];
				neighbors[randix] = temp;
			}
			boolean flag = true;
			for(LargeHexTile neighbor : neighbors) {
				//TODO someone get these calculations for i to behave well...
				if (mapType == "sfractal") {
					i = 0.299 / (Math.log(i + 2) * (0.01 * (seedCount - 1) + 1));
				} else if (mapType == "ssoft") {
					i = 0.17 / (i * (0.01 * (seedCount - 1) + 1));
				} else if (mapType == "sdisk") {
					i = Math.pow(2.618, -2.7 * i) / (0.01 * (seedCount - 1) + 1.01);
				} else if (mapType == "stand") {
					//look a bug
					i = (0.72*i + 0.1) / (0.01 * (seedCount - 1) + 1);
				} else if (mapType == "trig") {
					i = Math.cos(1.444 * i)/(1.4*(seedCount - 1) + 1);
				} else {
					i = 0;
				}
				if(neighbor != null && rng.nextDouble() < i && !neighbor.isLand()) {
					neighbor.setLand(true);
					land.add(neighbor);
					flag = false;
					break;
				}
			}
			if(flag) {
				land.remove(tile);
			}
		}
		
	}
}
