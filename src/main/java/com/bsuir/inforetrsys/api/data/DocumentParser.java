package com.bsuir.inforetrsys.api.data;

import java.util.List;

public interface DocumentParser {
    String parseTitle(String text);
    List<String> parse(String text);
}
