package gen;

import java.util.ArrayList;

import GUI.LargeHexTile;

public class World {

	public int HEXESACROSS = 200;
	public int HEXESDOWN = 200;
	
	public ArrayList<ArrayList<LargeHexTile>> hexes = new ArrayList<ArrayList<LargeHexTile>>();
	
	public World() {
		for (int i = 0; i < HEXESACROSS; i++) {
			hexes.add(new ArrayList<LargeHexTile>());
		}
		int apothem = LargeHexTile.apothem;
		for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				if (j % 2 == 0) {
					hexes.get(j).add(new LargeHexTile(i * 2 * apothem, (int) (j * 3 * apothem / Math.sqrt(3))));
				} else {
					hexes.get(j)
							.add(new LargeHexTile(i * 2 * apothem + apothem, (int) (j * 3 * apothem / Math.sqrt(3))));
				}
			}
		}
		for (int i = 0; i < HEXESACROSS; i++) {
			for (int j = 0; j < HEXESDOWN; j++) {
				if (j != 0 && i != 0 && j < (199) && i < (199)) {
					if (j % 2 == 0) {
						hexes.get(j).get(i).neighbors = new LargeHexTile[6];
						hexes.get(j).get(i).setLeftNeighbor(hexes.get(j).get(i - 1));
						hexes.get(j).get(i).setRightNeighbor(hexes.get(j).get(i + 1));
						hexes.get(j).get(i).setUpperRightNeighbor(hexes.get(j - 1).get(i - 1));
						hexes.get(j).get(i).setUpperLeftNeighbor(hexes.get(j - 1).get(i));
						hexes.get(j).get(i).setLowerLeftNeighbor(hexes.get(j + 1).get(i));
						hexes.get(j).get(i).setLowerRightNeighbor(hexes.get(j + 1).get(i - 1));

					} else {
						hexes.get(j).get(i).neighbors = new LargeHexTile[6];
						hexes.get(j).get(i).setLeftNeighbor(hexes.get(j).get(i - 1));
						hexes.get(j).get(i).setRightNeighbor(hexes.get(j).get(i + 1));
						hexes.get(j).get(i).setUpperRightNeighbor(hexes.get(j - 1).get(i + 1));
						hexes.get(j).get(i).setUpperLeftNeighbor(hexes.get(j - 1).get(i));
						hexes.get(j).get(i).setLowerLeftNeighbor(hexes.get(j + 1).get(i));
						hexes.get(j).get(i).setLowerRightNeighbor(hexes.get(j + 1).get(i + 1));
					}
				} else if (i == 0 && j == 0) {
					hexes.get(j).get(i).neighbors = new LargeHexTile[3];
					hexes.get(j).get(i).setLowerLeftNeighbor(hexes.get(j + 1).get(i));
					hexes.get(j).get(i).setLowerRightNeighbor(hexes.get(j + 1).get(i + 1));
					hexes.get(j).get(i).setRightNeighbor(hexes.get(j).get(i + 1));
				} else if (j == 0 && i == 199) {
					hexes.get(j).get(i).neighbors = new LargeHexTile[2];
					hexes.get(j).get(i).setLowerRightNeighbor(hexes.get(j).get(i - 1));
					hexes.get(j).get(i).setLowerLeftNeighbor(hexes.get(j + 1).get(i));
				} else {
					hexes.get(j).get(i).neighbors = new LargeHexTile[0];
				}
			}
		}
	}

}
