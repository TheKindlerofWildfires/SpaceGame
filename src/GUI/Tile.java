package Simon;
import java.awt.Polygon;

@SuppressWarnings("serial")
public abstract class Tile extends Polygon {

	protected boolean land = false;
	protected boolean ocean = false;
	
	public Tile() {
		super();
	}

	public Tile(int[] xpoints, int[] ypoints, int npoints) {
		super(xpoints, ypoints, npoints);
	}

	public void setLand(boolean land) {
		this.land = land;
	}

	public boolean isLand() {
		return land;
	}

	public void setOcean(boolean ocean) {
		this.ocean = ocean;
	}

	public boolean isOcean() {
		return ocean;
	}
}
