package entity;

import entity.monster.*;
import entity.player.*;

import java.util.*;

public abstract class Entity {
	protected String entityTag; // Entity identifier
	protected static String entityCondition;
	protected static double entityHealth;
	protected static double entitySanity;
	protected static double entityArmor;
	protected static double entitySanityResist; // like armor for sanity
	protected static String entityWeaponTag; // weapon identifier
	protected static double entityWeaponDamage;
	protected static double entityWeaponPriority; // higherprio fires later
	protected static String entityArmorTag;
	protected List<String> entityAbility = new ArrayList<String>(); // Special sauce
	protected static int entitySpeed; // blocks per turn
	protected static String entityNatAbility;
	public Entity(){
		//looks to me like the way we did instantiaing entities was not happy
		//passed entities seem to loose all feilds
		//and all entites are one, which migh be doing something
		addEntityAbility(this.entityNatAbility);
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
		default:
			System.err.println("Invalid entity Tag");
			return new Neo();
		}

}
}
