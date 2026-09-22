package com.projectguard.backend.risk;

/**
 * 위험 신호 카드 하나에 해당하는 데이터.
 *
 * @param code             규칙 식별 코드 (프론트에서 아이콘/문구 매핑에 사용)
 * @param title            사용자에게 보여줄 제목 (쉬운 말)
 * @param severity         심각도
 * @param source           근거 유형 (사실/법률/권고기준)
 * @param sourceDescription 출처를 구체적으로 밝히는 문구 (예: "HUG 전세보증금 반환보증 심사 기준 참고")
 * @param detail           이 집에 왜 이 신호가 뜨는지 구체적인 설명
 */
public record RiskSignal(
        String code,
        String title,
        RiskSeverity severity,
        RiskSource source,
        String sourceDescription,
        String detail
) {
}
