package com.bsuir.inforetrsys.server.logic;

import com.bsuir.inforetrsys.general.api.service.DocumentService;
import com.bsuir.inforetrsys.general.api.service.KeywordService;
import com.bsuir.inforetrsys.general.model.Keyword;
import com.bsuir.inforetrsys.general.model.TextDocument;
import com.bsuir.inforetrsys.server.api.Indexer;
import com.bsuir.inforetrsys.server.api.WordsParser;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class TextDocumentIndexer implements Indexer {
    private WordsParser wordsParser;
    private DocumentService documentService;
    private KeywordService keywordService;

    public TextDocumentIndexer(WordsParser wordsParser, DocumentService documentService,
                               KeywordService keywordService) {
        this.wordsParser = wordsParser;
        this.documentService = documentService;
        this.keywordService = keywordService;
    }

    @Override
    public void index(TextDocument document, int keywordsNumber) {
        List<String> allWordsValues = wordsParser.parse(document.getText());
        int documentsNumber = documentService.getDocumentsNumber();
        List<String> keywordValues = getKeywordValues(allWordsValues, keywordsNumber, documentsNumber);

        documentService.saveDocument(document);
        int documentId = documentService.getLastDocumentId();

        for (String keywordValue : keywordValues) {
            keywordService.addKeyword(new Keyword(documentId, keywordValue));
        }
    }

    private List<String> getKeywordValues(List<String> allWordsValues, int keywordsNumber, int documentsNumber) {
        SortedMap<Double, String> wordsWithWeights = new TreeMap<>(new HighestWeightComparator());
        for (String wordValue : allWordsValues) {
            if (!wordsWithWeights.containsValue(wordValue)) {
                int documentsWithWordNumber = keywordService.getNumberOfDocumentsWithWord(wordValue);
                double wordInverseFrequency = calculateWordInverseFrequency(documentsNumber, documentsWithWordNumber);
                int wordFrequencyInDocument = getWordFrequencyInDocument(wordValue, allWordsValues);
                double wordWeight = calculateWordWeightInDocument(wordFrequencyInDocument, wordInverseFrequency);

                wordsWithWeights.put(wordWeight, wordValue);
            }
        }

        List<String> keywordValues = new ArrayList<>();
        for (int i = 0; i < keywordsNumber; i++) {
            keywordValues.add(wordsWithWeights.remove(wordsWithWeights.firstKey()));
        }

        return keywordValues;
    }

    private double calculateWordInverseFrequency(int documentsNumber, int documentsWithWordNumber) {
        return documentsWithWordNumber > 0 ? Math.log((double) documentsNumber / (double) documentsWithWordNumber) : 1;
    }

    private double calculateWordWeightInDocument(int wordFrequencyInDocument, double wordInverseFrequency) {
        return (double) wordFrequencyInDocument * wordInverseFrequency;
    }

    private int getWordFrequencyInDocument(String requiredWordValue, List<String> allWordsValues) {
        return (int) allWordsValues.stream()
                .filter(requiredWordValue::equals)
                .count();
    }
}
