package entity.monster;


public class Tank extends Monster{
	public Tank(){
		entityTag = "Tank"; //Not that dangerous
		entityHealth = 300;
		entityArmor = 20;
		entityWeaponTag = "null";
		entityWeaponDamage = 2;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityAbility = "Reflect";
		entitySpeed = 2;
 }
}
