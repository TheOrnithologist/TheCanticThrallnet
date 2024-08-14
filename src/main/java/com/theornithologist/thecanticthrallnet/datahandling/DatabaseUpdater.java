package com.theornithologist.thecanticthrallnet.datahandling;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUpdater {

    DataParser dataParser = new DataParser();

    private static final String URL = "jdbc:sqlite:src/main/resources/com/theornithologist/thecanticthrallnet/data/munitorum.db";

    public DatabaseUpdater() throws SQLException {
    }

    public void generateDB() {
        try (var conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                var meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initializeTable() {
        var table = "CREATE TABLE IF NOT EXISTS Factions (" +
                "   id TEXT," +
                "   name TEXT" +
                ");";
        var date = "CREATE TABLE IF NOT EXISTS LastUpdated (" +
                "   lastUpdate TEXT" +
                ");";
        try (var conn = DriverManager.getConnection(URL)) {
            var stmt = conn.createStatement();
            stmt.execute(table);
            stmt.execute(date);
            System.out.println("table creation success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean updateRequired() throws IOException {
        var sql = "SELECT lastUpdate FROM LastUpdated";
        LocalDateTime currentDate = LocalDateTime.of(2000,1,1,1,1);
        try (var conn = DriverManager.getConnection(URL);
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            currentDate = LocalDateTime.parse(rs.getString(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        LocalDateTime newDate = LocalDateTime.parse(dataParser.parseLastUpdated());
        if (newDate.isAfter(currentDate)) {
            return true;
        } else {
            return false;
        }
    }

    public void populateFactions() throws IOException {
        DataParser dataParser = new DataParser();
        List<String> unsortedFactions = dataParser.parseFactionData();
        List<String> id = new ArrayList<>();
        List<String> name = new ArrayList<>();
        for (String string : unsortedFactions) {
            if (unsortedFactions.indexOf(string) % 2 == 0) {
                id.add(string);
            } else {
                name.add(string);
            }
        }

        String[] idArray = id.toArray(new String[0]);
        String[] nameArray = name.toArray(new String[0]);

        String sql = "INSERT INTO Factions(id,name) VALUES(?,?)";

        try (var conn = DriverManager.getConnection(URL);
             var pstmt = conn.prepareStatement(sql)) {
            for(int i = 0; i < 24; i++){
                pstmt.setString(1, idArray[i]);
                pstmt.setString(2, nameArray[i]);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void populateUpdateTime() {
        String sql = "INSERT INTO LastUpdated(lastUpdate) VALUES(?)";
        try (var conn = DriverManager.getConnection(URL);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dataParser.parseLastUpdated());
            pstmt.executeUpdate();
            System.out.println("update time populated");
            System.out.println(dataParser.parseLastUpdated());
        } catch (SQLException | IOException e) {
            e.getMessage();
        }
    }
}
