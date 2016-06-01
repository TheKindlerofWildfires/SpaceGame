package entity.player;

import entity.Entity;


public class Oc extends Entity{
	public Oc(){
		entityTag = "Neo";
		entityLevel = 1;
		entityHealth = 80;
		entitySanity = 50;
		entityArmor = 8;
		entitySanityResist = 8;
		entityWeaponTag = "Cultist's Blade";
		entityWeaponDamage = 13;
		entityWeaponPriority = 3;
		entityArmorTag = "Robes";
		entityNatAbility = "alcolyte"; 
		entitySpeed = 3;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
	}

}
