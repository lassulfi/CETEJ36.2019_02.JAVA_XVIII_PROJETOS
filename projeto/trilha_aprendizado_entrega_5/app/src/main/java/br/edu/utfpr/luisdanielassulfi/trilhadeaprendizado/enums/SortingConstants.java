package br.edu.utfpr.luisdanielassulfi.trilhadeaprendizado.enums;

public enum SortingConstants {
    TECHNOLOGY_NAME_ASC(0),
    TECHNOLOGY_NAME_DESC(1),
    TRAIL_ASC(2),
    TRAIL_DESC(3);

    private int value;

    SortingConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
