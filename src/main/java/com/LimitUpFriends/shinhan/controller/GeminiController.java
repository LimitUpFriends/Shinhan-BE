package com.LimitUpFriends.shinhan.controller;

import com.LimitUpFriends.shinhan.dto.GeminiRequest;
import com.LimitUpFriends.shinhan.service.GeminiService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/question")
    public ResponseEntity<Map<String, String>> getCompletion(@RequestBody GeminiRequest request) {
        // 질문 추출
        String promptText = request.getContents().get(0).getParts().get(0).getText();
        String completionText = geminiService.getCompletion(promptText);

        // 답변 생성
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("answer", completionText);

        return ResponseEntity.ok(responseBody);
    }
}