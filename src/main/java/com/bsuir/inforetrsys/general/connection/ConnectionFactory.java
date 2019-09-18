package com.bsuir.inforetrsys.general.connection;

import com.bsuir.inforetrsys.server.property.FileType;
import com.bsuir.inforetrsys.server.property.PropertyReader;
import com.bsuir.inforetrsys.server.property.PropertyReaderException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DATABASE_PROPERTIES_PATH = "database.properties";
    private static final String URL_PROPERTY = "url";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";

    public Connection create() throws ConnectionFactoryException {
        try {
            PropertyReader propertyReader = new PropertyReader(FileType.INTERNAL, DATABASE_PROPERTIES_PATH);
            String url = propertyReader.read(URL_PROPERTY);
            String user = propertyReader.read(USER_PROPERTY);
            String password = propertyReader.read(PASSWORD_PROPERTY);

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | PropertyReaderException e) {
            throw new ConnectionFactoryException("Problems with creating connection", e);
        }
    }
}
