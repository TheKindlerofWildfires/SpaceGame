package combat;

import java.util.Random;
import entity.*;

public class Mechanics {
	private AbilityHandler ab;
	private Random rng = new Random();
	static double hitChance;
	public Mechanics() {
		ab = new AbilityHandler();
	}

	public boolean tryAttack(Entity attacker, Entity target) {
		//this is called when someone tries to hit something
		for (int AR = 1; AR < 6;AR++) {
			double hitChance = Gaussian.cdf(AR*target.getEntitySpeed(), 12.5, 3);
			System.out.println(hitChance);
			if (rng.nextDouble() <= hitChance) {
				//dodge event
				return false;
			} else if (AR == attacker.getEntityWeaponPriority()) {
				return true;
				//hit event
			} else {
				AR++;
			}
			
		}
		System.out.println("tryAttack error");
		return false;
	}
	public void attackHit(Entity attacker, Entity target){
		ab.checkAbility("onHit", attacker, target);
		double damage = attacker.getEntityWeaponDamage() * (10/(10*target.getEntityArmor())); 
		target.setEntityHealth(target.getEntityHealth()-damage);
	}
	public void attackMiss(Entity attacker, Entity target){
		ab.checkAbility("onMiss", attacker, target);
	}
	public void attackHandler(String attackerTag, String targetTag){
		System.out.println("delay");
		//consume animation time or action counter - just a delay in code
		Entity attacker = Entity.getEntity(attackerTag);
		Entity target = Entity.getEntity(targetTag);
		if (tryAttack(attacker, target)){
			attackHit(attacker,target);
		}else{
			attackMiss(attacker,target);
		}
		System.out.println("Stop crossing the streams");
	}
}
