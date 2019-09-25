package com.bsuir.inforetrsys;

import com.bsuir.inforetrsys.api.data.WordsParser;
import com.bsuir.inforetrsys.api.logic.QuerySearcher;
import com.bsuir.inforetrsys.api.logic.SnippetSearcher;
import com.bsuir.inforetrsys.api.service.DocumentService;
import com.bsuir.inforetrsys.api.service.KeywordService;
import com.bsuir.inforetrsys.api.service.StopwordService;
import com.bsuir.inforetrsys.data.parser.AdaptiveWordsParser;
import com.bsuir.inforetrsys.data.parser.QueryParserChainBuilder;
import com.bsuir.inforetrsys.logic.searcher.SnippetSearcherImpl;
import com.bsuir.inforetrsys.entity.SearchResult;
import com.bsuir.inforetrsys.logic.searcher.LogicSearcher;
import com.bsuir.inforetrsys.logic.searcher.QuerySearchingProblemsException;
import com.bsuir.inforetrsys.service.DocumentServiceImpl;
import com.bsuir.inforetrsys.service.KeywordServiceImpl;
import com.bsuir.inforetrsys.service.StopwordServiceImpl;
import com.bsuir.inforetrsys.view.SearchResultsWindow;
import com.epam.info.handling.data.reader.TextReader;
import com.epam.info.handling.data.reader.impl.InformationTextReader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class MainController {
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
    private void controlSearch() {
        try {
            String query = queryField.getText();
            if (query == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Query is not entered!");

                alert.show();
                return;
            }

            List<SearchResult> searchResults = querySearcher.search(query, 0.5);

            new SearchResultsWindow(searchResults).show();
        } catch (QuerySearchingProblemsException e) {
            e.printStackTrace();
        }
    }
}
