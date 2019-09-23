package com.bsuir.inforetrsys.data.parser;

import com.bsuir.inforetrsys.api.logic.SnippetSearcher;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnippetSearcherImpl implements SnippetSearcher {
    @Override
    public String search(String text, List<String> keywordValues) {
        StringBuilder snippet = new StringBuilder();
        for (String keywordValue : keywordValues) {
            if (keywordValues.contains(keywordValue)) {
                Pattern pattern = Pattern.compile(keywordValue + ".*(\\.|!|?)");
                Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    String snippetPart = matcher.group();
                    keywordValues.removeIf(snippetPart::contains);
                    snippet.append("...").append(snippetPart).append("...");
                }
            }
        }

        return snippet.toString();
    }
}
