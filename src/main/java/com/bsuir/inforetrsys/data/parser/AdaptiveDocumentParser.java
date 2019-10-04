package com.bsuir.inforetrsys.data.parser;

import com.bsuir.inforetrsys.api.data.DocumentParser;
import com.epam.info.handling.data.composite.Component;
import com.epam.info.handling.data.composite.impl.lexeme.Lexeme;
import com.epam.info.handling.data.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdaptiveDocumentParser implements DocumentParser {
    private static final int FIRST = 0;

    private Parser parser;

    public AdaptiveDocumentParser(Parser parser) {
        this.parser = parser;
    }

    @Override
    public String parseTitle(String text) {
        Component textComponent = parser.parse(text);
        List<Component> paragraphComponents = textComponent.getChildren();
        Component firstParagraphComponent = paragraphComponents.get(FIRST);
        List<Component> sentenceComponents = firstParagraphComponent.getChildren();
        Component firstSentenceComponent = sentenceComponents.get(FIRST);
        List<String> titleWords = searchWordsRecursively(firstSentenceComponent);

        StringBuilder title = new StringBuilder();

        int lastTitleWordIndex = titleWords.size() - 1;
        for (int i = 0; i < lastTitleWordIndex; i++) {
            title.append(titleWords.get(i)).append(" ");
        }
        title.append(titleWords.get(lastTitleWordIndex));

        return title.toString();
    }

    @Override
    public List<String> parse(String text) {
        Component textComponent = parser.parse(text);
        return searchWordsRecursively(textComponent);
    }

    private List<String> searchWordsRecursively(Component textComponent) {
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
