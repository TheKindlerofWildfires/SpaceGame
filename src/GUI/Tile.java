package GUI;
import java.awt.Polygon;

@SuppressWarnings("serial")
public abstract class Tile extends Polygon {

	protected double moisture = 1;
	protected boolean land = false;
	protected boolean ocean = false;
	protected double elevation = 0;
	protected String biome = "WATER";
	
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
