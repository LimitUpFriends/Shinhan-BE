package com.LimitUpFriends.shinhan.dto;

import lombok.Data;


@Data
public class StockQuoteDto {
    private String symbol;       // 유가증권단축종목코드
    private long currentPrice;   // 주식현재가
    private int sign;            // 전일대비부호
    private long change;         // 전일대비
    private double changeRate;   // 전일대비율

    public StockQuoteDto(String symbol, long currentPrice, int sign, long change, double changeRate) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.sign = sign;
        this.change = change;
        this.changeRate = changeRate;
    }
}
