package gameEngine;

import java.util.Random;

import entity.Entity;

public class Block {
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
	public static final int JG_TREE = 13;
	public static final int ASH_TREE = 14;
	public static final int PALM_TREE = 15;
	public static final int FRUIT_BUSH = 16;
	public static final int THORN_BUSH = 17;
	public static final int REEDS = 18;
	
	static Random rng = Map.rng;
	public Block(){
	}
	public static int getBlock(int elevation, String worldType, int moisture){
		int biome = 100;
		double eP = 10*elevation/(double)Map.HEXESACROSS;
		double mP = 10*moisture/(double)Map.HEXESACROSS;
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

	public static boolean destinationTraversable(int xIndex, int yIndex) {
		switch(Map.land[xIndex][yIndex]){
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
		case JG_TREE:
			return true;
		case ASH_TREE:
			return true;
		case PALM_TREE:
			return true;
		case FRUIT_BUSH:
			return true;
		case THORN_BUSH:
			return true;
		case REEDS:
			return true;
		default:
			System.err.println("NOT A BLOCK");
			return false;
		}
	}
	public static void steppedOn(int xIndex, int yIndex, Entity entity) {
		switch(Map.land[xIndex][yIndex]){
		case THORN_BUSH:
			entity.setEntityHealth(entity.getEntityHealth()-(double)1/3);
			break;
		case FRUIT_BUSH:
			entity.setEntityHunger(entity.getEntityHunger()+(double)1/6);
			//drop fruit eventually
			break;
		case REEDS:
			//make stealth
			break;
		default:
		}
	}
}

