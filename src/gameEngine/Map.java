package gameEngine;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL31.glDrawArraysInstanced;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

import GUI.Vector3f;
import graphicEngine.ShaderManager;
import graphicEngine.Utilities;
import graphicEngine.VertexArrayObject;
import noiseLibrary.module.source.Perlin;

public class Map {
	public static final int HEXESACROSS = 320; //REMEMBER TO CHANGE TOTAL HEXES IN SHADER WHEN THESE CHANGE
	public static final int HEXESDOWN = 180;

	public static final int MOISTURESCALER = 12;
	public static final int ELEVATIONSCALER = 17;

	public static final float APOTHEM = 0.002f;

	public String mapType;
	public int seedCount;

	public String[] maps = { "fractal", "soft", "disk", "stand", "trig" };
	private ArrayList<ArrayList<Hexagon>> hexes = new ArrayList<ArrayList<Hexagon>>();
	private Hexagon[] seeds;

	ShaderManager shaderManager;

	private Random rng = new Random();

	public float sqrt3 = 1.7320508075688772f;
	public float aspectScaler = 16 / 9f;
	public float side = (float) (APOTHEM * 2 / sqrt3);

	public float[] vertices = { side, 0, 0, //right 0
			side / 2, -APOTHEM * aspectScaler, 0, // lower right 1
			-side / 2, -APOTHEM * aspectScaler, 0, //lower left 2
			-side, 0, 0, //left 3
			-side / 2, APOTHEM * aspectScaler, 0, //upper left 4
			side / 2, APOTHEM * aspectScaler, 0, //upper right 5
			0, 0, 0 //center 6
	};

	public byte[] indexes = new byte[] { 0, 1, 2, 3, 4, 5, 0 };
	public ByteBuffer indices = Utilities.createByteBuffer(indexes);
	public VertexArrayObject vao = new VertexArrayObject(vertices, indexes);
	public int vaoID = vao.getVaoID();

	public Map() {
		seedCount = rng.nextInt(2) + 1;
		///mapType = maps[rng.nextInt(maps.length)];
		mapType = "fractal";
		initializeMap();
		initShader();
	}

	private void initShader() {
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		shaderManager.shader1.start();
		shaderManager.shader1.setUniform1f("side", side);
		shaderManager.shader1.setUniform1i("hexesAcross", HEXESACROSS);
		shaderManager.shader1.setUniform1f("apothem", APOTHEM);
		shaderManager.shader1.setUniform1f("aspect", aspectScaler);
		shaderManager.shader1.setUniform3f("pos", new Vector3f(-1, 1, 0));
		int[] land = new int[HEXESACROSS*HEXESDOWN];
		for(int i=0;i<HEXESDOWN;i++){
			for(int j=0;j<HEXESACROSS;j++){
				land[i]= hexes.get(i).get(j).isLand()?GL_TRUE:GL_FALSE;
			}
		}
		shaderManager.shader1.setUniform1iv("land", Utilities.createIntBuffer(land));
		shaderManager.shader1.stop();
	}

	private Hexagon[] getAllNeighbors(Hexagon hex) {
		if (hex.xIndex > 0 && hex.yIndex > 0 && hex.xIndex < HEXESACROSS - 1 && hex.yIndex < HEXESDOWN - 1) {
			Hexagon[] neighbors = new Hexagon[6];
			neighbors[0] = hexes.get(hex.yIndex + 1).get(hex.xIndex);
			neighbors[1] = hexes.get(hex.yIndex - 1).get(hex.xIndex);
			neighbors[2] = hexes.get(hex.yIndex).get(hex.xIndex + 1);
			neighbors[3] = hexes.get(hex.yIndex).get(hex.xIndex - 1);
			if (hex.xIndex % 2 == 0) {
				neighbors[4] = hexes.get(hex.yIndex - 1).get(hex.xIndex + 1);
				neighbors[5] = hexes.get(hex.yIndex - 1).get(hex.xIndex - 1);
			} else {
				neighbors[4] = hexes.get(hex.yIndex + 1).get(hex.xIndex + 1);
				neighbors[5] = hexes.get(hex.yIndex + 1).get(hex.xIndex - 1);
			}
			return neighbors;
		} else {
			return new Hexagon[0];
		}
	}

	public void drawHex(Hexagon hex) {
		//	long time = System.nanoTime();
		//	ShaderManager.shader1.start();
		shaderManager.shader1.setUniform3f("pos", hex.position);
		Vector3f color = hex.isLand() ? new Vector3f(0f, 1.0f, 0f) : new Vector3f(0f, 0f, 1.0f);
		shaderManager.shader1.setUniform3f("color", color);
		//	long time2 = System.nanoTime();
		hex.draw();
		//	long time3 = System.nanoTime();
		//	ShaderManager.shader1.stop();
		//	long time4 = System.nanoTime();
		//	System.out.println("Shader Stuff: " + ((time2-time) + (time4-time3)));
		//	System.out.println("drawing: " + (time3-time2));
	}

	public void draw() {

		shaderManager.shader1.start();
		//.shader1.setUniform3f("color", new Vector3f(1,1,1));
		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);
		glDrawArraysInstanced(GL_TRIANGLE_FAN, 0, 6, HEXESACROSS * HEXESDOWN);
		//glDrawElementsInstanced(GL_TRIANGLE_FAN, indices, 1);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		shaderManager.shader1.stop();
	}

	private void initializeMap() {
		System.out.println("Initiailzing Tiles");
		// INIT TILES
		for (int i = 0; i <= HEXESACROSS; i++) {
			hexes.add(new ArrayList<Hexagon>());
		}

		Perlin noise = new Perlin();
		noise.setSeed(rng.nextInt(1000000));
		for (int j = 0; j < HEXESDOWN; j++) {
			for (int i = 0; i < HEXESACROSS; i++) {
				hexes.get(j)
						.add(new Hexagon(i, j,
								(float) Math.abs(noise.getValue(i / MOISTURESCALER, j / MOISTURESCALER, .1)),
								(float) Math.abs(noise.getValue(i / ELEVATIONSCALER, j / ELEVATIONSCALER, .3))));
			}
		}

		// INIT SEEDS
		System.out.println("Initiailzing Seeds");

		seeds = new Hexagon[seedCount];
		for (int i = 0; i < seedCount; i++) {
			seeds[i] = hexes.get(HEXESDOWN / (8 / 3) + rng.nextInt(HEXESDOWN / 3))
					.get(HEXESACROSS / (8 / 3) + rng.nextInt(HEXESACROSS / 3));
			seeds[i].setLand(true);
		}

		// GEN LAND
		System.out.println("Genning Land");
		ArrayList<Hexagon> outerLand = new ArrayList<Hexagon>();
		for (Hexagon s : seeds) {
			for (Hexagon i : getAllNeighbors(s)) {
				i.setLand(true);
				outerLand.add(i);
			}
		}
		double i = 1;
		while (outerLand.size() != 0 && i != 0) {
			ArrayList<Hexagon> newLand = new ArrayList<Hexagon>();
			for (int j = 0; j < outerLand.size(); j++) {

				for (Hexagon k : getAllNeighbors(outerLand.get(j))) {

					if (!k.isLand()) {
						if (rng.nextDouble() <= i) {
							k.setLand(true);
							newLand.add(k);
						}
					}
				}
			}
			outerLand.clear();
			outerLand.addAll(newLand);
			newLand.clear();

			switch (mapType) {
			case "fractal":
				i = 0.29 / (Math.log(i + 2));
				break;
			case "soft":
				i = 0.18 / i;
				break;
			case "disk":
				i = Math.pow(2.618, -2.47 * i);
				break;
			case "stand":
				i = 0.988 * i;
				break;
			case "trig":
				i = Math.cos(1.443 * i);
				break;
			default:
				System.err.println("invalid map type");
				i = 0;
			}
		}

		/*hexes.get(10).get(10).setLand(true);
		hexes.get(11).get(10).setLand(true);
		hexes.get(12).get(10).setLand(true);
		hexes.get(13).get(10).setLand(true);
		hexes.get(14).get(10).setLand(true);
		hexes.get(15).get(10).setLand(true);
		
		hexes.get(13).get(10).setLand(true);
		hexes.get(13).get(11).setLand(true);
		hexes.get(13).get(12).setLand(true);
		hexes.get(13).get(13).setLand(true);
		
		hexes.get(10).get(13).setLand(true);
		hexes.get(11).get(13).setLand(true);
		hexes.get(12).get(13).setLand(true);
		hexes.get(13).get(13).setLand(true);
		hexes.get(14).get(13).setLand(true);
		hexes.get(15).get(13).setLand(true);
		
		hexes.get(10).get(15).setLand(true);
		hexes.get(12).get(15).setLand(true);
		hexes.get(13).get(15).setLand(true);
		hexes.get(14).get(15).setLand(true);
		hexes.get(15).get(15).setLand(true);*/

		System.out.println("Map init complete");
	}
}
