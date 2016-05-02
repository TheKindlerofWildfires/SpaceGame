package GUI;
@SuppressWarnings("serial")
public class SmallTile extends Tile{

	public SmallTile(int[] xpts, int[] ypts, int npts, boolean isLand, boolean isOcean) { 
		super(xpts, ypts, npts);
		this.land = isLand;
		this.ocean = isOcean;
	}
	
	public SmallTile(boolean isLand, boolean isOcean){
		super();
	}

}
