package com.LimitUpFriends.shinhan.config;

/**
 * Spring Security 설정 클래스 - 접근 권한 설정, 로그인 로그아웃 및 세션 관리, CSRF 비활성화 등
 */

import com.LimitUpFriends.shinhan.security.CustomFormLoginSuccessHandler;
import com.LimitUpFriends.shinhan.security.CustomOAuth2LoginSuccessHandler;
import com.LimitUpFriends.shinhan.security.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {



    private static final Set<String> CSRF_REQUIRED_PROFILES = Set.of("prod", "performanceTest");

    public SecurityConfig() {

    }

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
        // 접근 설정
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 활성화
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                "/"
                        ).permitAll()
                        .requestMatchers("/api/v1/", "/api/v1/auth/login/**", "/api/v1/auth/join", "/confirm",
                                "/swagger-ui/**", "/v3/api-docs/**", "api/v1/auth/check-authId",
                                "api/v1/sync/inout").permitAll()
                        .requestMatchers("/api/v1/admin").hasRole("ADMIN")                  // 해당 role만 접근 가능
                        .requestMatchers("/api/v1/my/**").hasAnyRole("ADMIN", "USER") // /api/v1/my/**만 허용
                        .anyRequest().authenticated()
                )
//                .addFilterBefore(apiKeyAuthFilter,
//                        UsernamePasswordAuthenticationFilter.class) // API Key 필터 추가

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                    "Unauthorized"); // 비인가 접근 시 401 반환
                        })
                );

        http
                .formLogin((formLogin) -> formLogin
                        .loginProcessingUrl("/api/v1/auth/login") // 로그인 처리 경로
                        .successHandler(new CustomFormLoginSuccessHandler()) // 커스텀 성공 핸들러 등록(json 반환)
                        .permitAll() // 로그인 페이지 접근 허용
                )

                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        //.defaultSuccessUrl("/api/v1/session/info", true) // 소셜 로그인 성공 후 이동 경로
                        .successHandler(new CustomOAuth2LoginSuccessHandler()) // OAuth2 성공 핸들러 등록 (json 반환을 위해)
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

        // CSRF(Cross Site Request Forgery: 사이트 간 요청 위조)
//        if (!CSRF_REQUIRED_PROFILES.contains(activeProfile)) {
//            http.csrf(auth -> auth.disable());
//        } else {
//            http.csrf(csrf -> csrf
//                    .ignoringRequestMatchers("/api/v1/auth/custom-session-login")
//                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            );
//        }
        // http.csrf(auth -> auth.disable());
        // CSRF 설정
        http.csrf(csrf -> csrf.disable());


        http
                .httpBasic((basic) -> basic.disable());


        return http.build();
    }

    /**
     * CORS 설정을 Security Filter Chain에서 사용할 수 있도록 구성
     */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of( // 프론트엔드 도메인 허용
                "http://localhost:3000",
                "https://honorsparking-web.vercel.app"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드 설정
        config.setAllowedHeaders(List.of("*")); // 모든 요청 헤더 허용
        config.setExposedHeaders(List.of("Authorization", "Set-Cookie", "X-API-KEY"));

        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * 쿠키를 크로스 사이트 요청에서도 사용할 수 있도록 SameSite=None; Secure 설정 추가
     */
//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setCookieName("SESSION"); // 세션 쿠키 이름
//        serializer.setCookiePath("/");
//        serializer.setUseSecureCookie(true); // HTTPS에서만 쿠키 전송 (http에서는 쿠키 전송이 안되므로 개발 환경에서는 false로 설정)
//        serializer.setSameSite("None"); // 크로스 사이트 요청에서 쿠키 허용
//        return serializer;
//    }
}
