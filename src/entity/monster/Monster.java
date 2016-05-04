package entity.monster;

import entity.Entity;

public class Monster extends Entity{
	public String monsterTag; // monster identifier
	public double monsterHealth;
	public double monsterArmor;
	public String monsterWeaponTag; // weapon identifier
	public double monsterWeaponDamage;
	public double monsterWeaponToHit;
	public double monsterWeaponPriority; // higherprio fires later
	public String monsterArmorTag;
	public String monsterAbility; // Special sauce
	public int monsterLevel;
	public int monsterSpeed;
	
	public Monster(){
	}
}

