package gen;

public class WorldGenTree {

    public int trunkHeight = 6;

    public void generate(int[][][] world, int x, int y, int z) {
        for (int dz = 0; dz < this.trunkHeight; dz++) {
            world[x][y][z + dz] = 100;
        }
    }
}
