package com.bsuir.inforetrsys.controller;

import com.bsuir.inforetrsys.api.data.DocumentParser;
import com.bsuir.inforetrsys.api.logic.searcher.QuerySearcher;
import com.bsuir.inforetrsys.api.logic.searcher.SnippetSearcher;
import com.bsuir.inforetrsys.api.service.DocumentService;
import com.bsuir.inforetrsys.api.service.KeywordService;
import com.bsuir.inforetrsys.api.service.StopwordService;
import com.bsuir.inforetrsys.data.parser.AdaptiveDocumentParser;
import com.bsuir.inforetrsys.data.parser.QueryParserChainBuilder;
import com.bsuir.inforetrsys.entity.SearchResult;
import com.bsuir.inforetrsys.logic.searcher.LogicSearcher;
import com.bsuir.inforetrsys.logic.searcher.QuerySearchingProblemsException;
import com.bsuir.inforetrsys.logic.searcher.SnippetSearcherImpl;
import com.bsuir.inforetrsys.service.DocumentServiceImpl;
import com.bsuir.inforetrsys.service.KeywordServiceImpl;
import com.bsuir.inforetrsys.service.StopwordServiceImpl;
import com.bsuir.inforetrsys.view.MainAlert;
import com.bsuir.inforetrsys.view.SearchResultsWindow;
import com.epam.info.handling.data.reader.TextReader;
import com.epam.info.handling.data.reader.impl.InformationTextReader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

public class MainController {
    private static DocumentParser documentParser = new AdaptiveDocumentParser(new QueryParserChainBuilder().build());
    private static KeywordService keywordService = new KeywordServiceImpl();
    private static StopwordService stopwordService = new StopwordServiceImpl();
    private static DocumentService documentService = new DocumentServiceImpl();
    private static TextReader documentReader = new InformationTextReader();
    private static SnippetSearcher snippetSearcher = new SnippetSearcherImpl();
    private static QuerySearcher querySearcher = new LogicSearcher(documentParser, keywordService, stopwordService,
            documentService, documentReader, snippetSearcher);

    @FXML
    private TextField queryField;

    @FXML
    private TextField minRankField;

    @FXML
    private void controlSearch() {
        try {
            String query = queryField.getText();
            if (query.isEmpty()) {
                new MainAlert().show("Query is not entered!");
                return;
            }

            String minRankLine = minRankField.getText();
            double minRank;
            if (!minRankLine.isEmpty()) {
                minRank = Double.parseDouble(minRankLine);
            } else {
                minRank = 0;
            }

            List<SearchResult> searchResults = querySearcher.search(query, minRank);

            new SearchResultsWindow(searchResults).show();
        } catch (QuerySearchingProblemsException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void controlButtonPressed(KeyEvent event) {
        if (KeyCode.ENTER.equals(event.getCode())) {
            controlSearch();
        }
    }

    @FXML
    private void controlShowingHelp() {

    }
}
