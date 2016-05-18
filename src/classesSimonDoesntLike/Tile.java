package classesSimonDoesntLike;

public class Tile {

	private boolean land;
	private double moisture;
	private double elevation;
	
	public int xIndex;
	public int yIndex;
	
	public Tile(int xIndex, int yIndex, double moisture, double elevation) {
		this.moisture=moisture;
		this.elevation=elevation;
		this.xIndex=xIndex;
		this.yIndex=yIndex;
	}
	
	public void setLand(boolean land){
		this.land = land;
	}	
	
	public boolean isLand(){
		return land;
	}
}
