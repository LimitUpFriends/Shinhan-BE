package com.LimitUpFriends.shinhan.service;

import com.LimitUpFriends.shinhan.domain.entity.CommunityEntity;
import com.LimitUpFriends.shinhan.domain.entity.MemberEntity;
import com.LimitUpFriends.shinhan.domain.entity.StockEntity;
import com.LimitUpFriends.shinhan.dto.CommunityDto;
import com.LimitUpFriends.shinhan.repository.CommunityRepository;
import com.LimitUpFriends.shinhan.repository.MemberRepository;
import com.sun.jdi.LongValue;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    //현재 StockRepository가 없다. 만들어야하나?
    //private final StockRepository stockRepository;

    public CommunityService(CommunityRepository communityRepository, MemberRepository memberRepository) {
        this.communityRepository = communityRepository;
        this.memberRepository = memberRepository;
    }

    /*
    글 작성
     */
    public void writing(int stock_id, CommunityDto communityDto) {
        MemberEntity member = memberRepository.findById(communityDto.getMember_id()).orElseThrow(()->new IllegalArgumentException("멤버 아이디가 없음"));
        //StockEntity stock = StockRepository.findById(stock_id);
        CommunityEntity entity = CommunityEntity.builder()
                .stock(stock)
                .member(member)
                .body(communityDto.getBody())
                .createdAt(communityDto.getCreatedAt() != null ? communityDto.getCreatedAt() : LocalDate.now())
                .build();
        communityRepository.save(entity);
    }


    /*
    글 조회
     */
    public void searching(int stock_id, CommunityDto communityDto) {
        communityRepository.findByStock_Id(stock_id);
    }


    /*
    글 삭제
     */
    public void deleting(int stock_id, CommunityDto communityDto) {
        Long text_id = communityDto.getText_id();
        CommunityEntity entity = communityRepository.findById(text_id).orElseThrow(()->new IllegalArgumentException("해당 글이 존재하지 않습니다."));

        if(entity.getStock() == null || entity.getStock().getId() != text_id)
            throw new IllegalArgumentException("해당 주식의 게시글이 아님");
        else if(entity.getMember().getId() != communityDto.getMember_id())
            throw new IllegalArgumentException("해당 사용자의 게시글이 아님");
        else
            communityRepository.delete(entity);
    }


}
