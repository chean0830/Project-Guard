package com.projectguard.backend.risk.rules;

import com.projectguard.backend.registry.OwnershipEntry;
import com.projectguard.backend.risk.RiskAssessmentInput;
import com.projectguard.backend.risk.RiskRule;
import com.projectguard.backend.risk.RiskSeverity;
import com.projectguard.backend.risk.RiskSignal;
import com.projectguard.backend.risk.RiskSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 등기부상 최종 소유자와, 사용자가 입력한 계약 상대방(임대인) 이름이 다른지 확인한다.
 * 소유자가 아닌 사람과 계약하면 무권한 임대(불법 전대) 위험이 있다.
 */
@Component
public class OwnerMismatchRule implements RiskRule {

    @Override
    public Optional<RiskSignal> evaluate(RiskAssessmentInput input) {
        String declaredLandlord = input.declaredLandlordName();
        if (declaredLandlord == null || declaredLandlord.isBlank()) {
            return Optional.empty();
        }

        List<OwnershipEntry> history = input.registry().ownershipHistory();
        if (history.isEmpty()) {
            return Optional.empty();
        }

        String currentOwner = history.get(history.size() - 1).ownerName();
        if (currentOwner == null) {
            return Optional.empty();
        }

        if (currentOwner.trim().equals(declaredLandlord.trim())) {
            return Optional.empty();
        }

        String detail = String.format(
                "등기부상 소유자는 '%s'인데, 입력하신 계약 상대방은 '%s'입니다. "
                        + "소유자 본인이 아니라면 위임장·대리 관계를 반드시 확인하세요.",
                currentOwner, declaredLandlord);

        return Optional.of(new RiskSignal(
                "OWNER_MISMATCH",
                "등기부상 소유자와 계약 상대방이 다름",
                RiskSeverity.HIGH,
                RiskSource.FACTUAL,
                "등기사항증명서 갑구 소유자 정보와 입력값 대조",
                detail
        ));
    }
}
