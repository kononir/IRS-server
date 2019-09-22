package com.bsuir.inforetrsys.server.logic;

import com.bsuir.inforetrsys.general.api.DocumentService;
import com.bsuir.inforetrsys.general.api.KeywordService;
import com.bsuir.inforetrsys.general.entity.Keyword;
import com.bsuir.inforetrsys.general.entity.TextDocument;
import com.bsuir.inforetrsys.server.api.Indexer;
import com.bsuir.inforetrsys.server.api.WordsParser;
import com.bsuir.inforetrsys.server.data.reader.JSONReader;
import com.epam.cafe.service.ServiceException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextDocumentIndexer implements Indexer {
    private static final String STOPWORDS_PATH = "stopwords.json";

    private List<String> stopwords;

    private WordsParser wordsParser;
    private DocumentService documentService;
    private KeywordService keywordService;

    public TextDocumentIndexer(WordsParser wordsParser, DocumentService documentService,
                               KeywordService keywordService) {
        this.wordsParser = wordsParser;
        this.documentService = documentService;
        this.keywordService = keywordService;

        loadStopWords();
    }

    private void loadStopWords() {
        JSONReader reader = new JSONReader();
        stopwords = reader.read(STOPWORDS_PATH);
    }

    @Override
    public void index(TextDocument document, int keywordsNumber) throws IndexingProblemsException {
        try {
            List<String> allWordsValues = wordsParser.parse(document.getText());
            int documentsNumber = documentService.getDocumentsNumber();
            List<String> keywordValues = getKeywordValues(allWordsValues, keywordsNumber, documentsNumber);

            documentService.saveDocument(document);
            int documentId = documentService.getLastDocumentId();

            for (String keywordValue : keywordValues) {
                if (keywordService.getNumberOfDocumentsWithWord(keywordValue) == 0) {
                    keywordService.addKeyword(new Keyword(keywordValue, documentId));
                }
            }
        } catch (ServiceException e) {
            throw new IndexingProblemsException(e);
        }
    }

    private List<String> getKeywordValues(List<String> allWordsValues, int keywordsNumber, int documentsNumber)
            throws ServiceException {
        Map<String, Double> wordsWithWeights = new HashMap<>();

        for (String wordValue : allWordsValues) {
            if (!wordsWithWeights.containsKey(wordValue) && !stopwordsContainsIgnoreCase(wordValue)) {
                int documentsWithWordNumber = keywordService.getNumberOfDocumentsWithWord(wordValue);
                double wordInverseFrequency = calculateWordInverseFrequency(documentsNumber, documentsWithWordNumber);
                int wordFrequencyInDocument = getWordFrequencyInDocument(wordValue, allWordsValues);
                double wordWeight = calculateWordWeightInDocument(wordFrequencyInDocument, wordInverseFrequency);

                wordsWithWeights.put(wordValue, wordWeight);
            }
        }

        List<String> sortedWordValues = getWordsSortedByWeight(wordsWithWeights);

        return sortedWordValues.subList(0, keywordsNumber);
    }

    private boolean stopwordsContainsIgnoreCase(String wordValue) {
        return stopwords.stream()
                .anyMatch(s -> s.equalsIgnoreCase(wordValue));
    }

    private double calculateWordInverseFrequency(int documentsNumber, int documentsWithWordNumber) {
        return documentsWithWordNumber > 0 ? Math.log((double) documentsNumber / (double) documentsWithWordNumber) : 1;
    }

    private int getWordFrequencyInDocument(String requiredWordValue, List<String> allWordsValues) {
        return (int) allWordsValues.stream()
                .filter(requiredWordValue::equals)
                .count();
    }

    private double calculateWordWeightInDocument(int wordFrequencyInDocument, double wordInverseFrequency) {
        return (double) wordFrequencyInDocument * wordInverseFrequency;
    }

    private List<String> getWordsSortedByWeight(Map<String, Double> sorted) {
        return sorted.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
