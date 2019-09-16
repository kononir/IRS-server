package com.bsuir.inforetrsys.server.property;

import java.io.*;
import java.util.Properties;

public class PropertyReader {
    private FileType fileType;
    private String filePath;

    public PropertyReader(FileType fileType, String filePath) {
        this.fileType = fileType;
        this.filePath = filePath;
    }

    public String read(String propertyName) throws PropertyReaderException {
        try {
            InputStream stream;
            switch (fileType) {
                case EXTERNAL:
                    stream = new FileInputStream(filePath);
                    break;
                case INTERNAL:
                    stream = getClass().getClassLoader().getResourceAsStream(filePath);
                    break;
                default:
                    throw new EnumConstantNotPresentException(FileType.class, fileType.name());
            }

            Properties properties = new Properties();
            properties.load(stream);

            return properties.getProperty(propertyName);
        } catch (IOException e) {
            throw new PropertyReaderException("Error when read property with name " + propertyName, e);
        }
    }
}
