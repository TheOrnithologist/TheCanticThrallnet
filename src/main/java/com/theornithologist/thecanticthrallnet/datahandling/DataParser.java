package com.theornithologist.thecanticthrallnet.datahandling;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class DataParser {

    public void downloadData(String url, String filename) throws IOException {
        BufferedInputStream input = new BufferedInputStream(new URL(url).openStream());
        Files.copy(input, Path.of(FileConstants.DataRoot() + filename), StandardCopyOption.REPLACE_EXISTING);
    }

    public void trimCSV() {
        List<FileConstants> files = Arrays.asList(FileConstants.values());
        for (FileConstants file : files) {
            if (file.value != FileConstants.DataRoot()) {
                File path = new File(FileConstants.DataRoot() + file.value);
                File tmp = new File(FileConstants.DataRoot() + "tmp" + file.value);
                try (BufferedReader reader = new BufferedReader(new FileReader(path));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tmp))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String modifiedLine = line.replaceAll("\\|$", "");
                        writer.write(modifiedLine);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (path.delete()) {
                    tmp.renameTo(path);
                }
            }
        }
    }

    public String parseLastUpdated() throws IOException {
        String lastUpdateRaw = Files.readString(Paths.get(FileConstants.DataRoot() + FileConstants.UPDATE_FILE.value));
        if (FileConstants.IsWindows())
        {
            lastUpdateRaw = lastUpdateRaw.replaceAll("\r\n", "|");
        }
        else
        {
            lastUpdateRaw = lastUpdateRaw.replaceAll("\n", "|");
        }
        lastUpdateRaw = lastUpdateRaw.replaceAll("\\s","T");
        String[] lastUpdate = lastUpdateRaw.split("\\|");
        return lastUpdate[1];
    }

    public String[] getFileColumn(FileConstants file) throws IOException {
        CSVParser parser = new CSVParser(new FileReader(FileConstants.DataRoot() + file.value, StandardCharsets.UTF_8), CSVFormat.Builder.create().setDelimiter('|').setHeader().setSkipHeaderRecord(false).build());
        List <String> headers = parser.getHeaderNames();
        var results = headers.toArray(new String[0]);
        parser.close();
        return results; 
    }
}
