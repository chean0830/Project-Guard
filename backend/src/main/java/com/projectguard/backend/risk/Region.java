package com.projectguard.backend.risk;

/**
 * 주택임대차보호법 시행령 제10~11조의 소액임차인 최우선변제 지역 구분.
 * (수도권정비계획법상 과밀억제권역의 정확한 경계는 시/군/구 단위로 매우 세분화되어 있어,
 *  주소 문자열만으로는 완벽히 판별할 수 없다. RegionClassifier 주석 참고.)
 */
public enum Region {
    SEOUL,
    OVERCONCENTRATION_ZONE,   // 과밀억제권역(서울 제외)·세종·용인·화성·김포
    METROPOLITAN_TIER,        // 광역시(과밀억제권역 제외)·안산·광주(경기)·파주·이천·평택
    OTHER                     // 그 밖의 지역
}
