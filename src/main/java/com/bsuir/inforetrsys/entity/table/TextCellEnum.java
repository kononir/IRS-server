package com.bsuir.inforetrsys.entity.table;

public enum TextCellEnum {
    FOUND("Found by system"),
    NOT_FOUND("Not found by system");

    private String text;

    TextCellEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
