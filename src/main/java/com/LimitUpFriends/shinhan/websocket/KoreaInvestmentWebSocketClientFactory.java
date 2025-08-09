package com.LimitUpFriends.shinhan.websocket;

import com.LimitUpFriends.shinhan.service.WebSocketManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KoreaInvestmentWebSocketClientFactory {

    @Value("${kis.ws.url}")
    private String wsUrl;

    private final WebSocketManager manager;

    public KoreaInvestmentWebSocketClient create(List<String> codes, String approvalKey) throws Exception {
        return new KoreaInvestmentWebSocketClient(wsUrl, manager, approvalKey, codes);
    }
}
