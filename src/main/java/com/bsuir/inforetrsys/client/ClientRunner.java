package com.bsuir.inforetrsys.client;

import com.bsuir.inforetrsys.general.api.DocumentService;
import com.bsuir.inforetrsys.general.api.KeywordService;
import com.bsuir.inforetrsys.general.api.StopwordService;
import com.bsuir.inforetrsys.general.parser.AdaptiveWordsParser;
import com.bsuir.inforetrsys.general.service.DocumentServiceImpl;
import com.bsuir.inforetrsys.general.service.KeywordServiceImpl;
import com.bsuir.inforetrsys.general.service.StopwordServiceImpl;
import com.bsuir.inforetrsys.server.ServerDirector;
import com.bsuir.inforetrsys.server.api.FileSearcher;
import com.bsuir.inforetrsys.server.api.Indexer;
import com.bsuir.inforetrsys.server.api.WordsParser;
import com.bsuir.inforetrsys.server.indexer.TextDocumentIndexer;
import com.bsuir.inforetrsys.server.reader.FileType;
import com.bsuir.inforetrsys.server.reader.PropertyReader;
import com.bsuir.inforetrsys.server.searcher.TextFileSearcher;
import com.epam.info.handling.data.parser.Parser;
import com.epam.info.handling.data.parser.builder.ChainBuilder;
import com.epam.info.handling.data.parser.builder.impl.ParserChainBuilder;
import com.epam.info.handling.data.reader.TextReader;
import com.epam.info.handling.data.reader.impl.InformationTextReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientRunner extends Application {
    private static final String APP_PROPERTIES_FILE_PATH = "app.properties";
    private static final String FXML_FILE_PATH = "main.fxml";

    private static ServerDirector director;

    public static void main(String[] args) {
        TextReader propertyReader = new PropertyReader(FileType.EXTERNAL, APP_PROPERTIES_FILE_PATH);
        DocumentService documentService = new DocumentServiceImpl();
        FileSearcher fileSearcher = new TextFileSearcher(documentService);
        TextReader documentReader = new InformationTextReader();

        ChainBuilder<Parser> chainBuilder = new ParserChainBuilder();
        Parser parser = chainBuilder.build();
        WordsParser wordsParser = new AdaptiveWordsParser(parser);

        KeywordService keywordService = new KeywordServiceImpl();
        StopwordService stopwordService = new StopwordServiceImpl();
        Indexer indexer = new TextDocumentIndexer(wordsParser,  documentService, keywordService, stopwordService);

        director = new ServerDirector(propertyReader, fileSearcher, documentReader, indexer);
        director.handle();

        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(FXML_FILE_PATH));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Searcher");
        stage.setWidth(250);
        stage.setHeight(200);

        stage.setOnCloseRequest(event -> director.stop());

        stage.show();
    }
}
