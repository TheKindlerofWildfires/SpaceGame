package GUI;
import java.awt.BorderLayout;
import java.awt.DisplayMode;

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
		Canvas c = new Canvas();
		f.getContentPane().add(c, BorderLayout.CENTER);
		f.pack();
		f.setVisible(true); //true
		f.setLocation(0, 0);
		
		//x.init();
		//Render r = new Render("Null");
		//r.drawEntity();
		//Mechanics m = new Mechanics();
		//m.attackHandler("Neo","Juggernaut");
		/*for (int x = 0; x<100; x++){
			m.attackHandler("Neo", "Tank");
			m.attackHandler("Tank", "Neo");
		}*/
		
	}
	
}

