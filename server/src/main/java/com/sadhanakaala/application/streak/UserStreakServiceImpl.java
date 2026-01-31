package com.sadhanakaala.application.streak;

import com.sadhanakaala.api.dto.response.Streak;
import com.sadhanakaala.domain.analytics.RecoveryCalculator;
import com.sadhanakaala.domain.session.SessionEvaluationResult;
import com.sadhanakaala.domain.session.SessionEvaluationService;
import com.sadhanakaala.domain.streak.StreakService;
import com.sadhanakaala.persistence.entity.TimerSessionEntity;
import com.sadhanakaala.persistence.repository.TimerSessionRepository;
import com.sadhanakaala.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStreakServiceImpl implements UserStreakService {

	private final TimerSessionRepository sessionRepository;
	private final UserRepository userRepository;
	private final SessionEvaluationService sessionEvaluationService;
	private final StreakService streakService;

	@Override
	public Streak getUserStreak(String userId) {

		// 1️⃣ fetch sessions and user time zone
		List<TimerSessionEntity> sessions = sessionRepository.findByOwnerIdOrderByStartedAtAsc(userId);
		String userTimeZone = userRepository.getUserTimeZoneByUserId(userId);
		ZoneId zone = userTimeZone != null ? ZoneId.of(userTimeZone) : ZoneId.systemDefault();

		// 2️⃣ evaluate each session outcome
		List<SessionEvaluationResult> results = sessionEvaluationService.evaluateSessions(sessions, zone);

		// 3️⃣ compute current and longest streak
		int currentStreak = streakService.currentStreak(results);
		int longestStreak = streakService.longestStreak(results);
		LocalDate lastSession = sessions.isEmpty() ? null
				: sessions.get(sessions.size() - 1).getStartedAt().atZone(zone).toLocalDate();

		// 4️⃣ compute recovery metrics
		List<LocalDate> sessionDates = sessions.stream()
				.map(s -> s.getStartedAt().atZone(zone).toLocalDate())
				.toList();

		double avgRecovery = RecoveryCalculator.averageRecoveryDays(results, sessionDates, zone);

		int missedDays = (int) results.stream()
				.filter(r -> r.getOutcome().name().equals("INVALID"))
				.count();

		// 5️⃣ return DTO
		return Streak.builder()
				.currentStreakDays(currentStreak)
				.longestStreakDays(longestStreak)
				.lastSessionDate(lastSession)
				.missedDays(missedDays)
				.avgRecoveryDays(avgRecovery)
				.build();
	}
}
