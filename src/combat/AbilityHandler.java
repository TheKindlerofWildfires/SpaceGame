package combat;
import entity.*;

public class AbilityHandler {
	public AbilityHandler(){
		
	}
	public static AbilityHandler checkAbility(String entityAbility, String callType, Entity attacker, Entity target){
		switch(entityAbility){
		//Should return new classes that handles that specific ability
		case "Knockdown":
			return ;
		case "Surge":
			return ;
		case "Rally":
			return ;
		case "Surprise":
			return ;
		case "Net":
			return ;
		case "Stealth":
			return ;
		case "Dead Eye":
			return ;
		case "Reflect":
			return ;
		case "Pattern recognition":
			return ;
		case "Combat Expertise":
			return ;
		case "Alcolyte":
			return ;
		default:
			System.err.println("abilityHandler");
			return ;
		}

	}
}
