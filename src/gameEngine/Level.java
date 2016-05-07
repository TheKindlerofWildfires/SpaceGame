package gameEngine;

import graphicEngine.ShaderManager;

public class Level { //prolly should not be called level
	
	ShaderManager shaderManager;
	Player player;
	Monster monster;
	
	public Level(){
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		player = new Player(); //I suspect this will have to be cleaner --need to do all entities
		monster = new Monster();
	}
	public void update(){
		player.update();
		monster.update();
	}
	public void draw(){
		shaderManager.shader1.start();
		player.draw();
		monster.draw();
		shaderManager.shader1.stop();
	}
}
