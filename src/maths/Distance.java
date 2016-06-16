package maths;


public class Distance {
	public Distance() {
	}

	public double euclidDis(int[] attIndex, int[] tarIndex) {
		double disY = attIndex[1] - tarIndex[1];
		double disX = attIndex[0] - tarIndex[0];
		double disT = Math.sqrt((Math.pow(disY, 2) + Math.pow(disX, 2)));
		// System.out.println(disT);
		return disT;
	}

	public double manhattenDis(int[] attIndex, int[] tarIndex) {
		// doesnt work well on lowers
		double disY = Math.abs(attIndex[1] - tarIndex[1] - 1);
		double disX = Math.abs(attIndex[0] - tarIndex[0]);
		// System.out.println("y " +disY);
		// System.out.println("x " +disX);
		// double disT = disY+disX;
		double disT = 0;
		if (disY > disX) {
			disT = disY;
			// System.out.println(disY + "outY");
		} else if (disY < disX) {
			disT = disX;
			// System.out.println(disX + "outX");
		} else if (disY == disX) {
			disT = disX;
			// System.out.println(disX);
		}
		// int i = Map.elevation[attIndex[0]][attIndex[1]];
		// int j = Map.elevation[attIndex[0]][attIndex[1]];
		// double q = eleDis(i,j);
		// disT = Math.sqrt((Math.pow(q,2)+Math.pow(disT,2)));
		return disT;
	}

	public double largeAxis(int[] attIndex, int[] tarIndex) {
		//
		// why is y always o
		double disX = Math.abs(attIndex[1] - tarIndex[1] - 1);
		double disY = Math.abs(attIndex[0] - tarIndex[0] - 1);
		// System.out.println(disY +"x");
		// System.out.println(disX+"y");
		if (disY < disX) {
			return disY;
		} else {
			return disX;
		}
	}

	public double eleDis(int i, int j) {
		double disT = Math.abs(i - j);
		// System.out.println(disT);
		return disT;
	}
}
