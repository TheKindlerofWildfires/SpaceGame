package entity.monster;

import entity.Entity;


public class Scout extends Entity{
	public Scout(){
		entityTag = "Scout"; //Not that dangerous
		entityHealth = 40;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 3;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "Net";
		entitySpeed = 5;
		addEntityAbility(entityNatAbility);
 }
}
