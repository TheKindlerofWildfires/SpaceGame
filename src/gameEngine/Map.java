package gameEngine;

import java.util.ArrayList;
import java.util.Random;

import GUI.Vector3f;
import graphicEngine.ShaderManager;
import noiseLibrary.module.source.Perlin;

public class Map {
	public static final int HEXESACROSS = 100;
	public static final int HEXESDOWN = 100;
	static int i = 0;
	public static final int MOISTURESCALER = 12;
	public static final int ELEVATIONSCALER = 17;

	public static final float APOTHEM = 0.005f;

	public String mapType;
	public int seedCount;

	public String[] maps = { "fractal", "soft", "disk", "stand", "trig" };
	private ArrayList<ArrayList<Hexagon>> hexes = new ArrayList<ArrayList<Hexagon>>();
	private Hexagon[] seeds;

	ShaderManager shaderManager;

	private Random rng = new Random();

	public Map() {
		shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		seedCount = rng.nextInt(2)+1;
		///mapType = maps[rng.nextInt(maps.length)];
		mapType = "fractal";
		initializeMap();
	}

	private Hexagon[] getAllNeighbors(Hexagon hex) {
		if (hex.xIndex > 0 && hex.yIndex > 0 && hex.xIndex < HEXESACROSS - 1 && hex.yIndex < HEXESDOWN - 1) {
			Hexagon[] neighbors = new Hexagon[6];
			neighbors[0] = hexes.get(hex.yIndex + 1).get(hex.xIndex);
			neighbors[1] = hexes.get(hex.yIndex - 1).get(hex.xIndex);
			neighbors[2] = hexes.get(hex.yIndex).get(hex.xIndex + 1);
			neighbors[3] = hexes.get(hex.yIndex).get(hex.xIndex - 1);
			if (hex.xIndex % 2 == 0) {
				neighbors[4] = hexes.get(hex.yIndex - 1).get(hex.xIndex + 1);
				neighbors[5] = hexes.get(hex.yIndex - 1).get(hex.xIndex - 1);
			} else {
				neighbors[4] = hexes.get(hex.yIndex + 1).get(hex.xIndex + 1);
				neighbors[5] = hexes.get(hex.yIndex + 1).get(hex.xIndex - 1);
			}
			return neighbors;
		} else {
			return new Hexagon[0];
		}
	}

	public void drawHex(Hexagon hex) {
		//	long time = System.nanoTime();
		//	ShaderManager.shader1.start();
		ShaderManager.shader1.setUniform3f("pos", hex.position);
		Vector3f color = hex.isLand() ? new Vector3f(0f, 1.0f, 0f) : new Vector3f(0f, 0f, 1.0f);
		ShaderManager.shader1.setUniform3f("color", color);
		//	long time2 = System.nanoTime();
		hex.draw();
		//	long time3 = System.nanoTime();
		//	ShaderManager.shader1.stop();
		//	long time4 = System.nanoTime();
		//	System.out.println("Shader Stuff: " + ((time2-time) + (time4-time3)));
		//	System.out.println("drawing: " + (time3-time2));
	}

	public void draw() {
		ShaderManager.shader1.start();
		hexes.stream()
				.forEach(l -> l.stream()
						.filter(t -> (t.position.x < 1 && t.position.y < 1 && t.position.x > -1 && t.position.y > -1))
						.forEach(t -> drawHex(t)));
		ShaderManager.shader1.stop();
	}

	private void initializeMap() {
		System.out.println("Initiailzing Tiles");
		// INIT TILES
		for (int i = 0; i <= HEXESACROSS; i++) {
			hexes.add(new ArrayList<Hexagon>());
		}

		Perlin noise = new Perlin();
		noise.setSeed(rng.nextInt(1000000));
		for (int j = 0; j < HEXESDOWN; j++) {
			for (int i = 0; i < HEXESACROSS; i++) {
				hexes.get(j)
						.add(new Hexagon(i, j,
								(float) Math.abs(noise.getValue(i / MOISTURESCALER, j / MOISTURESCALER, .1)),
								(float) Math.abs(noise.getValue(i / ELEVATIONSCALER, j / ELEVATIONSCALER, .3))));
			}
		}

		// INIT SEEDS
		System.out.println("Initiailzing Seeds");

		seeds = new Hexagon[seedCount];
		for (int i = 0; i < seedCount; i++) {
			seeds[i] = hexes.get(HEXESDOWN / (8 / 3) + rng.nextInt(HEXESDOWN / 3))
					.get(HEXESACROSS / (8 / 3) + rng.nextInt(HEXESACROSS / 3));
			seeds[i].setLand(true);
		}

		// GEN LAND
		System.out.println("Genning Land");
		ArrayList<Hexagon> outerLand = new ArrayList<Hexagon>();
		for (Hexagon s : seeds) {
			for (Hexagon i : getAllNeighbors(s)) {
				i.setLand(true);
				outerLand.add(i);
			}
		}
		double i = 1;
		while (outerLand.size() != 0 && i != 0) {
			ArrayList<Hexagon> newLand = new ArrayList<Hexagon>();
			for (int j = 0; j < outerLand.size(); j++) {

				for (Hexagon k : getAllNeighbors(outerLand.get(j))) {

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
		
		/*hexes.get(10).get(10).setLand(true);
		hexes.get(11).get(10).setLand(true);
		hexes.get(12).get(10).setLand(true);
		hexes.get(13).get(10).setLand(true);
		hexes.get(14).get(10).setLand(true);
		hexes.get(15).get(10).setLand(true);
		
		hexes.get(13).get(10).setLand(true);
		hexes.get(13).get(11).setLand(true);
		hexes.get(13).get(12).setLand(true);
		hexes.get(13).get(13).setLand(true);

		hexes.get(10).get(13).setLand(true);
		hexes.get(11).get(13).setLand(true);
		hexes.get(12).get(13).setLand(true);
		hexes.get(13).get(13).setLand(true);
		hexes.get(14).get(13).setLand(true);
		hexes.get(15).get(13).setLand(true);
		
		hexes.get(10).get(15).setLand(true);
		hexes.get(12).get(15).setLand(true);
		hexes.get(13).get(15).setLand(true);
		hexes.get(14).get(15).setLand(true);
		hexes.get(15).get(15).setLand(true);*/

		System.out.println("Map init complete");
	}
}
