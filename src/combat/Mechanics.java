package combat;

import java.util.Random;
import java.math.*;
import combat.*;

import entity.*;

public class Mechanics {
	private Entity p; 
	private Entity m;
	private AbilityHandler ab;
	private Random rng = new Random();
	static double hitChance;
	public Mechanics() {
		String playerTag = "Neo"; //rework for final
		String monsterTag = "Hunter"; //rff
		AbilityHandler ab = new AbilityHandler();
		m = Entity.getEntity(monsterTag);
		p = Entity.getEntity(playerTag);
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
		ab.checkAbility(attacker.getEntityAbility(), "onhit", attacker, target);
		double damage = attacker.getEntityWeaponDamage() * (10/(10*target.getEntityArmor())); 
		target.setEntityHealth(target.getEntityHealth()-damage);
	}
	public void attackMiss(Entity attacker, Entity target){
		//check for miss conditions
		ab.checkAbility(target.getEntityAbility(), "onmiss");
	}
	public void attackHandler(Entity attacker, Entity target, attacker, target){
		//consume animation time or action counter - just a delay in code
		if (tryAttack(attacker, target)){ //rff vars
			attackHit(attacker,target);
		}else{
			attackMiss(attacker,target);
		}
	}
	//This function is used to test the code in dev
	public void init() {
		 attackHandler(p, m);// player attacks first
	}
}
