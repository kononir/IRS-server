package com.bsuir.inforetrsys.entity;

import java.time.LocalDateTime;

public class SearchResult {
    private String title;
    private String snippet;
    private double rank;
    private String filePath;
    private LocalDateTime addingTime;

    public SearchResult(String title, String snippet, double rank, String filePath, LocalDateTime addingTime) {
        this.title = title;
        this.snippet = snippet;
        this.rank = rank;
        this.filePath = filePath;
        this.addingTime = addingTime;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public double getRank() {
        return rank;
    }

    public String getFilePath() {
        return filePath;
    }

    public LocalDateTime getAddingTime() {
        return addingTime;
    }
}
