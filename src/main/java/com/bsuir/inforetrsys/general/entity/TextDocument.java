package com.bsuir.inforetrsys.general.entity;

import java.time.LocalDateTime;

public class TextDocument {
    public static final String ID_COLUMN = "ID";
    public static final String TITLE_COLUMN = "Title";
    public static final String ADDING_TIME_COLUMN = "AddingTime";
    public static final String FILEPATH_COLUMN = "FilePath";

    private int id;
    private String title;
    private String text;
    private LocalDateTime addingTime;
    private String filePath;

    public TextDocument(int id, String title, LocalDateTime addingTime, String filePath) {
        this.id = id;
        this.title = title;
        this.addingTime = addingTime;
        this.filePath = filePath;
    }

    public TextDocument(String title, String text, LocalDateTime addingTime, String filePath) {
        this.title = title;
        this.text = text;
        this.addingTime = addingTime;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getAddingTime() {
        return addingTime;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@id=" + id + " title=" + title + " dateTime=" + addingTime
                + " filePath=" + filePath;
    }
}
