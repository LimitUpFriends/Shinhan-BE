package com.LimitUpFriends.shinhan.dto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GeminiRequest {

    private List<Content> contents; // JSON 구조에 맞게 파싱

    public GeminiRequest(String text) {
        TextPart textPart = new TextPart(text);
        Content content = new Content(Collections.singletonList(textPart));
        this.contents = Collections.singletonList(content);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private List<TextPart> parts;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TextPart {
        private String text;
    }
}