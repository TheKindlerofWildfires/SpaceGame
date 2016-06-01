package entity.monster;

import entity.Entity;


public class Juggernaut extends Entity{
	public Juggernaut(){
		entityTag = "Juggernaut";
		entityLevel = 1;
		entityHealth = 200;
		entityArmor = 9;
		entityWeaponTag = "null";
		entityWeaponDamage = 20;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; 
		entityNatAbility = "surge";
		entitySpeed = 2;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
 }
}
