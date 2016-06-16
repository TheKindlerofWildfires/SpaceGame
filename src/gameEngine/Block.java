package gameEngine;

import java.util.Random;

import combat.BuffHandler;
import entity.Entity;

public class Block {
	public static final int AIR = 0;
	public static final int LAND = 100;
	public static final int WATER = 20;
	public static final int SEED = 50;
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
	public static int setBlock(int elevation, int moisture){
		int block = 100;
		String worldType = Map.worldType;
		//worldType = "telilic";
		double eP = elevation/8.0;
		double mP = moisture/4.0;
		//System.out.println(mP);
		double chance = Math.abs(rng.nextDouble());
		while (chance >0.6 || chance <0.4){
			chance = Math.abs(rng.nextDouble());
		}
		//System.out.println(mP+ "," + eP);
		switch(worldType){
		case "telilic":
			if (mP>= chance && eP>=chance){
				
				block = UPPERFOREST;
			}else if (mP>= chance && eP<=chance){
				block = LOWERFOREST;
			}else if (mP<= chance && eP>=chance){
 				block = MIDFOREST;
			}else if (mP<= chance && eP<=chance){
				block = MUD;
			}else{
				block = 100;
			}
			break;
		case "sapric":
			if (mP>= chance && eP>=chance){
				block = HIGHLAND;
			}else if (mP>= chance && eP<=chance){
				block = THORNS;
			}else if (mP<= chance && eP>=chance){
 				block = DEEPFOREST;
			}else if (mP<= chance && eP<=chance){
				block = UNDERBRUSH;
			}else{
				block = 100;
			}
			break;
		case "worlic":
			if (mP>= chance && eP>=chance){
				block = SANDSTONE;
			}else if (mP>= chance && eP<=chance){
				block = SAND;
			}else if (mP<= chance && eP>=chance){
 				block = ASH;
			}else if (mP<= chance && eP<=chance){
				block = ROCK;
			}else{
				block = 100;
			}
			break;
		default:
			System.err.println("Not a valid worldType");
		}
			
		return block;
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
			//System.out.println(entity.getEntityHealth());
			break;
		case FRUIT_BUSH:
			if(BuffHandler.gatedEvent(30)){
			entity.addEntityInventory("fruit");
			}
			//System.out.println(entity.getEntityInventory());
			//entity.setEntityHunger(entity.getEntityHunger()+(double)1/6);
			//drop fruit eventually
			break;
		case REEDS:
			//make stealth
			break;
		default:
		}
	}
}

