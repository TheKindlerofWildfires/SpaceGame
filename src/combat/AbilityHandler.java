package combat;

import java.util.Random;

import entity.*;
import gameEngine.Map;


public class AbilityHandler {
	BuffHandler buffHandler;
	Random rng = Map.rng;
	public AbilityHandler() {
		buffHandler = new BuffHandler();
	}
	public void checkAbility(String callType, Entity attacker, Entity target) {

		for (int x = 0; x < attacker.getEntityAbility().size(); x++) {
			//I HAVE NO IDEA WHY THIS DOES NOT WORK, index error
		//	System.out.println(target.getEntityAbility().get(x));
		//	if (target.getEntityAbility().get(x) == "reflect") {
		//		attacker.setEntityHealth(attacker.getEntityHealth() - (.1*attacker.getEntityWeaponDamage()));
		//	}
			switch (callType) {
			case "onInit":
				switch (attacker.getEntityAbility().get(x)) {
					case "fast":
						attacker.setEntitySpeed(attacker.getEntitySpeed()+1);
						break;
				default:
					break;
				}
			case "onHit":
				switch (attacker.getEntityAbility().get(x)) {
				case "knockdown":
					if(rng.nextDouble()<0.5){
						buffHandler.addDecayingBuff("speed",target , 60, -target.getEntitySpeed());
					}
					break;
				case "surge":
					if (target.getEntityHealth() < 50){
						buffHandler.addDecayingBuff("prio", attacker, 300, -2);
						buffHandler.addDecayingBuff("speed",attacker , 300, 1);
						attacker.setEntityAbility("surged");
						}
					break;
				case "surged":
					attacker.setEntityHealth(attacker.getEntityHealth()+5);
					break;
				case "rally":
					if(rng.nextDouble()<-0.7){
						buffHandler.addDecayingBuff("prio", attacker, 30, -1);
						buffHandler.addDecayingBuff("speed",attacker , 30, 1);
					}
					break;
				case "surprise":
					if (attacker.getEntitySpeed() > target.getEntitySpeed()){
						target.setEntityHealth(target.getEntityHealth()-attacker.getEntityWeaponDamage()); 
					}
					break;
				case "net":
					if(rng.nextDouble()<0.5){
						buffHandler.addDecayingBuff("speed",target , 60, -1);
					}
					break;
				case "deadEye":
					buffHandler.addDecayingBuff("armor", target, 60, -1);
					buffHandler.addDecayingBuff("speed",target , 60, -1);
				case "patternRecognition":
					break;
				case "combatExpertise":
					buffHandler.addDecayingBuff("speed",attacker , 60, 1);
					break;
				case "alcolyte":
					break;
				default:
					//System.err.println("onHit error--Ability Unlisted");
					break;
				}
			case "onMiss":
				switch (attacker.getEntityAbility().get(x)) {
					case "stealth":
						break;
					default:
						break;
				}
			default:
				break;
			}
		}
	}
}
