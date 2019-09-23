package com.bsuir.inforetrsys.repository.specification.document;


import com.epam.cafe.api.repository.specification.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class AllDocumentsSpecification implements SqlSpecification {
    private static final String QUERY = "SELECT * FROM irs_schema.document";

    @Override
    public String toSqlClause() {
        return QUERY;
    }

    @Override
    public List<Object> getParams() {
        return Collections.emptyList();
    }
}
