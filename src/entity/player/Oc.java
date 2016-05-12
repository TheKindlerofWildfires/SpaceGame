package entity.player;


public class Oc extends Player{
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
		addEntityAbility(entityNatAbility);
	}

}
