package com.bsuir.inforetrsys.general.api.service;

import com.bsuir.inforetrsys.general.model.TextDocument;

import java.util.Set;

public interface DocumentService {
    Set<String> getAllIndexedDocumentPaths();
    int getDocumentsNumber();
    int getLastDocumentId();

    void saveDocument(TextDocument document);
}
