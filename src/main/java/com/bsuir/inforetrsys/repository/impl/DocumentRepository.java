package com.bsuir.inforetrsys.repository.impl;

import com.bsuir.inforetrsys.connection.ConnectionPool;
import com.bsuir.inforetrsys.entity.TextDocument;
import com.bsuir.inforetrsys.entity.builder.DocumentBuilder;
import com.epam.cafe.api.repository.specification.SqlSpecification;
import com.epam.cafe.repository.RepositoryException;
import com.epam.cafe.repository.impl.AbstractRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DocumentRepository extends AbstractRepository<TextDocument> {
    private static final String TABLE_NAME = "irs_schema.document";

    public DocumentRepository() {
        super(ConnectionPool.getInstance().getConnection());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> getParams(TextDocument document) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put(TextDocument.ID_COLUMN, document.getId());
        params.put(TextDocument.TITLE_COLUMN, document.getTitle());
        params.put(TextDocument.ADDING_TIME_COLUMN, document.getAddingTime());
        params.put(TextDocument.FILEPATH_COLUMN, document.getFilePath());
        return params;
    }

    @Override
    public List<TextDocument> query(SqlSpecification specification) throws RepositoryException {
        return super.executeQuery(specification, new DocumentBuilder());
    }
}
