package com.bsuir.inforetrsys.logic.searcher;

import com.bsuir.inforetrsys.api.logic.searcher.QuerySearcher;
import com.bsuir.inforetrsys.api.logic.searcher.SnippetSearcher;
import com.bsuir.inforetrsys.api.service.DocumentService;
import com.bsuir.inforetrsys.api.service.KeywordService;
import com.bsuir.inforetrsys.api.service.StopwordService;
import com.bsuir.inforetrsys.entity.Keyword;
import com.bsuir.inforetrsys.entity.SearchResult;
import com.bsuir.inforetrsys.entity.TextDocument;
import com.bsuir.inforetrsys.api.data.DocumentParser;
import com.epam.cafe.service.ServiceException;
import com.epam.info.handling.data.reader.TextReader;
import com.epam.info.handling.data.reader.exception.InvalidPathException;
import com.epam.info.handling.data.reader.exception.ReadingException;

import java.util.*;
import java.util.stream.Collectors;

public class LogicSearcher implements QuerySearcher {
    private DocumentParser documentParser;
    private KeywordService keywordService;
    private StopwordService stopwordService;
    private DocumentService documentService;
    private TextReader documentReader;
    private SnippetSearcher snippetSearcher;

    public LogicSearcher(DocumentParser documentParser, KeywordService keywordService, StopwordService stopwordService,
                         DocumentService documentService, TextReader documentReader, SnippetSearcher snippetSearcher) {
        this.documentParser = documentParser;
        this.keywordService = keywordService;
        this.stopwordService = stopwordService;
        this.documentService = documentService;
        this.documentReader = documentReader;
        this.snippetSearcher = snippetSearcher;
    }

    @Override
    public List<SearchResult> search(String query, double minRank) throws QuerySearchingProblemsException {
        Map<Integer, List<String>> currResult = new HashMap<>();
        int queryKeywordsNumber;

        try {
            List<String> queryKeywordValues = documentParser.parse(query);
            queryKeywordsNumber = queryKeywordValues.size();
            for (String queryKeywordValue : queryKeywordValues) {
                String lowerCaseQueryKeywordValue = queryKeywordValue.toLowerCase();

                if (!stopwordService.containsIgnoreCase(lowerCaseQueryKeywordValue)) {
                    List<Keyword> keywords = keywordService.getKeywordRelationsWithValue(lowerCaseQueryKeywordValue);
                    for (Keyword keyword : keywords) {
                        int currDocumentId = keyword.getDocumentId();

                        List<String> newKeywordValues = new ArrayList<>();
                        newKeywordValues.add(keyword.getValue());

                        List<String> foundKeywordValues = currResult.putIfAbsent(currDocumentId, newKeywordValues);
                        if (foundKeywordValues != null) {
                            foundKeywordValues.add(keyword.getValue());
                        }
                    }
                }
            }

            List<SearchResult> searchResultsWithSomeRank
                    = getSearchResultsWithRankMoreThan(formSearchResults(currResult, queryKeywordsNumber), minRank);

            searchResultsWithSomeRank.sort(Collections.reverseOrder());

            return searchResultsWithSomeRank;
        } catch (ServiceException | ReadingException | InvalidPathException e) {
            throw new QuerySearchingProblemsException(e);
        }
    }

    private List<SearchResult> formSearchResults(Map<Integer, List<String>> currResult, int queryKeywordsNumber)
            throws ServiceException, ReadingException, InvalidPathException {
        List<SearchResult> searchResults = new ArrayList<>();
        for (Map.Entry<Integer, List<String>> entry : currResult.entrySet()) {
            int documentId = entry.getKey();
            List<String> foundKeywordValues = entry.getValue();

            TextDocument document = documentService.getDocumentById(documentId);
            String filePath = document.getFilePath();
            String documentText = documentReader.read(filePath);
            String documentSnippet = snippetSearcher.search(documentText, foundKeywordValues);

            double documentRank = computeRank(foundKeywordValues.size(), queryKeywordsNumber);

            SearchResult searchResult = new SearchResult(
                    document.getTitle(), documentSnippet, documentRank, filePath, document.getAddingTime()
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
