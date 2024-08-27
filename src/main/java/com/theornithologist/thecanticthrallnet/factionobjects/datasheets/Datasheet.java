package com.theornithologist.thecanticthrallnet.factionobjects.datasheets;

import com.theornithologist.thecanticthrallnet.datahandling.FactionQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Datasheet {

    String id;
    String name;
    String factionID;
    String legend;
    String role;
    String loadout;
    String transport;
    String virtual;
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
    FactionQuery factionQuery = new FactionQuery();

    public Datasheet(String id, String name, String factionID, String legend, String role, String loadout, String transport, String virtual, String leaderHead, String leaderFooter, String damagedw, String damagedDescription) throws SQLException {
        this.id = id;
        this.name = name;
        this.factionID = factionID;
        this.legend = legend;
        this.role = role;
        this.loadout = loadout;
        this.transport = transport;
        this.virtual = virtual;
        this.leaderHead = leaderHead;
        this.leaderFooter = leaderFooter;
        this.damagedw = damagedw;
        this.damagedDescription = damagedDescription;
        setDatasheetAbilities();
        setDatasheetKeywords();
        setDatasheetModels();
        setDatasheetModelCost();
        setDatasheetOptions();
        setDatasheetUnitComposition();
        setDatasheetWargear();
    }

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

    public String getVirtual() {
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

    public void setDatasheetAbilities() throws SQLException {
        ResultSet rs = factionQuery.getDatasheetAbilities(id);
        List<DatasheetAbility> abilityList = new ArrayList<>();
        while(rs.next()) {
            abilityList.add(new DatasheetAbility(rs.getString("line"),
                    rs.getString("ability_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("type"),
                    rs.getString("parameter")));
        }
        datasheetAbilities = abilityList;
    }

    public void setDatasheetKeywords() throws SQLException {
        ResultSet rs = factionQuery.getDatasheetKeywords(id);
        List<DatasheetKeyword> keywordList = new ArrayList<>();
        while(rs.next()) {
            keywordList.add(new DatasheetKeyword(rs.getString("keyword"),
                    rs.getString("model"),
                    rs.getString("is_faction_keyword")));
        }
        datasheetKeywords = keywordList;
    }

    public void setDatasheetLeaders() throws SQLException {
        ResultSet rs = factionQuery.getDatasheetLeaders(id);
        List<DatasheetLeader> leaderList = new ArrayList<>();
        while(rs.next()) {
            leaderList.add(new DatasheetLeader(rs.getString("attached_label")));
        }
        datasheetLeaders = leaderList;
    }

    public void setDatasheetModels() throws SQLException {
        ResultSet rs = factionQuery.getDatasheetModels(id);
        List<DatasheetModel> modelList = new ArrayList<>();
        while(rs.next()) {
            modelList.add(new DatasheetModel(rs.getString("line"),
                    rs.getString("name"),
                    rs.getString("M"),
                    rs.getString("T"),
                    rs.getString("Sv"),
                    rs.getString("inv_sv"),
                    rs.getString("inv_sv_descr"),
                    rs.getString("W"),
                    rs.getString("Ld"),
                    rs.getString("OC"),
                    rs.getString("base_size"),
                    rs.getString("bse_size_descr")));
        }
        datasheetModels = modelList;
    }

    public void setDatasheetModelCost() throws SQLException {
        ResultSet rs = factionQuery.getDatasheetModelsCost(id);
        List<DatasheetModelCost> modelCostList = new ArrayList<>();
        while(rs.next()) {
            modelCostList.add(new DatasheetModelCost(rs.getString("line"),
                    rs.getString("description"),
                    rs.getString("cost")));
        }
        datasheetModelCost = modelCostList;
    }

    public void setDatasheetOptions() throws SQLException {
        ResultSet rs = factionQuery.getDatasheetOptions(id);
        List<DatasheetOptions> optionsList = new ArrayList<>();
        while(rs.next()) {
            optionsList.add(new DatasheetOptions(rs.getString("line"),
                    rs.getString("button"),
                    rs.getString("description")));
        }
        datasheetOptions = optionsList;
    }

    public void setDatasheetUnitComposition() throws SQLException {
        ResultSet rs = factionQuery.getDatasheetUnitComposition(id);
        List<DatasheetUnitComposition> unitCompisitionList = new ArrayList<>();
        while(rs.next()) {
            unitCompisitionList.add(new DatasheetUnitComposition(rs.getString("line"),
                    rs.getString("description")));
        }
        datasheetUnitCompositions = unitCompisitionList;
    }

    public void setDatasheetWargear() throws SQLException {
        ResultSet rs = factionQuery.getDatasheetKeywords(id);
        List<DatasheetWargear> wargearList = new ArrayList<>();
        while(rs.next()) {
            wargearList.add(new DatasheetWargear(rs.getString("line"),
                    rs.getString("dice"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("range"),
                    rs.getString("type"),
                    rs.getString("A"),
                    rs.getString("BS_WS"),
                    rs.getString("S"),
                    rs.getString("AP"),
                    rs.getString("D")));
        }
        datasheetWargears = wargearList;
    }
}
