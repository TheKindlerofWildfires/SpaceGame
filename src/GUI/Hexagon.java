package GUI;

import java.awt.Polygon;

/**
 *
 * apothem 22 looks good
 * 
 * Corners
 * ----2----
 * -3-----1-
 * ---------
 * -4-----0-
 * ----5----
 * 
 * Neighbors
 * ---4---3---
 * -----------
 * -5-------2-
 * -----------
 * ---0---1---
 * 
 */

@SuppressWarnings("serial")
public class Hexagon extends Polygon{
	public static final int apothem = 3;
	
	Hexagon[] neighbors;
	
	private boolean land = false;
	private boolean ocean = false;
	
	public Hexagon(int cX,int cY){
		super();
		int[] xpts = new int[6];
		int[] ypts = new int[6];
		xpts[0]=cX+apothem;
		ypts[0]=(int) (cY-(apothem/Math.sqrt(3)));
		xpts[1]=cX+apothem;
		ypts[1]=(int) (cY+(apothem/Math.sqrt(3)));
		xpts[2]=cX;
		ypts[2]=(int) (cY+2*apothem/Math.sqrt(3));
		xpts[3]=cX-apothem;
		ypts[3]=(int) (cY+(apothem/Math.sqrt(3)));
		xpts[4]=cX-apothem;
		ypts[4]=(int) (cY-(apothem/Math.sqrt(3)));
		xpts[5]=cX;
		ypts[5]=(int) (cY-2*apothem/Math.sqrt(3));
		for(int i=0;i<6;i++){
			this.addPoint(xpts[i], ypts[i]);
		}
	}
	public Hexagon[] getNeighbors(){
		return neighbors;
	}
	
	public void setUpperRightNeighbor(Hexagon hex){
		neighbors[3] = hex;
	}
	
	public void setLowerRightNeighbor(Hexagon hex){
		neighbors[1] = hex;
	}
	
	public void setLowerLeftNeighbor(Hexagon hex){
		neighbors[0] = hex;
	}
	
	public void setUpperLeftNeighbor(Hexagon hex){
		neighbors[4] = hex;
	}
	
	public void setRightNeighbor(Hexagon hex){
		neighbors[2] = hex;
	}
	
	public void setLeftNeighbor(Hexagon hex){
		neighbors[5] = hex;
	}
	
	public void setLand(boolean land){
		this.land=land;
	}
	
	public boolean isLand(){
		return land;
	}
	
	public void setOcean(boolean ocean){
		this.ocean=ocean;
	}
	
	public boolean isOcean(){
		return ocean;
	}
}