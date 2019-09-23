package com.bsuir.inforetrsys.client.api;

import com.bsuir.inforetrsys.client.entity.SearchResult;
import com.bsuir.inforetrsys.client.query.searcher.QuerySearchingProblemsException;

import java.util.List;

public interface QuerySearcher {
    List<SearchResult> search(String query, double minRelevance) throws QuerySearchingProblemsException;
}
