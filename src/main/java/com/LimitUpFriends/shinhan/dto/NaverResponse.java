package com.LimitUpFriends.shinhan.dto;


import java.io.Serializable;
import java.util.Map;

public class NaverResponse implements OAuth2Response, Serializable {

    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {

        this.attribute = (Map<String, Object>)attribute.get("response");
    }

    @Override
    public String getProvider() {

        return "naver";
    }

    @Override
    public String getProviderId() {

        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {

        return attribute.get("email").toString();
    }

    @Override
    public String getName() {

        return attribute.get("name").toString();
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public Integer getBirthYear() {
        return null;
    }

    // 추가 데이터 반환
    public String getGender() {
        return attribute.get("gender") != null ? attribute.get("gender").toString() : null;
    }

    public String getMobile() {
        return attribute.get("mobile") != null ? attribute.get("mobile").toString() : null;
    }

    public String getBirthday() {
        return attribute.get("birthday") != null ? attribute.get("birthday").toString() : null;
    }

    public String getBirthyear() {
        return attribute.get("birthyear") != null ? attribute.get("birthyear").toString() : null;
    }
}
