package combat;
import entity.*;

public class Surprise {
	public Surprise(Entity attacker, Entity target){
		if (attacker.getEntitySpeed() > target.getEntitySpeed()){
			target.setEntityHealth(target.getEntityHealth()-attacker.getEntityWeaponDamage()); 
		}
	}
}
