package com.projectguard.backend.risk;

import java.util.Optional;

public interface RiskRule {
    /** 해당 없으면 Optional.empty() */
    Optional<RiskSignal> evaluate(RiskAssessmentInput input);
}
