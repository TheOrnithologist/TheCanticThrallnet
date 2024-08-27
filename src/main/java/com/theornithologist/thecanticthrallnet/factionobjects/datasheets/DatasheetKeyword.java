package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

public class DatasheetKeyword {

    String keyword;
    String model;
    String isFactionKeyword;

    public DatasheetKeyword(String keyword, String model, String isFactionKeyword) {
        this.keyword = keyword;
        this.model = model;
        this.isFactionKeyword = isFactionKeyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getModel() {
        return model;
    }

    public String getIsFactionKeyword() {
        return isFactionKeyword;
    }
}
