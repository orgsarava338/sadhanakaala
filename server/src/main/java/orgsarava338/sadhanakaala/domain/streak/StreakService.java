package orgsarava338.sadhanakaala.domain.streak;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import orgsarava338.sadhanakaala.domain.session.SessionEvaluationResult;
import orgsarava338.sadhanakaala.domain.session.SessionEvaluationService;
import orgsarava338.sadhanakaala.domain.session.SessionOutcome;
import orgsarava338.sadhanakaala.persistence.entity.TimerSessionEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreakService {

    private final SessionEvaluationService evaluator;

    public int calculateDailyStreak(
            List<TimerSessionEntity> sessions, ZoneId userZone) {

        Map<LocalDate, Boolean> validDays = new HashMap<>();

        for (TimerSessionEntity s : sessions) {
            LocalDate date = s.getStartedAt().atZone(userZone).toLocalDate();

            SessionEvaluationResult result = evaluator.evaluateSession(s);

            if (result.isCountsForStreak()) {
                validDays.put(date, true);
            }
        }

        int streak = 0;
        LocalDate today = LocalDate.now(userZone);

        while (validDays.getOrDefault(today.minusDays(streak), false)) {
            streak++;
        }

        return streak;
    }

    public int currentStreak(List<SessionEvaluationResult> results) {
        int streak = 0;

        // iterate from last to first
        for (int i = results.size() - 1; i >= 0; i--) {
            SessionEvaluationResult r = results.get(i);

            if (r.getOutcome() == SessionOutcome.COMPLETED) {
                streak++;
            } else {
                break; // streak broken
            }
        }

        return streak;
    }

    public int longestStreak(List<SessionEvaluationResult> results) {
        int maxStreak = 0;
        int current = 0;

        for (SessionEvaluationResult r : results) {
            if (r.getOutcome() == SessionOutcome.COMPLETED) {
                current++;
                if (current > maxStreak) {
                    maxStreak = current;
                }
            } else {
                current = 0; // reset streak
            }
        }

        return maxStreak;
    }
}
