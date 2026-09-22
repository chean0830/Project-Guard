package com.projectguard.backend.risk.rules;

import com.projectguard.backend.registry.RegistryAnalysis;
import com.projectguard.backend.risk.RiskAssessmentInput;
import com.projectguard.backend.risk.RiskSeverity;
import com.projectguard.backend.risk.RiskSignal;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JeonseRatioRuleTest {

    private final JeonseRatioRule rule = new JeonseRatioRule();

    private RegistryAnalysis emptyRegistry() {
        return new RegistryAnalysis("주소", "고유번호", List.of(), List.of(), List.of(), 0L);
    }

    @Test
    void 시세정보가_없으면_평가하지_않는다() {
        RiskAssessmentInput input = new RiskAssessmentInput(emptyRegistry(), 900_000_000L, null, null);
        assertTrue(rule.evaluate(input).isEmpty());
    }

    @Test
    void 전세가율_90퍼센트_이상이면_HIGH() {
        RiskAssessmentInput input = new RiskAssessmentInput(emptyRegistry(), 950_000_000L, 1_000_000_000L, null);
        Optional<RiskSignal> result = rule.evaluate(input);
        assertTrue(result.isPresent());
        assertEquals(RiskSeverity.HIGH, result.get().severity());
    }

    @Test
    void 전세가율_80에서_90퍼센트면_CAUTION() {
        RiskAssessmentInput input = new RiskAssessmentInput(emptyRegistry(), 850_000_000L, 1_000_000_000L, null);
        Optional<RiskSignal> result = rule.evaluate(input);
        assertTrue(result.isPresent());
        assertEquals(RiskSeverity.CAUTION, result.get().severity());
    }

    @Test
    void 전세가율_80퍼센트_미만이면_신호없음() {
        RiskAssessmentInput input = new RiskAssessmentInput(emptyRegistry(), 500_000_000L, 1_000_000_000L, null);
        assertTrue(rule.evaluate(input).isEmpty());
    }
}
