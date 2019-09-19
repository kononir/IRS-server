package com.bsuir.inforetrsys.general.repository.impl;

import com.bsuir.inforetrsys.general.api.repository.Repository;
import com.bsuir.inforetrsys.general.api.repository.SqlSpecification;
import com.bsuir.inforetrsys.general.connection.ConnectionPool;
import com.bsuir.inforetrsys.general.model.Keyword;

import java.sql.Connection;
import java.util.List;

public class KeywordRepository implements Repository<Keyword> {
    private Connection connection;
    {
        ConnectionPool pool = ConnectionPool.getInstance();
        connection = pool.getConnection();
    }

    @Override
    public void save(Keyword element) {

    }

    @Override
    public void update(Keyword element) {

    }

    @Override
    public void remove(Keyword element) {

    }

    @Override
    public List<Keyword> query(SqlSpecification specification) {
        return null;
    }
}
