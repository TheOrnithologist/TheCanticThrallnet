package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

public class DatasheetAbility {

    String line;
    String abilityID;
    String name;
    String description;
    String type;
    String parameter;

    public DatasheetAbility(String line, String abilityID, String name, String description, String type, String parameter) {
        this.line = line;
        this.abilityID = abilityID;
        this.name = name;
        this.description = description;
        this.type = type;
        this.parameter = parameter;
    }

    public String getLine() {
        return line;
    }

    public String getAbilityID() {
        return abilityID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getParameter() {
        return parameter;
    }
}
