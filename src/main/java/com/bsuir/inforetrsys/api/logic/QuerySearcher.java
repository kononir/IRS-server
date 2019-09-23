package com.bsuir.inforetrsys.api.logic;

import com.bsuir.inforetrsys.entity.SearchResult;
import com.bsuir.inforetrsys.logic.searcher.QuerySearchingProblemsException;

import java.util.List;

public interface QuerySearcher {
    List<SearchResult> search(String query, double minRelevance) throws QuerySearchingProblemsException;
}
