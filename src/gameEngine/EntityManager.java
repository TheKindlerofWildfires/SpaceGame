package gameEngine;

import graphicEngine.ShaderManager;

public class EntityManager { //prolly should not be called level
	
	ShaderManager shaderManager;
	Player player;
	Map map;
	
	public EntityManager(){
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		map = new Map();
		player = new Player();
		player.position.y = 0.5f;
		player.position.x = 0.5f;//sets starting poss
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
