package com.bsuir.inforetrsys.entity.builder;

import com.bsuir.inforetrsys.entity.Keyword;
import com.epam.cafe.api.EntityBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeywordBuilder implements EntityBuilder<Keyword> {
    @Override
    public Keyword build(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(Keyword.ID_COLUMN);
        String value = resultSet.getString(Keyword.VALUE_COLUMN);
        int documentId = resultSet.getInt(Keyword.DOCUMENT_ID_COLUMN);

        return new Keyword(id, value, documentId);
    }
}
