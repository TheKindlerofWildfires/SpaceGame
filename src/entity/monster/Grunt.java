package entity.monster;

import entity.Entity;

public class Grunt extends Entity{
	public Grunt(){
		entityTag = "Grunt"; //Not that dangerous
		entityLevel = 1;
		entityHealth = 50;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 5;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; 
		entityNatAbility = "null";
		entitySpeed = 2;
		entityRange = 1;
 }
}
