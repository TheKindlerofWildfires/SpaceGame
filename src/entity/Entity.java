package entity;

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
	protected static String entityAbility; // Special sauce
	protected static int entitySpeed; // blocks per turn
	public Entity(){
		
	}
	//write a big switch statement I guess
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
	public String getEntityAbility() {
		return entityAbility;
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
		this.entityAbility = entityAbility;
	}
	public static Entity getEntity(String entityTag){
		switch(entityTag){
		case "Neo":
			return new Neo();
		case "Tank":
			return new Tank();
		default:
			System.err.println("Invalid entity Tag");
			return new Neo();
		}

}
}
