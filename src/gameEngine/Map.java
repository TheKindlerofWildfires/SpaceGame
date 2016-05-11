package gameEngine;

import graphicEngine.ShaderManager;
import noiseLibrary.module.source.Perlin;

import java.util.ArrayList;
import java.util.Random;

import GUI.Tile;

public class Map {
	public static final int HEXESACROSS = 360;
	public static final int HEXESDOWN = 240;

	public static final int MOISTURESCALER = 12;
	public static final int ELEVATIONSCALER = 17;

	public String mapType;
	public int seedCount;

	public String[] maps = { "fractal", "soft", "disk", "stand", "trig" };

	private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();

	private Tile[] seeds;

	private Random rng = new Random();

	private double apothem = 3;

	ShaderManager shaderManager;

	private void initTiles() {
		// INIT TILES
		for (int i = 0; i <= HEXESACROSS; i++) {
			tiles.add(new ArrayList<Tile>());
		}

		Perlin noise = new Perlin();
		noise.setSeed(rng.nextInt(1000000));
		for (int j = 0; j <= HEXESDOWN; j++) {
			for (int i = 0; i <= HEXESACROSS; i++) {
				tiles.get(j).add(new Tile(i, j, Math.abs(noise.getValue(i / MOISTURESCALER, j / MOISTURESCALER, .1)),
						Math.abs(noise.getValue(i / ELEVATIONSCALER, j / ELEVATIONSCALER, .3))));
			}
		}

		// INIT SEEDS
		seeds = new Tile[seedCount];
		for (int i = 0; i < seedCount; i++) {
			seeds[i] = tiles.get(HEXESDOWN / (8 / 3) + rng.nextInt(HEXESDOWN / 3))
					.get(HEXESACROSS / (8 / 3) + rng.nextInt(HEXESACROSS / 3));
			seeds[i].setLand(true);
		}

		// GEN LAND
		ArrayList<Tile> outerLand = new ArrayList<Tile>();
		for (Tile s : seeds) {
			for (Tile i : getAllNeighbors(s)) {
				i.setLand(true);
				outerLand.add(i);
			}
		}
		double i = 1;
		while (outerLand.size() != 0 && i != 0) {
			ArrayList<Tile> newLand = new ArrayList<Tile>();
			for (int j = 0; j < outerLand.size(); j++) {

				for (Tile k : getAllNeighbors(outerLand.get(j))) {

					if (!k.isLand()) {
						if (rng.nextDouble() <= i) {
							k.setLand(true);
							newLand.add(k);
						}
					}
				}
			}
			outerLand.clear();
			outerLand.addAll(newLand);
			newLand.clear();

			switch (mapType) {
			case "fractal":
				i = 0.29 / (Math.log(i + 2));
				break;
			case "soft":
				i = 0.18 / i;
				break;
			case "disk":
				i = Math.pow(2.618, -2.47 * i);
				break;
			case "stand":
				i = 0.988 * i;
				break;
			case "trig":
				i = Math.cos(1.443 * i);
				break;
			default:
				System.err.println("invalid map type");
				i = 0;
			}
		}
	}

	public Map() {// do we really need a new shaderManager? we have one in
					// entity manager
		shaderManager = new ShaderManager();
		ShaderManager.loadAll();

		initTiles();

	}

	private Tile[] getAllNeighbors(Tile tile) {
		if (tile.xIndex > 0 && tile.yIndex > 0 && tile.xIndex < HEXESACROSS - 1 && tile.yIndex < HEXESDOWN - 1) {
			Tile[] neighbors = new Tile[6];
			neighbors[0] = tiles.get(tile.yIndex + 1).get(tile.xIndex);
			neighbors[1] = tiles.get(tile.yIndex - 1).get(tile.xIndex);
			neighbors[2] = tiles.get(tile.yIndex).get(tile.xIndex + 1);
			neighbors[3] = tiles.get(tile.yIndex).get(tile.xIndex - 1);
			if (tile.xIndex % 2 == 0) {
				neighbors[4] = tiles.get(tile.yIndex - 1).get(tile.xIndex + 1);
				neighbors[5] = tiles.get(tile.yIndex - 1).get(tile.xIndex - 1);
			} else {
				neighbors[4] = tiles.get(tile.yIndex + 1).get(tile.xIndex + 1);
				neighbors[5] = tiles.get(tile.yIndex + 1).get(tile.xIndex - 1);
			}
			return neighbors;
		} else {
			return new Tile[0];
		}
	}

	public void draw() {
		

	}
}
