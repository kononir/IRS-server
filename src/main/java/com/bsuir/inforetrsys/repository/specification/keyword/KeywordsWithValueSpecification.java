package com.bsuir.inforetrsys.repository.specification.keyword;

import com.epam.cafe.api.repository.specification.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class KeywordsWithValueSpecification implements SqlSpecification {
    private static final String QUERY = "SELECT * FROM irs_schema.keyword WHERE Value = ?";

    private String value;

    public KeywordsWithValueSpecification(String value) {
        this.value = value;
    }

    @Override
    public String toSqlClause() {
        return QUERY;
    }

    @Override
    public List<Object> getParams() {
        return Collections.singletonList(value);
    }
}
