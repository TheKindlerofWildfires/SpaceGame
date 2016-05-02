package Simon;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class LargeHexTile extends Hexagon {

	ArrayList<SmallTile> innerTiles = new ArrayList<SmallTile>(7);

	LargeHexTile[] neighbors;

	public LargeHexTile(int cX, int cY) {
		super(cX, cY);
		initializeInnerTiles();
	}

	private void initializeInnerTiles() {

		double apothem = Hexagon.apothem * Math.sqrt(1 / (3 * Math.sqrt(3) + 1));
		int[] xpts = new int[6];
		int[] ypts = new int[6];
		xpts[0] = (int) (cX + apothem);
		ypts[0] = (int) (cY - (apothem / Math.sqrt(3)));
		xpts[1] = (int) (cX + apothem);
		ypts[1] = (int) (cY + (apothem / Math.sqrt(3)));
		xpts[2] = cX;
		ypts[2] = (int) (cY + 2 * apothem / Math.sqrt(3));
		xpts[3] = (int) (cX - apothem);
		ypts[3] = (int) (cY + (apothem / Math.sqrt(3)));
		xpts[4] = (int) (cX - apothem);
		ypts[4] = (int) (cY - (apothem / Math.sqrt(3)));
		xpts[5] = cX;
		ypts[5] = (int) (cY - 2 * apothem / Math.sqrt(3));

		SmallTile inner = new SmallTile(land, ocean);

		for (int i = 0; i < ypts.length; i++) {
			inner.addPoint(xpts[i], ypts[i]);
		}
		innerTiles.add(inner);

		for (int i = 0; i < 5; i++) {
			SmallTile in = new SmallTile(land, ocean);
			in.addPoint(xpts[i], ypts[i]);
			in.addPoint(xpts[i + 1], ypts[i + 1]);
			in.addPoint(this.xpoints[i + 1], this.ypoints[i + 1]);
			in.addPoint(this.xpoints[i], this.ypoints[i]);
			innerTiles.add(in);
		}
	}
	
	public LargeHexTile[] getNeighbors() {
		return neighbors;
	}

	public void setUpperRightNeighbor(LargeHexTile hex) {
		neighbors[3] = hex;
	}

	public void setLowerRightNeighbor(LargeHexTile hex) {
		neighbors[1] = hex;
	}

	public void setLowerLeftNeighbor(LargeHexTile hex) {
		neighbors[0] = hex;
	}

	public void setUpperLeftNeighbor(LargeHexTile hex) {
		neighbors[4] = hex;
	}

	public void setRightNeighbor(LargeHexTile hex) {
		neighbors[2] = hex;
	}

	public void setLeftNeighbor(LargeHexTile hex) {
		neighbors[5] = hex;
	}
}
