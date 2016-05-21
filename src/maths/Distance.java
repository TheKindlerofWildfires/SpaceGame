package maths;


public class Distance {
	public Distance(){
	}
	public double euclidDis(int[] attIndex, int[] tarIndex){
		double disY = attIndex[1]-tarIndex[1];
		double disX = attIndex[0]-tarIndex[0];
		double disT = Math.sqrt((Math.pow(disY,2)+Math.pow(disX,2)));
		//System.out.println(disT);
		return disT;
	}
	public double manhattenDis(int[] attIndex, int[] tarIndex){
		//doesnt work well on lowers
		double disY = Math.abs(attIndex[1]-tarIndex[1]);
		double disX = Math.abs(attIndex[0]-tarIndex[0]);
		double disT = disY+disX;
		//System.out.println(disT);
		return disT;
	}
}
