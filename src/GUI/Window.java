package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
		f.addKeyListener(new UserInput());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocation(100, 100);
		f.getContentPane().add(new Canvas(), BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}
}

