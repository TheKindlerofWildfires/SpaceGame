package combat;
import java.util.Random;

import entity.*;

public class Knockdown {
	private Random rng = new Random();
	public Knockdown(Entity attacker, Entity target){
		if(rng.nextDouble()<0.5){
		int oldSpeed = target.getEntitySpeed();
		target.setEntitySpeed(0);
		}
		//wait a couple of seconds and then then target.setEntitySpeed(oldspeed);
	}
}
