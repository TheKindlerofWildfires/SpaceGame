package classesSimonDoesntLike;

import entity.Monster;
import entity.Player;
import graphicEngine.ShaderManager;

public class Level { //prolly should not be called level
	
	ShaderManager shaderManager;
	Player player;
	Monster monster;
	
	public Level(){
		shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		player = new Player(); //I suspect this will have to be cleaner --need to do all entities
		monster = new Monster();
		player.position.y = 0.5f;
		player.position.x = 0.5f;//sets starting poss
	}
	public void update(){
		checkCollision(player);
		player.update();
		monster.update();
	}
	public void draw(){
		ShaderManager.shader1.start();
		ShaderManager.shader1.setUniform3f("pos",player.position);
		player.draw();	
		ShaderManager.shader1.stop();
		
		ShaderManager.shader1.start();
		ShaderManager.shader1.setUniform3f("pos",monster.position);
		monster.draw();
		ShaderManager.shader1.stop();
	}
	public void checkCollision(Player player){
		if(player.position.x<monster.position.x+monster.WIDTH&&
				player.position.x+player.WIDTH > monster.position.x &&
				player.position.y < monster.position.y +monster.HEIGHT &&
				player.position.y +player.HEIGHT > monster.position.y){
			monster.movement.x *= -1.0f;
			monster.movement.y *= -1.0f;
		}
	}
}