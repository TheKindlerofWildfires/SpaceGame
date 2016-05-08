package gameEngine;

import graphicEngine.ShaderManager;

import java.util.ArrayList;

import GUI.Tile;

public class Map{
	private ArrayList<Hexagon> tiles = new ArrayList<Hexagon>();
	ShaderManager shaderManager;
	public static final int HEXAGONSACROSS = 42;
	public static final int HEXAGONSDOWN = 500;
	
	
	public Map(){
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		double q = 1;
		for(int i = 0; i < (2855); i++){
			
			tiles.add(new Hexagon());
			//if not edge then position = last position + some
			if(i == 0){
				tiles.get(i).position.x = -1f;
				tiles.get(i).position.y = -1f;
			}else{
				//System.out.println(i);
				if(!(i%(HEXAGONSACROSS) == 0)){
					//System.out.println("ac");
					tiles.get(i).position.x = (float) (tiles.get(i-1).position.x + (0.08*q));
					tiles.get(i).position.y = tiles.get(i-1).position.y;
				}else if (!(i%(HEXAGONSDOWN) == 0)){
					tiles.get(i).position.y = tiles.get(i-1).position.y + 0.03f;
					tiles.get(i).position.x = (float) (tiles.get(i-1).position.x -0.04*q);
					q *= -1;
					
					//System.out.println("dow");
				}
			//System.out.println(tiles.get(i).position.y);
			//System.out.println(tiles.get(i).position.x);
			}
			//System.out.println(tiles.get(i).position.y);
		}
		
	}
	public void update(){
		for(int i = 0; i < tiles.size(); i++){
			tiles.get(i).update();
			//System.out.println(tiles.get(i));
		}

	}
	public void draw(){
		for(int i = 0; i < tiles.size(); i++){
			shaderManager.shader1.start();
			shaderManager.shader1.setUniform3f("pos",tiles.get(i).position);
			tiles.get(i).draw();	
			shaderManager.shader1.stop();
		}
		

	}
	}
