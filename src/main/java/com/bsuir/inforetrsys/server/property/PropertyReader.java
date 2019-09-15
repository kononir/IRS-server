package com.bsuir.inforetrsys.server.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private String filePath;

    public PropertyReader(String filePath) {
        this.filePath = filePath;
    }

    public String read(String propertyName) throws PropertyReaderException {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(filePath);

            Properties properties = new Properties();
            properties.load(stream);

            return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new PropertyReaderException("Error when read property with name " + propertyName, e);
        }
    }
}
