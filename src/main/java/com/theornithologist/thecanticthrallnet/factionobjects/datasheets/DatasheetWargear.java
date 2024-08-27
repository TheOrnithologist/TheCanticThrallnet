package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

public class DatasheetWargear {

    String line;
    String dice;
    String name;
    String description;
    String range;
    String type;
    String attacks;
    String attackSkill;
    String strength;
    String armorPiercing;
    String damage;

    public DatasheetWargear(String line, String dice, String name, String description, String range, String type, String attacks, String attackSkill, String strength, String armorPiercing, String damage) {
        this.line = line;
        this.dice = dice;
        this.name = name;
        this.description = description;
        this.range = range;
        this.type = type;
        this.attacks = attacks;
        this.attackSkill = attackSkill;
        this.strength = strength;
        this.armorPiercing = armorPiercing;
        this.damage = damage;
    }

    public String getLine() {
        return line;
    }

    public String getDice() {
        return dice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRange() {
        return range;
    }

    public String getType() {
        return type;
    }

    public String getAttacks() {
        return attacks;
    }

    public String getAttackSkill() {
        return attackSkill;
    }

    public String getStrength() {
        return strength;
    }

    public String getArmorPiercing() {
        return armorPiercing;
    }

    public String getDamage() {
        return damage;
    }
}
