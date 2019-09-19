package com.bsuir.inforetrsys.server;

import com.bsuir.inforetrsys.Director;
import com.bsuir.inforetrsys.general.service.DocumentService;
import com.bsuir.inforetrsys.server.api.Indexer;
import com.bsuir.inforetrsys.server.api.Searcher;
import com.bsuir.inforetrsys.server.logic.TextDocumentIndexer;
import com.bsuir.inforetrsys.server.data.AdaptiveWordsParser;
import com.bsuir.inforetrsys.server.api.WordsParser;
import com.bsuir.inforetrsys.server.data.property.FileType;
import com.bsuir.inforetrsys.server.data.property.PropertyReader;
import com.bsuir.inforetrsys.server.searcher.FileSearcher;
import com.epam.info.handling.data.parser.Parser;
import com.epam.info.handling.data.parser.builder.impl.ParserChainBuilder;
import com.epam.info.handling.data.reader.TextReader;
import com.epam.info.handling.data.reader.impl.InformationTextReader;

public class Runner {
    private static final String APP_PROPERTIES_FILE_PATH = "src/main/app.properties";

    public static void main(String[] args) {
        TextReader propertyReader = new PropertyReader(FileType.EXTERNAL, APP_PROPERTIES_FILE_PATH);
        DocumentService documentService = new DocumentService();
        Searcher fileSearcher = new FileSearcher(documentService);
        TextReader documentReader = new InformationTextReader();

        ParserChainBuilder chainBuilder = new ParserChainBuilder();
        Parser parser = chainBuilder.build();
        WordsParser wordsParser = new AdaptiveWordsParser(parser);

        Indexer indexer = new TextDocumentIndexer(wordsParser, documentService);

        Director director = new Director(propertyReader, fileSearcher, documentReader, indexer);
        director.handle();
    }
}
