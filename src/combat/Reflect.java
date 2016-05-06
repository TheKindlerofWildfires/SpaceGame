package combat;
import entity.*;

public class Reflect {
	public Reflect(Entity attacker, Entity target){
		attacker.setEntityHealth(attacker.getEntityHealth() - (.1*attacker.getEntityWeaponDamage()));
	}
}
