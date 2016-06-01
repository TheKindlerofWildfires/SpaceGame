package entity.monster;

import entity.Entity;


public class Skirmisher extends Entity{
	public Skirmisher(){
		entityTag = "Skirmisher"; //Plays hide and seek
		entityLevel = 1;
		entityHealth = 100;
		entityArmor = 9;
		entityWeaponTag = "null";
		entityWeaponDamage = 10;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; 
		entityNatAbility = "stealth";
		entitySpeed = 3;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
 }
}
