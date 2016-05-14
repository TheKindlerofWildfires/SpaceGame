package gameEngine;

public class UpdateTick {
	long time;
	long delta;
	public UpdateTick(){
	}
	public boolean timer(int delayInMS){
		long lastTime = System.currentTimeMillis();
		for(int i = 0; i < delayInMS;){
			if (System.currentTimeMillis()-lastTime > delayInMS){
				return true;
			}else{
				i++;
			}	
		}
		return false;
	}
}
