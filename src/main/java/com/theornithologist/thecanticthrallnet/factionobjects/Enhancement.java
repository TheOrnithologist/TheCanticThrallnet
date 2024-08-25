package com.theornithologist.thecanticthrallnet.factionobjects;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
