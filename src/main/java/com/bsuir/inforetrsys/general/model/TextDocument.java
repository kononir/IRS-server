package com.bsuir.inforetrsys.general.model;

import java.time.LocalDateTime;

public class TextDocument {
    private int id;
    private String title;
    private String text;
    private LocalDateTime dateTime;
    private String filePath;

    public TextDocument(int id, String title, String text, LocalDateTime dateTime, String filePath) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.filePath = filePath;
    }

    public TextDocument(String title, String text, LocalDateTime dateTime, String filePath) {
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getFilePath() {
        return filePath;
    }
}
