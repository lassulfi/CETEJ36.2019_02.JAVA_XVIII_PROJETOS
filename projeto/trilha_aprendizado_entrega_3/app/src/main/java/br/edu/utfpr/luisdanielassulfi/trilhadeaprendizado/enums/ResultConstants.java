package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums;

public enum ResultConstants {
    ADD_TECHNOLOGY(1),
    SUCCESS(1000),
    CANCELED(2000);

    private final int value;

    private ResultConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
