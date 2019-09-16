package com.bsuir.inforetrsys;

import com.bsuir.inforetrsys.server.property.FileType;
import com.bsuir.inforetrsys.server.property.PropertyReader;
import com.bsuir.inforetrsys.server.property.PropertyReaderException;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Director {
    private static final String APP_PROPERTIES_FILE_PATH = "app.properties";

    public void handle() {
        /* Getting options from .properties or .json files:
        searching path, refreshing time, key words preferred number, db options */
        PropertyReader reader = new PropertyReader(FileType.EXTERNAL, APP_PROPERTIES_FILE_PATH);
        try {
            String searchingPath = reader.read("searching.path");
            long refreshingTime = Long.parseLong(reader.read("refreshing.time"));
            int keywordsNumber = Integer.parseInt(reader.read("keywords.number"));

            ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
            executorService.schedule(() -> {
                /* Searching of new files (comparison with files information in db) and checking old ones */

                /* Parsing founded documents, creating search patterns of its */

                /* Updating db with new information */

            }, refreshingTime, TimeUnit.SECONDS);
        } catch (PropertyReaderException e) {
            e.printStackTrace();
        }
    }
}
