package com.LimitUpFriends.shinhan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDTO {
    // 아이디
    private String memberId;

    // 비밀번호
    private String password;

    // 실제이름
    private String name;

    // 이메일
    private String email;

    // 닉네임
    private String nickname;

    // 전화번호
    private String phoneNumber;

    // 출생년도
    private Integer birthdayYear;

    // 생일
    private String birthday;
}

//public class JoinDto {
//    private String platform;          // Login platform
//    private String mobile;            // Phone number
//    private String name;              // User name
//    private String birthyear;         // Birth year
//    private String birthday;          // Birth day
//    private String carNumber;         // Car number
//    private String accountId;         // Account ID
//    private String accountPassword;   // Account password
//    private String email;             // Email
//}