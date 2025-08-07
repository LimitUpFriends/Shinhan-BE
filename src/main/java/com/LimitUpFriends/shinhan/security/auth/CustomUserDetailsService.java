package com.LimitUpFriends.shinhan.security.auth;

/**
 * Spring Security의 "인증" 과정을 담당 - 사용자 정보를 데이터베이스에서 조회하고, 조회된 사용자 정보를 Spring Security가 처리할 수 있는 형태로
 * 반환
 */


import com.LimitUpFriends.shinhan.domain.entity.MemberEntity;
import com.LimitUpFriends.shinhan.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        System.out.println("로그인 시도 : " + memberId);
        MemberEntity userData = memberRepository.findByMemberId(memberId);
        if (userData == null) {
            System.out.println("유저 정보를 찾을 수 없음 : " + memberId);
            throw new UsernameNotFoundException("User not found with memberId: " + memberId);
        }
        System.out.println("유저 정보 : " + userData);
        return new CustomUserDetails(userData);
    }
}
