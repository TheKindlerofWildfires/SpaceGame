package combat;

import java.util.Random;

import entity.Entity;
import maths.Distance;
import maths.Gaussian;

public class Mechanics {
	private AbilityHandler ab;
	private Random rng = new Random();
	double hitChance;
	int[] attIndex;
	int[] tarIndex;

	public Mechanics() {
		ab = new AbilityHandler();
		tarIndex = new int[2];
		attIndex = new int[2];
	}

	public boolean tryAttack(Entity attacker, Entity target) {
		//this is called when someone tries to hit something

		for (int AR = 1; AR < 6;) {
			double hitChance = Gaussian.cdf(AR * target.getEntitySpeed(), 12.5, 3);
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
		System.err.println("tryAttack error");
		return false;
	}

	public void attackHit(Entity attacker, Entity target) {
		ab.checkAbility("onHit", attacker, target);
		double damage = attacker.getEntityWeaponDamage() * (10 / (10 + target.getEntityArmor()));
		//System.out.println(target.getEntityArmor());
		target.setEntityHealth(target.getEntityHealth() - damage);
		System.out.println(target.getEntityTag() + " " + target.getEntityHealth());
	}

	public void attackMiss(Entity attacker, Entity target) {
		ab.checkAbility("onMiss", attacker, target);
	}

	public boolean inRange(Entity attacker) {
		Distance d = new Distance();
		
		double disInHexes = d.euclidDis(attIndex, tarIndex)/(Math.sqrt(2));
		System.out.println(disInHexes);
		//System.out.println(disInHexes);
		if (attacker.getEntityRange() >= (disInHexes)) {
			return true;
		} else {
			return false;
		}

	}

	public void attackHandler(Entity attacker, Entity target, int[] attIndex, int[] tarIndex) {
		//find if in range
		//System.out.println("e");
		this.attIndex = attIndex;
		this.tarIndex = tarIndex;
		if (inRange(attacker)) {
			
			//consume animation time or action counter - just a delay in code
			//Entity attacker = Entity.getEntity(attackerTag);
			//Entity target = Entity.getEntity(targetTag);
			if (tryAttack(attacker, target)) {
				System.out.println("Hit");
				//called 5 times
				attackHit(attacker, target);
			} else {
				System.out.println("Miss");
				attackMiss(attacker, target);
			}
		}
	}
}
