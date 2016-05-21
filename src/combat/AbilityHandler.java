package combat;

import java.util.Random;

import entity.*;


public class AbilityHandler {
	BuffHandler buffHandler;
	Random rng = new Random();
	public AbilityHandler() {
		buffHandler = new BuffHandler();
	}
	//System.out.println(used);
	public void checkAbility(String callType, Entity attacker, Entity target) {

		for (int x = 0; x < attacker.getEntityAbility().size(); x++) {
			switch (callType) {
			case "onHit":
				// looks like this should all be multi-threaded
				//System.out.println(attacker.getEntityAbility().get(x));
				//System.out.println(attacker.getEntityAbility().get(x));
				switch (attacker.getEntityAbility().get(x)) {
				case "Knockdown":
					if(rng.nextDouble()<0.5){
						buffHandler.addDecayingBuff("speed",target , 60, -target.getEntitySpeed());
					}
					break;
				case "Surge":
					new Surge(attacker, target);
					break;
				case "Surged":
					new Surged(attacker, target);
					break;
				case "Rally":
					new Rally(attacker, target);
					break;
				case "Surprise":
					break;
				case "Net":
					if(rng.nextDouble()<0.5){
						buffHandler.addDecayingBuff("speed",target , 60, -1);
					}
					break;
				case "Stealth":
					break;
				case "Dead Eye":
					new DeadEye(attacker, target);
				case "Reflect":
					break;
				case "Pattern recognition":
					break;
				case "Combat Expertise":
					buffHandler.addDecayingBuff("speed",attacker , 60, 1);
					break;
				case "Alcolyte":
					break;
				default:
					//System.err.println("onHit error--Ability Unlisted");
					break;
				}
				if (target.getEntityAbility().get(x) == "Reflect") {
					new Reflect(attacker, target);
					break;
				}
			case "onMiss":
				switch (target.getEntityAbility().get(x)) {
				case "Stealth":
					new Stealth(attacker, target);
					break;
				default:
					break;
				}
			default:
				//System.err.println("error on call type");
				
				//Suspect this is a big issue
				//System.out.println(attacker.getEntityAbility());
				break;
			}
		}
	}
}
