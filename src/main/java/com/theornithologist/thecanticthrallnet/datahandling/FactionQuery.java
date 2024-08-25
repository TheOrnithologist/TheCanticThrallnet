package com.theornithologist.thecanticthrallnet.datahandling;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FactionQuery {

    private static final String URL = "jdbc:sqlite:src/main/resources/com/theornithologist/thecanticthrallnet/data/munitorum.db";

    public String getArmyRuleName(String faction) {
        String sql = "SELECT name FROM Abilities where faction_id ='" + faction + "'";
        try (var conn = DriverManager.getConnection(URL);
             var stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
            return rs.getString(1);
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public String getArmyRule(String faction) {
        String sql = "SELECT description FROM Abilities where faction_id ='" + faction + "'";
        try (var conn = DriverManager.getConnection(URL);
             var stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            return rs.getString(1);
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public ResultSet getDetachments(String faction) {
        String sql = "SELECT name, legend, description, detachment FROM Detachment_abilities where faction_id ='" + faction + "'";
        try {
            var conn = DriverManager.getConnection(URL);
            var stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public ResultSet getStratagems(String detachment) {
        String sql = "SELECT name, type, cp_cost, legend, turn, phase, description FROM Stratagems where detachment ='" + detachment +"'";
        try {
            var conn = DriverManager.getConnection(URL);
            var stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.getMessage();
        }
        return null;
    }

    public ResultSet getEnhancements(String detachment) {
        String sql = "SELECT name, legend, description, cost FROM Enhancements where detachment ='" + detachment +"'";
        try {
            var conn = DriverManager.getConnection(URL);
            var stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

