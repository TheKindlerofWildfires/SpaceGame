package combat;

import entity.*;


public class AbilityHandler {
	public AbilityHandler() {

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
					new Knockdown(attacker, target);
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
					new Net(attacker, target);
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
					new CombatExpertiseHit(attacker, target);
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
