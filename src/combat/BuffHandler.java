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
	public BuffHandler(){
	}
	public void update() {
		//System.out.println(buffTagL.size());
		if(buffTagL.size()>0){
		for(int i = 0; i<buffTagL.size();i++){
			if(Tick.getUpdateTick() -startTimeL.get(i) >= durationL.get(i)){
				//System.out.println(entity.get(i).getEntitySpeed());
				remove(buffTagL.get(i), entityL.get(i), powerL.get(i));
			buffTagL.remove(i);
			entityL.remove(i);
			}
		}
		}
		
	}
	public void addDecayingBuff(String buffTag, Entity entity, int duration, int power){
		buffTagL.add(buffTag);
		//System.out.println(buffTagL.size());
		entityL.add(entity);
		durationL.add(duration);
		powerL.add(power);
		startTimeL.add(Tick.getUpdateTick());
		add(buffTag, entity, power);
	}
	private void remove(String buffTag, Entity entity, int power){
		switch(buffTag){
		case "speed":
			entity.setEntitySpeed(entity.getEntitySpeed()-power);
			break;
		default:
			System.out.println("No such buff");
		}
	}
	private void add(String buffTag, Entity entity, int power){
		switch(buffTag){
		case "speed":
			entity.setEntitySpeed(entity.getEntitySpeed()+power);
			break;
		default:
			System.out.println("No such buff");
		}
	}
}
