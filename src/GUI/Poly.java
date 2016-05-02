package GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Poly extends JPanel {

	private Path2D prettyPoly;

	public static void main(String[] args) {
		JFrame f = new JFrame("I can code i promise");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new Poly(), BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
		f.setLocation(0, 0);
		f.setSize(300, 300);
	}

	public Poly() {

		prettyPoly = new Path2D.Double();
		boolean isFirst = true;
	

		prettyPoly.moveTo(10.2, 20.1);
		prettyPoly.lineTo(105.8, 15.3);
		prettyPoly.lineTo(267.8, 132.3);
		prettyPoly.lineTo(19.8, 285.3);


		prettyPoly.closePath();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				System.out.println(prettyPoly.contains(p));

				repaint();
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.draw(prettyPoly);
		g2d.dispose();

	}
}