package entity.enemy;

public class enemy {
	public String enemyTag; // enemy identifier
	public double enemyHealth;
	public double enemyArmor;
	public String enemyWeaponTag; // weapon identifier
	public double enemyWeaponDamage;
	public double enemyWeaponToHit;
	public double enemyWeaponPriority; // higherprio fires later
	public String enemyArmorTag;
	public String enemyAbility; // Special sauce
	public int enemyLevel;
	public int enemySpeed;

	public void Juggernaut() { //lumbering and kitable, but hard to kill
		enemyTag = "Juggernaut"; //And dangerous to fight, even when low
		enemyLevel = 1;
		enemyHealth = 200;
		enemyArmor = 9;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 20;
		enemyWeaponToHit = 0;
		enemyWeaponPriority = 5;
		enemyArmorTag = "null";
		enemyAbility = "null"; //Surge (regain health when low)
		enemySpeed = 2;
	}
	public void Sniper() { //they sit back, take aim
		enemyTag = "Sniper"; //and remove fools, but get confused by cover
		enemyLevel = 1;
		enemyHealth = 50;
		enemyArmor = 8;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 20;
		enemyWeaponToHit = 1;
		enemyWeaponPriority = 3;
		enemyArmorTag = "null";
		enemyAbility = "null"; //dead eye
		enemySpeed = 1;
	}
	public void Skirmisher() { //they dash in and out of fights
		enemyTag = "Skirmisher"; //trying to out play char
		enemyLevel = 1;
		enemyHealth = 100;
		enemyArmor = 9;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 10;
		enemyWeaponToHit = 0;
		enemyWeaponPriority = 2;
		enemyArmorTag = "null";
		enemyAbility = "null"; //stealth, tele at later levels
		enemySpeed = 3;
	}
	public void Grunt() { //meaningless minion waves
		enemyTag = "Grunt"; //worth little exp
		enemyLevel = 1;
		enemyHealth = 50;
		enemyArmor = 8;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 5;
		enemyWeaponToHit = 0;
		enemyWeaponPriority = 5;
		enemyArmorTag = "null";
		enemyAbility = "null";
		enemySpeed = 2;
	}
	public void Swarm() { //Generally imobile, hard to escape if surprised
		enemyTag = "Swarm"; //basically a lurker
		enemyLevel = 1;
		enemyHealth = 100;
		enemyArmor = 8;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 15;
		enemyWeaponToHit = 1;
		enemyWeaponPriority = 1;
		enemyArmorTag = "null"; //larger size
		enemyAbility = "null"; //stickiness 
		enemySpeed = 1;
	}
	public void Tank() { //almost impossible to kill
		enemyTag = "Tank"; //Not that dangerous
		enemyLevel = 1;
		enemyHealth = 300;
		enemyArmor = 10;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 2;
		enemyWeaponToHit = 0;
		enemyWeaponPriority = 5;
		enemyArmorTag = "null"; //Reflect 1/10th of damage taken
		enemyAbility = "null";
		enemySpeed = 2;
	}
	public void Leader() { //Like a mini mini boss
		enemyTag = "Leader"; //or an eximus, helps allies
		enemyLevel = 1;
		enemyHealth = 150;
		enemyArmor = 9;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 15;
		enemyWeaponToHit = 0;
		enemyWeaponPriority = 3;
		enemyArmorTag = "null";
		enemyAbility = "null"; //Rally
		enemySpeed = 4;
	}
	public void Scout() { //a grunt with speed
		enemyTag = "Scout"; //and a slow to help team keep up
		enemyLevel = 1;
		enemyHealth = 40;
		enemyArmor = 8;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 3;
		enemyWeaponToHit = 0;
		enemyWeaponPriority = 5;
		enemyArmorTag = "null";
		enemyAbility = "null"; //weak slow
		enemySpeed = 5;
	}
	public void Mage() { //Does whatever a mage does
		enemyTag = "Mage"; //buffs, heals, sheilds, debuffs, nukes etc
		enemyLevel = 1;
		enemyHealth = 50;
		enemyArmor = 9;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 5;
		enemyWeaponToHit = 0;
		enemyWeaponPriority = 5;
		enemyArmorTag = "null";
		enemyAbility = "null"; //host of spells
		enemySpeed = 2;
	}
	public void Hunter() { //like a scout, but chases indefinitly and is dangerous
		enemyTag = "Hunter"; //technically a stalker
		enemyLevel = 1;
		enemyHealth = 100;
		enemyArmor = 8;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 15;
		enemyWeaponToHit = 0;
		enemyWeaponPriority = 2;
		enemyArmorTag = "null";
		enemyAbility = "null"; //knockdown, sprint
		enemySpeed = 5;
	}
	
}
