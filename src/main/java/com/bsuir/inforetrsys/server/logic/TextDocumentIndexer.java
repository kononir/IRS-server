package com.bsuir.inforetrsys.server.logic;

import com.bsuir.inforetrsys.general.model.TextDocument;
import com.bsuir.inforetrsys.general.service.DocumentService;
import com.bsuir.inforetrsys.server.api.Indexer;
import com.bsuir.inforetrsys.server.api.WordsParser;

import java.util.List;

public class TextDocumentIndexer implements Indexer {
    private WordsParser wordsParser;
    private DocumentService documentService;

    public TextDocumentIndexer(WordsParser wordsParser, DocumentService documentService) {
        this.wordsParser = wordsParser;
        this.documentService = documentService;
    }

    @Override
    public boolean index(TextDocument document, int keywordsNumber) {
        throw new UnsupportedOperationException();
    }

    private List<String> getKeyWords(List<String> textWords, int keywordsNumber) {
        throw new UnsupportedOperationException();
    }

    private double getWordWeightInDocument(String word, List<String> textWords) {
        throw new UnsupportedOperationException();
    }
}
