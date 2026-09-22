package com.projectguard.backend.risk;

import com.projectguard.backend.registry.RegistryAnalysis;

/**
 * @param registry        등기부 파싱 결과
 * @param depositAmount   사용자가 입력한 보증금 (원)
 * @param marketPrice     실거래가 API로 조회한 시세 (원). 아직 조회 전이면 null — 시세 비교가 필요한
 *                        규칙(전세가율 등)은 이때 평가를 건너뛴다.
 * @param declaredLandlordName 사용자가 입력한 임대인 이름 (선택). 등기부상 소유자와 대조하는 데 사용.
 */
public record RiskAssessmentInput(
        RegistryAnalysis registry,
        long depositAmount,
        Long marketPrice,
        String declaredLandlordName
) {
}
