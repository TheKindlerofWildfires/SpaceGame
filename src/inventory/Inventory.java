package inventory;

import java.util.ArrayList;

public class Inventory {
	int size;
	ArrayList<String> inventory;
	public Inventory(int size){
		this.size = size;
		inventory = new ArrayList<String>();
	}
	public void add(String item) {
		if(inventory.size()< size){
			this.inventory.add(item);
		}else{
			System.out.println("full");
		}
		
	}
	public int size() {
		return inventory.size();
	}
	public String get(int i){
		return inventory.get(i);
	}
}
