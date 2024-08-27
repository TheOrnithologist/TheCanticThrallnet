package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

import java.util.List;

public class Datasheet {

    String id;
    String name;
    String factionID;
    String legend;
    String role;
    String loadout;
    String transport;
    Boolean virtual;
    String leaderHead;
    String leaderFooter;
    String damagedw;
    String damagedDescription;
    List<DatasheetAbility> datasheetAbilities;
    List<DatasheetKeyword> datasheetKeywords;
    List<DatasheetLeader> datasheetLeaders;
    List<DatasheetModel> datasheetModels;
    List<DatasheetModelCost> datasheetModelCost;
    List<DatasheetOptions> datasheetOptions;
    List<DatasheetUnitComposition> datasheetUnitCompositions;
    List<DatasheetWargear> datasheetWargears;



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFactionID() {
        return factionID;
    }

    public String getLegend() {
        return legend;
    }

    public String getRole() {
        return role;
    }

    public String getLoadout() {
        return loadout;
    }

    public String getTransport() {
        return transport;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public String getLeaderHead() {
        return leaderHead;
    }

    public String getLeaderFooter() {
        return leaderFooter;
    }

    public String getDamagedw() {
        return damagedw;
    }

    public String getDamagedDescription() {
        return damagedDescription;
    }

    public List<DatasheetAbility> getDatasheetAbilities() {
        return datasheetAbilities;
    }

    public List<DatasheetKeyword> getDatasheetKeywords() {
        return datasheetKeywords;
    }

    public List<DatasheetLeader> getDatasheetLeaders() {
        return datasheetLeaders;
    }

    public List<DatasheetModel> getDatasheetModels() {
        return datasheetModels;
    }

    public List<DatasheetModelCost> getDatasheetModelCost() {
        return datasheetModelCost;
    }

    public List<DatasheetOptions> getDatasheetOptions() {
        return datasheetOptions;
    }

    public List<DatasheetUnitComposition> getDatasheetUnitCompositions() {
        return datasheetUnitCompositions;
    }

    public List<DatasheetWargear> getDatasheetWargears() {
        return datasheetWargears;
    }
}
