package com.LimitUpFriends.shinhan.dto.auth;


import java.io.Serializable;
import java.util.Map;

public class GoogleResponse implements OAuth2Response, Serializable {
    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {

        this.attribute = attribute;
    }

    @Override
    public String getProvider() {

        return "google";
    }

    @Override
    public String getProviderId() {

        return attribute.get("sub").toString();
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

    @Override
    public String getBirthday() {
        return null;
    }
}
