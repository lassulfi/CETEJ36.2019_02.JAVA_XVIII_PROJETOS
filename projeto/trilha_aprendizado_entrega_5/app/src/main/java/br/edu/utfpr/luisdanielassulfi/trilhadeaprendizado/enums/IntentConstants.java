package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums;

public enum IntentConstants {
    MODE("MODE"),
    NEW_TECHNOLOGY("NEW_TECHNOLOGY"),
    ADD_TECHNOLOGY_STATUS("ADD_TECHNOLOGY_STATUS"),
    UPDATE_TECHNOLOGY_STATUS("UPDATE_TECHNOLOGY_STATUS"),
    ADD_TECHNOLOGY_MESSAGE("ADD_TECHNOLOGY_MESSAGE"),
    SELECTED_TECHNOLOGY("SELECTED_TECHNOLOGY"),
    UPDATE_TECHNOLOGY_MESSAGE("UPDATE_TECHNOLOGY_MESSAGE");

    private final String value;

    private IntentConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
