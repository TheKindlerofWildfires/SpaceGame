package gameEngine;

import java.util.Random;

public class Biome {
	public static final int UPPERFOREST = 1;
	public static final int LOWERFOREST = 2;
	public static final int MIDFOREST = 3;
	public static final int MUD = 4;
	public static final int SAND = 5;
	public static final int SANDSTONE = 6;
	public static final int ASH = 7;
	public static final int ROCK = 8;
	public static final int HIGHLAND = 9;
	public static final int DEEPFOREST = 10;
	public static final int THORNS = 11;
	public static final int UNDERBRUSH = 12;
	static Random rng = Map.rng;
	Map map;
	public Biome(Map map){
		this.map = map;
	}
	public static int getBiome(int elevation, String worldType, int moisture){
		int biome = 100;
		double eP = elevation/(double)Map.HEXESACROSS;
		double mP = 2*moisture/(double)Map.HEXESACROSS;
		//System.out.println(mP);
		double chance = Math.abs(rng.nextDouble());
		while (chance >0.6 || chance <0.4){
			chance = Math.abs(rng.nextDouble());
		}
		switch(worldType){
		case "telilic":
			if (mP>= chance && eP>=chance){
				biome = UPPERFOREST;
			}else if (mP>= chance && eP<=chance){
				biome = LOWERFOREST;
			}else if (mP<= chance && eP>=chance){
 				biome = MIDFOREST;
			}else if (mP<= chance && eP<=chance){
				biome = MUD;
			}else{
				biome = 100;
			}
			break;
		case "sapric":
			if (mP>= chance && eP>=chance){
				biome = HIGHLAND;
			}else if (mP>= chance && eP<=chance){
				biome = THORNS;
			}else if (mP<= chance && eP>=chance){
 				biome = DEEPFOREST;
			}else if (mP<= chance && eP<=chance){
				biome = UNDERBRUSH;
			}else{
				biome = 100;
			}
			break;
		case "worlic":
			if (mP>= chance && eP>=chance){
				biome = SANDSTONE;
			}else if (mP>= chance && eP<=chance){
				biome = SAND;
			}else if (mP<= chance && eP>=chance){
 				biome = ASH;
			}else if (mP<= chance && eP<=chance){
				biome = ROCK;
			}else{
				biome = 100;
			}
			break;
		default:
			System.err.println("Not a valid worldType");
		}
			
		return biome;
	}

	public boolean destinationTraversable(int xIndex, int yIndex) {
		switch(map.land[xIndex][yIndex]){
		case Map.SEED:
			return true;
		case Map.LAND:
			return true;
		case UPPERFOREST:
			return true;
		case LOWERFOREST:
			return true;
		case MIDFOREST:
			return true;
		case SAND:
			return true;
		case MUD:
			return true;
		case SANDSTONE:
			return true;
		case ASH:
			return true;
		case ROCK:
			return true;
		case HIGHLAND:
			return true;
		case THORNS:
			return true;
		case DEEPFOREST:
			return true;
		case UNDERBRUSH:
			return true;
		case Map.WATER:
			return false;
		default:
			System.err.println("NOT A BIOME");
			return false;
		}
	}
}
