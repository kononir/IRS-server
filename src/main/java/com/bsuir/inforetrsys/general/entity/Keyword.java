package com.bsuir.inforetrsys.general.entity;

public class Keyword {
    public static final String ID_COLUMN = "ID";
    public static final String VALUE_COLUMN = "Value";
    public static final String DOCUMENT_ID_COLUMN = "DocumentID";

    private int id;
    private String value;
    private int documentId;

    public Keyword(int id, String value, int documentId) {
        this.id = id;
        this.value = value;
        this.documentId = documentId;
    }

    public Keyword(String value, int documentId) {
        this.value = value;
        this.documentId = documentId;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public int getDocumentId() {
        return documentId;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@id=" + id + " value=" + value + " documentId=" + documentId;
    }
}
