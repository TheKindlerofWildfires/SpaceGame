package entity.monster;


public class Scout extends Monster{
	public Scout(){
		entityTag = "Scout"; //Not that dangerous
		entityHealth = 40;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 3;
		entityWeaponPriority = 5;
		entityArmorTag = "null"; //Reflect 1/10th of damage taken
		entityAbility = "Net";
		entitySpeed = 5;
 }
}
