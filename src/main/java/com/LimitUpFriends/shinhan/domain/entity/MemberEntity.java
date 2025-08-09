package com.LimitUpFriends.shinhan.domain.entity;

import com.LimitUpFriends.shinhan.domain.enums.Access;
import com.LimitUpFriends.shinhan.domain.enums.Platform;
import com.LimitUpFriends.shinhan.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 아이디
    private String memberId;
    
    // 비밀번호
    private String password;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    // 실제이름
    private String name;

    // 이메일
    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String phoneNumber;

    private Integer birthdayYear;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private Access access;

    private Integer bankrupt;

    private LocalDate createdAt;
}
