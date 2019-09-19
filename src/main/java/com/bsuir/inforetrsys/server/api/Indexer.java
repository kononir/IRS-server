package com.bsuir.inforetrsys.server.api;

import com.bsuir.inforetrsys.general.model.TextDocument;

public interface Indexer {
    void index(TextDocument document, int keywordsNumber);
}
