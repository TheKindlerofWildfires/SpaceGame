package entity.player;

import entity.Entity;


public class Neo extends Entity{
	public Neo(){
		entityTag = "Neo";
		entityHealth = 100;
		entitySanity = 100;
		entityArmor = 9;
		entitySanityResist = 10;
		entityWeaponTag = "Salvage";
		entityWeaponDamage = 12;
		entityWeaponPriority = 3;
		entityArmorTag = "Leather Jacket";
		entityNatAbility = "Pattern recognition"; 
		entitySpeed = 3;
		addEntityAbility(entityNatAbility);
	}

}
