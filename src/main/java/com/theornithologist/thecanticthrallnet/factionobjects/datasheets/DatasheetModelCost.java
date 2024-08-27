package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

public class DatasheetModelCost {

    String line;
    String description;
    String cost;

    public DatasheetModelCost(String line, String description, String cost) {
        this.line = line;
        this.description = description;
        this.cost = cost;
    }

    public String getLine() {
        return line;
    }

    public String getDescription() {
        return description;
    }

    public String getCost() {
        return cost;
    }
}
