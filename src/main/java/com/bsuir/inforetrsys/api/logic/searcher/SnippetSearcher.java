package com.bsuir.inforetrsys.api.logic.searcher;

import java.util.List;

public interface SnippetSearcher {
    String search(String text, List<String> keywordValues);
}
