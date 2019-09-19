package com.bsuir.inforetrsys.server.api;

import com.bsuir.inforetrsys.server.searcher.SearcherException;

import java.io.File;
import java.util.List;

public interface Searcher {
    List<File> search(String searchingPath) throws SearcherException;
}
