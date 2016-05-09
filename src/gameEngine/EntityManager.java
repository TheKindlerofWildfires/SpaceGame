package gameEngine;

import graphicEngine.ShaderManager;

public class EntityManager { 
	
	ShaderManager shaderManager;
	Player player;
	Map map;
	
	public EntityManager(){
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		map = new Map();
		player = new Player();
		player.position.y = -0.01f;
		player.position.x = 0.0f;
	}
	public void update(){
		player.update();
		map.update();
	}
	public void draw(){
		shaderManager.playerShader.start();
		shaderManager.playerShader.setUniform3f("pos",player.position);
		player.draw();	
		shaderManager.playerShader.stop();
		map.draw();
		
	}
}
