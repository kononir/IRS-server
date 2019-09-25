package com.bsuir.inforetrsys.logic.indexer;

import com.bsuir.inforetrsys.api.service.DocumentService;
import com.bsuir.inforetrsys.api.service.KeywordService;
import com.bsuir.inforetrsys.api.service.StopwordService;
import com.bsuir.inforetrsys.entity.Keyword;
import com.bsuir.inforetrsys.entity.TextDocument;
import com.bsuir.inforetrsys.api.logic.Indexer;
import com.bsuir.inforetrsys.api.data.WordsParser;
import com.epam.cafe.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class TextDocumentIndexer implements Indexer {
    private static final int THIS_DOCUMENT = 1;

    private WordsParser wordsParser;
    private DocumentService documentService;
    private KeywordService keywordService;
    private StopwordService stopwordService;

    private static final Logger LOGGER = LogManager.getLogger(TextDocumentIndexer.class);

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
            LOGGER.info("Index document '" + document.getTitle() + "'");

            List<String> allWordsValues = wordsParser.parse(document.getText());
            LOGGER.info("Document parsed");

            int documentsNumber = documentService.getDocumentsNumber() + THIS_DOCUMENT;
            LOGGER.info("Search document keywords");
            List<String> keywordValues = getKeywordValues(allWordsValues, minWeight, maxWeight, documentsNumber);

            documentService.saveDocument(document);
            LOGGER.info("Document added");

            int documentId = documentService.getLastDocumentId();
            for (String keywordValue : keywordValues) {
                keywordService.addKeyword(new Keyword(keywordValue, documentId));
                LOGGER.info("Added keyword with value '" + keywordValue + "'");
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
                int documentsWithWordNumber
                        = keywordService.getNumberOfDocumentsWithWord(lowerCaseWordValue) + THIS_DOCUMENT;
                double wordInverseFrequency = calculateWordInverseFrequency(documentsNumber, documentsWithWordNumber);
                int wordFrequencyInDocument = getWordFrequencyInDocument(lowerCaseWordValue, allWordsValues);
                double wordWeight = calculateWordWeightInDocument(wordFrequencyInDocument, wordInverseFrequency);

                wordsWithWeights.put(lowerCaseWordValue, wordWeight);
            }
        }

        Map<String, Double> wordsWithTDIDFWeights = getWordsWithTDIDFWeights(wordsWithWeights);

        LOGGER.info("Filter words by weights: min weight - " + minWeight + ", max weight - " + maxWeight);
        return filterWordsByWeights(wordsWithTDIDFWeights, minWeight, maxWeight);
    }

    private Map<String, Double> getWordsWithTDIDFWeights(Map<String, Double> wordsWithWeights) {
        double tdDenominator = calculateTDDenominator(wordsWithWeights.values());

        Map<String, Double> wordsWithTDIDFWeights = new HashMap<>();
        for (Map.Entry<String, Double> entry : wordsWithWeights.entrySet()) {
            String wordValue = entry.getKey();
            Double wordWeight = entry.getValue() / tdDenominator;
            wordsWithTDIDFWeights.put(wordValue, wordWeight);

            LOGGER.info("Found word '" + wordValue + "' with weight - " + wordWeight);
        }
        return wordsWithTDIDFWeights;
    }

    private double calculateTDDenominator(Collection<Double> weights) {
        Optional<Double> optionalSum = weights.stream()
                .reduce((sum, weight) -> sum + (weight * weight));
        return Math.sqrt(optionalSum.get());
    }

    private double calculateWordInverseFrequency(int documentsNumber, int documentsWithWordNumber) {
        /* When we have one document or two documents with same words inverse_frequency = 0 for all words */
        return documentsNumber > 2 ? Math.log((double) documentsNumber / (double) documentsWithWordNumber) : 0.5;
    }

    private int getWordFrequencyInDocument(String requiredWordValue, List<String> allWordsValues) {
        return (int) allWordsValues.stream()
                .filter(requiredWordValue::equalsIgnoreCase)
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
