package gameEngine;

public class WorldGenerator {



    public int[][][] data = new int[Map.HEXESACROSS][Map.HEXESDOWN][Map.WORLDHEIGHT];

    public WorldGenerator() {

    }

    public int[][][] generate() {
        for (int x = 0; x < Map.HEXESACROSS; x++) {
            for (int y = 0; y < Map.HEXESDOWN; y++) {
                for (int z = 1; z < Map.WORLDHEIGHT; z++) {
                    data[x][y][z] = 1;
                }
                data[x][y][15] = 1;
            }
        }
        return data;
    }

}
