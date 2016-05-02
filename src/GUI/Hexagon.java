package GUI;

/**
 *
 * apothem 22 looks good
 * 
 * Corners ----2---- -3-----1- --------- -4-----0- ----5----
 * 
 * Neighbors ---4---3--- ----------- -5-------2- ----------- ---0---1---
 * 
 */

@SuppressWarnings("serial")
public class Hexagon extends Tile {
	public static final int apothem = 2;

	protected int cX, cY;
	protected int scaleFactor=1;
	
	public Hexagon(int cX, int cY) {
		super();
		int[] xpts = new int[6];
		int[] ypts = new int[6];
		xpts[0] = cX + apothem;
		ypts[0] = (int) (cY - (apothem / Math.sqrt(3)));
		xpts[1] = cX + apothem;
		ypts[1] = (int) (cY + (apothem / Math.sqrt(3)));
		xpts[2] = cX;
		ypts[2] = (int) (cY + 2 * apothem / Math.sqrt(3));
		xpts[3] = cX - apothem;
		ypts[3] = (int) (cY + (apothem / Math.sqrt(3)));
		xpts[4] = cX - apothem;
		ypts[4] = (int) (cY - (apothem / Math.sqrt(3)));
		xpts[5] = cX;
		ypts[5] = (int) (cY - 2 * apothem / Math.sqrt(3));
		for (int i = 0; i < 6; i++) {
			this.addPoint(xpts[i], ypts[i]);
		}
		this.cX = cX;
		this.cY = cY;
	}
	
	public void changeScaleFactor(int scaleFactor){
		int apothem = scaleFactor*this.apothem;
		this.scaleFactor = scaleFactor;
		int cx=cX*scaleFactor;
		int cy=cY*scaleFactor;
		xpoints[0] = cx + apothem;
		ypoints[0] = (int) (cy - (apothem / Math.sqrt(3)));
		xpoints[1] = cx + apothem;
		ypoints[1] = (int) (cy + (apothem / Math.sqrt(3)));
		xpoints[2] = cx;
		ypoints[2] = (int) (cy + 2 * apothem / Math.sqrt(3));
		xpoints[3] = cx - apothem;
		ypoints[3] = (int) (cy + (apothem / Math.sqrt(3)));
		xpoints[4] = cx - apothem;
		ypoints[4] = (int) (cy - (apothem / Math.sqrt(3)));
		xpoints[5] = cx;
		ypoints[5] = (int) (cy - 2 * apothem / Math.sqrt(3));
	}
}