package GUI;

public abstract class Tick {
	static int tickUpdate;
	static int tickSec;
	static int tickMin;
	public Tick(){
		tickUpdate = 0;
		tickSec = 0;
		tickMin = 0;
	}
	public static void updateTick(){
		tickUpdate++;
		if (tickUpdate == 60){
			tickSec++;
			if (tickSec == 60){
				tickMin++;
			}
		}
	}
	public static int getUpdateTick(){
		return tickUpdate;
	}
	public static int getSecTick(){
		return tickSec;
	}
	public static int getMinTick(){
		return tickMin;
	}
	
}
