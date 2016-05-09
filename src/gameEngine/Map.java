package gameEngine;

import graphicEngine.ShaderManager;

import java.util.ArrayList;
import java.util.Random;

import GUI.Tile;


public class Map{
	private ArrayList<Hexagon> tiles = new ArrayList<Hexagon>();
	ShaderManager shaderManager;
	public static final int HEXAGONSACROSS = 101; //always prime
	public static final int HEXAGONSDOWN = 501;
	public String mapType;
	public int seedCount;
	private Hexagon[] seeds;
	private Random rng = new Random();
	public String[] maps = { "fractal", "soft", "disk", "stand", "trig" };
	
	
	public Map(){
		shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		double apothem = 0.01;
		double q = 1;
		for(int i = 0; i < (21000); i++){
			
			tiles.add(new Hexagon(apothem));
			if(i == 0){
				tiles.get(i).position.x = -1f;
				tiles.get(i).position.y = -1f;
			}else{
				//is super intensive
				//System.out.println(i);
				if(!(i%(HEXAGONSACROSS) == 0)){
					//System.out.println("ac");
					tiles.get(i).position.x = (float) (tiles.get(i-1).position.x + (apothem/0.375*q));
					tiles.get(i).position.y = tiles.get(i-1).position.y;
				}else if (!(i%(HEXAGONSDOWN) == 0)){
					tiles.get(i).position.y = (float) (tiles.get(i-1).position.y + apothem);
					tiles.get(i).position.x = (float) (tiles.get(i-1).position.x -apothem/0.375/2*q);
					q *= -1;
				}else{
					System.out.println(i);
				}
			}
		}
		initMap();
	}
	private Hexagon[] getAllNeighbors(Hexagon hex) {
		if (hex.xIndex > 0 && hex.yIndex > 0 && hex.xIndex < HEXAGONSACROSS - 1 && hex.yIndex < HEXAGONSDOWN - 1) {
			Hexagon[] neighborsx = new Hexagon[6];
			Hexagon[] neighborsy = new Hexagon[6];
			neighborsx[0] = tiles.get(hex.yIndex + 1);
			neighborsx[0] = tiles.get(hex.xIndex);
			neighborsx[1] = tiles.get(hex.yIndex - 1);
			neighborsy[1] = tiles.get(hex.xIndex);
			neighborsx[2] = tiles.get(hex.yIndex);
			neighborsy[2] = tiles.get(hex.xIndex + 1);
			neighborsx[3] = tiles.get(hex.yIndex);
			neighborsy[3] = tiles.get(hex.xIndex - 1);
			if(hex.xIndex%2==0){
				neighborsx[4] = tiles.get(hex.yIndex - 1);
				neighborsy[4] = tiles.get(hex.xIndex + 1);
				neighborsx[5] = tiles.get(hex.yIndex - 1);
				neighborsy[5] = tiles.get(hex.xIndex - 1);
			} else{
				neighborsx[4] = tiles.get(hex.yIndex + 1);
				neighborsy[4] = tiles.get(hex.xIndex + 1);
				neighborsx[5] = tiles.get(hex.yIndex + 1);
				neighborsy[5] = tiles.get(hex.xIndex - 1);
			}//other option is to make an pair class
			//cross your fingers boyz 
			return concat(neighborsx, neighborsy);
			
		} else {
			return new Hexagon[0];
		}
	}
	
	private Hexagon[] concat(Hexagon[] a, Hexagon[] b){
		int aLen = a.length;
		int bLen = b.length;
		Hexagon[] c= new Hexagon[aLen+bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
	
	public void update(){
		for(int i = 0; i < tiles.size(); i++){
			tiles.get(i).update();
		}

	}
	
	public void draw(){
		for(int i = 0; i < tiles.size(); i++){
			if(tiles.get(i).isLand()){
				ShaderManager.shader1.start();
				ShaderManager.shader1.setUniform3f("pos",tiles.get(i).position);
				tiles.get(i).draw();	
				ShaderManager.shader1.stop();
			}else{ //looks like there is no land
				ShaderManager.playerShader.start();
				ShaderManager.playerShader.setUniform3f("pos",tiles.get(i).position);
				tiles.get(i).draw();	
				ShaderManager.playerShader.stop();	
			}
		}
		

	}
	
	private void initMap(){
		seeds = new Hexagon[seedCount];
		for (int i = 0; i < seedCount; i++) {
			Hexagon[] seedsx = new Hexagon[seedCount];
			seedsx[i] = tiles.get(HEXAGONSDOWN /(8/3)  + rng.nextInt(HEXAGONSDOWN / 3));
			Hexagon[] seedsy = new Hexagon[seedCount];
			seedsy[i] =tiles.get(HEXAGONSACROSS / (8/3) + rng.nextInt(HEXAGONSACROSS / 3));
			
			seeds = concat(seedsy, seedsx);
			seeds[i].setLand(true);
		}
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
				i = 0.18 /i;
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
	}
}
