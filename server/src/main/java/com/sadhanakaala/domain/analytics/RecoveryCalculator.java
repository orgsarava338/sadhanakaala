package com.sadhanakaala.domain.analytics;

import com.sadhanakaala.domain.session.SessionEvaluationResult;
import com.sadhanakaala.domain.session.SessionOutcome;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RecoveryCalculator {

    /**
     * Calculate average recovery days.
     * A recovery day is the gap between a missed day and the next counted session.
     *
     * @param sessionResults List of SessionEvaluationResult with session start
     *                       dates
     * @param sessionDates   List of session start dates aligned with sessionResults
     * @param userZone       User timezone for date normalization
     * @return average recovery in days (0 if no missed days)
     */
    public double averageRecoveryDays(
            List<SessionEvaluationResult> sessionResults, 
            List<LocalDate> sessionDates,
            ZoneId userZone) {

        if (sessionResults.size() != sessionDates.size()) {
            throw new IllegalArgumentException("sessionResults and sessionDates must be same size");
        }

        double totalGap = 0;
        int count = 0;

        LocalDate previousCounted = null;

        for (int i = 0; i < sessionResults.size(); i++) {
            SessionEvaluationResult result = sessionResults.get(i);
            LocalDate date = sessionDates.get(i);

            // Count only sessions that are valid for streak/consistency
            if (result.isCountsForConsistency()) {
                if (previousCounted != null) {
                    long gap = previousCounted.until(date).getDays() - 1;
                    if (gap > 0) {
                        totalGap += gap;
                        count++;
                    }
                }
                previousCounted = date;
            }
        }

        return count > 0 ? totalGap / count : 0.0;
    }

    /**
     * Helper to filter missed days from session results
     */
    public List<LocalDate> getMissedDays(
            List<SessionEvaluationResult> sessionResults,
            List<LocalDate> sessionDates) {

        return sessionResults.stream()
                .filter(r -> r.getOutcome() == SessionOutcome.INVALID)
                .map(sessionResults::indexOf)
                .map(sessionDates::get)
                .collect(Collectors.toList());
    }
}
