package entity.player;

import entity.Entity;


public class Agent extends Entity{
	public Agent(){
		entityTag = "Agent";
		entityLevel = 1;
		entityHealth = 120;
		entitySanity = 80;
		entityArmor = 10;
		entitySanityResist = 9;
		entityWeaponTag = "Handgun";
		entityWeaponDamage = 20;
		entityWeaponPriority = 2;
		entityArmorTag = "Bullet Proof Vest";
		entityNatAbility = "combatExpertise"; 
		entitySpeed = 4;
		entityRange = 4;
		addEntityAbility(entityNatAbility);
	}

}
