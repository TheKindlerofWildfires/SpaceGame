package combat;

import entity.player.*;
import entity.enemy.*;

import java.util.Random;

public class combatBasic {
	private Random rng = new Random();
	
	public static void main(String[] args) {
		//String playerName, String enemyName

				// should call playerAttack();
		init();
		//"Neo", "Juggernaut"
	}
	public static void init(){
		//Start init
		player player = new player();
		String playerTag = player.getPlayerTag();
		double playerHealth = player.getPlayerHealth();
		double playerArmor = player.getPlayerArmor();
		String playerWeaponTag = player.getPlayerWeaponTag();
		double playerWeaponDamage = player.getPlayerWeaponDamage();
		double playerWeaponPriority =player.getPlayerWeaponPriority();
		double playerSpeed = player.getPlayerSpeed();
		//System.out.println(playerTag);
		enemy enemy = new enemy();
		String enemyTag = enemy.getEnemyTag();
		double enemyHealth = enemy.getEnemyHealth();
		double enemyArmor = enemy.getEnemyArmor();
		String enemyWeaponTag = enemy.getEnemyWeaponTag();
		double enemyWeaponDamage = enemy.getEnemyWeaponDamage();
		double enemyWeaponPriority =enemy.getEnemyWeaponPriority();
		double enemySpeed = enemy.getEnemySpeed();
		//System.out.println(enemyTag);
		//end init
	}
	public void playerAttack(){
		double enemySpeed = 5;
		for (int AR = 1;  AR<6;AR++){
			double hitChance = (1/17.5*Math.pow(Math.E, .1*AR*enemySpeed));
			if (rng.nextDouble()<= hitChance){
				System.out.println("Dodge");
			}else if(1 == 2){ // should be AR = Attackprio
				System.out.println("Hit");
			}else{
				AR++;
			}
		}
	}
}
