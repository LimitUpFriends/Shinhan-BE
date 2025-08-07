package com.LimitUpFriends.shinhan.dto.auth;

import java.io.Serializable;
import java.util.Map;

public class KakaoResponse implements OAuth2Response, Serializable {

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        // `kakao_account`에서 이메일을 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        return kakaoAccount != null && kakaoAccount.get("email") != null
                ? kakaoAccount.get("email").toString()
                : null; // 이메일 정보가 없을 경우 null 반환
    }

    @Override
    public String getName() {
        // `properties`에서 닉네임을 추출
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        return properties != null && properties.get("nickname") != null
                ? properties.get("nickname").toString()
                : null; // 닉네임 정보가 없을 경우 null 반환
    }

    // 아직 이름 정보를 가져오지 못하기에 닉네임으로 이름을 대신 넣음. 사업자 인증 완료 후 아래 주석 풀고 위에 삭제
//    @Override
//    public String getName() {
//        // `kakao_account.profile`에서 이름을 추출
//        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
//        if (kakaoAccount != null) {
//            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
//            if (profile != null && profile.get("name") != null) {
//                return profile.get("name").toString(); // `name` 필드에서 값 가져오기
//            }
//        }
//        return null; // 이름 정보가 없을 경우 null 반환
//    }


    public String getBirthday() {
        // `kakao_account`에서 생일을 추출 (MM-DD 형식)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        return kakaoAccount != null && kakaoAccount.get("birthday") != null
                ? kakaoAccount.get("birthday").toString()
                : null;
    }

    public Integer getBirthYear() {
        // `kakao_account`에서 출생년도를 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        return kakaoAccount != null && kakaoAccount.get("birthyear") != null
                ? Integer.parseInt((String) kakaoAccount.get("birthyear"))
                : null;
    }

    public String getPhoneNumber() {
        // `kakao_account`에서 전화번호를 추출
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        return kakaoAccount != null && kakaoAccount.get("phone_number") != null
                ? kakaoAccount.get("phone_number").toString()
                : null;
    }
}
