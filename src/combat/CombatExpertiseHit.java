package combat;
import GUI.Tick;
import entity.*;

public class CombatExpertiseHit {
	boolean used = true;
	int lastTime;
	String attTag;
	public CombatExpertiseHit(Entity attacker, Entity target){
		lastTime = 0;
		attTag = attacker.getEntityTag();
		if(!used){
		attacker.setEntitySpeed(attacker.getEntitySpeed() + 1);
		used = true;
		lastTime = Tick.getUpdateTick();
		}else if (Tick.getUpdateTick()-lastTime > 30){
			System.out.println(attTag);
			attacker.setEntitySpeed(Entity.getEntity(attTag).getEntitySpeed());
			used = false;
		}
	}//second decay
}
