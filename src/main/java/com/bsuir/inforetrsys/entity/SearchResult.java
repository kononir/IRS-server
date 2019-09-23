package com.bsuir.inforetrsys.entity;

import java.time.LocalDateTime;

public class SearchResult {
    private int documentId;
    private String title;
    private String snippet;
    private double rank;
    private LocalDateTime addingTime;

    public SearchResult(int documentId, String title, String snippet, double rank, LocalDateTime addingTime) {
        this.documentId = documentId;
        this.title = title;
        this.snippet = snippet;
        this.rank = rank;
        this.addingTime = addingTime;
    }

    public int getDocumentId() {
        return documentId;
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

    public LocalDateTime getAddingTime() {
        return addingTime;
    }
}
