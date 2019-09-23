package com.bsuir.inforetrsys.connection;

import java.sql.Connection;

public class ConnectionPool {
    private static ConnectionPool instance;

    private Connection connection;

    private ConnectionPool() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connection = connectionFactory.create();
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
