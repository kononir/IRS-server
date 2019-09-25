package com.bsuir.inforetrsys.service;

import com.bsuir.inforetrsys.api.service.StopwordService;
import com.bsuir.inforetrsys.data.reader.JSONReader;

import java.util.HashSet;
import java.util.Set;

public class StopwordServiceImpl implements StopwordService {
    private static final String STOPWORDS_PATH = "stopwords.json";
    private Set<String> stopwords = load();

    @Override
    public boolean containsIgnoreCase(String wordValue) {
        return stopwords.stream()
                .anyMatch(s -> s.equalsIgnoreCase(wordValue));
    }

    private Set<String> load() {
        JSONReader reader = new JSONReader();
        return new HashSet<>(reader.read(STOPWORDS_PATH));
    }
}
