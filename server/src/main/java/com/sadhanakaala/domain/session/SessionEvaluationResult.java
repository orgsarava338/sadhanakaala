package com.sadhanakaala.domain.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SessionEvaluationResult {

    private SessionOutcome outcome;

    private double completionRatio;

    private boolean countsForStreak;

    private boolean countsForConsistency;
}
