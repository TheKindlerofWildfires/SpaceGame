package entity.player;

import entity.Entity;


public class Oc extends Entity{
	public Oc(){
		entityTag = "Neo";
		entityHealth = 80;
		entitySanity = 50;
		entityArmor = 8;
		entitySanityResist = 8;
		entityWeaponTag = "Cultist's Blade";
		entityWeaponDamage = 5;
		entityWeaponPriority = 3;
		entityArmorTag = "Robes";
		entityNatAbility = "Alcolyte"; 
		entitySpeed = 3;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
	}

}
