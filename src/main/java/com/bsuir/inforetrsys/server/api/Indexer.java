package com.bsuir.inforetrsys.server.api;

import com.bsuir.inforetrsys.general.entity.TextDocument;
import com.bsuir.inforetrsys.server.logic.IndexingProblemsException;

public interface Indexer {
    void index(TextDocument document, int keywordsNumber) throws IndexingProblemsException;
}
