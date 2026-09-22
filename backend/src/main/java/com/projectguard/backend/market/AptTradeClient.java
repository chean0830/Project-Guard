package com.projectguard.backend.market;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * 국토교통부 아파트매매 실거래자료 API. 서비스명(RTMSDataSvcAptTradeDev)과
 * 오퍼레이션명(getRTMSDataSvcAptTradeDev)이 별도로 필요하다 — data.go.kr 상세 페이지의
 * End Point는 서비스명까지만 표시되고, 실제 호출에는 오퍼레이션 경로가 하나 더 붙는다
 * (실제 호출로 확인, docs/결정사항.md 참고).
 */
@Component
public class AptTradeClient {

    private final RestClient restClient;
    private final AptTradeResponseParser parser;
    private final String endpoint;
    private final String apiKey;

    public AptTradeClient(
            RestClient.Builder restClientBuilder,
            AptTradeResponseParser parser,
            @Value("${MOLIT_APT_TRADE_ENDPOINT:}") String endpoint,
            @Value("${DATA_GO_KR_API_KEY:}") String apiKey
    ) {
        this.restClient = restClientBuilder.build();
        this.parser = parser;
        this.endpoint = endpoint;
        this.apiKey = apiKey;
    }

    /**
     * @param lawdCd      5자리 시군구코드
     * @param dealYearMonth "yyyyMM" 형식 계약월
     */
    public List<AptTradeRecord> fetchTrades(String lawdCd, String dealYearMonth) {
        String url = endpoint + "/getRTMSDataSvcAptTradeDev";
        String body = restClient.get()
                .uri(uriBuilder -> {
                    var uri = java.net.URI.create(url);
                    return uriBuilder
                            .scheme(uri.getScheme()).host(uri.getHost()).path(uri.getPath())
                            .queryParam("serviceKey", apiKey)
                            .queryParam("LAWD_CD", lawdCd)
                            .queryParam("DEAL_YMD", dealYearMonth)
                            .queryParam("_type", "json")
                            .queryParam("numOfRows", 500)
                            .build();
                })
                .retrieve()
                .body(String.class);

        return parser.parse(body);
    }
}
