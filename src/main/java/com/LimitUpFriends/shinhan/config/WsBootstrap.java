package com.LimitUpFriends.shinhan.config;

import com.LimitUpFriends.shinhan.service.WebSocketManager;
import com.LimitUpFriends.shinhan.websocket.KoreaInvestmentWebSocketClientFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WsBootstrap {

    private final KoreaInvestmentWebSocketClientFactory factory;
    private final WebSocketManager manager;

    // YAML 배열 → List<String>으로 주입
    private String[] keys = {
            "c2f298cf-930c-4498-aa4c-c7445f97b4d4",
            "76877eed-afe5-428b-a335-30425c698034",
            "a36716ee-0ef7-4ccd-ab18-c5e6b1469018",
            "d432bc39-c0f8-4e16-85cf-3b238f0ee444",
            "412ba42e-8d26-472d-82fc-862b5cd8fc94"
    };

    @Value("${kis.ws.url}")
    private String wsUrl;

    @Bean
    ApplicationRunner runAtBoot() {
        return args -> {
            List<String> top100 = fetchTop100Codes(); // TODO: 실제 종목코드 로직으로 교체
            int batch = 20;  // 세션당 종목 개수
            int sessionIdx = 0;

            for (int i = 0; i < top100.size(); i += batch) {
                List<String> sub = top100.subList(i, Math.min(i + batch, top100.size()));


                String key = keys[4];
                var ws = factory.create(sub, key);
                ws.connect();
                manager.registerSession(ws);

                sessionIdx++;

                try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            }
            System.out.println("[WS] 생성된 세션 수: " + sessionIdx);
        };
    }

    private List<String> fetchTop100Codes() {
        List<String> codes = new ArrayList<>();
        String[] seed = {"005930","000660","066570","035420","051910"};
        for (int i = 0; i < 100; i++) codes.add(seed[i % seed.length]);
        return codes;
    }
}
