package entity.monster;


public class Grunt extends Monster{
	public Grunt(){
		entityTag = "Grunt"; //Not that dangerous
		entityHealth = 50;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 5;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityAbility = "null";
		entitySpeed = 2;
 }
}
