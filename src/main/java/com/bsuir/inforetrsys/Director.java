package com.bsuir.inforetrsys;

import com.bsuir.inforetrsys.general.service.DocumentService;
import com.bsuir.inforetrsys.server.property.FileType;
import com.bsuir.inforetrsys.server.property.PropertyReader;
import com.bsuir.inforetrsys.server.property.PropertyReaderException;
import com.bsuir.inforetrsys.server.searcher.FileSearcher;
import com.bsuir.inforetrsys.server.searcher.SearcherException;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Director {
    private static final String APP_PROPERTIES_FILE_PATH = "src/main/app.properties";
    private static final int INITIAL_DELAY = 0;

    private FileSearcher fileSearcher;
    private DocumentService documentService;

    public Director(FileSearcher fileSearcher, DocumentService documentService) {
        this.fileSearcher = fileSearcher;
        this.documentService = documentService;
    }

    public void handle() {
        /* Getting options from .properties or .json files:
        searching path, refreshing time, key words preferred number, db options */
        PropertyReader reader = new PropertyReader(FileType.EXTERNAL, APP_PROPERTIES_FILE_PATH);
        try {
            String searchingPath = reader.read("searching.path");
            long refreshingTime = Long.parseLong(reader.read("refreshing.time"));
            int keywordsNumber = Integer.parseInt(reader.read("keywords.number"));

            ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
            executorService.scheduleAtFixedRate(() -> {
                try {
                    /* Searching of new files (comparison with indexed files information) */
                    Set<String> indexedDocumentPaths = documentService.getAllIndexedDocumentPaths();
                    List<File> newFiles = fileSearcher.searchForNewFiles(searchingPath, indexedDocumentPaths);

                    /* Searching old ones */

                    /* Parsing founded documents, creating search patterns of its */

                    /* Updating indexed files with new information */

                } catch (SearcherException e) {
                    e.printStackTrace();
                }

            }, INITIAL_DELAY, refreshingTime, TimeUnit.SECONDS);
        } catch (PropertyReaderException e) {
            e.printStackTrace();
        }
    }
}
