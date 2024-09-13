package com.theornithologist.thecanticthrallnet.factionobjects.detachments;

public class Enhancement {

    String name;
    String legend;
    String description;
    String cost;

    public Enhancement(String name, String legend, String description, String cost) {
        this.name = name;
        this.legend = legend;
        this.description = description;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getLegend() {
        return legend;
    }

    public String getDescription() {
        return description;
    }

    public String getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name;
    }
}
