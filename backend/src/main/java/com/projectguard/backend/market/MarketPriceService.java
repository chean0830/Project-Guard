package com.projectguard.backend.market;

import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 사용자가 입력한 주소/단지명으로 최근 2개월 실거래가를 조회해 시세를 추정한다.
 * 주소를 법정동코드로 바꾸는 데 juso.go.kr 주소검색 API를, 실거래 내역 조회에
 * 국토교통부 아파트매매 실거래자료 API를 쓴다. 매칭되는 거래가 없으면 empty를 반환하고,
 * 이 경우 위험 판단 규칙 중 시세 비교가 필요한 규칙(전세가율 등)은 평가를 건너뛴다.
 */
@Service
public class MarketPriceService {

    private final JusoAddressClient jusoAddressClient;
    private final AptTradeClient aptTradeClient;

    public MarketPriceService(JusoAddressClient jusoAddressClient, AptTradeClient aptTradeClient) {
        this.jusoAddressClient = jusoAddressClient;
        this.aptTradeClient = aptTradeClient;
    }

    public Optional<Long> lookupApartmentMarketPrice(String address, String complexName, Double exclusiveAreaSqm) {
        Optional<JusoAddressResult> addressResult = jusoAddressClient.search(address);
        if (addressResult.isEmpty()) {
            return Optional.empty();
        }
        String lawdCd = addressResult.get().lawdCd();
        if (lawdCd == null) {
            return Optional.empty();
        }

        List<AptTradeRecord> records = new ArrayList<>();
        YearMonth now = YearMonth.now();
        records.addAll(aptTradeClient.fetchTrades(lawdCd, yyyyMM(now)));
        records.addAll(aptTradeClient.fetchTrades(lawdCd, yyyyMM(now.minusMonths(1))));

        String nameToMatch = (complexName != null && !complexName.isBlank())
                ? complexName
                : addressResult.get().bdNm();

        return MarketPriceMatcher.match(records, nameToMatch, exclusiveAreaSqm);
    }

    private String yyyyMM(YearMonth ym) {
        return String.format("%04d%02d", ym.getYear(), ym.getMonthValue());
    }
}
