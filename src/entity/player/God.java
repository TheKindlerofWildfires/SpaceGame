package entity.player;

import entity.Entity;


public class God extends Entity{
	public God(){
		entityTag = "God";
		entityHealth = 10000;
		entitySanity = 10000;
		entityArmor = 1000;
		entitySanityResist = 1000;
		entityWeaponTag = "Nerf Bat";
		entityWeaponDamage = 100;
		entityWeaponPriority = 1;
		entityArmorTag = "Protagonist Armor";
		entityNatAbility = "combatExpertise"; 
		entitySpeed = 10;
		entityRange = 10;
		addEntityAbility(entityNatAbility);
	}

}
