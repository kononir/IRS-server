package com.bsuir.inforetrsys.general.service;

import com.bsuir.inforetrsys.general.model.TextDocument;
import com.bsuir.inforetrsys.general.repository.DocumentRepository;
import com.bsuir.inforetrsys.general.repository.Repository;
import com.bsuir.inforetrsys.general.repository.specification.impl.AllDocumentsSpecification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentService {

    public Set<String> getAllIndexedDocumentPaths() {
        Repository<TextDocument> repository = new DocumentRepository();
        List<TextDocument> documents = repository.query(new AllDocumentsSpecification());

        return documents.stream()
                .map(TextDocument::getFilePath)
                .collect(Collectors.toSet());
    }
}
