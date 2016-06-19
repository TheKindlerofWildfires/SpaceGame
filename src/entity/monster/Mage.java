package entity.monster;

import entity.Entity;


public class Mage extends Entity{
	public Mage(){
		entityTag = "Mage"; //BIGGEST CLASS
		entityLevel = 1;
		entityHealth = 50;
		entityArmor = 10;
		entityWeaponTag = "null";
		entityWeaponDamage = 5;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; 
		entityNatAbility = "mage";
		entitySpeed = 2;
		entityRange = 3;
		addEntityAbility(entityNatAbility);
 }
}
