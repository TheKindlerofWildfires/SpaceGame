package maths;

import gameEngine.Hexagon;

public class Euclidian {
	public Euclidian(){
	}
	public double euclidDis(Hexagon start, Hexagon end){
		double disY = start.yIndex-end.yIndex;
		double disX = start.xIndex-end.xIndex;
		double disT = Math.sqrt((Math.pow(disY,2)+Math.pow(disX,2)));
		return disT;
	}
}
