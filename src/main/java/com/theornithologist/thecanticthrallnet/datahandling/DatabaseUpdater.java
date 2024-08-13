package com.theornithologist.thecanticthrallnet.datahandling;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUpdater {

    private static final String URL = "jdbc:sqlite:src/main/resources/com/theornithologist/thecanticthrallnet/data/Munitorum.db";

    public void generateDB() {
        try (var conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                var meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
