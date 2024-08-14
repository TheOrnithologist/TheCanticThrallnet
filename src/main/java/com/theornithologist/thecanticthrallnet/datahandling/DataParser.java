package com.theornithologist.thecanticthrallnet.datahandling;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataParser {

    public void downloadData(String url, String filename) throws IOException {
        BufferedInputStream input = new BufferedInputStream(new URL(url).openStream());
        Files.copy(input, Path.of(FileConstants.DATA_ROOT.value + filename), StandardCopyOption.REPLACE_EXISTING);
    }

    public List<String> parseData(FileConstants file) throws IOException {
        String datasheetsRaw = Files.readString(Paths.get(FileConstants.DATA_ROOT.value + file.value));
        String[] columns = datasheetsRaw.split("\\r\\n");
        String[] firstRow = columns[1].split("\\|");
        int columnCount = firstRow.length;
        datasheetsRaw = datasheetsRaw.replaceAll("\\r\\n", "");
        String[] datasheets = datasheetsRaw.split("\\|");
        List<String> datasheetsParsed = new ArrayList<>(Arrays.asList(datasheets));
        for (int i = columnCount; i > 0; i--) {
            datasheetsParsed.remove(i);
        }
        return datasheetsParsed;
    }

    public String parseLastUpdated() throws IOException {
        String lastUpdateRaw = Files.readString(Paths.get(FileConstants.DATA_ROOT.value + FileConstants.UPDATE_FILE.value));
        lastUpdateRaw = lastUpdateRaw.replaceAll("\r\n","");
        lastUpdateRaw = lastUpdateRaw.replaceAll("\\s","T");
        String[] lastUpdate = lastUpdateRaw.split("\\|");
        return lastUpdate[1];
    }

    public String[] getFileColumn(FileConstants file) throws IOException {
        String datasheetsRaw = Files.readString(Paths.get(FileConstants.DATA_ROOT.value + file.value));
        String[] columns = datasheetsRaw.split("\\r\\n");
        return columns[0].split("\\|");
    }

}
