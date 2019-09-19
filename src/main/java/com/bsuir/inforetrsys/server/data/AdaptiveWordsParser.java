package com.bsuir.inforetrsys.server.data;

import com.bsuir.inforetrsys.server.api.WordsParser;
import com.epam.info.handling.data.composite.Component;
import com.epam.info.handling.data.parser.Parser;

import java.util.List;

public class AdaptiveWordsParser implements WordsParser {
    private Parser parser;

    public AdaptiveWordsParser(Parser parser) {
        this.parser = parser;
    }

    @Override
    public List<String> parse(String text) {
        Component textComponent = parser.parse(text);

        return null;
    }
}
