package com.theornithologist.thecanticthrallnet.datahandling;

import java.util.Arrays;
import java.util.List;

public class ActiveFaction {

    String factionName;
    String armyRuleName;
    String armyRule;
    FactionQuery factionQuery = new FactionQuery();
    List<FactionIDConstants> factionIDConstants = Arrays.asList(FactionIDConstants.values());

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

    public void updateData() {
        setArmyRuleName();
        setArmyRule();
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
}
