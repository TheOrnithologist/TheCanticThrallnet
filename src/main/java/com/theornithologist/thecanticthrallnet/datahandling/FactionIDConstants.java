package com.theornithologist.thecanticthrallnet.datahandling;

public enum FactionIDConstants {
    AoI("Agents of the Imperium"),
    AM("Astra Militaurm"),
    GC("Genestealer Cults"),
    NEC("Necrons"),
    AE("Aeldari"),
    TL("Adeptus Titanicus"),
    ORK("Orks"),
    UN("Unaligned Forces"),
    GK("Grey Knights"),
    TAU("T’au Empire"),
    LoV("Leagues of Votann"),
    AdM("Adeptus Mechanicus"),
    TS("Thousand Sons"),
    DG("Death Guard"),
    WE("World Eaters"),
    QT("Chaos Knights"),
    CD("Chaos Daemons"),
    QI("Imperial Knights"),
    SM("Space Marines"),
    TYR("Tyranids"),
    AC("Adeptus Custodes"),
    AS("Adepta Sororitas"),
    CSM("Chaos Space Marines"),
    DRU("Drukhari");

    public final String value;

    FactionIDConstants(String value) {
        this.value = value;
    }

    public static FactionIDConstants fromKey(String key) {
        for (FactionIDConstants faction : values()) {
            if (faction.name().equals(key)) {
                return faction;
            }
        }
        return null;
    }
}
