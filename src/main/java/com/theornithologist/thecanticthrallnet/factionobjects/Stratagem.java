package com.theornithologist.thecanticthrallnet.factionobjects;

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
}
