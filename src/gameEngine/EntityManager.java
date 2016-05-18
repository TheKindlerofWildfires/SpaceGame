package gameEngine;

import graphicEngine.ShaderManager;

public class EntityManager { 
	
	ShaderManager shaderManager;
	Player player;	
	public EntityManager(){
		shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		player = new Player();
		player.position.y = -0.01f;
		player.position.x = 0.0f;
	}
	public void update(){
		player.update();
	}
	public void draw(){
		ShaderManager.playerShader.start();
		ShaderManager.playerShader.setUniform3f("pos",player.position);
		player.draw();	
		ShaderManager.playerShader.stop();
		
	}
}
