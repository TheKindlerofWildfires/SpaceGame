package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//import javax.swing.BorderFactory;
//import javax.swing.JPanel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import noiseLibrary.module.source.Perlin;

/**
 * OUTER LAYER NO NEIGHBOR even water tiles, which do not need, have moisture
 * and elevation
 * 
 * @author Simon
 */

//udfg	wonder what that meant

public class Canvas extends Window {
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

	public Canvas() {
		//udfg	
		//setLayout(null);
		//setBorder(BorderFactory.createLineBorder(Color.black));
		//setBackground(Color.BLACK);

		//mapType = maps[rng.nextInt(maps.length)];
		mapType = "fractal";
		seedCount = rng.nextInt(2) + 1;

		initializeMap();
		/*
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				apothem=10;
				repaint();
			}	
		}, 2000); */
	}


	private Tile[] getAllNeighbors(Tile tile) {
		if (tile.xIndex > 0 && tile.yIndex > 0 && tile.xIndex < HEXESACROSS - 1 && tile.yIndex < HEXESDOWN - 1) {
			Tile[] neighbors = new Tile[6];
			neighbors[0] = tiles.get(tile.yIndex + 1).get(tile.xIndex);
			neighbors[1] = tiles.get(tile.yIndex - 1).get(tile.xIndex);
			neighbors[2] = tiles.get(tile.yIndex).get(tile.xIndex + 1);
			neighbors[3] = tiles.get(tile.yIndex).get(tile.xIndex - 1);
			if(tile.xIndex%2==0){
				neighbors[4] = tiles.get(tile.yIndex - 1).get(tile.xIndex + 1);
				neighbors[5] = tiles.get(tile.yIndex - 1).get(tile.xIndex - 1);
			} else{
				neighbors[4] = tiles.get(tile.yIndex + 1).get(tile.xIndex + 1);
				neighbors[5] = tiles.get(tile.yIndex + 1).get(tile.xIndex - 1);
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
			seeds[i] = tiles.get(HEXESDOWN /(8/3)  + rng.nextInt(HEXESDOWN / 3))
					.get(HEXESACROSS / (8/3) + rng.nextInt(HEXESACROSS / 3));
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
				i = 0.18 /i;
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
		} else {
			cY = tile.yIndex * apothem * 2 + apothem;
			cX = tile.xIndex * 3 * apothem / Math.sqrt(3);
		}
		poly.moveTo(cX + side, cY);
		poly.lineTo(cX + side / 2, cY - apothem);
		poly.lineTo(cX - side / 2, cY - apothem);
		poly.lineTo(cX - side, cY);
		poly.lineTo(cX - side / 2, cY + apothem);
		poly.lineTo(cX + side / 2, cY + apothem);
		poly.closePath();
		g2d.fill(poly);
	}

	//ONLY FOR TESTING ETC
	private void fillTile(Graphics2D g2d, Tile tile, Color color) {
		g2d.setColor(color);
		double cX;
		double cY;
		double side = apothem * 2 / Math.sqrt(3);
		Path2D.Double poly = new Path2D.Double();
		if (tile.xIndex % 2 == 0) {
			cY = tile.yIndex * apothem * 2;
			cX = tile.xIndex * 3 * apothem / Math.sqrt(3);
		} else {
			cY = tile.yIndex * apothem * 2 + apothem;
			cX = tile.xIndex * 3 * apothem / Math.sqrt(3);
		}
		poly.moveTo(cX + side, cY);
		poly.lineTo(cX + side / 2, cY - apothem);
		poly.lineTo(cX - side / 2, cY - apothem);
		poly.lineTo(cX - side, cY);
		poly.lineTo(cX - side / 2, cY + apothem);
		poly.lineTo(cX + side / 2, cY + apothem);
		poly.closePath();
		g2d.fill(poly);
	}
	
	private void drawTile(Graphics2D g2d, Tile tile) {
		g2d.setColor(Color.BLACK);
		double cX;
		double cY;
		double side = apothem * 2 / Math.sqrt(3);
		Path2D.Double poly = new Path2D.Double();
		if (tile.xIndex % 2 == 0) {
			cY = tile.yIndex * apothem * 2;
			cX = tile.xIndex * 3 * apothem / Math.sqrt(3);
		} else {
			cY = tile.yIndex * apothem * 2 + apothem;
			cX = tile.xIndex * 3 * apothem / Math.sqrt(3);
		}
		poly.moveTo(cX + side, cY);
		poly.lineTo(cX + side / 2, cY - apothem);
		poly.lineTo(cX - side / 2, cY - apothem);
		poly.lineTo(cX - side, cY);
		poly.lineTo(cX - side / 2, cY + apothem);
		poly.lineTo(cX + side / 2, cY + apothem);
		poly.closePath();
		g2d.draw(poly);
	}
	

		/*
		 * fillTile(g2d, tiles.get(3).get(4), Color.RED); fillTile(g2d,
		 * tiles.get(2).get(4), Color.BLUE); fillTile(g2d, tiles.get(4).get(4),
		 * Color.BLUE); fillTile(g2d, tiles.get(3).get(5), Color.BLUE);
		 * fillTile(g2d, tiles.get(3).get(3), Color.BLUE); fillTile(g2d,
		 * tiles.get(2).get(3), Color.BLUE); fillTile(g2d, tiles.get(2).get(5),
		 * Color.BLUE);
		 */

	//	fillTile(g2d, tiles.get(26).get(25), Color.RED);
	//	for (Tile t : getAllNeighbors(tiles.get(26).get(25))) {
	//		fillTile(g2d, t, Color.CYAN);
	//	}
	}

