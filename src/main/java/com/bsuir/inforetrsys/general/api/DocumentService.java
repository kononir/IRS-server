package com.bsuir.inforetrsys.general.api;

import com.bsuir.inforetrsys.general.entity.TextDocument;
import com.epam.cafe.service.ServiceException;

import java.util.Set;

public interface DocumentService {
    Set<String> getAllIndexedDocumentPaths() throws ServiceException;
    int getDocumentsNumber() throws ServiceException;
    int getLastDocumentId() throws ServiceException;

    void saveDocument(TextDocument document) throws ServiceException;
}
