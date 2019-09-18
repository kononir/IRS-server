package com.bsuir.inforetrsys.server.searcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileSearcher {
    public List<File> searchForNewFiles(String searchingPath, Set<String> indexedDocumentPaths)
            throws SearcherException {
        File searchingDir = new File(searchingPath);
        if (!searchingDir.exists() || !searchingDir.isDirectory()) {
            throw new SearcherException("Illegal dir path");
        }

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
