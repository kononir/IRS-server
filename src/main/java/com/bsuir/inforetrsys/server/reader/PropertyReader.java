package com.bsuir.inforetrsys.server.reader;

import com.epam.info.handling.data.reader.TextReader;
import com.epam.info.handling.data.reader.exception.ReadingException;

import java.io.*;
import java.util.Properties;

public class PropertyReader implements TextReader {
    private FileType fileType;
    private String filePath;

    public PropertyReader(FileType fileType, String filePath) {
        this.fileType = fileType;
        this.filePath = filePath;
    }

    public String read(String propertyName) throws ReadingException {
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
            throw new ReadingException("Error when read property with name " + propertyName, e);
        }
    }
}
