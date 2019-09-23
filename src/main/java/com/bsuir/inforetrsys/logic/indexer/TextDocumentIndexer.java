package com.bsuir.inforetrsys.logic.indexer;

import com.bsuir.inforetrsys.api.service.DocumentService;
import com.bsuir.inforetrsys.api.service.KeywordService;
import com.bsuir.inforetrsys.api.service.StopwordService;
import com.bsuir.inforetrsys.entity.Keyword;
import com.bsuir.inforetrsys.entity.TextDocument;
import com.bsuir.inforetrsys.api.logic.Indexer;
import com.bsuir.inforetrsys.api.data.WordsParser;
import com.epam.cafe.service.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextDocumentIndexer implements Indexer {
    private WordsParser wordsParser;
    private DocumentService documentService;
    private KeywordService keywordService;
    private StopwordService stopwordService;

    public TextDocumentIndexer(WordsParser wordsParser, DocumentService documentService,
                               KeywordService keywordService, StopwordService stopwordService) {
        this.wordsParser = wordsParser;
        this.documentService = documentService;
        this.keywordService = keywordService;
        this.stopwordService = stopwordService;
    }

    @Override
    public void index(TextDocument document, double minWeight, double maxWeight) throws IndexingProblemsException {
        try {
            List<String> allWordsValues = wordsParser.parse(document.getText());
            int documentsNumber = documentService.getDocumentsNumber();
            List<String> keywordValues = getKeywordValues(allWordsValues, minWeight, maxWeight, documentsNumber);

            documentService.saveDocument(document);
            int documentId = documentService.getLastDocumentId();

            for (String keywordValue : keywordValues) {
                keywordService.addKeyword(new Keyword(keywordValue, documentId));
            }
        } catch (ServiceException e) {
            throw new IndexingProblemsException(e);
        }
    }

    private List<String> getKeywordValues(List<String> allWordsValues, double minWeight,
                                          double maxWeight, int documentsNumber) throws ServiceException {
        Map<String, Double> wordsWithWeights = new HashMap<>();

        for (String wordValue : allWordsValues) {
            String lowerCaseWordValue = wordValue.toLowerCase();

            if (!wordsWithWeights.containsKey(lowerCaseWordValue) && !stopwordService.containsIgnoreCase(wordValue)) {
                int documentsWithWordNumber = keywordService.getNumberOfDocumentsWithWord(lowerCaseWordValue);
                double wordInverseFrequency = calculateWordInverseFrequency(documentsNumber, documentsWithWordNumber);
                int wordFrequencyInDocument = getWordFrequencyInDocument(lowerCaseWordValue, allWordsValues);
                double wordWeight = calculateWordWeightInDocument(wordFrequencyInDocument, wordInverseFrequency);

                wordsWithWeights.put(lowerCaseWordValue, wordWeight);
            }
        }

        return filterWordsByWeights(wordsWithWeights, minWeight, maxWeight);
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

    private List<String> filterWordsByWeights(Map<String, Double> filtered, double minWeight, double maxWeight) {
        return filtered.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > minWeight && entry.getValue() < maxWeight)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
