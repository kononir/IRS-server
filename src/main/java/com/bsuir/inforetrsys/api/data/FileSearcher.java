package com.bsuir.inforetrsys.api.data;

import com.bsuir.inforetrsys.data.searcher.SearcherException;

import java.io.File;
import java.util.List;

public interface FileSearcher {
    List<File> search(String searchingPath) throws SearcherException;
}
