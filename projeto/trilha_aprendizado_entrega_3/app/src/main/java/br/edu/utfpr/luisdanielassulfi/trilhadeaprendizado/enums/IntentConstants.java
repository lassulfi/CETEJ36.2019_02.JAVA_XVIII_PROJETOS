package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums;

public enum IntentConstants {
    MODE("MODE");

    private final String value;

    private IntentConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
