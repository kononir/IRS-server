package com.bsuir.inforetrsys.general.repository.impl;

import com.bsuir.inforetrsys.general.connection.ConnectionPool;
import com.bsuir.inforetrsys.general.entity.Keyword;
import com.bsuir.inforetrsys.general.entity.builder.KeywordBuilder;
import com.epam.cafe.api.repository.specification.SqlSpecification;
import com.epam.cafe.repository.RepositoryException;
import com.epam.cafe.repository.impl.AbstractRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KeywordRepository extends AbstractRepository<Keyword> {
    private static final String TABLE_NAME = "irs_schema.keyword";

    public KeywordRepository() {
        super(ConnectionPool.getInstance().getConnection());
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, Object> getParams(Keyword entity) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put(Keyword.ID_COLUMN, entity.getId());
        params.put(Keyword.VALUE_COLUMN, entity.getValue());
        params.put(Keyword.DOCUMENT_ID_COLUMN, entity.getDocumentId());
        return params;
    }

    @Override
    public List<Keyword> query(SqlSpecification specification) throws RepositoryException {
        return super.executeQuery(specification, new KeywordBuilder());
    }
}
