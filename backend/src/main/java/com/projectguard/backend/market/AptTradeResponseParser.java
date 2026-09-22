package com.projectguard.backend.market;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 국토교통부 아파트매매 실거래자료 API(RTMSDataSvcAptTradeDev) 응답(JSON)을 파싱한다.
 * dealAmount는 "284,000"처럼 만원 단위·콤마 포함 문자열로 오기 때문에 원(KRW) 단위로 환산한다.
 */
@Component
public class AptTradeResponseParser {

    private final ObjectMapper objectMapper;

    public AptTradeResponseParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<AptTradeRecord> parse(String responseBody) {
        List<AptTradeRecord> records = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode items = root.path("response").path("body").path("items").path("item");
            if (!items.isArray()) {
                return records;
            }
            for (JsonNode item : items) {
                String dealAmountStr = item.path("dealAmount").asText("0").replace(",", "").trim();
                long dealAmountManwon = dealAmountStr.isEmpty() ? 0 : Long.parseLong(dealAmountStr);
                records.add(new AptTradeRecord(
                        item.path("aptNm").asText(null),
                        dealAmountManwon * 10_000L,
                        item.path("excluUseAr").asDouble(0),
                        item.path("dealYear").asInt(0),
                        item.path("dealMonth").asInt(0),
                        item.path("dealDay").asInt(0),
                        item.path("floor").asInt(0),
                        item.path("umdNm").asText(null)
                ));
            }
        } catch (Exception e) {
            return records;
        }
        return records;
    }
}
