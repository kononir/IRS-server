package com.bsuir.inforetrsys.server;

import com.bsuir.inforetrsys.Director;
import com.bsuir.inforetrsys.general.service.DocumentService;
import com.bsuir.inforetrsys.server.searcher.FileSearcher;

public class Runner {
    public static void main(String[] args) {
        DocumentService documentService = new DocumentService();
        FileSearcher fileSearcher = new FileSearcher();
        Director director = new Director(fileSearcher, documentService);
        director.handle();
    }
}
