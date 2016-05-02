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
		return this.playerSanityResist;
	}
	public double getPlayerWeaponPriority() {
		return this.playerSanityResist;
	}
	public String getPlayerArmorTag() {
		return this.playerWeaponTag;
	}
	public String getPlayerAbility() {
		return this.playerWeaponTag;
	}
	public double getPlayerSpeed() {
		return this.playerSanityResist;
	}
	public player() {
		playerTag = "Neo";
		if (playerTag == "Neo") {
			playerHealth = 100;
			playerSanity = 100;
			playerArmor = 9;
			playerSanityResist = 10;
			playerWeaponTag = "Salvage";
			playerWeaponDamage = 12;
			playerWeaponPriority = 4;
			playerArmorTag = "Leather Jacket";
			playerAbility = "Pattern recognition"; // Helps to solve cyphers &&
													// ruins
			playerSpeed = 3;
		} else if (playerTag == "Agent") {
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
		} else if (playerTag == "Occultist") {
			playerHealth = 80;
			playerSanity = 50;
			playerArmor = 8;
			playerSanityResist = 8;
			playerWeaponTag = "Knife";
			playerWeaponDamage = 5;
			playerWeaponPriority = 3;
			playerArmorTag = "Robes";
			playerAbility = "Alcoltye"; // Slows anger of old gods, helps with
										// figuring out magic weapons
			playerSpeed = 3;

		}
	}
}
