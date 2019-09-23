package com.bsuir.inforetrsys;

import com.bsuir.inforetrsys.api.data.WordsParser;
import com.bsuir.inforetrsys.api.logic.QuerySearcher;
import com.bsuir.inforetrsys.api.logic.SnippetSearcher;
import com.bsuir.inforetrsys.api.service.DocumentService;
import com.bsuir.inforetrsys.api.service.KeywordService;
import com.bsuir.inforetrsys.api.service.StopwordService;
import com.bsuir.inforetrsys.data.parser.AdaptiveWordsParser;
import com.bsuir.inforetrsys.data.parser.QueryParserChainBuilder;
import com.bsuir.inforetrsys.data.parser.SnippetSearcherImpl;
import com.bsuir.inforetrsys.entity.SearchResult;
import com.bsuir.inforetrsys.logic.searcher.LogicSearcher;
import com.bsuir.inforetrsys.logic.searcher.QuerySearchingProblemsException;
import com.bsuir.inforetrsys.service.DocumentServiceImpl;
import com.bsuir.inforetrsys.service.KeywordServiceImpl;
import com.bsuir.inforetrsys.service.StopwordServiceImpl;
import com.epam.info.handling.data.reader.TextReader;
import com.epam.info.handling.data.reader.impl.InformationTextReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {
    private static final String SEARCH_RESULT_PAGE = "search_result.fxml";

    private static WordsParser wordsParser = new AdaptiveWordsParser(new QueryParserChainBuilder().build());
    private static KeywordService keywordService = new KeywordServiceImpl();
    private static StopwordService stopwordService = new StopwordServiceImpl();
    private static DocumentService documentService = new DocumentServiceImpl();
    private static TextReader documentReader = new InformationTextReader();
    private static SnippetSearcher snippetSearcher = new SnippetSearcherImpl();
    private static QuerySearcher querySearcher = new LogicSearcher(wordsParser, keywordService, stopwordService,
            documentService, documentReader, snippetSearcher);

    @FXML
    private TextField queryField;

    @FXML
    private void handleSearch() {
        try {
            List<SearchResult> searchResults = querySearcher.search(queryField.getText(), 0.5);

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(SEARCH_RESULT_PAGE));
            Scene scene = new Scene(root);

            Stage stage = (Stage) queryField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (QuerySearchingProblemsException | IOException e) {
            e.printStackTrace();
        }
    }
}
