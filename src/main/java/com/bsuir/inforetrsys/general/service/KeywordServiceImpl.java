package com.bsuir.inforetrsys.general.service;

import com.bsuir.inforetrsys.general.api.KeywordService;
import com.bsuir.inforetrsys.general.entity.Keyword;
import com.bsuir.inforetrsys.general.repository.impl.KeywordRepository;
import com.bsuir.inforetrsys.general.repository.specification.keyword.KeywordsWithValueSpecification;
import com.epam.cafe.repository.RepositoryException;
import com.epam.cafe.service.ServiceException;

import java.util.List;

public class KeywordServiceImpl implements KeywordService {
    private KeywordRepository repository = new KeywordRepository();

    @Override
    public int getNumberOfDocumentsWithWord(String wordValue) throws ServiceException {
        int numberOfDocumentsWithWord;
        try {
            List<Keyword> sameKeywords = repository.query(new KeywordsWithValueSpecification(wordValue));
            numberOfDocumentsWithWord = sameKeywords.size();
        } catch (RepositoryException e) {
            throw new ServiceException("Problems with getting number of documents with word '" + wordValue + "'", e);
        }
        return numberOfDocumentsWithWord;
    }

    @Override
    public void addKeyword(Keyword keyword) throws ServiceException {
        try {
            repository.save(keyword);
        } catch (RepositoryException e) {
            throw new ServiceException("Problems with adding keyword: " + keyword, e);
        }
    }
}
