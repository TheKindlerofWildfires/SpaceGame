package gameEngine;

import java.util.ArrayList;
import java.util.Random;

public class WorldType {
	public static String worldType;
	private static float difficulty;
	private static ArrayList<String>hazards= new ArrayList<String>();
	private static ArrayList<String> adaptations= new ArrayList<String>();
	private static ArrayList<String> interference= new ArrayList<String>();
	private static Random rng = Map.rng;
	
	public WorldType(){
		//PLEASE PLEASE INIT ME
		worldType = Map.worldType; 
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
			interference.add("octividRuins");
			break;
		case "sapric":
			difficulty = 1;
			hazards.add("dry");
			hazards.add("exposed");
			hazards.add("tempHigh");
			hazards.add("sandStorm");
			adaptations.add("burrow");
			adaptations.add("plateArmor");
			adaptations.add("fast");
			adaptations.add("flashSwarm");
			interference.add("kinikariRuins");
			break;
		case "worlic":
			difficulty = 1;
			hazards.add("marsh");
			hazards.add("fog");
			hazards.add("bramble");
			hazards.add("mudSlide");
			adaptations.add("guerilla");
			adaptations.add("rage");
			adaptations.add("tracker");
			interference.add("jeneunGates");
			interference.add("jeneunCultists");
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
}
