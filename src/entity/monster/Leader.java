package entity.monster;


public class Leader extends Monster{
	public Leader(){
		entityTag = "Leader"; //Not that dangerous
		entityHealth = 150;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 15;
		entityWeaponPriority = 4;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "Rally";
		entitySpeed = 3;
		addEntityAbility(entityNatAbility);
 }
}
