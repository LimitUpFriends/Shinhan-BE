package com.LimitUpFriends.shinhan.service;

import com.LimitUpFriends.shinhan.dto.StockQuoteDto;
import com.LimitUpFriends.shinhan.websocket.KoreaInvestmentWebSocketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class WebSocketManager {

    // 종목별 최신 시세 저장
    private final Map<String, StockQuoteDto> stockMap = new ConcurrentHashMap<>();

    // 실행 중인 웹소켓 세션 보관
    private final List<KoreaInvestmentWebSocketClient> sessions = new CopyOnWriteArrayList<>();

    public void update(StockQuoteDto dto) {
        stockMap.put(dto.getSymbol(), dto);
    }

    public Map<String, StockQuoteDto> snapshot() {
        return stockMap;
    }

    public void registerSession(KoreaInvestmentWebSocketClient ws) {
        sessions.add(ws);
    }

    public List<KoreaInvestmentWebSocketClient> getSessions() {
        return sessions;
    }
}
