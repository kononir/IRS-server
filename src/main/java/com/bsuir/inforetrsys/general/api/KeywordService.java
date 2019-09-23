package com.bsuir.inforetrsys.general.api;

import com.bsuir.inforetrsys.general.entity.Keyword;
import com.epam.cafe.service.ServiceException;

import java.util.List;

public interface KeywordService {
    int getNumberOfDocumentsWithWord(String wordValue) throws ServiceException;

    List<Keyword> getKeywordRelationsWithValue(String wordValue) throws ServiceException;

    void addKeyword(Keyword keyword) throws ServiceException;
}
