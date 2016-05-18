package maths;


public class Euclidian {
	public Euclidian(){
	}
	public double euclidDis(Vector3f start, Vector3f end){
		double disY = start.y-end.y;
		double disX = start.x-end.x;
		double disT = Math.sqrt((Math.pow(disY,2)+Math.pow(disX,2)));
		return disT;
	}
}
