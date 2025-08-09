package com.LimitUpFriends.shinhan.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityDto {
    private Long text_id;         // 커뮤니티 글의 id
    private Long stock_id;        // 관련 주식의 id
    private Long member_id;       // 작성자 id
    private String body;          // 글 내용
    private LocalDate createdAt;  // 생성일

    // 필요 시 변환 메서드 (Entity → Dto)
    public static CommunityDto fromEntity(com.LimitUpFriends.shinhan.domain.entity.CommunityEntity entity) {
        return CommunityDto.builder()
                .text_id(entity.getId())
                .stock_id(entity.getStock().getId())
                .member_id(entity.getMember().getId())
                .body(entity.getBody())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
