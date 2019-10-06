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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MainController {
    private static final String MAIN_HELP_FXML_FILE_PATH = "view/main_help.fxml";
    private static final String STYLE_FILE_PATH = "style/main.css";

    private static final int MILLIS_FROM_NANO = 1000000;

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

            int timeBeforeSearch = LocalDateTime.now().getNano() / MILLIS_FROM_NANO;
            List<SearchResult> searchResults = querySearcher.search(query, minRank);
            int timeAfterSearch = LocalDateTime.now().getNano() / MILLIS_FROM_NANO;

            new SearchResultsWindow(searchResults, timeAfterSearch - timeBeforeSearch).show();
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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(MAIN_HELP_FXML_FILE_PATH));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getClassLoader().getResource(STYLE_FILE_PATH).toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
