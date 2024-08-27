package com.theornithologist.thecanticthrallnet.factionobjects.detachments;

public class DetachmentAbility {

    String name;
    String legend;
    String description;

    public DetachmentAbility(String name, String legend, String description) {
        this.name = name;
        this.legend = legend;
        this.description = description;
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
}
