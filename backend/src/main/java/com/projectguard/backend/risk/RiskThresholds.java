package com.projectguard.backend.risk;

/**
 * 위험 판단에 쓰는 수치 기준표.
 *
 * 여기 있는 수치들은 법으로 확정된 값이 아니라, 정부/공공기관이 공개적으로 권고하는
 * 참고 지표다(RiskSource.GOVERNMENT_GUIDELINE 규칙에서만 사용). 시행령처럼 법적 근거가
 * 필요한 수치(예: 소액임차인 최우선변제금)는 지역별로 다르고 개정 주기가 있어서,
 * 확인되지 않은 값을 넣느니 이번 단계에서는 규칙 자체를 넣지 않았다 (docs/결정사항.md 참고).
 *
 * TODO: 실제 서비스로 내보내기 전에 아래 값들을 최신 공식 자료로 재검증할 것.
 *   - HUG 전세보증금 반환보증 관련 공개 자료
 *   - 국토교통부/금융감독원 전세사기 예방 안내자료
 *
 * 지금은 상수로 두지만, 기획서 8번 원칙(수치는 DB로 관리)에 따라 추후 DB 기준표로 옮길 예정.
 */
public final class RiskThresholds {

    /** 전세가율(보증금/시세)이 이 값 이상이면 고위험 (HUG 기준 참고). */
    public static final double JEONSE_RATIO_HIGH = 0.90;

    /** 전세가율이 이 값 이상이면 주의. */
    public static final double JEONSE_RATIO_CAUTION = 0.80;

    /** (활성 선순위채권 + 보증금) / 시세 비율이 이 값 이상이면 고위험 (국토부/금감원 가이드 참고). */
    public static final double SENIOR_DEBT_PLUS_DEPOSIT_RATIO_HIGH = 0.80;

    /** (활성 선순위채권 + 보증금) / 시세 비율이 이 값 이상이면 주의. */
    public static final double SENIOR_DEBT_PLUS_DEPOSIT_RATIO_CAUTION = 0.70;

    private RiskThresholds() {
    }
}
