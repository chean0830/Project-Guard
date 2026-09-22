package com.projectguard.backend.risk;

import java.util.List;

public record RiskAssessmentResult(List<RiskSignal> signals) {

    public boolean hasHighRisk() {
        return signals.stream().anyMatch(s -> s.severity() == RiskSeverity.HIGH);
    }
}
