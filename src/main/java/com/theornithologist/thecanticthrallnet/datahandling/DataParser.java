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

    private static final String FACTIONS_URL = "https://wahapedia.ru/wh40k10ed/Factions.csv";
    private static final String DATASHEETS_URL = "https://wahapedia.ru/wh40k10ed/Datasheets.csv";
    private static final String DATASHEET_ABILITIES_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_abilities.csv";
    private static final String DATASHEET_KEYWORDS_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_keywords.csv";
    private static final String DATASHEET_MODELS_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_models.csv";
    private static final String DATASHEET_OPTIONS_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_options.csv";
    private static final String DATASHEET_WARGEAR_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_wargear.csv";
    private static final String DATASHEET_UNIT_COMPOSITION_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_unit_composition.csv";
    private static final String DATASHEET_MODELCOST_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_model_cost.csv";
    private static final String DATASHEET_STRATAGEMS_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_stratagems.csv";
    private static final String DATASHEET_ENHANCEMENTS_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_enhancements.csv";
    private static final String DATASHEET_DETACHMENT_ABILITIES_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_detachement_abilities.csv";
    private static final String DATASHEET_LEADER_URL = "https://wahapedia.ru/wh40k10ed/Datasheets_leader.csv";
    private static final String STATAGEMS_URL = "https://wahapedia.ru/wh40k10ed/Stratagems.csv";
    private static final String ABILITIES_URL = "https://wahapedia.ru/wh40k10ed/Abilities.csv";
    private static final String ENHANCEMENTS_URL = "https://wahapedia.ru/wh40k10ed/Enhancements.csv";
    private static final String DETACHMENT_ABILITIES_URL = "https://wahapedia.ru/wh40k10ed/Detachment_abilities.csv";
    private static final String UPDATE_URL = "https://wahapedia.ru/wh40k10ed/Last_update.csv";

    private static final String DATA_ROOT = "src/main/resources/com/theornithologist/thecanticthrallnet/data/";

    private static final String FACTIONS_FILE = "Factions.csv";
    private static final String DATASHEETS_FILE = "Datasheets.csv";
    private static final String DATASHEET_ABILITIES_FILE = "Datasheets_abilities.csv";
    private static final String DATASHEET_KEYWORDS_FILE = "Datasheets_keywords.csv";
    private static final String DATASHEET_MODELS_FILE = "Datasheets_models.csv";
    private static final String DATASHEET_OPTIONS_FILE = "Datasheets_options.csv";
    private static final String DATASHEET_WARGEAR_FILE = "Datasheets_wargear.csv";
    private static final String DATASHEET_UNIT_COMPOSITION_FILE = "Datasheets_unit_composition.csv";
    private static final String DATASHEET_MODELCOST_FILE = "Datasheets_model_cost.csv";
    private static final String DATASHEET_STRATAGEMS_FILE = "Datasheets_stratagems.csv";
    private static final String DATASHEET_ENHANCEMENTS_FILE = "Datasheets_enhancements.csv";
    private static final String DATASHEET_DETACHMENT_ABILITIES_FILE = "Datasheets_detachement_abilities.csv";
    private static final String DATASHEET_LEADER_FILE = "Datasheets_leader.csv";
    private static final String STATAGEMS_FILE = "Stratagems.csv";
    private static final String ABILITIES_FILE = "Abilities.csv";
    private static final String ENHANCEMENTS_FILE = "Enhancements.csv";
    private static final String DETACHMENT_ABILITIES_FILE = "Detachment_abilities.csv";
    private static final String UPDATE_FILE = "Last_update.csv";

    public void downloadData(String url, String filename) throws IOException {
        BufferedInputStream input = new BufferedInputStream(new URL(url).openStream());
        Files.copy(input, Path.of(DATA_ROOT + filename), StandardCopyOption.REPLACE_EXISTING);
    }

    public List<String> parseFactionData() throws IOException {
        String factionsRaw = Files.readString(Paths.get(DATA_ROOT + FACTIONS_FILE));
        factionsRaw = factionsRaw.replaceAll("\\r\\n", "");
        String[] factions = factionsRaw.split("\\|");
        List<String> factionsParsed = new ArrayList<>();
        factionsParsed.addAll(Arrays.asList(factions));
        factionsParsed.remove(2);
        factionsParsed.remove(1);
        factionsParsed.remove(0);
        List<String> toRemove = new ArrayList<>();
        for (String string : factionsParsed) {
            if (string.contains("https://")) {
                toRemove.add(string);
            }
        }
        factionsParsed.removeAll(toRemove);
        return factionsParsed;
    }

    public String parseLastUpdated() throws IOException {
        String lastUpdateRaw = Files.readString(Paths.get(DATA_ROOT + UPDATE_FILE));
        lastUpdateRaw = lastUpdateRaw.replaceAll("\r\n","");
        lastUpdateRaw = lastUpdateRaw.replaceAll("\\s","T");
        String[] lastUpdate = lastUpdateRaw.split("\\|");
        return lastUpdate[1];
    }
}
