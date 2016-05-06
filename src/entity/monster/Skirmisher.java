package entity.monster;


public class Skirmisher extends Monster{
	public Skirmisher(){
		entityTag = "Skirmisher"; //Not that dangerous
		entityHealth = 100;
		entityArmor = 9;
		entityWeaponTag = "null";
		entityWeaponDamage = 10;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "Stealth";
		entitySpeed = 3;
		addEntityAbility(entityNatAbility);
 }
}
