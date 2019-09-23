package com.bsuir.inforetrsys.general.service;

import com.bsuir.inforetrsys.general.api.StopwordService;
import com.bsuir.inforetrsys.server.reader.JSONReader;

import java.util.List;

public class StopwordServiceImpl implements StopwordService {
    private static final String STOPWORDS_PATH = "stopwords.json";
    private List<String> stopwords = load();

    @Override
    public boolean containsIgnoreCase(String wordValue) {
        return stopwords.stream()
                .anyMatch(s -> s.equalsIgnoreCase(wordValue));
    }

    private List<String> load() {
        JSONReader reader = new JSONReader();
        return reader.read(STOPWORDS_PATH);
    }
}
