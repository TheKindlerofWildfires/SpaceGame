package entity.monster;

import entity.Entity;


public class Hunter extends Entity{
	public Hunter(){
		entityTag = "Hunter"; //Not that dangerous
		entityHealth = 100;
		entityArmor = 10;
		entityWeaponTag = "null";
		entityWeaponDamage = 15;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility ="knockdown";
		entitySpeed = 2;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
 }
}
