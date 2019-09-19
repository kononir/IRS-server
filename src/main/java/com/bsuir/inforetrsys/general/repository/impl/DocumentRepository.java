package com.bsuir.inforetrsys.general.repository.impl;

import com.bsuir.inforetrsys.general.api.repository.Repository;
import com.bsuir.inforetrsys.general.connection.ConnectionPool;
import com.bsuir.inforetrsys.general.model.TextDocument;
import com.bsuir.inforetrsys.general.api.repository.SqlSpecification;

import java.sql.Connection;
import java.util.List;

public class DocumentRepository implements Repository<TextDocument> {
    private Connection connection;
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        connection = pool.getConnection();
    }

    @Override
    public void save(TextDocument element) {

    }

    @Override
    public void update(TextDocument element) {

    }

    @Override
    public void remove(TextDocument element) {

    }

    @Override
    public List<TextDocument> query(SqlSpecification specification) {
        return null;
    }
}
