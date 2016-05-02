package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gen.World;
import gen.WorldGenerator;

/**
 * Lacunarity: 1.5
 */

public class Window {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		JFrame f = new JFrame("Perlin");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocation(100, 100);
		World w = new World();
		WorldGenerator gen = new WorldGenerator(w);
		gen.initializeMap();
		Canvas c = new Canvas(w, gen);
		f.getContentPane().add(c, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}
	
}

