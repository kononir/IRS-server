package com.bsuir.inforetrsys.general.model;

import java.time.LocalDateTime;

public class TextDocument {
    private long id;
    private String title;
    private String text;
    private String date;
    private LocalDateTime dateTime;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }
}
