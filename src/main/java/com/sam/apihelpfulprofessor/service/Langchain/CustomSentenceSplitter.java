package com.sam.apihelpfulprofessor.service.Langchain;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.SentenceSplitter;
import dev.langchain4j.data.segment.TextSegment;

import java.util.ArrayList;
import java.util.List;

public class CustomSentenceSplitter extends SentenceSplitter {
    private static final int MAX_TOKENS = 510;

    @Override
    public List<TextSegment> split(Document document) {
        List<TextSegment> chunks = new ArrayList<>();
        String text = document.text();
        String[] tokens = text.split(" ");
        StringBuilder sb = new StringBuilder();
        int tokenCount = 0;
        for (String token : tokens) {
            if (tokenCount + token.length() > MAX_TOKENS) {
                chunks.add(new TextSegment(sb.toString().trim(), document.metadata()));
                sb = new StringBuilder();
                tokenCount = 0;
            }
            sb.append(token).append(" ");
            tokenCount += token.length() + 1;
        }
        if (sb.length() > 0) {
            chunks.add(new TextSegment(sb.toString().trim(), document.metadata()));
        }
        return chunks;
    }

}
