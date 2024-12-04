package com.theornithologist.thecanticthrallnet.datahandling;

public enum FileConstants {

//    DATA_ROOT("src/main/resources/com/theornithologist/thecanticthrallnet/data/"),
    FACTIONS_FILE("Factions.csv"), 
    DATASHEETS_FILE("Datasheets.csv"), 
    DATASHEET_ABILITIES_FILE("Datasheets_abilities.csv"),
    DATASHEET_KEYWORDS_FILE("Datasheets_keywords.csv"),
    DATASHEET_MODELS_FILE("Datasheets_models.csv"),
    DATASHEET_OPTIONS_FILE("Datasheets_options.csv"),
    DATASHEET_WARGEAR_FILE("Datasheets_wargear.csv"),
    DATASHEET_UNIT_COMPOSITION_FILE("Datasheets_unit_composition.csv"),
    DATASHEET_MODELCOST_FILE("Datasheets_models_cost.csv"),
    DATASHEET_STRATAGEMS_FILE("Datasheets_stratagems.csv"),
    DATASHEET_ENHANCEMENTS_FILE("Datasheets_enhancements.csv"),
    DATASHEET_DETACHMENT_ABILITIES_FILE("Datasheets_detachment_abilities.csv"),
    DATASHEET_LEADER_FILE("Datasheets_leader.csv"),
    STATAGEMS_FILE("Stratagems.csv"),
    ABILITIES_FILE("Abilities.csv"),
    ENHANCEMENTS_FILE("Enhancements.csv"),
    DETACHMENT_ABILITIES_FILE("Detachment_abilities.csv"),
    UPDATE_FILE("Last_update.csv");

    public final String value;
    public static boolean _isWindows;

    FileConstants(String value) {
        this.value = value;
    }

    public static String DataRoot()
    {
        var os = System.getProperty("os.name");
        if (os == "Windows")
        {
            _isWindows = true;
            return System.getProperty("user.home") + "\\Documents\\CanticThrallnet\\";
        }

        _isWindows = false;
        return System.getProperty("user.home") + "//Documents//CanticThrallnet//";
    }

    public static boolean IsWindows()
    {
        return _isWindows;
    }
}
