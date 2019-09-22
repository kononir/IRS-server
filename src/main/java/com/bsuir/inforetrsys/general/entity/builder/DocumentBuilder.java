package com.bsuir.inforetrsys.general.entity.builder;

import com.bsuir.inforetrsys.general.entity.TextDocument;
import com.epam.cafe.api.EntityBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DocumentBuilder implements EntityBuilder<TextDocument> {
    @Override
    public TextDocument build(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(TextDocument.ID_COLUMN);
        String title = resultSet.getString(TextDocument.TITLE_COLUMN);

        Timestamp timestamp = resultSet.getTimestamp(TextDocument.ADDING_TIME_COLUMN);
        LocalDateTime dateTime = timestamp.toLocalDateTime();

        String filePath = resultSet.getString(TextDocument.FILEPATH_COLUMN);

        return new TextDocument(id, title, dateTime, filePath);
    }
}
