package combat;
import java.util.Random;

import entity.*;
import gameEngine.Tick;

public class DeadEye {
	boolean can = true;
	int time = 0;
	int lastTime = 0;
	int oldSpeed = 0;
	double oldArmor = 0;
	private Random rng = new Random();
	public DeadEye(Entity attacker, Entity target){
		if (can){
			if(rng.nextDouble()<0.3){
			oldSpeed = target.getEntitySpeed();
			oldArmor = target.getEntityArmor();
			target.setEntityArmor(target.getEntityArmor()-1);
			target.setEntitySpeed(target.getEntitySpeed()-1);
			can = false;
			lastTime = Tick.getUpdateTick();
			}
		}else if (Tick.getUpdateTick()-lastTime > 30){ //I suspect this only works onhit
			target.setEntitySpeed(oldSpeed);
			target.setEntityArmor(oldArmor);
			can = true;
		}
	}
}
