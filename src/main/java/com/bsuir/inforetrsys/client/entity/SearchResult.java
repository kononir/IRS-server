package com.bsuir.inforetrsys.client.entity;

import java.util.List;

public class SearchResult {
    private int documentId;
    private List<String> foundKeywords;
    private double rank;

    public SearchResult(int documentId, List<String> foundKeywords, double rank) {
        this.documentId = documentId;
        this.foundKeywords = foundKeywords;
        this.rank = rank;
    }

    public int getDocumentId() {
        return documentId;
    }

    public List<String> getFoundKeywords() {
        return foundKeywords;
    }

    public double getRank() {
        return rank;
    }
}
