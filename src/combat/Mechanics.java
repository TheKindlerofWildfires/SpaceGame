package combat;

import java.util.Random;

import maths.Euclidian;
import maths.Gaussian;
import maths.Vector3f;
import entity.*;
import gameEngine.Hexagon;
import gameEngine.Map;

public class Mechanics {
	private AbilityHandler ab;
	private Random rng = new Random();
	static double hitChance;
	static Vector3f attHex;
	static Vector3f tarHex;
	public Mechanics() {
		ab = new AbilityHandler();
		tarHex = new Vector3f();
		attHex = new Vector3f();
	}

	public boolean tryAttack(Entity attacker, Entity target) {
		//this is called when someone tries to hit something
		
		for (int AR = 1; AR < 6;) {
			double hitChance = Gaussian.cdf(AR*target.getEntitySpeed(), 12.5, 3);
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
		double damage = attacker.getEntityWeaponDamage() * (10/(10+target.getEntityArmor())); 
		//System.out.println(target.getEntityArmor());
		target.setEntityHealth(target.getEntityHealth()-damage);
		System.out.println(target.getEntityTag()+" " +target.getEntityHealth());
	}
	public void attackMiss(Entity attacker, Entity target){
		ab.checkAbility("onMiss", attacker, target);
	}
	public static boolean inRange(Entity attacker){
		Euclidian e = new Euclidian();
		double disInHexes = e.euclidDis(attHex, tarHex)/Map.APOTHEM/4.26666625;
		//System.out.println(disInHexes);
		if(attacker.getEntityRange()>(disInHexes) ){
			return true;
		}else{
			return false;
		}
		
	}
	public void attackHandler(Entity attacker, Entity target, Vector3f attHex,  Vector3f tarHex){
		//find if in range
		this.attHex = attHex;
		this.tarHex = tarHex;
		if(inRange(attacker)){

			//consume animation time or action counter - just a delay in code
			//Entity attacker = Entity.getEntity(attackerTag);
			//Entity target = Entity.getEntity(targetTag);
			if (tryAttack(attacker, target)){
				//System.out.println("Hit");
				//called 5 times
				attackHit(attacker,target);
			}else{
				//System.out.println("Miss");
				attackMiss(attacker,target);
			}
		}
	}
}
