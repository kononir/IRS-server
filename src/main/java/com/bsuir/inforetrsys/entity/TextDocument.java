package com.bsuir.inforetrsys.entity;

import java.time.LocalDateTime;
import java.util.List;

public class TextDocument {
    public static final String ID_COLUMN = "ID";
    public static final String TITLE_COLUMN = "Title";
    public static final String ADDING_TIME_COLUMN = "AddingTime";
    public static final String FILEPATH_COLUMN = "FilePath";

    private int id;
    private String title;
    private List<String> wordsValues;
    private LocalDateTime addingTime;
    private String filePath;

    public TextDocument(int id, String title, LocalDateTime addingTime, String filePath) {
        this.id = id;
        this.title = title;
        this.addingTime = addingTime;
        this.filePath = filePath;
    }

    public TextDocument(String title, List<String> wordsValues, LocalDateTime addingTime, String filePath) {
        this.title = title;
        this.wordsValues = wordsValues;
        this.addingTime = addingTime;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getWordsValues() {
        return wordsValues;
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
