package com.theornithologist.thecanticthrallnet.datahandling;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class DataPull {

    private static final String FACTIONS_URL = "wahapedia.ru/wh40k10ed/Factions.csv";
    private static final String DATASHEETS_URL = "wahapedia.ru/wh40k10ed/Datasheets.csv";
    private static final String DATASHEET_ABILITIES_URL = "wahapedia.ru/wh40k10ed/Datasheets_abilities.csv";
    private static final String DATASHEET_KEYWORDS_URL = "wahapedia.ru/wh40k10ed/Datasheets_keywords.csv";
    private static final String DATASHEET_MODELS_URL = "wahapedia.ru/wh40k10ed/Datasheets_models.csv";
    private static final String DATASHEET_OPTIONS_URL = "wahapedia.ru/wh40k10ed/Datasheets_options.csv";
    private static final String DATASHEET_WARGEAR_URL = "wahapedia.ru/wh40k10ed/Datasheets_wargear.csv";
    private static final String DATASHEET_UNIT_COMPOSITION_URL = "wahapedia.ru/wh40k10ed/Datasheets_unit_composition.csv";
    private static final String DATASHEET_MODELCOST_URL = "wahapedia.ru/wh40k10ed/Datasheets_model_cost.csv";
    private static final String DATASHEET_STRATAGEMS_URL = "wahapedia.ru/wh40k10ed/Datasheets_stratagems.csv";
    private static final String DATASHEET_ENHANCEMENTS_URL = "wahapedia.ru/wh40k10ed/Datasheets_enhancements.csv";
    private static final String DATASHEET_DETACHMENT_ABILITIES_URL = "wahapedia.ru/wh40k10ed/Datasheets_detachement_abilities.csv";
    private static final String DATASHEET_LEADER_URL = "wahapedia.ru/wh40k10ed/Datasheets_leader.csv";
    private static final String STATAGEMS_URL = "wahapedia.ru/wh40k10ed/Stratagems.csv";
    private static final String ABILITIES_URL = "wahapedia.ru/wh40k10ed/Abilities.csv";
    private static final String ENHANCEMENTS_URL = "wahapedia.ru/wh40k10ed/Enhancements.csv";
    private static final String DETACHMENT_ABILITIES_URL = "wahapedia.ru/wh40k10ed/Detachment_abilities.csv";
    private static final String UPDATE_URL = "wahapedia.ru/wh40k10ed/Last_update.csv";

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
        Files.copy(input, Path.of("src/main/resources/com/theornithologist/thecanticthrallnet/data/" + filename), StandardCopyOption.REPLACE_EXISTING);
    }
}
