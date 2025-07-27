package com.LimitUpFriends.shinhan.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "holding")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private StockEntity stock;

    private Integer quantity;

    private Integer averagePrice;

    private LocalDate updatedAt;
}
