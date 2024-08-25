package com.theornithologist.thecanticthrallnet.factionobjects;

import com.theornithologist.thecanticthrallnet.datahandling.FactionQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Detachment {

    String name;
    String legend;
    String description;
    List<Stratagem> stratagems;
    List<Enhancement> enhancements;
    FactionQuery factionQuery = new FactionQuery();
    String detachmentName;

    public Detachment(String name, String legend, String description, String detachmentName) throws SQLException {
        this.name = name;
        this.legend = legend;
        this.description = description;
        this.detachmentName = detachmentName;
        setStratagems();
        setEnhancements();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Stratagem> getStratagems() {
        return stratagems;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public List<Enhancement> getEnhancements() {
        return enhancements;
    }

    public void setEnhancements() throws SQLException {
        ResultSet rs = factionQuery.getEnhancements(name);
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
        ResultSet rs = factionQuery.getStratagems(name);
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

    @Override
    public String toString() {
        return detachmentName;
    }
}
