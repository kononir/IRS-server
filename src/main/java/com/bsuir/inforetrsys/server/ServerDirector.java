package com.bsuir.inforetrsys.server;

import com.bsuir.inforetrsys.general.entity.TextDocument;
import com.bsuir.inforetrsys.server.api.Indexer;
import com.bsuir.inforetrsys.server.api.FileSearcher;
import com.bsuir.inforetrsys.server.indexer.IndexingProblemsException;
import com.bsuir.inforetrsys.server.searcher.SearcherException;
import com.epam.info.handling.data.reader.TextReader;
import com.epam.info.handling.data.reader.exception.InvalidPathException;
import com.epam.info.handling.data.reader.exception.ReadingException;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerDirector {
    private static final int INITIAL_DELAY = 0;

    private TextReader propertyReader;
    private FileSearcher fileSearcher;
    private TextReader documentReader;
    private Indexer indexer;

    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);

    public ServerDirector(TextReader propertyReader, FileSearcher fileSearcher,
                          TextReader documentReader, Indexer indexer) {
        this.propertyReader = propertyReader;
        this.fileSearcher = fileSearcher;
        this.documentReader = documentReader;
        this.indexer = indexer;
    }

    public void handle() {
        try {
            /* Getting options from .properties or .json files:
            searching path, refreshing time, key words preferred number, db options */
            String searchingPath = propertyReader.read("searching.path");
            long refreshingTime = Long.parseLong(propertyReader.read("refreshing.time"));
            int keywordsNumber = Integer.parseInt(propertyReader.read("keywords.number"));

            executorService.scheduleAtFixedRate(() -> {
                try {
                    /* Searching of new files (comparison with indexed files information) */
                    List<File> newFiles = fileSearcher.search(searchingPath);

                    /* Searching old ones */

                    /* Reading founded documents */
                    List<TextDocument> documents = new ArrayList<>();
                    for (File newFile : newFiles) {
                        documents.add(new TextDocument(
                                newFile.getName(),
                                documentReader.read(newFile.getAbsolutePath()),
                                LocalDateTime.now(),
                                newFile.getAbsolutePath()
                        ));
                    }

                    /* Indexing founded documents (parsing, creating search patterns of its, adding to db) */
                    for (TextDocument document : documents) {
                        indexer.index(document, keywordsNumber);
                    }

                    /* Deleting old files */

                } catch (SearcherException | InvalidPathException | ReadingException | IndexingProblemsException e) {
                    e.printStackTrace();
                    executorService.shutdown();
                }

            }, INITIAL_DELAY, refreshingTime, TimeUnit.SECONDS);
        } catch (InvalidPathException | ReadingException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        executorService.shutdown();
    }
}
