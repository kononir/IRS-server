package com.bsuir.inforetrsys.server;

import com.bsuir.inforetrsys.general.api.service.DocumentService;
import com.bsuir.inforetrsys.general.api.service.KeywordService;
import com.bsuir.inforetrsys.general.service.DocumentServiceImpl;
import com.bsuir.inforetrsys.general.service.KeywordServiceImpl;
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
    private static final String APP_PROPERTIES_FILE_PATH = "app.properties";

    public static void main(String[] args) {
        TextReader propertyReader = new PropertyReader(FileType.EXTERNAL, APP_PROPERTIES_FILE_PATH);
        DocumentService documentService = new DocumentServiceImpl();
        Searcher fileSearcher = new FileSearcher(documentService);
        TextReader documentReader = new InformationTextReader();

        ParserChainBuilder chainBuilder = new ParserChainBuilder();
        Parser parser = chainBuilder.build();
        WordsParser wordsParser = new AdaptiveWordsParser(parser);

        KeywordService keywordService = new KeywordServiceImpl();
        Indexer indexer = new TextDocumentIndexer(wordsParser, documentService, keywordService);

        Director director = new Director(propertyReader, fileSearcher, documentReader, indexer);
        director.handle();
    }
}
