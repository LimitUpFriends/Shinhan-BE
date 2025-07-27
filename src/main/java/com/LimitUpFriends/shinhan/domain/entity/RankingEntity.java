package com.LimitUpFriends.shinhan.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ranking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    private Integer rank;

    private Integer totalAssets;

    private LocalDate updatedAt;
}
