package com.bsuir.inforetrsys.util;

import com.bsuir.inforetrsys.entity.table.TextCellEnum;
import javafx.util.StringConverter;

public class TextCellEnumConverter extends StringConverter<TextCellEnum> {
    @Override
    public String toString(TextCellEnum object) {
        return object.getText();
    }

    @Override
    public TextCellEnum fromString(String string) {
        return TextCellEnum.valueOf(string.toUpperCase());
    }
}
