package com.bsuir.inforetrsys.server.searcher;

import com.bsuir.inforetrsys.general.api.service.DocumentService;
import com.bsuir.inforetrsys.general.service.DocumentServiceImpl;
import com.bsuir.inforetrsys.server.api.Searcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileSearcher implements Searcher {
    private DocumentService documentService;

    public FileSearcher(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public List<File> search(String searchingPath)
            throws SearcherException {
        File searchingDir = new File(searchingPath);
        if (!searchingDir.exists() || !searchingDir.isDirectory()) {
            throw new SearcherException("Illegal dir path");
        }

        Set<String> indexedDocumentPaths = documentService.getAllIndexedDocumentPaths();

        return searchRecursively(searchingDir, indexedDocumentPaths);
    }

    private List<File> searchRecursively(File searchingDir, Set<String> indexedDocumentPaths) {
        List<File> newFiles = new ArrayList<>();

        File[] allFiles = searchingDir.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if (file.isDirectory()) {
                    newFiles.addAll(searchRecursively(file, indexedDocumentPaths));
                } else {
                    /* searching file name in indexed files */
                    if (indexedDocumentPaths.contains(file.getAbsolutePath())) {
                        newFiles.add(file);
                    }
                }
            }
        }

        return newFiles;
    }

    private List<File> searchForDeletedFiles() {
        throw new UnsupportedOperationException();
    }
}
