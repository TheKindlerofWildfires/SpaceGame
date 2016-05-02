package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import noiseLibrary.module.source.Perlin;

/**
 * OUTER LAYER NO NEIGHBOR
 * 
 * @author Simon
 */

@SuppressWarnings("serial")
public class Canvas extends JPanel {
	public static final int HEXESACROSS = 360;
	public static final int HEXESDOWN = 240;

	public static final int MOISTURESCALER = 12;
	public static final int ELEVATIONSCALER = 17;

	public String mapType;
	public int seedCount;
	public String[] maps;
	private ArrayList<ArrayList<LargeHexTile>> hexes = new ArrayList<ArrayList<LargeHexTile>>();
	private Random rng = new Random();

	private LargeHexTile seed;
	private LargeHexTile seed2;
	private LargeHexTile seed3;

	private double zoomFactor = 1;

	private boolean zoomed = false;

	public Canvas() {
		setLayout(null);
		maps = new String[5];
		maps[0] = "sfractal";
		maps[1] = "ssoft";
		maps[2] = "sdisk";
		maps[3] = "stand";
		maps[4] = "trig";
		/*
		 * maps[5] = "disk";
		 */
		mapType = maps[rng.nextInt(maps.length)];
		// mapType = "trig";
		seedCount = rng.nextInt(2) + 1;
		// seedCount = 3;

		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.BLACK);
		for (int i = 0; i < HEXESACROSS; i++) {
			hexes.add(new ArrayList<LargeHexTile>());
		}
		int apothem = new Hexagon(0,0).apothem;
		for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				if (j % 2 == 0) {
					hexes.get(j).add(new LargeHexTile(i * 2 * apothem, (int) (j * 3 * apothem / Math.sqrt(3))));
				} else {
					hexes.get(j)
							.add(new LargeHexTile(i * 2 * apothem + apothem, (int) (j * 3 * apothem / Math.sqrt(3))));
				}
			}
		}
		for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				if (j != 0 && i != 0 && j < HEXESDOWN - 1 && i < HEXESACROSS - 1) {
					if (j % 2 == 0) {
						hexes.get(j).get(i).neighbors = new LargeHexTile[6];
						hexes.get(j).get(i).setLeftNeighbor(hexes.get(j).get(i - 1));
						hexes.get(j).get(i).setRightNeighbor(hexes.get(j).get(i + 1));
						hexes.get(j).get(i).setUpperRightNeighbor(hexes.get(j - 1).get(i - 1));
						hexes.get(j).get(i).setUpperLeftNeighbor(hexes.get(j - 1).get(i));
						hexes.get(j).get(i).setLowerLeftNeighbor(hexes.get(j + 1).get(i));
						hexes.get(j).get(i).setLowerRightNeighbor(hexes.get(j + 1).get(i - 1));

					} else {
						hexes.get(j).get(i).neighbors = new LargeHexTile[6];
						hexes.get(j).get(i).setLeftNeighbor(hexes.get(j).get(i - 1));
						hexes.get(j).get(i).setRightNeighbor(hexes.get(j).get(i + 1));
						hexes.get(j).get(i).setUpperRightNeighbor(hexes.get(j - 1).get(i + 1));
						hexes.get(j).get(i).setUpperLeftNeighbor(hexes.get(j - 1).get(i));
						hexes.get(j).get(i).setLowerLeftNeighbor(hexes.get(j + 1).get(i));
						hexes.get(j).get(i).setLowerRightNeighbor(hexes.get(j + 1).get(i + 1));
					}
				} else if (i == 0 && j == 0) {
					hexes.get(j).get(i).neighbors = new LargeHexTile[3];
					hexes.get(j).get(i).setLowerLeftNeighbor(hexes.get(j + 1).get(i));
					hexes.get(j).get(i).setLowerRightNeighbor(hexes.get(j + 1).get(i + 1));
					hexes.get(j).get(i).setRightNeighbor(hexes.get(j).get(i + 1));
				} else if (j == 0 && i == HEXESACROSS - 1) {
					hexes.get(j).get(i).neighbors = new LargeHexTile[2];
					hexes.get(j).get(i).setLowerRightNeighbor(hexes.get(j).get(i - 1));
					hexes.get(j).get(i).setLowerLeftNeighbor(hexes.get(j + 1).get(i));
				} else {
					hexes.get(j).get(i).neighbors = new LargeHexTile[0];
				}
			}
		}
		initializeMap();
		// displayMapThenZoom();
		
		hexes.stream().forEach(l -> l.stream().forEach(t -> t.changeScaleFactor(10)));
		validate();
		repaint();
		
	}

	private void displayMapThenZoom() {
		repaint();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				zoomed = true;
				zoomFactor = 5;
				repaint();
			}
		}, 2000);
	}

	public Dimension getPreferredSize() {
		return new Dimension(1920, 1080);
	}

	private void drawHex(Graphics2D g2d, LargeHexTile hex) {
		g2d.drawLine((int) (hex.xpoints[0] * zoomFactor), (int) (hex.ypoints[0] * zoomFactor),
				(int) (hex.xpoints[1] * zoomFactor), (int) (hex.ypoints[1] * zoomFactor));
		g2d.drawLine((int) (hex.xpoints[3] * zoomFactor), (int) (hex.ypoints[3] * zoomFactor),
				(int) (hex.xpoints[4] * zoomFactor), (int) (hex.ypoints[4] * zoomFactor));
		g2d.drawLine((int) (hex.xpoints[4] * zoomFactor), (int) (hex.ypoints[4] * zoomFactor),
				(int) (hex.xpoints[5] * zoomFactor), (int) (hex.ypoints[5] * zoomFactor));
		g2d.drawLine((int) (hex.xpoints[5] * zoomFactor), (int) (hex.ypoints[5] * zoomFactor),
				(int) (hex.xpoints[0] * zoomFactor), (int) (hex.ypoints[0] * zoomFactor));
	}

	private void drawSmTile(Graphics2D g2d, SmallTile hex) {
		Polygon poly = new Polygon();
		for (int i = 0; i < hex.npoints; i++) {
			poly.addPoint((int) (hex.xpoints[i] * zoomFactor), (int) (hex.ypoints[i] * zoomFactor));
		}
		g2d.drawPolygon(poly);
	}

	private void fillHex(Graphics2D g2d, LargeHexTile hex) {
	
		//TODO: FIX BIOMES IN A SATISFACTORY MANNER
		switch(hex.biome){
		case "SNOW":
			g2d.setColor(Color.WHITE);
			break;
		case "TUNDRA":
			g2d.setColor(Color.LIGHT_GRAY);
			break;
		case "BARE":
			g2d.setColor(Color.GRAY);
			break;
		case "SCORCHED":
			g2d.setColor(Color.DARK_GRAY);
			break;
		case "WATER":
			g2d.setColor(Color.BLUE);
			break;
		case "TAIGA":
			g2d.setColor(Color.RED);
			break;
		default:
			g2d.setColor(Color.GREEN);
			break;
		}
		Polygon poly = new Polygon();
		for (int i = 0; i < hex.npoints; i++) {
			poly.addPoint((int) (hex.xpoints[i] * zoomFactor), (int) (hex.ypoints[i] * zoomFactor));
		}
		g2d.fillPolygon(poly);
	}

	private void initializeMoistureAndElevation(LargeHexTile hex) {
		Perlin perlin = new Perlin();
		perlin.setSeed(rng.nextInt(200000));
		hex.moisture = Math.abs(perlin.getValue(hex.cX / MOISTURESCALER, hex.cY / MOISTURESCALER, .1));
		hex.elevation = Math.abs(perlin.getValue(hex.cX / ELEVATIONSCALER, hex.cY / ELEVATIONSCALER, .5));

		//TODO: FIX BIOMES IN A SATISFACTORY MANNER
		if (hex.moisture * 6 > 5) {
			if (hex.elevation * 4 > 3) {
				hex.biome = "SNOW";
			} else if (hex.elevation * 4 > 1.5) {
				hex.biome = "TAIGA";
			} else if (hex.elevation * 4 > 1) {
				hex.biome = "TEMPERATE RAIN FOREST";
			} else {
				hex.biome = "TROPICAL RAIN FOREST";
			}
		} else if (hex.moisture * 6 > 4) {
			if (hex.elevation * 4 > 3) {
				hex.biome = "SNOW";
			} else if (hex.elevation * 4 > 1.5) {
				hex.biome = "TAIGA";
			} else if (hex.elevation * 4 > 1) {
				hex.biome = "TEMPERATE DECIDUOUS FOREST";
			} else {
				hex.biome = "TROPICAL RAIN FOREST";
			}
		} else if (hex.moisture * 6 > 3) {
			if (hex.elevation * 4 > 3) {
				hex.biome = "SNOW";
			} else if (hex.elevation * 4 > 2) {
				hex.biome = "SHRUBLAND";
			} else if (hex.elevation * 4 > 1) {
				hex.biome = "TEMPERATE DECIDUOUS FOREST";
			} else {
				hex.biome = "TROPICAL SEASONAL FOREST";
			}
		} else if (hex.moisture * 6 > 2) {
			if (hex.elevation * 4 > 3) {
				hex.biome = "TUNDRA";
			} else if (hex.elevation * 4 > 2) {
				hex.biome = "SHRUBLAND";
			} else if (hex.elevation * 4 > 1) {
				hex.biome = "GRASSLAND";
			} else {
				hex.biome = "TROPICAL SEASONAL FOREST";
			}
		} else if (hex.moisture * 6 > 1) {
			if (hex.elevation * 4 > 3) {
				hex.biome = "BARE";
			} else if (hex.elevation * 4 > 2) {
				hex.biome = "TEMPERATE DESERT";
			} else if (hex.elevation * 4 > 1) {
				hex.biome = "GRASSLAND";
			} else {
				hex.biome = "GRASSLAND";
			}
		} else{
			if (hex.elevation * 4 > 3) {
				hex.biome = "SCORCHED";
			} else if (hex.elevation * 4 > 2) {
				hex.biome = "TEMPERATE DESERT";
			} else if (hex.elevation * 4 > 1) {
				hex.biome = "TEMPERATE DESERT";
			} else {
				hex.biome = "SUBTROPICAL DESERT";
			} 
		}
	}

	private void initializeMap() {
		seed = hexes.get(HEXESACROSS / 4 + rng.nextInt(HEXESACROSS / 2))
				.get(HEXESDOWN / 4 + rng.nextInt(HEXESDOWN / 2));
		seed2 = hexes.get(HEXESACROSS / 4 + rng.nextInt(HEXESACROSS / 2))
				.get(HEXESDOWN / 4 + rng.nextInt(HEXESDOWN / 2));
		seed3 = hexes.get(HEXESACROSS / 4 + rng.nextInt(HEXESACROSS / 2))
				.get(HEXESDOWN / 4 + rng.nextInt(HEXESDOWN / 2));
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
				i = 0.29 / (Math.log(i + 2) * (0.01 * (seedCount - 1) + 1));
			} else if (mapType == "ssoft") {
				i = 0.18 / (i * (0.01 * (seedCount - 1) + 1));
			} else if (mapType == "sdisk") {
				i = Math.pow(2.618, -2.47 * i) / (0.01 * (seedCount - 1) + 1.01);
			} else if (mapType == "stand") {
				;
				i = 0.98 * i / (0.01 * (seedCount - 1) + 1);
			} else if (mapType == "trig") {
				i = Math.cos(1.43 * i);
				/*
				 * }else if (mapType == "disk"){ i = Math.pow(2.6,
				 * -2.46*i)/(0.01*(seedCount-1)+1);
				 */
			} else {
				i = 0;
			}
		}

		ArrayList<LargeHexTile> outerOcean = new ArrayList<LargeHexTile>();
		outerOcean.add(hexes.get(0).get(0));
		outerOcean.add(hexes.get(0).get(HEXESACROSS - 1));
		outerOcean.add(hexes.get(HEXESDOWN - 1).get(HEXESACROSS - 1));
		outerOcean.add(hexes.get(HEXESDOWN - 1).get(0));

		// while(outerOcean.size()!=0){

		// }
		// if len(land)>= 500{ regen}

		hexes.stream().forEach(l -> l.stream().filter(h -> h.isLand()).forEach(t -> initializeMoistureAndElevation(t)));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		System.out.println("it");

		// g2d.setColor(Color.GREEN);
		hexes.stream().forEach(l -> l.stream().forEach(h -> fillHex(g2d, h)));

		// g2d.setColor(Color.BLUE);

		g2d.setColor(Color.BLACK);
		hexes.stream().forEach(l -> l.stream().forEach(h -> drawHex(g2d, h)));

		g2d.setColor(Color.MAGENTA);
		g2d.fill(seed);
		g2d.setColor(Color.ORANGE);
		if (seedCount >= 2) {
			g2d.fill(seed2);
		}
		if (seedCount >= 3) {
			g2d.fill(seed3);
		}
		System.out.println(seedCount);
		System.out.println(mapType);

		if (zoomed) {
			g2d.setColor(Color.YELLOW);
			hexes.stream()
					.forEach(l -> l.stream().forEach(h -> h.innerTiles.stream().forEach(t -> drawSmTile(g2d, t))));
		}
	}

}
