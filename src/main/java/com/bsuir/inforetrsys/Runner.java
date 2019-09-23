package com.bsuir.inforetrsys;

import com.bsuir.inforetrsys.api.service.DocumentService;
import com.bsuir.inforetrsys.api.service.KeywordService;
import com.bsuir.inforetrsys.api.service.StopwordService;
import com.bsuir.inforetrsys.data.parser.AdaptiveWordsParser;
import com.bsuir.inforetrsys.service.DocumentServiceImpl;
import com.bsuir.inforetrsys.service.KeywordServiceImpl;
import com.bsuir.inforetrsys.service.StopwordServiceImpl;
import com.bsuir.inforetrsys.api.data.FileSearcher;
import com.bsuir.inforetrsys.api.logic.Indexer;
import com.bsuir.inforetrsys.api.data.WordsParser;
import com.bsuir.inforetrsys.logic.indexer.TextDocumentIndexer;
import com.bsuir.inforetrsys.data.reader.FileType;
import com.bsuir.inforetrsys.data.reader.PropertyReader;
import com.bsuir.inforetrsys.data.searcher.TextFileSearcher;
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

public class Runner extends Application {
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
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(FXML_FILE_PATH));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Searcher");
        primaryStage.setOnCloseRequest(event -> director.stop());
        primaryStage.show();
    }
}
