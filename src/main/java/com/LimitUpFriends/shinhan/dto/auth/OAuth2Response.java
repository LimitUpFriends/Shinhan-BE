package com.LimitUpFriends.shinhan.dto.auth;

/**
 * 소셜 로그인 응답의 공통 인터페이스
 */

public interface OAuth2Response {

    //제공자 (Ex. naver, google, ...)
    String getProvider();

    //제공자에서 발급해주는 아이디(번호)
    String getProviderId();

    //이메일
    String getEmail();

    //사용자 실명 (설정한 이름)
    String getName();

    // 추가 속성 (필요하지 않을 경우 null 반환 가능)
    String getPhoneNumber();    // 전화번호
    Integer getBirthYear();      // 출생 연도
    String getBirthday();       // 생일
}
