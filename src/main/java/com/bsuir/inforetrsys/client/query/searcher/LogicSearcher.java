package com.bsuir.inforetrsys.client.query.searcher;

import com.bsuir.inforetrsys.client.api.QuerySearcher;
import com.bsuir.inforetrsys.client.entity.SearchResult;
import com.bsuir.inforetrsys.general.api.KeywordService;
import com.bsuir.inforetrsys.general.api.StopwordService;
import com.bsuir.inforetrsys.general.entity.Keyword;
import com.bsuir.inforetrsys.server.api.WordsParser;
import com.epam.cafe.service.ServiceException;

import java.util.*;
import java.util.stream.Collectors;

public class LogicSearcher implements QuerySearcher {
    private WordsParser wordsParser;
    private KeywordService keywordService;
    private StopwordService stopwordService;

    public LogicSearcher(WordsParser wordsParser, KeywordService keywordService, StopwordService stopwordService) {
        this.wordsParser = wordsParser;
        this.keywordService = keywordService;
        this.stopwordService = stopwordService;
    }

    @Override
    public List<SearchResult> search(String query, double minRank) throws QuerySearchingProblemsException {
        Map<Integer, List<String>> currResult = new HashMap<>();
        int queryKeywordsNumber;

        try {
            List<String> queryKeywordValues = wordsParser.parse(query);
            queryKeywordsNumber = queryKeywordValues.size();
            for (String queryKeywordValue : queryKeywordValues) {
                if (!stopwordService.containsIgnoreCase(queryKeywordValue)) {
                    List<Keyword> keywords = keywordService.getKeywordRelationsWithValue(queryKeywordValue);
                    for (Keyword keyword : keywords) {
                        int currDocumentId = keyword.getDocumentId();
                        List<String> currDocumentFoundKeywords
                                = currResult.putIfAbsent(currDocumentId, Collections.singletonList(keyword.getValue()));
                        if (currDocumentFoundKeywords != null) {
                            currDocumentFoundKeywords.add(keyword.getValue());
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            throw new QuerySearchingProblemsException(e);
        }

        return getSearchResultsWithRankMoreThan(formSearchResults(currResult, queryKeywordsNumber), minRank);
    }

    private List<SearchResult> formSearchResults(Map<Integer, List<String>> currResult, int queryKeywordsNumber) {
        List<SearchResult> searchResults = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : currResult.entrySet()) {
            List<String> foundKeywords = entry.getValue();
            SearchResult searchResult = new SearchResult(
                    entry.getKey(), foundKeywords, computeRank(foundKeywords.size(), queryKeywordsNumber)
            );
            searchResults.add(searchResult);
        }
        return searchResults;
    }

    private double computeRank(int foundKeywordsNumber, int queryKeywordsNumber) {
        return (double) foundKeywordsNumber / (double) queryKeywordsNumber;
    }

    private List<SearchResult> getSearchResultsWithRankMoreThan(List<SearchResult> searchResults, double minRank) {
        return searchResults.stream()
                .filter(searchResult -> searchResult.getRank() >= minRank)
                .collect(Collectors.toList());
    }
}
