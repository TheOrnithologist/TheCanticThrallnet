package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

public class DatasheetLeader {

    String attachedID;
    String name;

    public DatasheetLeader(String attachedID, String name) {
        this.attachedID = attachedID;
        this.name = name;
    }

    public String getAttachedID() {
        return attachedID;
    }

    public String getName() {
        return name;
    }
}
