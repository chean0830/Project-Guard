package com.projectguard.backend.risk.rules;

import com.projectguard.backend.risk.Region;
import com.projectguard.backend.risk.RegionClassifier;
import com.projectguard.backend.risk.RiskAssessmentInput;
import com.projectguard.backend.risk.RiskRule;
import com.projectguard.backend.risk.RiskSeverity;
import com.projectguard.backend.risk.RiskSignal;
import com.projectguard.backend.risk.RiskSource;
import com.projectguard.backend.risk.SmallDepositThresholds;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 보증금이 주택임대차보호법상 "소액임차인" 범위에 들어가는지, 들어간다면 경매 시
 * 다른 채권자보다 먼저 받을 수 있는 최우선변제금이 얼마인지 안내한다.
 *
 * 법적 근거가 있는 규칙이지만, 지역 판정은 주소 문자열 기반 추정치라는 한계가 있다
 * (RegionClassifier 주석 참고). 이 신호는 "위험"이 아니라 "법적 보호 범위 안내"이므로
 * 심각도는 항상 INFO로 표시한다.
 */
@Component
public class SmallDepositPriorityRepaymentRule implements RiskRule {

    @Override
    public Optional<RiskSignal> evaluate(RiskAssessmentInput input) {
        String address = input.registry().address();
        if (address == null || address.isBlank()) {
            return Optional.empty();
        }

        Region region = RegionClassifier.classify(address);
        SmallDepositThresholds.Bracket bracket = SmallDepositThresholds.forRegion(region);
        long deposit = input.depositAmount();

        String detail;
        if (deposit <= bracket.depositCap()) {
            detail = String.format(
                    "보증금 %,d원은 소액임차인 최우선변제 대상입니다. 이 집이 경매로 넘어가더라도 "
                            + "다른 채권자보다 먼저 최대 %,d원까지 돌려받을 수 있습니다 "
                            + "(단, 낙찰가의 2분의 1을 넘는 금액은 받을 수 없습니다). "
                            + "지역 판정은 주소 기반 추정치이니, 정확한 적용 여부는 전문가 확인을 권장합니다.",
                    deposit, bracket.priorityAmount());
        } else {
            detail = String.format(
                    "보증금 %,d원은 이 지역 소액임차인 기준(%,d원)을 초과해 최우선변제 대상이 아닙니다. "
                            + "경매 시 순위대로만 배당받으므로, 선순위 채권 규모를 더 꼼꼼히 확인하세요.",
                    deposit, bracket.depositCap());
        }

        return Optional.of(new RiskSignal(
                "SMALL_DEPOSIT_PRIORITY_REPAYMENT",
                "소액임차인 최우선변제 적용 여부",
                RiskSeverity.INFO,
                RiskSource.LAW,
                "주택임대차보호법 시행령 제10~11조 (지역 판정은 주소 기반 추정치)",
                detail
        ));
    }
}
