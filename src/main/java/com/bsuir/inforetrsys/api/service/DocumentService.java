package com.bsuir.inforetrsys.api.service;

import com.bsuir.inforetrsys.entity.TextDocument;
import com.epam.cafe.service.ServiceException;

import java.util.Set;

public interface DocumentService {
    Set<String> getAllIndexedDocumentPaths() throws ServiceException;

    int getDocumentsNumber() throws ServiceException;

    int getLastDocumentId() throws ServiceException;

    TextDocument getDocumentById(int id) throws ServiceException;

    void saveDocument(TextDocument document) throws ServiceException;
}
