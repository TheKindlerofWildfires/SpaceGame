package entity.monster;

import entity.Entity;


public class Scout extends Entity{
	public Scout(){
		entityTag = "Scout"; //Fast
		entityLevel = 1;
		entityHealth = 40;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 3;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; 
		entityNatAbility = "net";
		entitySpeed = 5;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
 }
}
