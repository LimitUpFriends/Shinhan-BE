package com.LimitUpFriends.shinhan.service.login;

import com.LimitUpFriends.shinhan.domain.entity.MemberEntity;
import com.LimitUpFriends.shinhan.dto.JoinRequestDTO;
import com.LimitUpFriends.shinhan.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 회원가입
     * @param request
     */
    public void joinProcess(JoinRequestDTO request) {

        boolean isUser= memberRepository.existsByMemberId(request.getMemberId());
        boolean isPhoneNumber= memberRepository.existsByPhoneNumber(request.getPhoneNumber());
        if(isUser){
            throw new IllegalArgumentException("해당 memberId는 이미 존재합니다.");
        }
        if(isPhoneNumber){
            throw new IllegalArgumentException("해당 전화번호는 이미 존재합니다.");
        }

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(request.getMemberId());
        memberEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        memberEntity.setName(request.getName());
        memberEntity.setEmail(request.getEmail());
        memberEntity.setNickname(request.getNickname());
        memberEntity.setPhoneNumber(request.getPhoneNumber());
        memberEntity.setBirthdayYear(request.getBirthdayYear());
        memberEntity.setBirthday(memberEntity.getBirthday());

        memberRepository.save(memberEntity);
    }

    /**
     * ID 중복확인
     * @param memberId
     * @return
     */
    public Boolean memberIdAvailable(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }
}
