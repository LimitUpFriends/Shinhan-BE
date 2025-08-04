package com.LimitUpFriends.shinhan.security;

/**
 * OAuth2 성공 핸들러 (리다이렉트만 수행)
 */


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {


    public CustomOAuth2LoginSuccessHandler() {}

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 로그인 성공 후 프론트엔드로 리디렉트
        String sessionId = request.getSession().getId();
        response.sendRedirect("http://localhost:3000/oauth2/success?sessionId=" + sessionId);
        // response.sendRedirect("honorsparking://?sessionId=" + encryptedSessionId);
    }
}
