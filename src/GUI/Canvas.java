package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * OUTER LAYER NO NEIGHBOR
 * 
 * @author Simon paintComponent calls on screen refresh
 */

@SuppressWarnings("serial")
public class Canvas extends JPanel {
	public static int HEXESACROSS = 200;
	public static int HEXESDOWN = 200;
	public String mapType;
	public int seedCount;
	public String[] maps;
	private ArrayList<ArrayList<Hexagon>> hexes = new ArrayList<ArrayList<Hexagon>>();
	private Random rng = new Random();

	public Canvas() {
		InputMap inKeys = getInputMap(WHEN_FOCUSED);
		ActionMap acKeys = getActionMap();

		inKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "onEnter");
		acKeys.put("onEnter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Enter pressed
			}
		});
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
			hexes.add(new ArrayList<Hexagon>());
		}
		int apothem = Hexagon.apothem;
		for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				if (j % 2 == 0) {
					hexes.get(j).add(
							new Hexagon(i * 2 * apothem,
									(int) (j * 3 * apothem / Math.sqrt(3))));
				} else {
					hexes.get(j).add(
							new Hexagon(i * 2 * apothem + apothem, (int) (j * 3
									* apothem / Math.sqrt(3))));
				}
			}
		}
		for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				if (j != 0 && i != 0 && j < (199) && i < (199)) {
					if (j % 2 == 0) {
						hexes.get(j).get(i).neighbors = new Hexagon[6];
						hexes.get(j).get(i)
								.setLeftNeighbor(hexes.get(j).get(i - 1));
						hexes.get(j).get(i)
								.setRightNeighbor(hexes.get(j).get(i + 1));
						hexes.get(j)
								.get(i)
								.setUpperRightNeighbor(
										hexes.get(j - 1).get(i - 1));
						hexes.get(j).get(i)
								.setUpperLeftNeighbor(hexes.get(j - 1).get(i));
						hexes.get(j).get(i)
								.setLowerLeftNeighbor(hexes.get(j + 1).get(i));
						hexes.get(j)
								.get(i)
								.setLowerRightNeighbor(
										hexes.get(j + 1).get(i - 1));

					} else {
						hexes.get(j).get(i).neighbors = new Hexagon[6];
						hexes.get(j).get(i)
								.setLeftNeighbor(hexes.get(j).get(i - 1));
						hexes.get(j).get(i)
								.setRightNeighbor(hexes.get(j).get(i + 1));
						hexes.get(j)
								.get(i)
								.setUpperRightNeighbor(
										hexes.get(j - 1).get(i + 1));
						hexes.get(j).get(i)
								.setUpperLeftNeighbor(hexes.get(j - 1).get(i));
						hexes.get(j).get(i)
								.setLowerLeftNeighbor(hexes.get(j + 1).get(i));
						hexes.get(j)
								.get(i)
								.setLowerRightNeighbor(
										hexes.get(j + 1).get(i + 1));
					}
				} else if (i == 0 && j == 0) {
					hexes.get(j).get(i).neighbors = new Hexagon[3];
					hexes.get(j).get(i)
							.setLowerLeftNeighbor(hexes.get(j + 1).get(i));
					hexes.get(j).get(i)
							.setLowerRightNeighbor(hexes.get(j + 1).get(i + 1));
					hexes.get(j).get(i)
							.setRightNeighbor(hexes.get(j).get(i + 1));
				} else if (j == 0 && i == 199) {
					hexes.get(j).get(i).neighbors = new Hexagon[2];
					hexes.get(j).get(i)
							.setLowerRightNeighbor(hexes.get(j).get(i - 1));
					hexes.get(j).get(i)
							.setLowerLeftNeighbor(hexes.get(j + 1).get(i));
				}
			}
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(1920, 1080);
	}

	public void drawHex(Graphics2D g2d, Hexagon hex) {
		g2d.drawLine(hex.xpoints[0], hex.ypoints[0], hex.xpoints[1],
				hex.ypoints[1]);
		g2d.drawLine(hex.xpoints[3], hex.ypoints[3], hex.xpoints[4],
				hex.ypoints[4]);
		g2d.drawLine(hex.xpoints[4], hex.ypoints[4], hex.xpoints[5],
				hex.ypoints[5]);
		g2d.drawLine(hex.xpoints[5], hex.ypoints[5], hex.xpoints[0],
				hex.ypoints[0]);

	}

	/*
	 * public void addKeyListener(KeyListener keyboard){
	 * 
	 * }
	 */

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		System.out.println("it");

		Hexagon seed = hexes
				.get(HEXESACROSS / 4 + rng.nextInt(HEXESACROSS / 2)).get(
						HEXESDOWN / 4 + rng.nextInt(HEXESDOWN / 2));
		Hexagon seed2 = hexes.get(
				HEXESACROSS / 4 + rng.nextInt(HEXESACROSS / 2)).get(
				HEXESDOWN / 4 + rng.nextInt(HEXESDOWN / 2));
		Hexagon seed3 = hexes.get(
				HEXESACROSS / 4 + rng.nextInt(HEXESACROSS / 2)).get(
				HEXESDOWN / 4 + rng.nextInt(HEXESDOWN / 2));
		seed.setLand(true);
		if (seedCount >= 2) {
			seed2.setLand(true);
		}
		if (seedCount >= 3) {
			seed3.setLand(true);
		}

		ArrayList<Hexagon> outerLand = new ArrayList<Hexagon>();
		for (Hexagon i : seed.getNeighbors()) {
			i.setLand(true);
			outerLand.add(i);
		}
		for (Hexagon i : seed2.getNeighbors()) {
			if (seedCount >= 2) {
				i.setLand(true);
				outerLand.add(i);
			}

		}
		for (Hexagon i : seed3.getNeighbors()) {
			if (seedCount >= 3) {
				i.setLand(true);
				outerLand.add(i);
			}
		}
		double i = 1; // changed
		// int to double and i from 0
		while (outerLand.size() != 0) {
			ArrayList<Hexagon> newLand = new ArrayList<Hexagon>();
			for (int j = 0; j < outerLand.size(); j++) {

				for (Hexagon k : outerLand.get(j).getNeighbors()) {

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
				i = Math.pow(2.618, -2.47 * i)
						/ (0.01 * (seedCount - 1) + 1.01);
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

		ArrayList<Hexagon> outerOcean = new ArrayList<Hexagon>();
		outerOcean.add(hexes.get(0).get(0));
		outerOcean.add(hexes.get(0).get(HEXESACROSS - 1));
		outerOcean.add(hexes.get(HEXESDOWN - 1).get(HEXESACROSS - 1));
		outerOcean.add(hexes.get(HEXESDOWN - 1).get(0));

		// while(outerOcean.size()!=0){

		// }
		// if len(land)>= 500{ regen}

		g2d.setColor(Color.GREEN);
		hexes.stream().forEach(
				l -> l.stream().filter(h -> h.isLand())
						.forEach(h -> g2d.fillPolygon(h)));

		g2d.setColor(Color.BLUE);
		hexes.stream().forEach(
				l -> l.stream().filter(h -> !h.isLand())
						.forEach(h -> g2d.fillPolygon(h)));

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

		seed.setLand(true);
		System.out.println(seedCount);
		System.out.println(mapType);

	}

}
