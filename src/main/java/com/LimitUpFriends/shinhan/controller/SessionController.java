package com.LimitUpFriends.shinhan.controller;

import com.LimitUpFriends.shinhan.security.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    @GetMapping("/info")
    public Map<String, Object> getSessionInfo() {
        // 현재 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 🔽 SecurityUtil 사용한 정보 출력
        System.out.println(" 현재 사용자 ID: " + SecurityUtil.getCurrentUserId());
        System.out.println(" 현재 사용자 이름: " + SecurityUtil.getCurrentUsername());
        System.out.println(" 로그인 플랫폼: " + (SecurityUtil.getCurrentUserLoginPlatform() ? "일반 로그인" : "소셜 로그인"));


        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("userName", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        response.put("authenticated", authentication.isAuthenticated());
        response.put("credentials", authentication.getCredentials());
        response.put("details", authentication.getDetails());
        response.put("principal", authentication.getPrincipal());

        // JSON 응답 반환
        return response;
    }
}

