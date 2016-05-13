package combat;
import entity.*;

public class Stealth {
	public Stealth(Entity attacker, Entity target){
		attacker.setEntityHealth(attacker.getEntityHealth()-target.getEntityWeaponDamage());
	}
}
