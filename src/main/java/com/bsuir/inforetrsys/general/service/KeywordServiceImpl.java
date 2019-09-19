package com.bsuir.inforetrsys.general.service;

import com.bsuir.inforetrsys.general.api.service.KeywordService;
import com.bsuir.inforetrsys.general.model.Keyword;
import com.bsuir.inforetrsys.general.repository.impl.KeywordRepository;
import com.bsuir.inforetrsys.general.repository.specification.KeywordsWithValueSpecification;

import java.util.List;

public class KeywordServiceImpl implements KeywordService {
    private KeywordRepository repository = new KeywordRepository();

    @Override
    public int getNumberOfDocumentsWithWord(String wordValue) {
        List<Keyword> sameKeywords = repository.query(new KeywordsWithValueSpecification());
        return sameKeywords.size();
    }

    @Override
    public void addKeyword(Keyword keyword) {
        repository.save(keyword);
    }
}
