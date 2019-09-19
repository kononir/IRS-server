package com.bsuir.inforetrsys.general.api.service;

import com.bsuir.inforetrsys.general.model.Keyword;

public interface KeywordService {
    int getNumberOfDocumentsWithWord(String wordValue);

    void addKeyword(Keyword keyword);
}
