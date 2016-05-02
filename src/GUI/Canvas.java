package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import noiseLibrary.module.source.Perlin;

/**
 * OUTER LAYER NO NEIGHBOR even water tiles, which do not need, have moisture
 * and elevation
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

	public String[] maps = { "sfractal", "ssoft", "sdisk", "stand", "trig" };
	private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
	private Tile[] seeds;

	private Random rng = new Random();

	private double apothem = 2;

	public Canvas() {
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.BLACK);

		mapType = maps[rng.nextInt(maps.length)];
		seedCount = rng.nextInt(2) + 1;

		initializeMap();

	}

	public Dimension getPreferredSize() {
		return new Dimension(1920, 1080);
	}

	private Tile[] getAllNeighbors(Tile tile) {
		if (tile.xIndex > 0 && tile.yIndex > 0 && tile.xIndex < HEXESACROSS - 1 && tile.yIndex < HEXESDOWN - 1) {
			Tile[] neighbors = new Tile[6];
			if (tile.yIndex % 2 == 0) {
				neighbors[0] = tiles.get(tile.yIndex).get(tile.xIndex - 1);
				neighbors[1] = tiles.get(tile.yIndex).get(tile.xIndex + 1);
				neighbors[2] = tiles.get(tile.yIndex - 1).get(tile.xIndex - 1);
				neighbors[3] = tiles.get(tile.yIndex - 1).get(tile.xIndex);
				neighbors[4] = tiles.get(tile.yIndex + 1).get(tile.xIndex);
				neighbors[5] = tiles.get(tile.yIndex + 1).get(tile.xIndex - 1);
			} else {
				neighbors[0] = tiles.get(tile.yIndex).get(tile.xIndex - 1);
				neighbors[1] = tiles.get(tile.yIndex).get(tile.xIndex + 1);
				neighbors[2] = tiles.get(tile.yIndex - 1).get(tile.xIndex + 1);
				neighbors[3] = tiles.get(tile.yIndex - 1).get(tile.xIndex);
				neighbors[4] = tiles.get(tile.yIndex + 1).get(tile.xIndex);
				neighbors[5] = tiles.get(tile.yIndex + 1).get(tile.xIndex + 1);
			}
			return neighbors;
		} else {
			return new Tile[0];
		}
	}

	private void initializeMap() {
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
			seeds[i] = tiles.get(HEXESDOWN / 4 + rng.nextInt(HEXESDOWN / 2))
					.get(HEXESACROSS / 4 + rng.nextInt(HEXESACROSS / 2));
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
			case "sfractal":
				i = 0.29 / (Math.log(i + 2) * (0.01 * (seedCount - 1) + 1));
				break;
			case "ssoft":
				i = 0.18 / (i * (0.01 * (seedCount - 1) + 1));
				break;
			case "sdisk":
				i = Math.pow(2.618, -2.47 * i) / (0.01 * (seedCount - 1) + 1.01);
				break;
			case "stand":
				i = 0.98 * i / (0.01 * (seedCount - 1) + 1);
				break;
			case "trig":
				i = Math.cos(1.43 * i);
				break;
			default:
				System.err.println("invalid map type");
				i = 0;
			}
		}
	}

	private void fillTile(Graphics2D g2d, Tile tile) {
		if (tile.isLand()) {
			g2d.setColor(Color.GREEN);
		} else {
			g2d.setColor(Color.BLUE);
		}
		double cX;
		double cY;
		double side = apothem * 2 / Math.sqrt(3);
		Path2D.Double poly = new Path2D.Double();
		if (tile.xIndex % 2 == 0) {
			cY = tile.yIndex * apothem * 2;
			cX = tile.xIndex * 3 * apothem / Math.sqrt(3);
			poly.moveTo(cX + side, cY);
			poly.lineTo(cX + side / 2, cY - apothem);
			poly.lineTo(cX - side / 2, cY - apothem);
			poly.lineTo(cX - side, cY);
			poly.lineTo(cX - side / 2, cY + apothem);
			poly.lineTo(cX + side / 2, cY + apothem);
			poly.closePath();
			g2d.draw(poly);
		} else {
			cY = tile.yIndex * apothem * 2 + apothem;
			cX = tile.xIndex * 3 * apothem / Math.sqrt(3);
			poly.moveTo(cX + side, cY);
			poly.lineTo(cX + side / 2, cY - apothem);
			poly.lineTo(cX - side / 2, cY - apothem);
			poly.lineTo(cX - side, cY);
			poly.lineTo(cX - side / 2, cY + apothem);
			poly.lineTo(cX + side / 2, cY + apothem);
			poly.closePath();
			g2d.draw(poly);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// g2d.setColor(Color.GREEN);
		tiles.stream().forEach(l -> l.stream().forEach(h -> fillTile(g2d, h)));

		/*
		 * // g2d.setColor(Color.BLUE);
		 * 
		 * g2d.setColor(Color.BLACK); hexes.stream().forEach(l ->
		 * l.stream().forEach(h -> drawHex(g2d, h)));
		 * 
		 * g2d.setColor(Color.MAGENTA); g2d.fill(seed);
		 * g2d.setColor(Color.ORANGE); if (seedCount >= 2) { g2d.fill(seed2); }
		 * if (seedCount >= 3) { g2d.fill(seed3); }
		 * System.out.println(seedCount); System.out.println(mapType);
		 * 
		 * if (zoomed) { g2d.setColor(Color.YELLOW); hexes.stream() .forEach(l
		 * -> l.stream().forEach(h -> h.innerTiles.stream().forEach(t ->
		 * drawSmTile(g2d, t)))); }
		 */
	}

}
