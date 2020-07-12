package br.utfpr.activity2.enums;

import java.util.HashMap;
import java.util.Map;

public enum IntentConstants {
    NOME("NOME"),
    POSSUI_CARRO("POSSUI_CARRO"),
    TIPO("TIPO"),
    MODO("MODO"),
    NOTA("NOTA");

    private final String label;

    private static final Map<String, IntentConstants> BY_LABEL = new HashMap<>();

    static {
        for(IntentConstants c: values()) {
            BY_LABEL.put(c.label, c);
        }
    }

    private IntentConstants(String label) {
        this.label = label;
    }

    public static IntentConstants valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public String getLabel() {
        return label;
    }
}
