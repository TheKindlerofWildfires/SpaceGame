package entity.monster;


public class Sniper extends Monster{
	public Sniper(){
		entityTag = "Sniper"; 
		entityHealth = 50;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 20;
		entityWeaponPriority = 2;
		entityArmorTag = "null"; 
		entityNatAbility = "Dead Eye";
		entitySpeed = 1;
		addEntityAbility(entityNatAbility);
 }
}
