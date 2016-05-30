package entity.monster;

import entity.Entity;


public class Skirmisher extends Entity{
	public Skirmisher(){
		entityTag = "Skirmisher"; //Not that dangerous
		entityHealth = 100;
		entityArmor = 9;
		entityWeaponTag = "null";
		entityWeaponDamage = 10;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "stealth";
		entitySpeed = 3;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
 }
}
