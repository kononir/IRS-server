package com.bsuir.inforetrsys.general.parser;

import com.bsuir.inforetrsys.server.api.WordsParser;
import com.epam.info.handling.data.composite.Component;
import com.epam.info.handling.data.composite.impl.lexeme.Lexeme;
import com.epam.info.handling.data.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdaptiveWordsParser implements WordsParser {
    private Parser parser;

    public AdaptiveWordsParser(Parser parser) {
        this.parser = parser;
    }

    @Override
    public List<String> parse(String text) {
        Component textComponent = parser.parse(text);
        return searchWordsRecursively(textComponent);
    }

    public List<String> searchWordsRecursively(Component textComponent) {
        List<String> result;

        List<Component> children = textComponent.getChildren();
        if (children.isEmpty()) {
            Lexeme lexeme = (Lexeme) textComponent;
            if (lexeme.isWord()) {
                result = Collections.singletonList(lexeme.getValue());
            } else {
                result = Collections.emptyList();
            }
        } else {
            result = new ArrayList<>();
            for (Component child : children) {
                result.addAll(searchWordsRecursively(child));
            }
        }

        return result;
    }
}
