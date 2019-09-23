package com.bsuir.inforetrsys.client;

import com.bsuir.inforetrsys.client.api.QuerySearcher;
import com.bsuir.inforetrsys.client.query.QueryParserChainBuilder;
import com.bsuir.inforetrsys.client.query.searcher.LogicSearcher;
import com.bsuir.inforetrsys.client.query.searcher.QuerySearchingProblemsException;
import com.bsuir.inforetrsys.general.api.KeywordService;
import com.bsuir.inforetrsys.general.api.StopwordService;
import com.bsuir.inforetrsys.general.parser.AdaptiveWordsParser;
import com.bsuir.inforetrsys.general.service.KeywordServiceImpl;
import com.bsuir.inforetrsys.general.service.StopwordServiceImpl;
import com.bsuir.inforetrsys.server.api.WordsParser;
import com.epam.info.handling.data.parser.Parser;
import com.epam.info.handling.data.parser.builder.ChainBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainController {


    @FXML
    private TextField queryField;

    @FXML
    private void handleSearch() {
        try {
            ChainBuilder<Parser> chainBuilder = new QueryParserChainBuilder();
            Parser parser = chainBuilder.build();
            WordsParser wordsParser = new AdaptiveWordsParser(parser);

            KeywordService keywordService = new KeywordServiceImpl();
            StopwordService stopwordService = new StopwordServiceImpl();
            QuerySearcher querySearcher = new LogicSearcher(wordsParser, keywordService, stopwordService);
            querySearcher.search(queryField.getText(), 0.5);
        } catch (QuerySearchingProblemsException e) {
            e.printStackTrace();
        }
    }
}
