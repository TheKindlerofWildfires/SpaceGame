package gameEngine;

import graphicEngine.ShaderManager;

public class EntityManager { 
	
	ShaderManager shaderManager;
	Player player;
	Map map;
	
	public EntityManager(){
		shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		map = new Map();
		player = new Player();
		player.position.y = 0.0f;
		player.position.x = 0.0f;
	}
	public void update(){
		player.update();
		map.update();
	}
	public void draw(){
		ShaderManager.playerShader.start();
		ShaderManager.playerShader.setUniform3f("pos",player.position);
		player.draw();	
		ShaderManager.playerShader.stop();
		map.draw();
		
	}
}
