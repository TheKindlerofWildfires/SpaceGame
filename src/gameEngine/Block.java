 package gameEngine;

import java.util.Random;

import combat.BuffHandler;
import entity.Entity;
import gen.WorldType;

public class Block {
	public static final int AIR = 0;
	public static final int LAND = 100;
	public static final int WATER = 128;
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
	public static final int JG_LEAF = 14;
	public static final int BIRCH_TREE = 15;
	public static final int BIRCH_LEAF = 16;
	public static final int PALM_TREE = 17;
	public static final int PALM_LEAF = 18;
	public static final int FRUIT_BUSH = 19;
	public static final int THORN_BUSH = 20;
	public static final int REEDS = 21;
	public static final int OCTO_WALL = 22;
	public static final int OCTO_ROOF = 23;
	public static final int JENEUN_WALL = 24;
	public static final int JENEUN_ROOF = 25;
	public static final int KINIKARI_WALL = 26;
	public static final int KINIKARI_ROOF = 27;
	public static final int CLOUD = 28;
	static Random rng = Map.rng;
	public static final int[] gen= {LAND, AIR,SEED};
	public static final int[] natural = {WATER, UPPERFOREST, LOWERFOREST, 
		MIDFOREST, MUD, SAND, SANDSTONE, ASH, ROCK, HIGHLAND, DEEPFOREST, THORNS, UNDERBRUSH
	};
	public static final int[] foliage =  {JG_TREE, JG_LEAF,BIRCH_TREE, BIRCH_LEAF, PALM_TREE, PALM_LEAF, FRUIT_BUSH, THORN_BUSH, REEDS};
	public static final int[] octo = {OCTO_WALL, OCTO_ROOF};
	public static final int[] jenuen = {JENEUN_WALL, JENEUN_ROOF};
	public static final int[] kinikari = {KINIKARI_WALL, KINIKARI_ROOF};
	
	public Block(){
	}
	public static int setBlock(int moisture){
		int block = 100;
		String worldType = WorldType.worldType;
		//System.out.println(moisture);
		//double eP = elevation/8.0;
		//double mP = moisture/4.0;
		//System.out.println(mP);
		//double chance = Math.abs(rng.nextDouble());
		//chance = 0.5;
		//System.out.println(mP+ "," + eP);
		switch(worldType){
		case "telilic":
			if (moisture ==0){
				block = LOWERFOREST;
			}else if (moisture ==1){
				block = MUD;
			}else if (moisture ==2){
 				block = MIDFOREST;
			}else if (moisture ==3){
				block = UPPERFOREST;
			}else{
				block = 100;
			}
			break;
		case "sapric":
			if (moisture ==0){
				block = DEEPFOREST;
			}else if (moisture ==1){
				block = UNDERBRUSH;
			}else if (moisture ==2){
 				block = HIGHLAND;
			}else if (moisture ==3){
				block = THORNS;
			}else{
				block = 100;
			}
			break;
		case "worlic":
			if (moisture ==0){
				block = ROCK;
			}else if (moisture ==1){
				block = SANDSTONE;
			}else if (moisture ==2){
 				block = ASH;
			}else if (moisture ==3){
				block = SAND;
			}else{
				block = 100;
			}
			break;
		default:
			System.err.println("Not a valid worldType");
		}
			
		return block;
	}

	public static boolean destinationTraversable(int xIndex, int yIndex, int zIndex,Entity entity) {
		switch(Map.land[xIndex][yIndex][zIndex]){
		case SEED:
			return true;
		case LAND:
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
		case WATER:
			return false;
		case JG_TREE:
			return true;
		case BIRCH_TREE:
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
	public static void steppedOn(int xIndex, int yIndex, int zIndex,Entity entity) {
		switch(Map.land[xIndex][yIndex][zIndex]){
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

