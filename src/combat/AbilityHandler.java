package combat;
import entity.*;
//sigh, how do switch statements work?
public class AbilityHandler {
	public AbilityHandler(){
		
	}
	public void checkAbility(String callType, Entity attacker, Entity target){
		switch (callType){
		case "onHit":
			//looks like this should all be multithreaded
			switch(attacker.getEntityAbility()){
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
				System.err.println("onHit error");
				break;
			}
			if (target.getEntityAbility() == "Reflect"){
				new Reflect(attacker, target);
				break;
			}
		case "onMiss":
			switch(target.getEntityAbility()){
			case "Stealth":
				new Stealth(attacker, target);
				break;
			default:
				System.err.println("onMiss error");
				break;
			}
		default:
			System.err.println("error on call type");
			break;
		}
	}
}
