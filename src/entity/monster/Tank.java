package entity.monster;

import entity.Entity;


public class Tank extends Entity{
	public Tank(){
		entityTag = "Tank"; //Not that dangerous
		entityLevel = 1;
		entityHealth = 300;
		entityArmor = 20;
		entityWeaponTag = "null";
		entityWeaponDamage = 2;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "reflect";
		entitySpeed = 2;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
 }
}
