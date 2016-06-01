package entity.player;

import entity.Entity;


public class Neo extends Entity{
	public Neo(){
		entityTag = "Neo";
		entityLevel = 1;
		entityHealth = 100;
		entitySanity = 100;
		entityArmor = 9;
		entitySanityResist = 10;
		entityWeaponTag = "Salvage";
		entityWeaponDamage = 18;
		entityWeaponPriority = 3;
		entityArmorTag = "Leather Jacket";
		entityNatAbility = "patternRecognition"; 
		entitySpeed = 3;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
	}

}
