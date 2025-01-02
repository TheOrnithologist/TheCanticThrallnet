package com.theornithologist.thecanticthrallnet.factionobjects.detachments;

import com.theornithologist.thecanticthrallnet.datahandling.FactionQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Detachment {

    List<Stratagem> stratagems;
    List<Enhancement> enhancements;
    List<DetachmentAbility> abilities;
    FactionQuery factionQuery = new FactionQuery();
    String detachmentName;

    public Detachment(String detachmentName) throws SQLException {
        this.detachmentName = detachmentName;
        setStratagems();
        stratagems.sort(Comparator.comparing(Stratagem::getName));
        setEnhancements();
        enhancements.sort(Comparator.comparing(Enhancement::getName));
        setAbilities();
    }

    public List<Stratagem> getStratagems() {
        return stratagems;
    }

    public List<Enhancement> getEnhancements() {
        return enhancements;
    }

    public void setEnhancements() throws SQLException {
        ResultSet rs = factionQuery.getEnhancements(detachmentName);
        List<Enhancement> enhancementList = new ArrayList<>();
        while(rs.next()) {
            enhancementList.add(new Enhancement(rs.getString("name"),
                    rs.getString("legend"),
                    rs.getString("description"),
                    rs.getString("cost")));
        }
        enhancements = enhancementList;
    }

    public void setStratagems() throws SQLException {
        ResultSet rs = factionQuery.getStratagems(detachmentName);
        List<Stratagem> stratagemList = new ArrayList<>();
        while (rs.next()) {
            stratagemList.add(new Stratagem(rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("cp_cost"),
                    rs.getString("legend"),
                    rs.getString("turn"),
                    rs.getString("phase"),
                    rs.getString("description")));
        }
        stratagems = stratagemList;
    }

    public void setAbilities() throws SQLException {
        ResultSet rs = factionQuery.getDetachmentAbilities(detachmentName);
        List<DetachmentAbility> detachmentAbilityList = new ArrayList<>();
        while(rs.next()) {
            detachmentAbilityList.add(new DetachmentAbility(rs.getString("name"),
                    rs.getString("legend"),
                    rs.getString("description")));
        }
        abilities = detachmentAbilityList;
    }

    public String getDetachmentName() {
        return detachmentName;
    }

    public List<DetachmentAbility> getAbilities() {
        return abilities;
    }

    @Override
    public String toString() {
        return detachmentName;
    }
}
