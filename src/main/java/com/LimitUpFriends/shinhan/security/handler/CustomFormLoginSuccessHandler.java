package com.LimitUpFriends.shinhan.security.handler;

/**
 * 일반 로그인 성공 핸들러
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomFormLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 상태 코드 200만 반환
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
