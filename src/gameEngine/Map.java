package gameEngine;

import graphicEngine.ShaderManager;

import java.util.ArrayList;
import java.util.Random;


public class Map{
	private ArrayList<ArrayList<Hexagon>> tiles = new ArrayList<ArrayList<Hexagon>>();
	ShaderManager shaderManager;
	public static final double apothem = 0.01;//really just hex constant
	public static final int HEXAGONSACROSS = (int) (0.65/apothem); //always prime cuz map gen is bad
	public static final int HEXAGONSDOWN = (int)(2.02/apothem);//both of these need to be stabalized to ln functions
	//distance across = 2f
	//distance across/apothem should equal number of tiles
	public String mapType;
	public int seedCount;
	private Hexagon[] seeds;
	private Random rng = new Random();
	public String[] maps = { "fractal", "soft", "disk", "stand", "trig" };
	
	
	public Map(){
		//shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		System.out.println(HEXAGONSDOWN);
		mapType = "fractal";
		seedCount = rng.nextInt(2) + 1;
		double q = 1;
		for(int i = 0; i < (HEXAGONSACROSS); i++){
			for(int j = 0; j < (HEXAGONSDOWN); j++){
			System.out.println(j);
			if(i == 0){
				tiles.get(j).add(0,new Hexagon(i,j,apothem));
				tiles.get(i).get(j).position.x = -1f;
				tiles.get(i).get(j).position.y = -1f;
			}else{
				tiles.get(i).add(j,new Hexagon(i, j,apothem));
				//basically all needs to be redone
				//is super intensive
				//something funny is up here
				//update for new for loop
				//flips out when i%HEXAGONSACROSS=0 for some reason
				if((i%(HEXAGONSACROSS)!=0)){
					tiles.get(i).get(j).position.x = (float) (tiles.get(i-1).get(j).position.x + (apothem/0.3*q));//0.375
					tiles.get(i).get(j).position.y = tiles.get(i-1).get(j).position.y;
				}else if (!(i%(HEXAGONSDOWN) == 0)){
					tiles.get(i).get(j).position.y = (float) (tiles.get(i-1).get(j).position.y + apothem);
					tiles.get(i).get(j).position.x = (float) (tiles.get(i-1).get(j).position.x -apothem/0.3/2*q);
					q *= -1;
				}else{
					System.out.println(i);
				}
			}
		}
		}
		//initMap(); cuz nullpointers
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
		for(int i = 1; i < tiles.size(); i++){
			for(int j = 1; j < tiles.size(); j++){
				tiles.get(i).get(j).update();
			}
		}

	}
	
	public void draw(){
		for(int i = 0; i < tiles.size(); i++){
			for(int j = 0; j < tiles.size(); j++){
			//System.out.println(tiles.get(i).isLand());
			if(tiles.get(i).get(j).isLand()){
				ShaderManager.landShader.start();
				ShaderManager.landShader.setUniform3f("pos",tiles.get(i).position);
				tiles.get(i).get(j).draw();	
				ShaderManager.landShader.stop();
			}else{ //looks like there is no land
				ShaderManager.waterShader.start();
				ShaderManager.waterShader.setUniform3f("pos",tiles.get(i).position);
				tiles.get(i).get(j).draw();	
				ShaderManager.waterShader.stop();	
			}
			}
		}
		

	}
	//looks like the old tiles were done based on position in array of nextInt and this doesnt have that.
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
