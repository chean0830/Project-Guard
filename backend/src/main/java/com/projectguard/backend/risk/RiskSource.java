package com.projectguard.backend.risk;

/**
 * 위험 신호의 근거 유형. 위험 신호 카드에 이 값을 함께 표시해
 * "법적으로 확정된 사실"과 "권고 수준의 참고 지표"를 사용자가 구분할 수 있게 한다.
 */
public enum RiskSource {
    /** 등기부에 기록된 객관적 사실 그대로 (판단이 아니라 사실 확인). */
    FACTUAL,
    /** 법률/시행령 등 법적 근거가 있는 기준. */
    LAW,
    /** 정부·공공기관(HUG, 국토부 등)이 권고하는 참고 기준. 법적 구속력은 없음. */
    GOVERNMENT_GUIDELINE
}
