package entity;

import entity.monster.*;
import entity.player.*;
import inventory.Inventory;

import java.util.*;

public abstract class Entity {
	protected String entityTag; // Entity identifier
	protected String entityCondition;
	protected double entityHealth;
	protected double entitySanity;
	protected double entityArmor;
	protected double entitySanityResist; // like armor for sanity
	protected String entityWeaponTag; // weapon identifier
	protected double entityWeaponDamage;
	protected double entityWeaponPriority; // higherprio fires later
	protected String entityArmorTag;
	protected List<String> entityAbility = new ArrayList<String>(); // Special sauce
	protected int entitySpeed; // blocks per turn
	protected String entityNatAbility;
	protected double entityRange;
	protected int entityLevel;
	protected double entityHunger;
	protected Inventory inventory = new Inventory(144);
	public Entity(){
		entityHunger = 100;
	}
	public String getEntityTag() { 
		return entityTag;
	}
	public String getEntityCondition() { 
		return entityCondition;
	}
	public double getEntityHealth() { //needs to call entityTag
		return entityHealth; //to decide which entityHealth to display
	}
	public double getEntitySanity() {
		return entitySanity;
	}
	public double getEntityArmor() {
		return entityArmor;
	}
	public double getEntitySanityResist() {
		return entitySanityResist;
	}
	public String getEntityWeaponTag() {
		return entityWeaponTag;
	}
	public double getEntityWeaponDamage() {
		return entityWeaponDamage;
	}
	public double getEntityWeaponPriority() {
		return entityWeaponPriority;
	}
	public String getEntityArmorTag() {
		return entityArmorTag;
	}
	public List<String> getEntityAbility() {
		return entityAbility;
	}
	public String getEntityNatAbility() {
		return entityNatAbility;
	}
	public int getEntitySpeed() {
		return entitySpeed;
	}
	public double getEntityRange() {
		return entityRange;
	}
	public int getEntityLevel() {
		return entityLevel;
	}
	public double getEntityHunger() {
		return entityHunger;
	}
	public Inventory getEntityInventory() {
		return inventory;
	}
	public void initEntity(String entityTag) {
		Entity buffer = getEntity(entityTag);
		entityCondition = buffer.getEntityCondition();
		entityHealth = buffer.getEntityHealth();
		entitySanity = buffer.getEntitySanity();
		entityArmor= buffer.getEntityArmor();
		entitySanityResist= buffer.getEntitySanityResist();
		entityWeaponTag = buffer.getEntityWeaponTag();
		entityWeaponDamage = buffer.getEntityWeaponDamage();
		entityWeaponPriority = buffer.getEntityWeaponPriority(); 
		entityArmorTag = buffer.getEntityArmorTag();
		entityAbility = buffer.getEntityAbility(); 
		entitySpeed = buffer.getEntitySpeed();
		entityNatAbility = buffer.getEntityNatAbility();
		entityRange = buffer.getEntitySanity();
		entityLevel = buffer.getEntityLevel();
		entityHunger = buffer.getEntityHunger();
	}
	
	public void setEntityTag(String entityTag){
		this.entityTag = entityTag;
	}
	public void setEntityCondition(String entityCondtion){
		this.entityCondition = entityCondtion;
	}
	public void setEntityHealth(double entityHealth){
		this.entityHealth = entityHealth;
	}
	public void setEntityArmor(double entityArmor){
		this.entityArmor = entityArmor;
	}
	public void setEntitySanityResist(double entitySanityResist){
		this.entitySanityResist = entitySanityResist;
	}
	public void setEntityWeaponTag(String entityWeaponTag){
		this.entityWeaponTag = entityWeaponTag;
	}
	public void setEntityWeaponPriority(double entityWeaponPriority){
		this.entityWeaponPriority = entityWeaponPriority;
	}
	public void setEntityArmorTag(String entityArmorTag){
		this.entityArmorTag = entityArmorTag;
	}
	public void setEntitySpeed(int entitySpeed){
		this.entitySpeed = entitySpeed;
	}
	public void setEntityRange(double entityRange){
		this.entityRange = entityRange;
	}
	public void setEntityAbility(String entityAbility){
		this.entityAbility.clear();
		this.entityAbility.add(entityAbility);
	}
	public void addEntityAbility(String entityAbility){
		if (!(this.entityAbility.contains(entityAbility))){
			this.entityAbility.add(entityAbility);
		}

	}
	public void setEntityLevel(int level){
		this.entityLevel = level;
	}
	public void setEntityHunger(double hunger){
		this.entityHunger = hunger;
	}
	public void addEntityInventory(String item){
		//System.out.println("adding");
		this.inventory.add(item);
	}
	public static Entity getEntity(String entityTag){
		switch(entityTag){
		case "Grunt":
			return new Grunt();
		case "Hunter":
			return new Hunter();
		case "Juggernaut":
			return new Juggernaut();
		case "Leader":
			return new Leader();
		case "Lurker":
			return new Lurker();
		case "Mage":
			return new Mage();
		case "Scout":
			return new Scout();
		case "Skirmisher":
			return new Skirmisher();
		case "Sniper":
			return new Sniper();
		case "Tank":
			return new Tank();
		case "Neo":
			return new Neo();
		case "Agent":
			return new Agent();
		case "Oc":
			return new Oc();
		case "God":
			return new God();
		default:
			System.err.println("Invalid entity Tag");
			return new Neo();
		}
	
}
	public static int getID(String entityTag){
		switch(entityTag){
		case "Grunt":
			return 0;
		case "Hunter":
			return 1;
		case "Juggernaut":
			return 2;
		case "Leader":
			return 3;
		case "Lurker":
			return 4;
		case "Mage":
			return 5;
		case "Scout":
			return 6;
		case "Skirmisher":
			return 7;
		case "Sniper":
			return 8;
		case "Tank":
			return 9;
		case "Neo":
			return 10;
		case "Agent":
			return 11;
		case "Oc":
			return 12;
		case "God":
			return 13;
		default:
			System.err.println("Invalid entity Tag");
			return -1;
		}
	}
	public static Entity getBlankEntity(String entityTag, int level, List<String> entityAbility){
		Entity value = getEntity(entityTag);
		value.setEntityLevel(level);
		for(int i = 0; i>entityAbility.size();i++){
			value.addEntityAbility(entityAbility.get(i));
		}
		return value;
	}
}
