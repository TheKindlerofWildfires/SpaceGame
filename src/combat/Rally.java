package combat;
import entity.*;

public class Rally {
	public Rally(Entity attacker, Entity target){
		//change this to a %health thing
		//make sure only fires once
		attacker.setEntitySpeed(attacker.getEntitySpeed() + 1);
		attacker.setEntityWeaponPriority(attacker.getEntityWeaponPriority()-2);
		attacker.setEntityAbility("Rallied");
	}
}
