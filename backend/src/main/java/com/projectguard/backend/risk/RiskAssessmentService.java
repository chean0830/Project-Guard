package com.projectguard.backend.risk;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskAssessmentService {

    private final List<RiskRule> rules;

    public RiskAssessmentService(List<RiskRule> rules) {
        this.rules = rules;
    }

    public RiskAssessmentResult assess(RiskAssessmentInput input) {
        List<RiskSignal> signals = rules.stream()
                .map(rule -> rule.evaluate(input))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .toList();
        return new RiskAssessmentResult(signals);
    }
}
