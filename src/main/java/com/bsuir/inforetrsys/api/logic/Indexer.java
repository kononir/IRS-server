package com.bsuir.inforetrsys.api.logic;

import com.bsuir.inforetrsys.entity.TextDocument;
import com.bsuir.inforetrsys.logic.indexer.IndexingProblemsException;

public interface Indexer {
    void index(TextDocument document, double minWeight, double maxWeight) throws IndexingProblemsException;
}
