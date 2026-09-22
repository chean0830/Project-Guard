package com.projectguard.backend.risk;

/**
 * 주소 문자열로 소액임차인 최우선변제 지역 구분을 추정한다.
 *
 * 주의: 수도권정비계획법 시행령 별표1이 정하는 "과밀억제권역"의 실제 경계는
 * 인천·경기 내 특정 동/읍/면 단위까지 세분화되어 있어(예: 인천 강화군·옹진군은 제외 등),
 * 시/군 단위 문자열 매칭만으로는 완벽히 재현할 수 없다. 이 클래스는 실무에서 통용되는
 * 수준의 "최선 추정"이며, 정확한 지역 구분이 필요하면 국가법령정보센터 별표를 직접 대조해야 한다.
 * (docs/결정사항.md 10번 참고)
 */
public final class RegionClassifier {

    private static final String[] MID_TIER_CITIES = {"세종특별자치시", "용인시", "화성시", "김포시"};
    private static final String[] LOWER_METRO_CITIES = {"안산시", "광주시", "파주시", "이천시", "평택시"};

    public static Region classify(String address) {
        if (address == null || address.isBlank()) {
            return Region.OTHER;
        }

        if (address.contains("서울특별시")) {
            return Region.SEOUL;
        }

        for (String city : MID_TIER_CITIES) {
            if (address.contains(city)) {
                return Region.OVERCONCENTRATION_ZONE;
            }
        }

        for (String city : LOWER_METRO_CITIES) {
            if (address.contains(city)) {
                return Region.METROPOLITAN_TIER;
            }
        }

        // 인천은 대부분 과밀억제권역에 속한다 (강화군·옹진군 등 일부 예외는 반영하지 못함 — 최선 추정).
        if (address.contains("인천광역시")) {
            return Region.OVERCONCENTRATION_ZONE;
        }

        if (address.contains("광역시")) {
            return Region.METROPOLITAN_TIER;
        }

        return Region.OTHER;
    }

    private RegionClassifier() {
    }
}
