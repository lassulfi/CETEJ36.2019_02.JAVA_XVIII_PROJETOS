package br.utfpr.activity2.enums;

public enum ResultConstants {
    PEDIR_NOTA(1);

    private final int value;

    private ResultConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
