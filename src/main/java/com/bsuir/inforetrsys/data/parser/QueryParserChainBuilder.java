package com.bsuir.inforetrsys.data.parser;

import com.epam.info.handling.data.parser.Parser;
import com.epam.info.handling.data.parser.builder.ChainBuilder;
import com.epam.info.handling.data.parser.impl.LexemeParser;
import com.epam.info.handling.data.parser.impl.SentenceParser;

public class QueryParserChainBuilder implements ChainBuilder<Parser> {
    @Override
    public Parser build() {
        LexemeParser lexemeParser = new LexemeParser();

        SentenceParser sentenceParser = new SentenceParser();
        sentenceParser.setSuccessor(lexemeParser);

        return sentenceParser;
    }
}
