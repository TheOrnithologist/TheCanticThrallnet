package com.theornithologist.thecanticthrallnet.factionobjects.detachments;

public class Stratagem {

    String name;
    String type;
    String cp_cost;
    String legend;
    String turn;
    String phase;
    String description;

    public Stratagem(String name, String type, String cp_cost, String legend, String turn, String phase, String description) {
        this.name = name;
        this.type = type;
        this.cp_cost = cp_cost;
        this.legend = legend;
        this.turn = turn;
        this.phase = phase;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getCp_cost() {
        return cp_cost;
    }

    public String getLegend() {
        return legend;
    }

    public String getTurn() {
        return turn;
    }

    public String getPhase() {
        return phase;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
