package com.LimitUpFriends.shinhan.security.auth;

/**
 * OAuth2User를 구현하여 로그인한 사용자의 정보를 담는 클래스. 세션에 저장될 사용자 정보 포맷
 */

import com.LimitUpFriends.shinhan.dto.auth.OAuth2Response;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, Serializable {

    private static final long serialVersionUID = 1L;
    private final OAuth2Response oAuth2Response;
    private final String role;
    private final Long id;

    public CustomOAuth2User(OAuth2Response oAuth2Response, String role, Long id) {

        this.oAuth2Response = oAuth2Response;
        this.role = role;
        this.id = id;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("getAuthorities() called! 내부 role = " + role);
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return role;
            }
        });
        return collection;
    }

    @Override
    public String getName() {

        return oAuth2Response.getName();
    }

    public String getUsername() {

        return oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
    }

    public Long getId() {
        return this.id;
    }

    public OAuth2Response getOAuth2Response() {
        return this.oAuth2Response;
    }
}