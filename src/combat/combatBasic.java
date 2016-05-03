package combat;

import entity.player.*;
import entity.enemy.*;

import java.util.Random;

public class combatBasic {
	private Random rng = new Random();
	private String playerTag;
	private double playerHealth;
	private double playerArmor;
	private String playerWeaponTag;
	private double playerWeaponDamage;
	private double playerWeaponPriority;
	private double playerSpeed;
	private String enemyTag;
	private double enemyHealth;
	private double enemyArmor;
	private String enemyWeaponTag;
	private double enemyWeaponDamage;
	private double enemyWeaponPriority;
	private double enemySpeed;
	
	public combatBasic(){
		
		//Me being bad
		player player = new player();
		String playerTag = player.getPlayerTag();
		double playerHealth = player.getPlayerHealth();
		double playerArmor = player.getPlayerArmor();
		String playerWeaponTag = player.getPlayerWeaponTag();
		double playerWeaponDamage = player.getPlayerWeaponDamage();
		double playerWeaponPriority =player.getPlayerWeaponPriority();
		double playerSpeed = player.getPlayerSpeed();
		enemy enemy = new enemy();
		String enemyTag = enemy.getEnemyTag();
		double enemyHealth = enemy.getEnemyHealth();
		double enemyArmor = enemy.getEnemyArmor();
		String enemyWeaponTag = enemy.getEnemyWeaponTag();
		double enemyWeaponDamage = enemy.getEnemyWeaponDamage();
		double enemyWeaponPriority =enemy.getEnemyWeaponPriority();
		double enemySpeed = enemy.getEnemySpeed();
		this.playerTag = playerTag;
		this.playerHealth = playerHealth;
		this.playerArmor = playerArmor;
		this.playerWeaponTag = playerWeaponTag;
		this.playerWeaponDamage = playerWeaponDamage;
		this.playerWeaponPriority = playerWeaponPriority;
		this.playerSpeed = playerSpeed;
		this.enemyTag = enemyTag;
		this.enemyHealth = enemyHealth;
		this.enemyArmor = enemyArmor;
		this.enemyWeaponTag = enemyWeaponTag;
		this.enemyWeaponDamage = enemyWeaponDamage;
		this.enemyWeaponPriority = enemyWeaponPriority;
		this.enemySpeed = enemySpeed;

		
	}
	public void Attack(String attacker, String defender){
		for (int AR = 1;  AR<6;){
			double hitChance = (1/17.5*Math.pow(Math.E, .1*AR*enemySpeed));
			if (rng.nextDouble()<= hitChance){
				Dodge(attacker, defender);
				break;
			}else if(AR == playerWeaponPriority){ 
				Hit(attacker, defender);
				break;
			}else{
				AR++;
			}
		}
	}
	public void Dodge(String attacker, String defender){
		System.out.println(defender + " Dodge");
		iliterativeCombat(attacker, defender);
	}
	public void Hit(String attacker, String defender){
		System.out.println(attacker + " Hit");
		if (attacker == playerTag){
			double damage;
			damage = playerWeaponDamage*(10/(10+enemyArmor));
			enemyHealth = enemyHealth -damage;
			if ((playerWeaponTag == "Cultist's Blade")&&(rng.nextDouble()<=0.8)&&(!(playerHealth >= 200))){
				playerHealth = playerHealth + playerWeaponDamage;
			}
		}else{
			double damage;
			damage = enemyWeaponDamage*(10/(10+playerArmor));
			playerHealth = playerHealth-damage;
		}
		iliterativeCombat(attacker, defender);
	}
	public void iliterativeCombat(String attacker, String defender){ //its a pun
		if (!(playerHealth <= 0) && !(enemyHealth<=0)){
				Attack(defender, attacker);
		}else if (playerHealth>0){
			System.out.println("Winner is " +playerTag);
			System.out.println( playerTag+" has "+ playerHealth);
			System.out.println(enemyTag +" died at " + enemyHealth);
		}else if (enemyHealth>0){
			System.out.println("Winner is " +enemyTag);
			System.out.println(playerTag + " died at "+ playerHealth);
			System.out.println(enemyTag + " has " + enemyHealth);
		}else
			System.out.println("Checkout iliterateCombat cuz it has a bug");
	}
	public void init() {
		Attack(playerTag, enemyTag); //player attacks first
	}
}
