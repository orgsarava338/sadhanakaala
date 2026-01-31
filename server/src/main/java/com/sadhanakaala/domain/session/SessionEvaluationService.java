package com.sadhanakaala.domain.session;

import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sadhanakaala.persistence.entity.TimerSessionEntity;

@Service
public class SessionEvaluationService {

    private static final double COMPLETE_RATIO = 0.8;
    private static final double PARTIAL_RATIO = 0.4;

    public SessionEvaluationResult evaluateSession(TimerSessionEntity session) {

        if (session.getActualDurationSeconds() == null
                || session.getExpectedDurationSeconds() == null
                || session.getExpectedDurationSeconds() <= 0) {

            return invalid();
        }

        double ratio = (double) session.getActualDurationSeconds()
                / session.getExpectedDurationSeconds();

        if (Boolean.TRUE.equals(session.getInterrupted())) {
            return interrupted(ratio);
        }

        if (ratio >= COMPLETE_RATIO) {
            return completed(ratio);
        }

        if (ratio >= PARTIAL_RATIO) {
            return partial(ratio);
        }

        return invalid();
    }

    public List<SessionEvaluationResult> evaluateSessions(
            List<TimerSessionEntity> sessions, ZoneId userZone) {

        return sessions.stream()
                .map(this::evaluateSession)
                .toList();
    }

    private SessionEvaluationResult completed(double ratio) {
        return SessionEvaluationResult.builder()
                .outcome(SessionOutcome.COMPLETED)
                .completionRatio(ratio)
                .countsForStreak(true)
                .countsForConsistency(true)
                .build();
    }

    private SessionEvaluationResult partial(double ratio) {
        return SessionEvaluationResult.builder()
                .outcome(SessionOutcome.PARTIAL)
                .completionRatio(ratio)
                .countsForStreak(false)
                .countsForConsistency(true)
                .build();
    }

    private SessionEvaluationResult interrupted(double ratio) {
        return SessionEvaluationResult.builder()
                .outcome(SessionOutcome.INTERRUPTED)
                .completionRatio(ratio)
                .countsForStreak(false)
                .countsForConsistency(true)
                .build();
    }

    private SessionEvaluationResult invalid() {
        return SessionEvaluationResult.builder()
                .outcome(SessionOutcome.INVALID)
                .completionRatio(0)
                .countsForStreak(false)
                .countsForConsistency(false)
                .build();
    }
}
