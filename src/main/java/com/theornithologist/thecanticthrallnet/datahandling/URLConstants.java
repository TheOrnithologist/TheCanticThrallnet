package com.theornithologist.thecanticthrallnet.datahandling;

public enum URLConstants {

    FACTIONS_URL("https://wahapedia.ru/wh40k10ed/Factions.csv"),
    DATASHEETS_URL("https://wahapedia.ru/wh40k10ed/Datasheets.csv"),
    DATASHEET_ABILITIES_URL("https://wahapedia.ru/wh40k10ed/Datasheets_abilities.csv"),
    DATASHEET_KEYWORDS_URL("https://wahapedia.ru/wh40k10ed/Datasheets_keywords.csv"),
    DATASHEET_MODELS_URL("https://wahapedia.ru/wh40k10ed/Datasheets_models.csv"),
    DATASHEET_OPTIONS_URL("https://wahapedia.ru/wh40k10ed/Datasheets_options.csv"),
    DATASHEET_WARGEAR_URL("https://wahapedia.ru/wh40k10ed/Datasheets_wargear.csv"),
    DATASHEET_UNIT_COMPOSITION_URL("https://wahapedia.ru/wh40k10ed/Datasheets_unit_composition.csv"),
    DATASHEET_MODELCOST_URL("https://wahapedia.ru/wh40k10ed/Datasheets_models_cost.csv"),
    DATASHEET_STRATAGEMS_URL("https://wahapedia.ru/wh40k10ed/Datasheets_stratagems.csv"),
    DATASHEET_ENHANCEMENTS_URL("https://wahapedia.ru/wh40k10ed/Datasheets_enhancements.csv"),
    DATASHEET_DETACHMENT_ABILITIES_URL("https://wahapedia.ru/wh40k10ed/Datasheets_detachment_abilities.csv"),
    DATASHEET_LEADER_URL("https://wahapedia.ru/wh40k10ed/Datasheets_leader.csv"),
    STATAGEMS_URL("https://wahapedia.ru/wh40k10ed/Stratagems.csv"),
    ABILITIES_URL("https://wahapedia.ru/wh40k10ed/Abilities.csv"),
    ENHANCEMENTS_URL("https://wahapedia.ru/wh40k10ed/Enhancements.csv"),
    DETACHMENT_ABILITIES_URL("https://wahapedia.ru/wh40k10ed/Detachment_abilities.csv"),
    UPDATE_URL("https://wahapedia.ru/wh40k10ed/Last_update.csv");

    public final String value;

    URLConstants(String value) {
        this.value = value;
    }
}
