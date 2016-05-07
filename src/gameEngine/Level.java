package gameEngine;

public class Level { //prolly should not be called level
	Player player;
	Monster monster;
	
	public Level(){
		player = new Player(); //I suspect this will have to be cleaner --need to do all entities
		monster = new Monster();
	}
	public void update(){
		player.update();
		monster.update();
	}
	public void draw(){
		//player.draw();
		monster.draw();
	}
}
