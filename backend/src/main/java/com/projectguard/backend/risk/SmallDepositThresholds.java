package com.projectguard.backend.risk;

import java.util.Map;

/**
 * 주택임대차보호법 시행령 제10조(우선변제를 받을 임차인의 범위),
 * 제11조(우선변제를 받을 보증금 중 일정액의 범위) 별표 기준.
 *
 * 출처: 국가법령정보센터 연계 "찾기쉬운 생활법령정보"
 * (https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=629&ccfNo=5&cciNo=2&cnpClsNo=2),
 * 2026-09-22 기준 조회. 2023-02-21 시행 기준과 동일한 수치가 유지되고 있음을
 * 별도의 2026년 자료(apure.kr)로 교차 확인함.
 *
 * TODO: 시행령은 개정될 수 있으므로, 실제 서비스 배포 전 국가법령정보센터에서 최신 수치로
 * 재검증할 것. 기획서 8번 원칙에 따라 추후 DB 기준표로 이전 예정.
 */
public final class SmallDepositThresholds {

    public record Bracket(long depositCap, long priorityAmount) {
    }

    private static final Map<Region, Bracket> TABLE = Map.of(
            Region.SEOUL, new Bracket(165_000_000L, 55_000_000L),
            Region.OVERCONCENTRATION_ZONE, new Bracket(145_000_000L, 48_000_000L),
            Region.METROPOLITAN_TIER, new Bracket(85_000_000L, 28_000_000L),
            Region.OTHER, new Bracket(75_000_000L, 25_000_000L)
    );

    public static Bracket forRegion(Region region) {
        return TABLE.get(region);
    }

    private SmallDepositThresholds() {
    }
}
