package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

public class DatasheetOptions {

    String line;
    String button;
    String description;

    public DatasheetOptions(String line, String button, String description) {
        this.line = line;
        this.button = button;
        this.description = description;
    }

    public String getLine() {
        return line;
    }

    public String getButton() {
        return button;
    }

    public String getDescription() {
        return description;
    }
}
