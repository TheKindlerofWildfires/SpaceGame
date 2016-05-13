package entity.monster;

import entity.Entity;

public class Grunt extends Entity{
	public Grunt(){
		entityTag = "Grunt"; //Not that dangerous
		entityHealth = 50;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 5;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "null";
		entitySpeed = 2;
 }
}
