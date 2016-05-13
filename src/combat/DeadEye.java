package combat;
import java.util.Random;

import entity.*;

public class DeadEye {
	private Random rng = new Random();
	public DeadEye(Entity attacker, Entity target){
		if(rng.nextDouble()<0.3){
		int oldSpeed = target.getEntitySpeed();
		double oldArmor = target.getEntityArmor();
		target.setEntityArmor(target.getEntityArmor()-1);
		target.setEntitySpeed(target.getEntitySpeed()-1);
		//second decay
		}
	}
}
