package com.theornithologist.thecanticthrallnet.datahandling;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.theornithologist.thecanticthrallnet.datahandling.FileConstants.DATA_ROOT;

public class DatabaseUpdater {

    DataParser dataParser = new DataParser();
    private static final CSVFormat FORMAT = CSVFormat.Builder.create().setDelimiter('|').setHeader().setSkipHeaderRecord(true).build();
    private static final String URL = "jdbc:sqlite:" + DATA_ROOT.value + "\\munitorum.db";

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

    public void initializeTable() throws IOException {
        FileConstants[] files = FileConstants.values();
        List<String> tableStrings = new ArrayList<>();
        for (FileConstants file : files) {
            if (file != DATA_ROOT) {
                tableStrings.add(generateTableString(file));
                System.out.println(generateTableString(file));
            }
        }
        for (String table : tableStrings) {
            try (var conn = DriverManager.getConnection(URL)) {
                var stmt = conn.createStatement();
                stmt.execute(table);
                System.out.println("table added");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean updateRequired() throws IOException {
        var sql = "SELECT last_update FROM Last_update";
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

    public void clearTable() {
        String abilities = "DELETE from Abilities";
        String datasheets = "DELETE from Datasheets";
        String datasheetAbilities = "DELETE from Datasheets_abilities";
        String datasheetDetachmentAbilities = "DELETE from Datasheets_detachment_abilities";
        String datasheetEnhancements = "DELETE from Datasheets_enhancements";
        String datasheetKeywords = "DELETE from Datasheets_keywords";
        String datasheetLeader = "DELETE from Datasheets_leader";
        String datasheetModel = "DELETE from Datasheets_models";
        String datasheetModelsCost = "DELETE from Datasheets_models_cost";
        String datasheetOptions = "DELETE from Datasheets_options";
        String datasheetStratagems = "DELETE from Datasheets_stratagems";
        String datasheetUnitComposition = "DELETE from Datasheets_unit_composition";
        String datasheetWargear = "DELETE from Datasheets_wargear";
        String detachmentAbilities = "DELETE from Detachment_abilities";
        String enhancements = "DELETE from Enhancements";
        String factions = "DELETE from Factions";
        String stratagems = "DELETE from Stratagems";
        try {
            var conn = DriverManager.getConnection(URL);
            var stmt = conn.createStatement();
            stmt.addBatch(abilities);
            stmt.addBatch(datasheets);
            stmt.addBatch(datasheetAbilities);
            stmt.addBatch(datasheetDetachmentAbilities);
            stmt.addBatch(datasheetEnhancements);
            stmt.addBatch(datasheetKeywords);
            stmt.addBatch(datasheetLeader);
            stmt.addBatch(datasheetModel);
            stmt.addBatch(datasheetModelsCost);
            stmt.addBatch(datasheetOptions);
            stmt.addBatch(datasheetStratagems);
            stmt.addBatch(datasheetUnitComposition);
            stmt.addBatch(datasheetWargear);
            stmt.addBatch(detachmentAbilities);
            stmt.addBatch(enhancements);
            stmt.addBatch(factions);
            stmt.addBatch(stratagems);
            stmt.executeBatch();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void clearLastUpdate() {

    }

    public void fileUpdate() throws IOException {
        dataParser.downloadData(URLConstants.UPDATE_URL.value, FileConstants.UPDATE_FILE.value);
        List<FileConstants> fileConstants = Arrays.asList(FileConstants.values());
        List<FileConstants> fileConstantsNoRoot = new ArrayList<>();
        for (FileConstants constant : fileConstants) {
            if (!constant.equals(DATA_ROOT)) {
                fileConstantsNoRoot.add(constant);
            }
        }
        List<URLConstants> urlConstants = Arrays.asList(URLConstants.values());
        URLConstants[] urlArray = urlConstants.toArray(new URLConstants[0]);
        FileConstants[] fileArray = fileConstantsNoRoot.toArray(new FileConstants[0]);
        for (int i = 0; i < urlConstants.size() - 1; i++) {
            dataParser.downloadData(urlArray[i].value, fileArray[i].value);
        }
    }

    public void populateData() throws IOException {
        List<FileConstants> files = Arrays.asList(FileConstants.values());
        List<String> tableNames = new ArrayList<>();
        for (FileConstants file : files) {
            String[] fileName = file.value.split("\\.");
            String tableName = fileName[0];
            tableNames.add(tableName);
        }
        for (FileConstants file : files) {
            if (file != DATA_ROOT && file != FileConstants.UPDATE_FILE) {
                String filePath = FileConstants.DATA_ROOT.value + file.value;
                InputStream fileStream = new FileInputStream(filePath);
                BOMInputStream bomInputStream = new BOMInputStream(fileStream);
                InputStreamReader reader = new InputStreamReader(bomInputStream, StandardCharsets.UTF_8);
                Iterable<CSVRecord> records = FORMAT.parse(reader);
                String[] headers = dataParser.getFileColumn(file);
                    try (var conn = DriverManager.getConnection(URL);
                         var pstmt = conn.prepareStatement(generateDataString(file))) {
                        for (CSVRecord record : records) {
                            for (int i = 0; i < headers.length; i++) {
                                pstmt.setString(i+1, record.get(i));
                            }
                            pstmt.execute();
                        }
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
            }
            System.out.println("table updated");
        }

    }

    public void populateUpdateTime() {
        String sql = "INSERT INTO Last_update(last_update) VALUES(?)";
        try (var conn = DriverManager.getConnection(URL);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dataParser.parseLastUpdated());
            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.getMessage();
        }
    }

    public String generateTableString(FileConstants file) throws IOException {
        String[] fileName = file.value.split("\\.");
        String tableName = fileName[0];
        StringBuilder tableString = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + "(");
        String[] columns = dataParser.getFileColumn(file);
        String type;
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].contains("virtual") || columns[i].contains("is_faction_keyword")){
                type = "INTEGER";
            } else {
                type = "TEXT";
            }
            if (i == columns.length - 1) {
                tableString.append("   " + columns[i] + " " + type + ");");
            } else {
                tableString.append("   " + columns[i] + " " + type + ",");
            }
        }
        return tableString.toString();
    }

    public String generateDataString(FileConstants file) throws IOException {
        String[] fileName = file.value.split("\\.");
        String tableName = fileName[0];
        StringBuilder dataString = new StringBuilder("INSERT INTO " + tableName + "(");
        String[] columns = dataParser.getFileColumn(file);
        for(int i = 0; i < columns.length; i++) {
            if (i == columns.length - 1) {
                dataString.append(columns[i]+")");
            } else {
                dataString.append(columns[i] + ",");
            }
        }
        dataString.append(" VALUES (");
        for (int i = 0; i < columns.length; i++) {
            if (i == columns.length - 1) {
                dataString.append("?)");
            } else {
                dataString.append("?,");
            }
        }
        return dataString.toString();
    }
}
