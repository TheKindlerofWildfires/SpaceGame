package entity.monster;

import entity.Entity;


public class Sniper extends Entity{
	public Sniper(){
		entityTag = "Sniper"; 
		entityHealth = 50;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 20;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; 
		entityNatAbility = "deadEye";
		entitySpeed = 1;
		entityRange = 5;
		addEntityAbility(entityNatAbility);
 }
}
