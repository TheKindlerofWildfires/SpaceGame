package entity.monster;


public class Lurker extends Monster{
	public Lurker(){
		entityTag = "Lurker"; //Not that dangerous
		entityHealth = 100;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 15;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityNatAbility = "Surprise";
		entitySpeed = 1;
		addEntityAbility(entityNatAbility);
 }
}
