package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import gen.World;
import gen.WorldGenerator;

/**
 * OUTER LAYER NO NEIGHBOR
 * 
 * @author Simon paintComponent calls on screen refresh
 */

@SuppressWarnings("serial")
public class Canvas extends JPanel {
	

	
	public World world;
	public WorldGenerator gen;

	public Canvas(World world, WorldGenerator gen) {
		// start keyboardinput stuff
		this.world = world;
		this.gen = gen;
		setLayout(null);
		/*
		 * maps[5] = "disk";
		 */
		//mapType = maps[rng.nextInt(maps.length)];

		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.BLACK);
	}

	public Dimension getPreferredSize() {
		return new Dimension(1920, 1080);
	}

	public void drawHex(Graphics2D g2d, LargeHexTile hex) {
		g2d.drawLine(hex.xpoints[0], hex.ypoints[0], hex.xpoints[1], hex.ypoints[1]);
		g2d.drawLine(hex.xpoints[3], hex.ypoints[3], hex.xpoints[4], hex.ypoints[4]);
		g2d.drawLine(hex.xpoints[4], hex.ypoints[4], hex.xpoints[5], hex.ypoints[5]);
		g2d.drawLine(hex.xpoints[5], hex.ypoints[5], hex.xpoints[0], hex.ypoints[0]);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		//System.out.println("it");

		g2d.setColor(Color.GREEN);
		world.hexes.stream().forEach(l -> l.stream().filter(h -> h.isLand()).forEach(h -> g2d.fillPolygon(h)));

		g2d.setColor(Color.BLUE);
		world.hexes.stream().forEach(l -> l.stream().filter(h -> !h.isLand()).forEach(h -> g2d.fillPolygon(h)));

		g2d.setColor(Color.BLACK);
		world.hexes.stream().forEach(l -> l.stream().forEach(h -> drawHex(g2d, h)));

		g2d.setColor(Color.MAGENTA);
		g2d.fill(gen.seed);
		g2d.setColor(Color.ORANGE);
		if (gen.seedCount >= 2) {
			g2d.fill(gen.seed2);
		}
		if (gen.seedCount >= 3) {
			g2d.fill(gen.seed3);
		}

		gen.seed.setLand(true);
		System.out.println(gen.seedCount);
		System.out.println(gen.mapType);

	}
}
