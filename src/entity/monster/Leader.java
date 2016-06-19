package entity.monster;

import entity.Entity;
import gameEngine.WorldType;


public class Leader extends Entity{
	public Leader(){
		entityTag = "Leader"; //fancy term for support
		entityLevel = 1;
		entityHealth = 150;
		entityArmor = 8;
		entityWeaponTag = "null";
		entityWeaponDamage = 15;
		entityWeaponPriority = 4;
		entityArmorTag = "null"; 
		entityNatAbility = "rally";
		entitySpeed = 3;
		entityRange = 1;
		addEntityAbility(entityNatAbility);
		addEntityAbility(WorldType.getRandomAdaptation());
 }
}
