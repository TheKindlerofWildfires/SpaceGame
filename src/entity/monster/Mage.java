package entity.monster;


public class Mage extends Monster{
	public Mage(){
		entityTag = "Mage"; //Not that dangerous
		entityHealth = 50;
		entityArmor = 10;
		entityWeaponTag = "null";
		entityWeaponDamage = 5;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "Mage";
		entitySpeed = 2;
		addEntityAbility(entityNatAbility);
 }
}
