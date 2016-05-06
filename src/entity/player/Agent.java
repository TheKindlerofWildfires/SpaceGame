package entity.player;


public class Agent extends Player{
	public Agent(){
		entityTag = "Agent";
		entityHealth = 120;
		entitySanity = 80;
		entityArmor = 10;
		entitySanityResist = 9;
		entityWeaponTag = "Handgun";
		entityWeaponDamage = 20;
		entityWeaponPriority = 1;
		entityArmorTag = "Bullet Proof Vest";
		entityNatAbility = "Combat Expertise"; 
		entitySpeed = 4;
	}

}
