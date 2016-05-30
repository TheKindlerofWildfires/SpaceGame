package entity.monster;

import entity.Entity;


public class Mage extends Entity{
	public Mage(){
		entityTag = "Mage"; //Not that dangerous
		entityHealth = 50;
		entityArmor = 10;
		entityWeaponTag = "null";
		entityWeaponDamage = 5;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "mage";
		entitySpeed = 2;
		entityRange = 3;
		addEntityAbility(entityNatAbility);
 }
}
