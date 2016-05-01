package entity.player;

import java.awt.event.KeyEvent;

public class player {
	public String playerTag; // Player identifier
	public double playerHealth;
	public double playerSanity;
	public double playerArmor;
	public double playerSanityResist; // like armor for sanity
	public String playerWeaponTag; // weapon identifier
	public double playerWeaponDamage;
	public double playerWeaponToHit;
	public double playerWeaponPriority; // higherprio fires later
	public String playerArmorTag;
	public String playerAbility; // Special sauce
	public int playerSpeed; //blocks per turn

	public void keyPressed(KeyEvent event){
		if (event.getKeyCode()== KeyEvent.VK_UP){
			System.out.println("sup dog");
		}
	}

	public void Neo() {
		playerTag = "Neo";
		playerHealth = 100;
		playerSanity = 100;
		playerArmor = 9;
		playerSanityResist = 10;
		playerWeaponTag = "Salvage";
		playerWeaponDamage = 12;
		playerWeaponToHit = 0;
		playerWeaponPriority = 4;
		playerArmorTag = "Leather Jacket";
		playerAbility = "Pattern recognition"; // Helps to solve cyphers && ruins 
		playerSpeed = 3;
	}

	public void Agent() {
		playerTag = "Agent";
		playerHealth = 120;
		playerSanity = 80;
		playerArmor = 10;
		playerSanityResist = 9;
		playerWeaponTag = "Handgun";
		playerWeaponDamage = 20;
		playerWeaponToHit = 1;
		playerWeaponPriority = 1;
		playerArmorTag = "Bullet Proof Vest";
		playerAbility = "Combat Expertise"; // Helps to see enemies
		playerSpeed = 4;
	}

	public void Occultist() {
		playerTag = "Occultist";
		playerHealth = 80;
		playerSanity = 50;
		playerArmor = 8;
		playerSanityResist = 8;
		playerWeaponTag = "Knife";
		playerWeaponDamage = 5;
		playerWeaponToHit = 1;
		playerWeaponPriority = 3;
		playerArmorTag = "Robes";
		playerAbility = "Alcoltye"; //Slows anger of old gods, helps with figuring out magic weapons
		playerSpeed = 3;
	}
}
