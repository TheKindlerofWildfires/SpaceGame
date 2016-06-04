package combat;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import gameEngine.Tick;

public class BuffHandler {
	protected static List<String> buffTagL = new ArrayList<String>();
	protected static List<Entity> entityL = new ArrayList<Entity>();
	protected static List<Integer> durationL = new ArrayList<Integer>();
	protected static List<Integer> startTimeL = new ArrayList<Integer>();
	protected static List<Integer> powerL = new ArrayList<Integer>();
	public String[] abilityList = {"poison", "treeClimb", "camo", "nightHunter", 
			"burrow", "plateArmor", "fast", "flashSwarm", "guerilla", "rage", 
			"tracker", "knockdown","surge","rally","surprise","mage","net","stealth",
			"deadEye","reflect","patternRecognition","combatExpertise","alcolyte"};
	public BuffHandler(){
		
	}
	public void update() {
		if(buffTagL.size()>0){
		for(int i = 0; i<buffTagL.size();i++){
			if(Tick.getUpdateTick() -startTimeL.get(i) >= durationL.get(i)){
				remove(buffTagL.get(i), entityL.get(i), powerL.get(i));
			buffTagL.remove(i);
			entityL.remove(i);
			durationL.remove(i);
			startTimeL.remove(i);
			powerL.remove(i);
			}
		}
		}
		
	}
	public void addDecayingBuff(String buffTag, Entity entity, int duration, int power){
		buffTagL.add(buffTag);
		entityL.add(entity);
		durationL.add(duration);
		powerL.add(power);
		startTimeL.add(Tick.getUpdateTick());
		add(buffTag, entity, power);
	}
	private void remove(String buffTag, Entity entity, int power){
		switch(buffTag){
		case "regen":
			entity.setEntityHealth(entity.getEntityHealth()-power);
		case "speed":
			entity.setEntitySpeed(entity.getEntitySpeed()-power);
			break;
		case "prio":
			entity.setEntityWeaponPriority(entity.getEntityWeaponPriority()-power);
			break;
		case "armor":
			entity.setEntityArmor(entity.getEntityArmor()-power);
			break;
		case "hunger":
			entity.setEntityHunger(entity.getEntityHunger()-power);
			break;
		default:
			System.out.println("No such buff");
		}
	}
	private void add(String buffTag, Entity entity, int power){
		switch(buffTag){
		case "regen":
			entity.setEntityHealth(entity.getEntityHealth()+power);
		case "speed":
			entity.setEntitySpeed(entity.getEntitySpeed()+power);
			break;
		case "prio":
			entity.setEntityWeaponPriority(entity.getEntityWeaponPriority()+power);
			break;
		case "armor":
			entity.setEntityArmor(entity.getEntityArmor()+power);
			break;
		case "hunger":
			entity.setEntityHunger(entity.getEntityHunger()+power);
			break;
		default:
			System.out.println("No such buff");
		}
	}
}
