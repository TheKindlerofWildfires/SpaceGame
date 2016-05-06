package combat;
import entity.*;

public class CombatExpertiseHit {
	public CombatExpertiseHit(Entity attacker, Entity target){
		int oldSpeed = attacker.getEntitySpeed();
		attacker.setEntitySpeed(attacker.getEntitySpeed() + 1);
	}//second decay
}
