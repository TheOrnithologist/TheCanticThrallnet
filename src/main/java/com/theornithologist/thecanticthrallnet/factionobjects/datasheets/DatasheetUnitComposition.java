package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

public class DatasheetUnitComposition {

    String line;
    String description;

    public DatasheetUnitComposition(String line, String description) {
        this.line = line;
        this.description = description;
    }

    public String getLine() {
        return line;
    }

    public String getDescription() {
        return description;
    }
}
