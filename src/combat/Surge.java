package combat;

import entity.Entity;

public class Surge{
	public Surge(Entity attacker, Entity target) {
		if (target.getEntityHealth() > 50){
		//change this to a %health thing
		//make sure only fires once
		attacker.setEntitySpeed(attacker.getEntitySpeed() + 1);
		attacker.setEntityWeaponPriority(attacker.getEntityWeaponPriority()-2);
		attacker.setEntityAbility("Surged");
	}
		
}

}
