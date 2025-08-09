package com.LimitUpFriends.shinhan.repository;

import com.LimitUpFriends.shinhan.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    // 일반 로그인 사용자의 memberId로 존재 여부 확인
    boolean existsByMemberId(String memberId);

    // 일반 로그인 사용자의 memberId로 회원 조회
    MemberEntity findByMemberId(String memberId);

    // 전화번호 인증 시 이미 가입된 번호인지 확인
    boolean existsByPhoneNumber(String phoneNumber);
}
