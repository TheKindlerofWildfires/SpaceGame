package entity.monster;

import entity.Entity;


public class Lurker extends Entity{
	public Lurker(){
		entityTag = "Lurker"; //It hold still and spooks you
		entityLevel = 1;
		entityHealth = 100;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 15;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; 
		entityNatAbility = "surprise";
		entitySpeed = 1;
		entityRange = 2;
		addEntityAbility(entityNatAbility);
 }
}
