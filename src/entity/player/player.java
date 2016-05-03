package entity.player;

public class player {
	private String playerTag; // Player identifier
	private double playerHealth;
	private double playerSanity;
	private double playerArmor;
	private double playerSanityResist; // like armor for sanity
	private String playerWeaponTag; // weapon identifier
	private double playerWeaponDamage;
	private double playerWeaponPriority; // higherprio fires later
	private String playerArmorTag;
	private String playerAbility; // Special sauce
	private int playerSpeed; // blocks per turn

	public String getPlayerTag() {
		return this.playerTag;
	}
	public double getPlayerHealth() {
		return this.playerHealth;
	}
	public double getPlayerSanity() {
		return this.playerSanity;
	}
	public double getPlayerArmor() {
		return this.playerArmor;
	}
	public double getPlayerSanityResist() {
		return this.playerSanityResist;
	}
	public String getPlayerWeaponTag() {
		return this.playerWeaponTag;
	}
	public double getPlayerWeaponDamage() {
		return this.playerWeaponDamage;
	}
	public double getPlayerWeaponPriority() {
		return this.playerWeaponPriority;
	}
	public String getPlayerArmorTag() {
		return this.playerArmorTag;
	}
	public String getPlayerAbility() {
		return this.playerAbility;
	}
	public double getPlayerSpeed() {
		return this.playerSpeed;
	}
	public player() {
		playerTag = "Agent";
		if (playerTag == "Neo") { //Basic char, balanced and easier
			playerHealth = 100;
			playerSanity = 100;
			playerArmor = 9;
			playerSanityResist = 10;
			playerWeaponTag = "Salvage";
			playerWeaponDamage = 12;
			playerWeaponPriority = 3;
			playerArmorTag = "Leather Jacket";
			playerAbility = "Pattern recognition"; // Helps to solve cyphers &&
													// ruins
			playerSpeed = 3;
		} else if (playerTag == "Agent") { //Combat intensive, low utility
			playerHealth = 120;
			playerSanity = 80;
			playerArmor = 10;
			playerSanityResist = 9;
			playerWeaponTag = "Handgun";
			playerWeaponDamage = 20;
			playerWeaponPriority = 1;
			playerArmorTag = "Bullet Proof Vest";
			playerAbility = "Combat Expertise"; // Helps to see enemies
			playerSpeed = 4;
		} else if (playerTag == "Occultist") {//Utility based, more risky to play
			playerHealth = 80;
			playerSanity = 50;
			playerArmor = 8;
			playerSanityResist = 8;
			playerWeaponTag = "Cultist's Blade";
			playerWeaponDamage = 5;
			playerWeaponPriority = 3;
			playerArmorTag = "Robes";
			playerAbility = "Alcoltye"; // Slows anger of old gods, helps with
										// figuring out magic weapons
			//lifestrike on basic weapon
			//watch the overheal
			playerSpeed = 3;

		}
	}
}
