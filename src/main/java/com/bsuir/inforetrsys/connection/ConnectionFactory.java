package com.bsuir.inforetrsys.connection;

import com.bsuir.inforetrsys.data.reader.FileType;
import com.bsuir.inforetrsys.data.reader.PropertyReader;
import com.epam.info.handling.data.reader.exception.ReadingException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DATABASE_PROPERTIES_PATH = "database.properties";
    private static final String URL_PROPERTY = "url";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";

    public Connection create() throws ConnectionFactoryException {
        try {
            Driver driver = new SQLServerDriver();
            DriverManager.registerDriver(driver);

            PropertyReader propertyReader = new PropertyReader(FileType.EXTERNAL, DATABASE_PROPERTIES_PATH);
            String url = propertyReader.read(URL_PROPERTY);
            String user = propertyReader.read(USER_PROPERTY);
            String password = propertyReader.read(PASSWORD_PROPERTY);

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | ReadingException e) {
            throw new ConnectionFactoryException("Problems with creating connection", e);
        }
    }
}
