package com.LimitUpFriends.shinhan.service;

import com.LimitUpFriends.shinhan.dto.GeminiRequest;
import com.LimitUpFriends.shinhan.dto.GeminiResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/v1beta/models/")
public interface GeminiInterface {

    // API 통신
    @PostExchange("gemini-2.5-flash:generateContent")
    GeminiResponse getCompletion(
            @RequestBody GeminiRequest request
    );
}