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
        return getKeywordRelationsWithValue(wordValue).size();
    }

    @Override
    public List<Keyword> getKeywordRelationsWithValue(String wordValue) throws ServiceException {
        List<Keyword> sameKeywords;
        try {
            sameKeywords = repository.query(new KeywordsWithValueSpecification(wordValue));
        } catch (RepositoryException e) {
            throw new ServiceException("Problems with getting keyword relations with word value '" + wordValue + "'", e);
        }
        return sameKeywords;
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
