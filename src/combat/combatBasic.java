package combat;

import entity.player.*;
import entity.enemy.*;

import java.util.ArrayList;
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
	private String enemyAbility;
	private double damage;
	private double deadEyeStacks;
	private String playerCondition;
	private String spell[];
	private String spellChoice;

	public combatBasic() {

		// Me being bad
		player player = new player();
		String playerTag = player.getPlayerTag();
		double playerHealth = player.getPlayerHealth();
		double playerArmor = player.getPlayerArmor();
		String playerWeaponTag = player.getPlayerWeaponTag();
		double playerWeaponDamage = player.getPlayerWeaponDamage();
		double playerWeaponPriority = player.getPlayerWeaponPriority();
		double playerSpeed = player.getPlayerSpeed();
		enemy enemy = new enemy();
		String enemyTag = enemy.getEnemyTag();
		double enemyHealth = enemy.getEnemyHealth();
		double enemyArmor = enemy.getEnemyArmor();
		String enemyWeaponTag = enemy.getEnemyWeaponTag();
		double enemyWeaponDamage = enemy.getEnemyWeaponDamage();
		double enemyWeaponPriority = enemy.getEnemyWeaponPriority();
		double enemySpeed = enemy.getEnemySpeed();
		String enemyAbility = enemy.getEnemyAbility();
		deadEyeStacks = 0;
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
		this.enemyAbility = enemyAbility;
		this.enemyWeaponPriority = enemyWeaponPriority;
		this.enemySpeed = enemySpeed;

	}

	public void Attack(String attacker, String defender) {
		if (attacker == "Mage") {
			spellCasting();
		} else {
			for (int AR = 1; AR < 6;) {
				double hitChance = (1 / 17.5 * Math.pow(Math.E, .1 * AR
						* enemySpeed));
				double attackerWeaponPriority;
				if (attacker == playerTag) {
					attackerWeaponPriority = playerWeaponPriority;
				} else
					attackerWeaponPriority = enemyWeaponPriority;

				if (rng.nextDouble() <= hitChance) {
					Dodge(attacker, defender);
					break;
				} else if (AR == attackerWeaponPriority) {
					Hit(attacker, defender);
					break;
				} else {
					AR++;
				}
			}
		}
	}

	public void Dodge(String attacker, String defender) {
		System.out.println(defender + " Dodge");
		iliterativeCombat(attacker, defender);
	}

	// Stealth: Every thrid attack auto dodge
	// Reactive attacks: Attacks advantage
	// Reflective Armor: Reflects 1/10 of damage taken
	// Mage: random spell every round instead of attack, including nuke, heal,
	// debuff
	// Nockdown: Skip a player turn on % */
	public void Hit(String attacker, String defender) {
		System.out.println(attacker + " Hit");
		if (attacker == playerTag) {
			damage = playerWeaponDamage * (10 / (10 + enemyArmor));
			enemyHealth = enemyHealth - damage;
			if ((playerWeaponTag == "Cultist's Blade")
					&& (rng.nextDouble() <= 0.8) && (!(playerHealth >= 200))) {
				playerHealth = playerHealth + playerWeaponDamage;
			}
			iliterativeCombat(attacker, defender);
		} else {
			onHitAbilityCall(attacker, defender);

		}
	}

	public void onHitAbilityCall(String attacker, String defender) {
		if (enemyAbility == "Surge") {
			if (enemyHealth >= 50) {
				enemyAbility = "Surged";
				enemySpeed = enemySpeed + 1;
				enemyWeaponPriority = enemyWeaponPriority - 2;
			}
		} else if (enemyAbility == "Surged") {
			enemyHealth = enemyHealth + damage;
		} else if (enemyAbility == "Dead Eye") {
			if (rng.nextDouble() <= 0.8 / ((deadEyeStacks + 1))) {

				playerSpeed = playerSpeed - 1;
				playerArmor = playerArmor - 1;
				deadEyeStacks++;
			} else if (deadEyeStacks >= 1) {
				deadEyeStacks = deadEyeStacks - 1;
				playerSpeed = playerSpeed + 1;
				playerArmor = playerArmor + 1;
			}
		} else if (enemyAbility == "Knockdown") {
			if (rng.nextDouble() <= 0.3) {
				playerCondition = "Knockdown";
			}
		} else if (enemyAbility == "Rally") {
			enemyAbility = "Rallied";
			// allies too
			enemySpeed = enemySpeed + 1;
			enemyWeaponPriority = enemyWeaponPriority - 1;
		} else
			damage = enemyWeaponDamage * (10 / (10 + playerArmor));
		playerHealth = playerHealth - damage;
		iliterativeCombat(attacker, defender);
	}

	public void iliterativeCombat(String attacker, String defender) { // its a
																		// pun
		if (!(playerHealth <= 0) && !(enemyHealth <= 0)) {
			Attack(defender, attacker);
		} else if (playerHealth > 0) {
			System.out.println("Winner is " + playerTag);
			System.out.println(playerTag + " has " + playerHealth);
			System.out.println(enemyTag + " died at " + enemyHealth);
		} else if (enemyHealth > 0) {
			System.out.println("Winner is " + enemyTag);
			System.out.println(playerTag + " died at " + playerHealth);
			System.out.println(enemyTag + " has " + enemyHealth);
		} else
			System.out.println("Checkout iliterateCombat cuz it has a bug");
	}

	public void init() {
		Attack(playerTag, enemyTag); // player attacks first
	}

	public void spellCasting() {
		spell = new String[3];
		spell[0] = "Firebolt";
		spell[1] = "Life Siphon";
		spell[2] = "Heal";
		//spellChoice = spell[rng.nextInt(spell.length)];
		if (enemyHealth >= 70){
			spellChoice = "Firebolt";
		}else if (enemyHealth <= 20){
			spellChoice = "Heal";
		}else{
			spellChoice = "Life Siphon";
		}
		System.out.println("Mage cast "+ spellChoice);
		if (spellChoice == "Firebolt"){
			playerHealth = playerHealth-5;
		}else if (spellChoice == "Life Siphon"){
			playerHealth = playerHealth-3;
			enemyHealth = enemyHealth +3;
		}else if (spellChoice == "Heal"){
			enemyHealth = enemyHealth +5;
		}
		iliterativeCombat(enemyTag, playerTag);
	}
}
