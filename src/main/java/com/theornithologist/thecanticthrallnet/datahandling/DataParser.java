package com.theornithologist.thecanticthrallnet.datahandling;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class DataParser {

    public void downloadData(String url, String filename) throws IOException {
        BufferedInputStream input = new BufferedInputStream(new URL(url).openStream());
        Files.copy(input, Path.of(FileConstants.DATA_ROOT.value + filename), StandardCopyOption.REPLACE_EXISTING);
    }

    public void trimCSV() {
        List<FileConstants> files = Arrays.asList(FileConstants.values());
        for (FileConstants file : files) {
            if (file != FileConstants.DATA_ROOT) {
                File path = new File(FileConstants.DATA_ROOT.value + file.value);
                File tmp = new File(FileConstants.DATA_ROOT.value + "tmp" + file.value);
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
        String lastUpdateRaw = Files.readString(Paths.get(FileConstants.DATA_ROOT.value + FileConstants.UPDATE_FILE.value));
        lastUpdateRaw = lastUpdateRaw.replaceAll("\r\n", "|");
        lastUpdateRaw = lastUpdateRaw.replaceAll("\\s","T");
        String[] lastUpdate = lastUpdateRaw.split("\\|");
        return lastUpdate[1];
    }

    public String[] getFileColumn(FileConstants file) throws IOException {
        String fileRaw = Files.readString(Paths.get(FileConstants.DATA_ROOT.value + file.value));
        String[] columns = fileRaw.split("\\r\\n");
        return columns[0].split("\\|");
    }
}
