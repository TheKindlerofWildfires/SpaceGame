package entity;

import entity.monster.*;
import gameEngine.WorldType.*;
import entity.player.*;

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
	public Entity(){
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
}
