package com.bsuir.inforetrsys.entity.table;

public class ClassificationMatrixData {
    private TextCellEnum firstColumnText;
    private int relevanceNumber;
    private int noRelevanceNumber;

    public ClassificationMatrixData(TextCellEnum firstColumnText, int relevanceNumber, int NoRelevanceNumber) {
        this.firstColumnText = firstColumnText;
        this.relevanceNumber = relevanceNumber;
        this.noRelevanceNumber = NoRelevanceNumber;
    }

    public TextCellEnum getFirstColumnText() {
        return firstColumnText;
    }

    public int getRelevanceNumber() {
        return relevanceNumber;
    }

    public int getNoRelevanceNumber() {
        return noRelevanceNumber;
    }
}
