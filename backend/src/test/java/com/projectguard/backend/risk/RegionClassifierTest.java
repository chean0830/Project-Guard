package com.projectguard.backend.risk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegionClassifierTest {

    @Test
    void 서울특별시_주소는_SEOUL() {
        assertEquals(Region.SEOUL, RegionClassifier.classify("서울특별시 강남구 테스트로 123"));
    }

    @Test
    void 용인시_주소는_과밀억제권역_구간() {
        assertEquals(Region.OVERCONCENTRATION_ZONE, RegionClassifier.classify("경기도 용인시 기흥구 123"));
    }

    @Test
    void 인천광역시_주소는_과밀억제권역_구간() {
        assertEquals(Region.OVERCONCENTRATION_ZONE, RegionClassifier.classify("인천광역시 계양구 서운동 230"));
    }

    @Test
    void 안산시_주소는_광역시급_구간() {
        assertEquals(Region.METROPOLITAN_TIER, RegionClassifier.classify("경기도 안산시 단원구 123"));
    }

    @Test
    void 부산광역시_주소는_광역시급_구간() {
        assertEquals(Region.METROPOLITAN_TIER, RegionClassifier.classify("부산광역시 해운대구 123"));
    }

    @Test
    void 그_외_지역은_OTHER() {
        assertEquals(Region.OTHER, RegionClassifier.classify("강원특별자치도 춘천시 123"));
    }
}
