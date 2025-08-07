package com.LimitUpFriends.shinhan.config;

/**
 * Spring Security 설정 클래스 -접근 권한 설정, 로그인 로그아웃 및 세션 관리, CSRF 비활성화 등
 */

import com.LimitUpFriends.shinhan.security.CustomFormLoginSuccessHandler;
import com.LimitUpFriends.shinhan.security.CustomOAuth2LoginSuccessHandler;
import com.LimitUpFriends.shinhan.security.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityConfig() {}

    /**
     * 비밀번호 암호화
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security 관련 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService) throws Exception {

        /**
         * 접근 설정
         */
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 활성화
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/",
                                "/**" // 모든 경로에 대해 허용해두었습니다.
                        ).permitAll()
                        .requestMatchers("/api/v1/관리자만접근해야하는").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                    "Unauthorized"); // 비인가 접근 시 401 반환
                        })
                );

        /**
         * 로그인 처리 경로 설정
         */
        http
                .formLogin((formLogin) -> formLogin
                        .loginProcessingUrl("/api/v1/auth/login") // 일반 로그인 처리 경로
                        .successHandler(new CustomFormLoginSuccessHandler()) // 일반 로그인 성공 시
                        .permitAll() // 일반 로그인 페이지 접근 허용
                )

                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        //.defaultSuccessUrl("/api/v1/session/info", true) // 소셜 로그인 성공 후 이동 경로
                        .successHandler(new CustomOAuth2LoginSuccessHandler()) // OAuth2 성공 시
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

        // CSRF 설정
        http.csrf(csrf -> csrf.disable()); // 꺼두겠습니다.

        // HTTP Basic 인증 설정
        http.httpBasic((basic) -> basic.disable()); // 꺼두겠습니다.

        return http.build();
    }

    /**
     * CORS 설정을 Security Filter Chain에서 사용할 수 있도록 구성
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        CorsConfiguration config= new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of( // 프론트엔드 도메인 허용
                "http://localhost:3000",
                "https://배포된 프론트 도메인"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP method 설정
        config.setAllowedHeaders(List.of("*")); // 요청에 포함될 수 있는 모든 헤더를 허용
        config.setExposedHeaders(List.of("Authorization", "Set-Cookie", "X-API-KEY")); // 클라이언트(브라우저)에서 응답 헤더 중 해당 헤더들을 읽을 수 있도록 허용

        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 위의 CORS 설정 적용
        return source;
    }

    /**
     * Cookie를 크로스 사이트 요청에서도 사용할 수 있도록 SameSite=None; Secure 설정 추가
     */
//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setCookieName("SESSION"); // 세션 쿠키 이름.
//        serializer.setCookiePath("/");
//        serializer.setUseSecureCookie(true); // HTTPS에서만 쿠키 전송 (http에서는 쿠키 전송이 안되므로 개발 환경에서는 false로 설정.)
//        serializer.setSameSite("None"); // 크로스 사이트 요청에서 쿠키 허용.
//        return serializer;
//    }
}
