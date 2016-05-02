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
	
	public String getEnemyTag() {
		return this.enemyTag;
	}
	public double getEnemyHealth() {
		return this.enemyHealth;
	}
	public double getEnemyArmor() {
		return this.enemyArmor;
	}
	public String getEnemyWeaponTag() {
		return this.enemyWeaponTag;
	}
	public double getEnemyWeaponDamage() {
		return this.enemyWeaponDamage;
	}
	public double getEnemyWeaponPriority() {
		return this.enemyWeaponPriority;
	}
	public String getEnemyArmorTag() {
		return this.enemyWeaponTag;
	}
	public String getEnemyAbility() {
		return this.enemyWeaponTag;
	}
	public double getEnemySpeed() {
		return this.enemySpeed;
	}
	public enemy(){
		enemyTag = "Juggernaut";
		if (enemyTag =="Juggernaut"){
		enemyTag = "Juggernaut"; //And dangerous to fight, even when low
		enemyLevel = 1;
		enemyHealth = 200;
		enemyArmor = 9;
		enemyWeaponTag = "null";
		enemyWeaponDamage = 20;
		enemyWeaponPriority = 5;
		enemyArmorTag = "null";
		enemyAbility = "null"; //Surge (regain health when low)
		enemySpeed = 2;
		}else if (enemyTag =="Sniper"){//they sit back, take aim
			enemyTag = "Sniper"; //and remove fools, but get confused by cover
			enemyLevel = 1;
			enemyHealth = 50;
			enemyArmor = 8;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 20;
			enemyWeaponPriority = 3;
			enemyArmorTag = "null";
			enemyAbility = "null"; //dead eye
			enemySpeed = 1;	
		}else if (enemyTag =="Skirmisher"){//they dash in and out of fights
			enemyTag = "Skirmisher"; //trying to out play char
			enemyLevel = 1;
			enemyHealth = 100;
			enemyArmor = 9;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 10;
			enemyWeaponPriority = 2;
			enemyArmorTag = "null";
			enemyAbility = "null"; //stealth, tele at later levels
			enemySpeed = 3;
		}else if (enemyTag =="Grunt"){//meaningless minion waves
			enemyTag = "Grunt"; //worth little exp
			enemyLevel = 1;
			enemyHealth = 50;
			enemyArmor = 8;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 5;
			enemyWeaponPriority = 5;
			enemyArmorTag = "null";
			enemyAbility = "null";
			enemySpeed = 2;
		}else if (enemyTag =="Lurker"){//Generally imobile, hard to escape if surprised
			enemyTag = "Lurker"; //basically a lurker
			enemyLevel = 1;
			enemyHealth = 100;
			enemyArmor = 8;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 15;
			enemyWeaponPriority = 1;
			enemyArmorTag = "null"; //larger size
			enemyAbility = "null"; //stickiness 
			enemySpeed = 1;
		}else if (enemyTag =="Tank"){//almost impossible to kill
			enemyTag = "Tank"; //Not that dangerous
			enemyLevel = 1;
			enemyHealth = 300;
			enemyArmor = 10;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 2;
			enemyWeaponPriority = 5;
			enemyArmorTag = "null"; //Reflect 1/10th of damage taken
			enemyAbility = "null";
			enemySpeed = 2;
		}else if (enemyTag =="Leader"){//Like a mini mini boss
			enemyTag = "Leader"; //or an eximus, helps allies
			enemyLevel = 1;
			enemyHealth = 150;
			enemyArmor = 9;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 15;
			enemyWeaponPriority = 3;
			enemyArmorTag = "null";
			enemyAbility = "null"; //Rally
			enemySpeed = 4;
		}else if (enemyTag =="Scout"){ //a grunt with speed
			enemyTag = "Scout"; //and a slow to help team keep up
			enemyLevel = 1;
			enemyHealth = 40;
			enemyArmor = 8;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 3;
			enemyWeaponPriority = 5;
			enemyArmorTag = "null";
			enemyAbility = "null"; //weak slow
			enemySpeed = 5;
		}else if (enemyTag =="Mage"){
			enemyTag = "Mage"; //buffs, heals, sheilds, debuffs, nukes etc
			enemyLevel = 1;
			enemyHealth = 50;
			enemyArmor = 9;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 5;
			enemyWeaponPriority = 5;
			enemyArmorTag = "null";
			enemyAbility = "null"; //host of spells
			enemySpeed = 2;
		}else if (enemyTag =="Hunter"){ //like a scout, but chases indefinitly and is dangerous
			enemyTag = "Hunter"; //technically a stalker
			enemyLevel = 1;
			enemyHealth = 100;
			enemyArmor = 8;
			enemyWeaponTag = "null";
			enemyWeaponDamage = 15;
			enemyWeaponPriority = 2;
			enemyArmorTag = "null";
			enemyAbility = "null"; //knockdown, sprint
			enemySpeed = 5;
			}
		}
	}

