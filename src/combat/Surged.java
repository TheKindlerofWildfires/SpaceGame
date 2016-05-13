package combat;
import entity.*;

public class Surged {
	public Surged(Entity attacker, Entity target){
		attacker.setEntityHealth(attacker.getEntityHealth()+5);
	}
}
