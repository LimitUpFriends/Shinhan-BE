package com.LimitUpFriends.shinhan.domain.entity;

import com.LimitUpFriends.shinhan.domain.enums.Active;
import com.LimitUpFriends.shinhan.domain.enums.RewardType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "quest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private RewardType rewardType;

    private Integer rewardValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    @Enumerated(EnumType.STRING)
    private Active isActive;

    private LocalDate createdAt;
}
