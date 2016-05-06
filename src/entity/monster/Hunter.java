package entity.monster;


public class Hunter extends Monster{
	public Hunter(){
		entityTag = "Hunter"; //Not that dangerous
		entityHealth = 100;
		entityArmor = 10;
		entityWeaponTag = "null";
		entityWeaponDamage = 15;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility ="Knockdown";
		entitySpeed = 2;
		addEntityAbility(entityNatAbility);
 }
}
