package com.LimitUpFriends.shinhan.websocket;

import com.LimitUpFriends.shinhan.dto.StockQuoteDto;
import com.LimitUpFriends.shinhan.service.WebSocketManager;
import com.LimitUpFriends.shinhan.util.KisAesCbc;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KoreaInvestmentWebSocketClient extends WebSocketClient {

    private final WebSocketManager manager;
    private final String approvalKey;
    private final List<String> stockList;


    private final Map<String, KeyIv> aesBySymbol = new ConcurrentHashMap<>();

    public KoreaInvestmentWebSocketClient(String url,
                                          WebSocketManager manager,
                                          String approvalKey,
                                          List<String> stockList) throws Exception {
        super(new URI(url));
        this.manager = manager;
        this.approvalKey = approvalKey;
        this.stockList = stockList;
        System.out.println(approvalKey);
        System.out.println(url);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        JsonObject header = new JsonObject();
        header.addProperty("approval_key", approvalKey);
        header.addProperty("custtype", "P");
        header.addProperty("tr_type", "1");
        header.addProperty("content-type", "utf-8");

        for (String code : stockList) {
            JsonObject input = new JsonObject();
            input.addProperty("tr_id", "H0STCNT0"); // 실시간 체결
            input.addProperty("tr_key", code);      // 종목코드

            JsonObject body = new JsonObject();
            body.add("input", input);

            JsonObject msg = new JsonObject();
            msg.add("header", header);
            msg.add("body", body);

            send(msg.toString());
        }
        System.out.println("[WS] 연결 성공 (" + stockList.size() + "개 등록)");
    }

    @Override
    public void onMessage(String s) {
        if (s == null || s.isEmpty()) return;
        System.out.println(s);
        // 틱 데이터: 0|TR_ID|TR_KEY|<BASE64 암호문>
        if (s.charAt(0) == '0') {
            String[] parts = s.split("\\|");
            if (parts.length >= 4) {
                String trKey = parts[2];
                String enc   = parts[3];

                KeyIv keyIv = aesBySymbol.get(trKey);
                if (keyIv == null) {

                    return;
                }

                try {
                    String decrypted = KisAesCbc.decryptBase64(enc, keyIv.key(), keyIv.iv());

                    StockQuoteDto dto = parseStockQuote(decrypted);
                    if (dto != null) manager.update(dto);
                } catch (Exception e) {
                    System.out.println("[WS] 복호화 실패 trKey=" + trKey + " : " + e.getMessage());
                }
            }
            return;
        }


        try {
            JsonObject json = JsonParser.parseString(s).getAsJsonObject();
            JsonObject header = json.getAsJsonObject("header");
            JsonObject body   = json.getAsJsonObject("body");


            if (header != null && "PINGPONG".equals(header.get("tr_id").getAsString())) {
                send(s);
                return;
            }

            if (body != null) {
                String trKey = body.has("tr_key") ? body.get("tr_key").getAsString() : null;
                String key   = body.has("key")    ? body.get("key").getAsString()    : null;
                String iv    = body.has("iv")     ? body.get("iv").getAsString()     : null;

                if (trKey != null && key != null && iv != null) {
                    aesBySymbol.put(trKey, new KeyIv(key, iv));

                }
            }
        } catch (Exception ignore) {

        }
    }

    private StockQuoteDto parseStockQuote(String plain) {
        try {
            String[] f = plain.split("\\^");
            String symbol = f[0];
            long currentPrice = Long.parseLong(f[2]);
            int sign = Integer.parseInt(f[3]);
            long change = Long.parseLong(f[4]);
            double changeRate = Double.parseDouble(f[5]);
            return new StockQuoteDto(symbol, currentPrice, sign, change, changeRate);
        } catch (Exception e) {
            System.out.println("[파싱오류] plain=" + plain);
            return null;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("[WS] 종료 code=" + code + ", reason=" + reason + ", remote=" + remote);
    }

    @Override public void onError(Exception e) {
        System.out.println("[WS] 오류"); e.printStackTrace();
    }


    private record KeyIv(String key, String iv) {}
}
