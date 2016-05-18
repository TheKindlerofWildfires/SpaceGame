package combat;
import java.util.Random;

import entity.*;

public class CombatExpertiseHit {
	Random rng = new Random();
	public CombatExpertiseHit(Entity attacker, Entity target){
		//System.out.println(ah.buffLoop(30));
		String attTag = attacker.getEntityTag();
		Entity natAttacker = Entity.getEntity(attTag);
		if(rng.nextInt(1)== 0){
			attacker.setEntitySpeed(natAttacker.getEntitySpeed() + 1);
		}
		else{
			attacker.setEntitySpeed(natAttacker.getEntitySpeed());
		}
		//System.out.println(natAttacker.getEntitySpeed());
	}
}
