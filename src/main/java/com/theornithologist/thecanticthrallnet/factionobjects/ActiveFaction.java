package com.theornithologist.thecanticthrallnet.factionobjects;

import com.theornithologist.thecanticthrallnet.datahandling.FactionIDConstants;
import com.theornithologist.thecanticthrallnet.datahandling.FactionQuery;
import com.theornithologist.thecanticthrallnet.factionobjects.datasheets.Datasheet;
import com.theornithologist.thecanticthrallnet.factionobjects.detachments.Detachment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ActiveFaction {

    String factionName;
    String armyRuleName;
    String armyRule;
    List<Detachment> detachments;
    FactionQuery factionQuery = new FactionQuery();
    List<FactionIDConstants> factionIDConstants = Arrays.asList(FactionIDConstants.values());
    List<Datasheet> datasheets;

    private static ActiveFaction instance;

    private ActiveFaction() {
    }

    public static ActiveFaction getInstance() {
        if (instance == null) {
            synchronized (ActiveFaction.class) {
                if (instance == null) {
                    instance = new ActiveFaction();
                }
            }
        }
        return instance;
    }

    public void updateData() throws SQLException {
        setArmyRuleName();
        setArmyRule();
        setDetachments();
        detachments.sort(Comparator.comparing(Detachment::getDetachmentName));
        setDatasheets();
        datasheets.sort(Comparator.comparing(Datasheet::getName));
    }


    public void setArmyRuleName() {
        String factionID = "";
        for(FactionIDConstants faction : factionIDConstants) {
            if(factionName.equals(faction.value)) {
                factionID = faction.toString();
            }
        }
        this.armyRuleName = factionQuery.getArmyRuleName(factionID);
    }

    public void setArmyRule() {
        String factionID = "";
        for(FactionIDConstants faction : factionIDConstants) {
            if(factionName.equals(faction.value)) {
                factionID = faction.toString();
            }
        }
        this.armyRule = factionQuery.getArmyRule(factionID);
    }

    public void setDetachments() throws SQLException {
        String factionValue = FactionIDConstants.fromValue(factionName).toString();
        System.out.println(factionValue);
        ResultSet rs = factionQuery.getDetachment(factionValue);
        List<Detachment> detachmentList = new ArrayList<>();
        while(rs.next()) {
            detachmentList.add(new Detachment(rs.getString("detachment")));
        }
        detachments = detachmentList;
    }

    public void setDatasheets() throws SQLException {
        String factionValue = FactionIDConstants.fromValue(factionName).toString();
        ResultSet rs = factionQuery.getDatasheet(factionValue);
        List<Datasheet> datasheetList = new ArrayList<>();
        while(rs.next()) {
            datasheetList.add(new Datasheet(rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("legend"),
                    rs.getString("role"),
                    rs.getString("loadout"),
                    rs.getString("transport"),
                    rs.getString("virtual"),
                    rs.getString("leader_footer"),
                    rs.getString("damaged_w"),
                    rs.getString("damaged_description")));
        }
        datasheets = datasheetList;
    }

    public List<Datasheet> getDatasheets() {
        return datasheets;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public String getArmyRule() {
        return armyRule;
    }

    public String getArmyRuleName() {
        return armyRuleName;
    }

    public List<Detachment> getDetachments() {
        return detachments;
    }
}
