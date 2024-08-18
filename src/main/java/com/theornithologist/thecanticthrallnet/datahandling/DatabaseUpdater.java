package com.theornithologist.thecanticthrallnet.datahandling;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseUpdater {

    DataParser dataParser = new DataParser();

    private static final CSVFormat FORMAT = CSVFormat.Builder.create().setDelimiter('|').setHeader().setSkipHeaderRecord(true).build();
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

    public void initializeTable() throws IOException {
        List<FileConstants> files = Arrays.asList(FileConstants.values());
        List<String> tableStrings = new ArrayList<>();
        for (FileConstants file : files) {
            if (file != FileConstants.DATA_ROOT) {
                tableStrings.add(generateTableString(file));
            }
        }
        for (String table : tableStrings) {
            try (var conn = DriverManager.getConnection(URL)) {
                var stmt = conn.createStatement();
                stmt.execute(table);
                System.out.println("table creation success");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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

    public boolean fileUpdate() throws IOException {
        dataParser.downloadData(URLConstants.UPDATE_URL.value, FileConstants.UPDATE_FILE.value);
        if (updateRequired()) {
            List<FileConstants> fileConstants = Arrays.asList(FileConstants.values());
            List<FileConstants> fileConstantsNoRoot = new ArrayList<>();
            for (FileConstants constant : fileConstants) {
                if (!constant.equals(FileConstants.DATA_ROOT)) {
                    fileConstantsNoRoot.add(constant);
                }
            }
            List<URLConstants> urlConstants = Arrays.asList(URLConstants.values());
            URLConstants[] urlArray = urlConstants.toArray(new URLConstants[0]);
            FileConstants[] fileArray = fileConstantsNoRoot.toArray(new FileConstants[0]);
            for (int i = 0; i < urlConstants.size() - 1; i++) {
                dataParser.downloadData(urlArray[i].value, fileArray[i].value);
            }
            return true;
        } else {
            return false;
        }
    }

    public void populateData2() throws IOException {
        List<FileConstants> files = Arrays.asList(FileConstants.values());
        List<String> tableNames = new ArrayList<>();
        for (FileConstants file : files) {
            String[] fileName = file.value.split("\\.");
            String tableName = fileName[0];
            tableNames.add(tableName);
        }
        for (FileConstants file : files) {
            if (file != FileConstants.DATA_ROOT) {
                Reader reader = Files.newBufferedReader(Paths.get(FileConstants.DATA_ROOT.value + file.value));
                Iterable<CSVRecord> records = FORMAT.parse(reader);
                String[] headers = dataParser.getFileColumn(file);
                    try (var conn = DriverManager.getConnection(URL);
                         var pstmt = conn.prepareStatement(generateDataString(file))) {
                        for (CSVRecord record : records) {
                            System.out.println(headers.length);
                            for (int i = 0; i < headers.length; i++) {
                                pstmt.setString(i+1, record.get(i));
                            }
                            pstmt.execute();
                        }
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
            }
        }

    }

//    public void populateData() throws IOException {
//        List<FileConstants> files = Arrays.asList(FileConstants.values());
//        for (FileConstants file : files) {
//            if (file != FileConstants.DATA_ROOT) {
//                String[] columns = dataParser.getFileColumn(file);
//                String[] rows = dataParser.parseData(file).toArray(new String[0]);
//                String dataString = generateDataString(file);
//                try (var conn = DriverManager.getConnection(URL);
//                     var pstmt = conn.prepareStatement(dataString)) {
//                    for(int i = 0; i < columns.length; i++){
//                        int iteration = 1;
//                        for (int j = 0; j < rows.length; j++) {
//                            if (j == i || j == i + columns.length * iteration) {
//                                pstmt.setString(i + 1, rows[j]);
//                                pstmt.executeUpdate();
//                                iteration++;
//                            }
//                        }
//
//                    }
//                } catch (SQLException e) {
//                    System.err.println(e.getMessage());
//                }
//            }
//        }
//    }

    public void populateUpdateTime() {
        String sql = "INSERT INTO LastUpdated(lastUpdate) VALUES(?)";
        try (var conn = DriverManager.getConnection(URL);
             var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dataParser.parseLastUpdated());
            pstmt.executeUpdate();
            System.out.println(dataParser.parseLastUpdated());
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
