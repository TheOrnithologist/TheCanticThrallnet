package com.theornithologist.thecanticthrallnet.factionobjects;

import com.theornithologist.thecanticthrallnet.factionobjects.datasheets.Datasheet;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Detachment;

import java.util.List;

public class Roster {

    String rosterName;
    String factionName;
    int pointTotal;
    int battleSize;
    Detachment detachment;
    List<Datasheet> datasheets;

    public Roster(String rosterName, String factionName, int battleSize, Detachment detachment) {
        this.rosterName = rosterName;
        this.factionName = factionName;
        this.battleSize = battleSize;
        this.detachment = detachment;
    }

    public String getRosterName() {
        return rosterName;
    }

    public void setRosterName(String rosterName) {
        this.rosterName = rosterName;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public int getPointTotal() {
        return pointTotal;
    }

    public void setPointTotal(int pointTotal) {
        this.pointTotal = pointTotal;
    }

    public int getBattleSize() {
        return battleSize;
    }

    public void setBattleSize(int battleSize) {
        this.battleSize = battleSize;
    }

    public Detachment getDetachment() {
        return detachment;
    }

    public void setDetachment(Detachment detachment) {
        this.detachment = detachment;
    }

    public List<Datasheet> getDatasheets() {
        return datasheets;
    }

    public void setDatasheets(List<Datasheet> datasheets) {
        this.datasheets = datasheets;
    }
}
