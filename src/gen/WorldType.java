package gen;

import gameEngine.Map;
import graphicEngine.Chunk;

import java.util.ArrayList;
import java.util.Random;

public class WorldType {
	public static String worldType;
	public static float difficulty;
	public static ArrayList<String>hazards= new ArrayList<String>();
	public static ArrayList<String> adaptations= new ArrayList<String>();
	public static ArrayList<String> interference= new ArrayList<String>();
	public static Random rng = Map.rng;
	public static final String[] worldTypes = { "telilic", "sapric", "worlic" };
	
	
	public static int height = 0;
	public static int elevationScaler = 0;
	public static int moistureScaler = 0;
	public static int waterLevel;
	public static double cloudCover = 1;
	public WorldType(){
		worldType = worldTypes[rng.nextInt(worldTypes.length)];
		System.out.println(worldType);
	    //worldType = "sapric";
		switch(worldType){
		case "telilic":
			difficulty = 1;
			hazards.add("heavyForest");
			hazards.add("overGrowth");
			hazards.add("mountainous");
			hazards.add("excessRain");
			adaptations.add("poison");
			adaptations.add("treeClimb");
			adaptations.add("camo");
			adaptations.add("nightHunter");
			interference.add("octividRuinsLeast");
			height = (int) (Chunk.CHUNKHEIGHT/2);
			elevationScaler = 70;
			moistureScaler = 20;
			waterLevel = height/5;
			cloudCover = 0.997;
			break;
		case "worlic":
			difficulty = 1;
			hazards.add("dry");
			hazards.add("exposed");
			hazards.add("tempHigh");
			hazards.add("sandStorm");
			adaptations.add("burrow");
			adaptations.add("plateArmor");
			adaptations.add("fast");
			adaptations.add("flashSwarm");
			interference.add("kinikariRuinsLeast");
			height = (int) (Chunk.CHUNKHEIGHT/3);
			elevationScaler = 200;
			moistureScaler = 20;
			waterLevel = height/6;
			cloudCover = 0.999;
			break;
		case "sapric":
			difficulty = 1;
			hazards.add("marsh");
			hazards.add("fog");
			hazards.add("bramble");
			hazards.add("mudSlide");
			adaptations.add("guerilla");
			adaptations.add("rage");
			adaptations.add("tracker");
			interference.add("jeneunGateLeast");
			interference.add("jeneunRuinsLeast");
			height = (int) (Chunk.CHUNKHEIGHT/1.5);
			elevationScaler = 50;
			moistureScaler = 40;
			waterLevel = height/4;
			cloudCover = 0.998;
			break;
		default:
				System.err.println("Not a worldType");
				System.exit(-1);
		}
	}
	public float getDifficulty(){
		return difficulty;
	}
	public static String getRandomHazard(){
		return hazards.get(rng.nextInt(hazards.size()));
	}
	public static String getRandomAdaptation(){
		return adaptations.get(rng.nextInt(adaptations.size()));
	}
	public static String getRandomInterference(){
		return interference.get(rng.nextInt(interference.size()));
	}
	public static int getHeight(){
		return height;
	}
	public static double getElevationScaler() {
		return elevationScaler;
	}
	public static int getWaterLevel() {
		return waterLevel;
	}
	public static int getMoistureScaler() {
		return moistureScaler;
	}

}
