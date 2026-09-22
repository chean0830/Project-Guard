package com.projectguard.backend.risk.rules;

import com.projectguard.backend.registry.SeizureEntry;
import com.projectguard.backend.risk.RiskAssessmentInput;
import com.projectguard.backend.risk.RiskRule;
import com.projectguard.backend.risk.RiskSeverity;
import com.projectguard.backend.risk.RiskSignal;
import com.projectguard.backend.risk.RiskSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 등기부에 말소되지 않은 압류/가압류/경매개시결정/가처분이 남아있는지 확인한다.
 * 판단이 아니라 등기부에 기록된 사실을 그대로 보여주는 규칙이라 근거 신뢰도가 가장 높다.
 */
@Component
public class UnresolvedSeizureRule implements RiskRule {

    @Override
    public Optional<RiskSignal> evaluate(RiskAssessmentInput input) {
        List<SeizureEntry> unresolved = input.registry().seizures().stream()
                .filter(s -> !s.cancelled())
                .toList();

        if (unresolved.isEmpty()) {
            return Optional.empty();
        }

        String detail = String.format(
                "등기부에 말소되지 않은 압류/가압류/경매 관련 기록이 %d건 있습니다. "
                        + "이런 기록이 있으면 보증금을 돌려받지 못할 위험이 커집니다.",
                unresolved.size());

        return Optional.of(new RiskSignal(
                "UNRESOLVED_SEIZURE",
                "말소되지 않은 압류/가압류 기록",
                RiskSeverity.HIGH,
                RiskSource.FACTUAL,
                "등기사항증명서 갑구에 기록된 사실",
                detail
        ));
    }
}
