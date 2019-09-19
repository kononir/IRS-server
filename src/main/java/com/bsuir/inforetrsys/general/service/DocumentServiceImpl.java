package com.bsuir.inforetrsys.general.service;

import com.bsuir.inforetrsys.general.api.service.DocumentService;
import com.bsuir.inforetrsys.general.model.TextDocument;
import com.bsuir.inforetrsys.general.repository.impl.DocumentRepository;
import com.bsuir.inforetrsys.general.api.repository.Repository;
import com.bsuir.inforetrsys.general.repository.specification.AllDocumentsSpecification;
import com.bsuir.inforetrsys.general.repository.specification.LastDocumentSpecification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentServiceImpl implements DocumentService {
    private static final int FIRST = 0;

    Repository<TextDocument> repository = new DocumentRepository();

    @Override
    public Set<String> getAllIndexedDocumentPaths() {
        List<TextDocument> documents = repository.query(new AllDocumentsSpecification());
        return documents.stream()
                .map(TextDocument::getFilePath)
                .collect(Collectors.toSet());
    }

    @Override
    public int getDocumentsNumber() {
        List<TextDocument> documents = repository.query(new AllDocumentsSpecification());
        return documents.size();
    }

    @Override
    public void saveDocument(TextDocument document) {
        repository.save(document);
    }

    @Override
    public int getLastDocumentId() {
        List<TextDocument> documents = repository.query(new LastDocumentSpecification());
        TextDocument document = documents.get(FIRST);
        return document.getId();
    }
}
