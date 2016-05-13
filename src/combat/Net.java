package combat;
import java.util.Random;

import entity.*;

public class Net {
	private Random rng = new Random();
	public Net(Entity attacker, Entity target){
		if(rng.nextDouble()<0.5){
		int oldSpeed = target.getEntitySpeed();
		target.setEntitySpeed(target.getEntitySpeed()-1);
		}
	}
}
