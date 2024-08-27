package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

public class DatasheetModel {

    String line;
    String name;
    String movement;
    String toughness;
    String save;
    String invulnerableSave;
    String invulnerableSaveDesc;
    String wounds;
    String leadership;
    String objectiveControl;
    String baseSize;
    String baseSizeDesc;

    public DatasheetModel(String line, String name, String movement, String toughness, String save, String invulnerableSave, String invulnerableSaveDesc, String wounds, String leadership, String objectiveControl, String baseSize, String baseSizeDesc) {
        this.line = line;
        this.name = name;
        this.movement = movement;
        this.toughness = toughness;
        this.save = save;
        this.invulnerableSave = invulnerableSave;
        this.invulnerableSaveDesc = invulnerableSaveDesc;
        this.wounds = wounds;
        this.leadership = leadership;
        this.objectiveControl = objectiveControl;
        this.baseSize = baseSize;
        this.baseSizeDesc = baseSizeDesc;
    }

    public String getLine() {
        return line;
    }

    public String getName() {
        return name;
    }

    public String getMovement() {
        return movement;
    }

    public String getToughness() {
        return toughness;
    }

    public String getSave() {
        return save;
    }

    public String getInvulnerableSave() {
        return invulnerableSave;
    }

    public String getInvulnerableSaveDesc() {
        return invulnerableSaveDesc;
    }

    public String getWounds() {
        return wounds;
    }

    public String getLeadership() {
        return leadership;
    }

    public String getObjectiveControl() {
        return objectiveControl;
    }

    public String getBaseSize() {
        return baseSize;
    }

    public String getBaseSizeDesc() {
        return baseSizeDesc;
    }
}
