package combat;

import entity.Player;
import entity.Monster;
import entity.Entity;
import entity.*;

import java.util.ArrayList;
import java.util.Random;

public class combatBasic {

	private double damage;
	private double deadEyeStacks;
	private String spell[];
	private String spellChoice;
	private Entity p; 
	private Entity m;
	private Random rng  = new Random();

	public combatBasic() {
		String playerTag = "Neo";
		String monsterTag = "Tank";
		//rng spits out random player
		//Weapon damage is bugged
		//
		p = Entity.getEntity(playerTag);
		m = Entity.getEntity(monsterTag);
		System.out.println(m.getEntityAbility());
		deadEyeStacks = 0;


	}

	public void Attack(Entity attacker, Entity defender) {
		if (attacker.getEntityTag() == "Mage") {
			spellCasting();
		} else {
			for (int AR = 1; AR < 6;) {
				double hitChance = (1 / 17.5 * Math.pow(Math.E, .1 * AR
						* defender.getEntitySpeed()));
				if (rng.nextDouble() <= hitChance) {
					Dodge(attacker, defender);
					break;
				} else if (AR == attacker.getEntityWeaponPriority()) {
					Hit(attacker, defender);
					break;
				} else {
					AR++;
				}
			}
		}
	}

	public void Dodge(Entity attacker, Entity defender) {
		System.out.println(defender.getEntityTag() + " Dodge");
		iliterativeCombat(attacker, defender);
	}

	// Stealth: Every thrid attack auto dodge
	// Reactive attacks: Attacks advantage
	// Reflective Armor: Reflects 1/10 of damage taken
	// Mage: random spell every round instead of attack, including nuke, heal,
	// debuff
	// Nockdown: Skip a player turn on % */
	public void Hit(Entity attacker, Entity defender) {
		System.out.println(attacker.getEntityTag() + " Hit");
		if (attacker.getEntityTag() == p.getEntityTag()) {
			damage = p.getEntityWeaponDamage() * (10 / (10 + m.getEntityArmor()));
			m.setEntityHealth(m.getEntityHealth() - damage);
			if ((p.getEntityWeaponTag() == "Cultist's Blade")
					&& (rng.nextDouble() <= 0.8) && (!(p.getEntityHealth() >= 200))) {
				p.setEntityHealth(p.getEntityHealth() + p.getEntityWeaponDamage());
			}
			iliterativeCombat(attacker, defender);
		} else {
			onHitAbilityCall(attacker, defender);

		}
	}

	public void onHitAbilityCall(Entity attacker, Entity defender) {
		if (m.getEntityAbility() == "Surge") {
			if (m.getEntityHealth() >= 50) {
				m.setEntityAbility("Surged");
				m.setEntitySpeed(m.getEntitySpeed()+1);
				m.setEntityWeaponPriority(m.getEntityWeaponPriority() - 2);
			}
		} else if (m.getEntityAbility() == "Surged") {
			m.setEntityHealth(m.getEntityHealth() + damage);
		} else if (m.getEntityAbility() == "Dead Eye") {
			if (rng.nextDouble() <= 0.8 / ((deadEyeStacks + 1))) {
				p.setEntityHealth(p.getEntityHealth()-1);
				p.setEntitySpeed(p.getEntitySpeed()-1);
				deadEyeStacks++;
			} else if (deadEyeStacks >= 1) {
				deadEyeStacks = deadEyeStacks - 1;
				p.setEntityHealth(p.getEntityHealth()+1);
				p.setEntitySpeed(p.getEntitySpeed()+1);
			}
		} else if (m.getEntityAbility() == "Knockdown") {
			if (rng.nextDouble() <= 0.3) {
				p.setEntityCondition("Knockdown");
			}
		} else if (m.getEntityAbility() == "Rally") {
			m.setEntityAbility("Rallied");
			m.setEntitySpeed(m.getEntitySpeed()+1);
			m.setEntityWeaponPriority(m.getEntityWeaponPriority()-1);
		} else
			damage = m.getEntityWeaponDamage() * (10 / (10 + p.getEntityArmor()));
			p.setEntityHealth(p.getEntityHealth()-damage);
			iliterativeCombat(attacker, defender);
	}

	public void iliterativeCombat(Entity attacker, Entity defender) { // its a
																		// pun
		if (!(p.getEntityHealth() <= 0) && !(p.getEntityHealth() <= 0)) {
			System.out.println(m.getEntityHealth());
			Attack(defender, attacker);
		} else if (p.getEntityHealth() > 0) {
			System.out.println("Winner is " + p.getEntityTag());
			System.out.println(p.getEntityTag() + " has " + p.getEntityHealth());
			System.out.println(m.getEntityTag() + " died at " + m.getEntityHealth());
		} else if (m.getEntityHealth() > 0) {
			System.out.println("Winner is " + m.getEntityTag());
			System.out.println(p.getEntityTag() + " died at " + p.getEntityHealth());
			System.out.println(m.getEntityTag() + " has " + m.getEntityHealth());
		} else
			System.out.println("Checkout iliterateCombat cuz it has a bug");
	}

	public void init() {
		Attack(p, m); // player attacks first
	}

	public void spellCasting() {
		spell = new String[3];
		spell[0] = "Firebolt";
		spell[1] = "Life Siphon";
		spell[2] = "Heal";
		//spellChoice = spell[rng.nextInt(spell.length)];
		if (m.getEntityHealth() >= 70){
			spellChoice = "Firebolt";
		}else if (m.getEntityHealth() <= 20){
			spellChoice = "Heal";
		}else{
			spellChoice = "Life Siphon";
		}
		System.out.println("Mage cast "+ spellChoice);
		if (spellChoice == "Firebolt"){
			p.setEntityHealth(p.getEntityHealth()-5);
		}else if (spellChoice == "Life Siphon"){
			p.setEntityHealth(p.getEntityHealth()-3);
			m.setEntityHealth(m.getEntityHealth()-3);
		}else if (spellChoice == "Heal"){
			m.setEntityHealth(m.getEntityHealth()-5);
		}
		iliterativeCombat(m,p);
	}
}
